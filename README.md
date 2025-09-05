package com.example.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class to automatically generate and update
 * element-specific Javadoc comments for Java methods.
 * 
 * Handles descriptive Selenium Java method names like
 * clearAndEnterTheUsernameIntoTheFieldUsername, clickOnTheButtonNamedSearch, etc.
 */
public class ElementCommentUpdater {

    /**
     * Updates Javadoc comments for all methods in the given Java file.
     * Existing code is preserved; only comments are updated or added.
     *
     * @param filePath Path to the Java source file
     * @throws IOException if file reading or writing fails
     */
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

    /**
     * Generates element-specific comment based on method name patterns
     */
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
        } else if (lowerName.contains("is")) {
            return "This method checks if the " + elementName + " is present or visible.";
        } else if (lowerName.contains("get")) {
            return "This method retrieves the value or text from the " + elementName + ".";
        } else if (lowerName.contains("set")) {
            return "This method sets a value in the " + elementName + ".";
        } else {
            return "This method performs an action on the " + elementName + ".";
        }
    }

    /**
     * Extracts a readable element name from descriptive camel-case method names
     * like clearAndEnterTheUsernameIntoTheFieldUsername or clickOnTheButtonNamedSearch.
     */
    private static String extractElementName(String methodName) {
        String lowerName = methodName.toLowerCase();
        String elementName = methodName;

        // Define common action keywords and prepositions
        String[] actions = {"validate", "verify", "check", "click", "clear", "enter", "type", "sendkeys", 
                          "select", "wait", "find", "is", "get", "set", "and", "then"};
        String[] prepositions = {"on", "into", "the", "named", "if"};

        // Remove initial action prefixes
        for (String action : actions) {
            if (lowerName.contains(action) && (lowerName.indexOf(action) == 0 || 
                    lowerName.charAt(lowerName.indexOf(action) - 1) == ' ')) {
                elementName = elementName.substring(elementName.indexOf(action) + action.length());
                break;
            }
        }

        // Handle compound actions like "clear and enter" or "clear then enter"
        if (lowerName.contains("clear") && (lowerName.contains("and") || lowerName.contains("then")) &&
            lowerName.contains("enter")) {
            int andIndex = lowerName.indexOf("and");
            int thenIndex = lowerName.indexOf("then");
            int splitIndex = (andIndex > 0 ? andIndex : thenIndex) + (andIndex > 0 ? 3 : 4);
            elementName = elementName.substring(splitIndex);
        }

        // Clean up by removing prepositions and trailing conditions
        String[] words = elementName.replaceAll("([a-z])([A-Z])", "$1 $2").toLowerCase().split("\\s+");
        StringBuilder cleanedName = new StringBuilder();
        boolean skipNext = false;

        for (int i = 0; i < words.length; i++) {
            if (isPrepositionOrCondition(words[i], prepositions)) {
                skipNext = (words[i].equals("if") || words[i].equals("named")); // Skip next word for "if" or "named"
                continue;
            }
            if (skipNext) {
                skipNext = false;
                continue;
            }
            if (!cleanedName.isEmpty()) cleanedName.append(" ");
            cleanedName.append(words[i]);
        }

        elementName = cleanedName.toString().trim();
        return elementName.isEmpty() ? "element" : (elementName.endsWith("s") ? elementName : elementName);
    }

    /**
     * Helper method to check if a word is a preposition or conditional term
     */
    private static boolean isPrepositionOrCondition(String word, String[] prepositions) {
        word = word.toLowerCase();
        for (String prep : prepositions) {
            if (word.equals(prep)) return true;
        }
        return false;
    }
}