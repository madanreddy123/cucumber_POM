
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
 * Analyzes method calls and assertions, producing descriptive comments based on By locator reference variables.
 */
public class SeleniumCommentGenerator {

    private static final String[] IGNORE_VARS = {"driver", "actions", "by", "propertyreader"};

    /**
     * Updates Javadoc comments for methods in the specified Java file.
     *
     * @param filePath Path to the Java file to process
     * @throws IOException If an I/O error occurs while reading or writing the file
     */
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

    /**
     * Generates a Javadoc comment for a given method based on its Selenium method calls and assertions.
     */
    private static String generateComment(MethodDeclaration method) {
        if (!method.getBody().isPresent()) {
            return "This method has no implementation yet.";
        }

        List<String> sentences = new ArrayList<>();

        for (MethodCallExpr call : method.findAll(MethodCallExpr.class)) {
            String methodCall = call.getNameAsString().toLowerCase();

            if (isAssertion(call)) {
                sentences.add(generateAssertionComment(methodCall, call));
                continue;
            }

            String elementName = extractByLocatorFromArgs(call);
            if (elementName != null) {
                sentences.add(generateActionComment(methodCall, elementName));
            }
        }

        return formatComment(sentences);
    }

    /**
     * Generates a comment for an assertion method call.
     */
    private static String generateAssertionComment(String methodCall, MethodCallExpr call) {
        String element = extractByLocatorFromArgs(call);

        if (methodCall.contains("asserttrue")) {
            return element != null ? "the " + element + " element is validated as displayed" : "a condition is validated as true";
        } else if (methodCall.contains("assertfalse")) {
            return element != null ? "the " + element + " element is validated as not displayed" : "a condition is validated as false";
        } else if (methodCall.contains("assertequals")) {
            return element != null ? "the " + element + " element value is validated as equal to the expected value" : "the values are validated as equal";
        } else if (methodCall.contains("assertnotequals")) {
            return element != null ? "the " + element + " element value is validated as not equal to the expected value" : "the values are validated as not equal";
        } else if (methodCall.contains("assertnull")) {
            return "the object is validated as null";
        } else if (methodCall.contains("assertnotnull")) {
            return "the object is validated as not null";
        } else if (methodCall.contains("fail")) {
            return "the test is marked as failed";
        }

        return "";
    }

    /**
     * Generates a comment for a Selenium action method call.
     */
    private static String generateActionComment(String methodCall, String elementName) {
        if (methodCall.contains("click")) {
            return "clicks on the " + elementName + " element";
        } else if (methodCall.contains("clear")) {
            return "clears the " + elementName + " element";
        } else if (methodCall.contains("sendkeys") || methodCall.contains("type") || methodCall.contains("settext")) {
            return "enters text into the " + elementName + " element";
        } else if (methodCall.contains("select")) {
            return "selects a value from the " + elementName + " element";
        } else if (methodCall.contains("waitfor") || methodCall.contains("wait")) {
            return "waits for the " + elementName + " element";
        } else if (methodCall.contains("movetoelement")) {
            return "moves to the " + elementName + " element";
        }
        return "";
    }

    /**
     * Formats a list of sentences into a single Javadoc comment.
     */
    private static String formatComment(List<String> sentences) {
        if (sentences.isEmpty()) {
            return "This method performs an action.";
        }

        StringBuilder comment = new StringBuilder("This method ");
        for (int i = 0; i < sentences.size(); i++) {
            if (i > 0) {
                comment.append(i == sentences.size() - 1 ? " and " : ", ");
            }
            comment.append(sentences.get(i));
        }
        comment.append(".");

        return comment.substring(0, 1).toUpperCase() + comment.substring(1);
    }

    private static boolean isAssertion(MethodCallExpr call) {
        return call.getScope().isPresent() && call.getScope().get().toString().toLowerCase().contains("assert");
    }

    private static String extractByLocatorFromArgs(MethodCallExpr call) {
        for (Expression arg : call.getArguments()) {
            if (arg.isNameExpr()) {
                String varName = arg.asNameExpr().getNameAsString();
                if (!isIgnored(varName)) {
                    return varName;
                }
            }
        }
        return null;
    }

    private static boolean isIgnored(String varName) {
        for (String ignore : IGNORE_VARS) {
            if (ignore.equalsIgnoreCase(varName)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/main/java/com/example/MySeleniumClass.java";
        updateComments(filePath);
        System.out.println("Selenium Javadoc comments updated with actions and assertions!");
    }
}