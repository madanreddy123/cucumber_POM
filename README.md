package com.example.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ElementCommentUpdater {

    private static final Set<String> ACTION_WORDS = new HashSet<>(Arrays.asList(
            "validate", "verify", "check", "click", "clear", "enter", "type", "sendkeys",
            "select", "wait", "find", "is", "get", "set"
    ));

    private static final Set<String> IGNORED_WORDS = new HashSet<>(Arrays.asList(
            "on", "in", "into", "from", "for", "the", "named", "and", "then", "of", "with"
    ));

    public static void updateComments(String filePath) throws IOException {
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
            String commentText = generateElementSpecificComment(method.getNameAsString());
            method.setJavadocComment(new JavadocComment(commentText));
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(cu.toString());
        }
    }

    private static String generateElementSpecificComment(String methodName) {
        String lowerName = methodName.toLowerCase();
        String elementName = extractElementName(methodName);

        if (lowerName.contains("validate") || lowerName.contains("verify") || lowerName.contains("check")) {
            return "This method validates the " + elementName + ".";
        } else if (lowerName.contains("click")) {
            return "This method clicks on the " + elementName + ".";
        } else if (lowerName.contains("clear") && lowerName.contains("enter")) {
            return "This method clears and enters text into the " + elementName + ".";
        } else if (lowerName.contains("enter") || lowerName.contains("type") || lowerName.contains("sendkeys")) {
            return "This method enters text into the " + elementName + ".";
        } else if (lowerName.contains("select")) {
            return "This method selects a value from the " + elementName + ".";
        } else if (lowerName.contains("wait")) {
            return "This method waits for the " + elementName + ".";
        } else if (lowerName.contains("find")) {
            return "This method finds the " + elementName + ".";
        } else if (lowerName.startsWith("is")) {
            return "This method checks if the " + elementName + " is present or visible.";
        } else if (lowerName.startsWith("get")) {
            return "This method retrieves the value or text from the " + elementName + ".";
        } else if (lowerName.startsWith("set")) {
            return "This method sets a value in the " + elementName + ".";
        } else {
            return "This method performs an action on the " + elementName + ".";
        }
    }

    private static String extractElementName(String methodName) {
        String[] words = methodName.replaceAll("([a-z])([A-Z])", "$1 $2").toLowerCase().split("\\s+");

        int startIndex = 0;

        if (words.length > 0 && ACTION_WORDS.contains(words[0])) {
            startIndex = 1;
        }

        if (words.length > 2 &&
                ACTION_WORDS.contains(words[0]) &&
                words[1].equals("and") &&
                ACTION_WORDS.contains(words[2])) {
            startIndex = 3;
        }

        StringBuilder elementName = new StringBuilder();
        for (int i = startIndex; i < words.length; i++) {
            if (!IGNORED_WORDS.contains(words[i])) {
                if (elementName.length() > 0) {
                    elementName.append(" ");
                }
                elementName.append(words[i]);
            }
        }

        return elementName.toString().trim();
    }
}