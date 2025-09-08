package com.example.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to automatically generate Javadoc comments for Selenium methods.
 * It analyzes method calls and assertions, and produces descriptive comments
 * based only on By locator reference variables.
 */
public class SeleniumCommentGenerator {

    // Variables/types to ignore in comments
    private static final String[] IGNORE_VARS = {"driver", "actions", "by", "propertyreader"};

    public static void updateComments(String filePath) throws IOException {
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
            String commentText = generateComment(method);
            method.setJavadocComment(new JavadocComment(commentText));
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(cu.toString());
        }
    }

    private static String generateComment(MethodDeclaration method) {
        if (!method.getBody().isPresent()) {
            return "This method has no implementation yet.";
        }

        List<String> sentences = new ArrayList<>();

        for (MethodCallExpr call : method.findAll(MethodCallExpr.class)) {
            String methodCall = call.getNameAsString().toLowerCase();

            // -------------------
            // Handle Assertions
            // -------------------
            if (isAssertion(call)) {
                if (methodCall.contains("asserttrue")) {
                    String element = extractByLocatorFromArgs(call);
                    if (element != null) sentences.add("the " + element + " element is validated as displayed");
                    continue;
                } else if (methodCall.contains("assertfalse")) {
                    String element = extractByLocatorFromArgs(call);
                    if (element != null) sentences.add("the " + element + " element is validated as not displayed");
                    continue;
                } else if (methodCall.contains("assertequals")) {
                    String element = extractByLocatorFromArgs(call);
                    if (element != null) sentences.add("the " + element + " element value is validated as equal to the expected value");
                    else sentences.add("the values are validated as equal");
                    continue;
                } else if (methodCall.contains("assertnotequals")) {
                    String element = extractByLocatorFromArgs(call);
                    if (element != null) sentences.add("the " + element + " element value is validated as not equal to the expected value");
                    else sentences.add("the values are validated as not equal");
                    continue;
                } else if (methodCall.contains("assertnull")) {
                    sentences.add("the object is validated as null");
                    continue;
                } else if (methodCall.contains("assertnotnull")) {
                    sentences.add("the object is validated as not null");
                    continue;
                } else if (methodCall.contains("fail")) {
                    sentences.add("the test is marked as failed");
                    continue;
                }
            }

            // -------------------
            // Normal Selenium/Action calls
            // -------------------
            String elementName = extractByLocatorFromArgs(call);
            if (elementName == null) continue;

            if (methodCall.contains("click")) sentences.add("clicks on the " + elementName + " element");
            else if (methodCall.contains("clear")) sentences.add("clears the " + elementName + " element");
            else if (methodCall.contains("sendkeys") || methodCall.contains("type") || methodCall.contains("settext"))
                sentences.add("enters text into the " + elementName + " element");
            else if (methodCall.contains("select")) sentences.add("selects a value from the " + elementName + " element");
            else if (methodCall.contains("waitfor") || methodCall.contains("wait")) sentences.add("waits for the " + elementName + " element");
            else if (methodCall.contains("movetoelement")) sentences.add("moves to the " + elementName + " element");
        }

        if (sentences.isEmpty()) return "This method performs an action.";

        // Combine sentences into a single descriptive Javadoc
        StringBuilder comment = new StringBuilder("This method ");
        for (int i = 0; i < sentences.size(); i++) {
            if (i > 0) {
                if (i == sentences.size() - 1) comment.append(" and ");
                else comment.append(", ");
            }
            comment.append(sentences.get(i));
        }
        comment.append(".");

        // Capitalize first letter
        return comment.substring(0, 1).toUpperCase() + comment.substring(1);
    }

    private static boolean isAssertion(MethodCallExpr call) {
        if (!call.getScope().isPresent()) return false;
        String scope = call.getScope().get().toString().toLowerCase();
        return scope.contains("assert");
    }

    private static String extractByLocatorFromArgs(MethodCallExpr call) {
        for (Expression arg : call.getArguments()) {
            if (arg.isNameExpr()) {
                String varName = arg.asNameExpr().getNameAsString();
                if (!isIgnored(varName)) return varName;
            }
        }
        return null;
    }

    private static boolean isIgnored(String varName) {
        for (String ignore : IGNORE_VARS) {
            if (ignore.equalsIgnoreCase(varName)) return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/main/java/com/example/MySeleniumClass.java";
        updateComments(filePath);
        System.out.println("Selenium Javadoc comments updated with actions and assertions!");
    }
}