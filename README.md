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

public class SeleniumCommentGeneratorByOnly {

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

            // Only consider arguments that are By locator references (variable names, not literals)
            String elementName = null;
            for (Expression arg : call.getArguments()) {
                if (arg.isNameExpr()) {
                    String varName = arg.asNameExpr().getNameAsString();
                    if (!isIgnored(varName)) {
                        elementName = varName;
                        break; // take the first valid By locator
                    }
                }
            }

            if (elementName == null) continue; // skip if no By locator

            // Map action -> comment
            if (methodCall.contains("click")) {
                sentences.add("clicks on " + elementName);
            } else if (methodCall.contains("clear")) {
                sentences.add("clears " + elementName);
            } else if (methodCall.contains("sendkeys") || methodCall.contains("type") || methodCall.contains("settext")) {
                sentences.add("enters text into " + elementName);
            } else if (methodCall.contains("select")) {
                sentences.add("selects a value from " + elementName);
            } else if (methodCall.contains("waitfor") || methodCall.contains("wait")) {
                sentences.add("waits for " + elementName);
            } else if (methodCall.contains("movetoelement")) {
                sentences.add("moves to " + elementName);
            }
        }

        if (sentences.isEmpty()) {
            return "This method performs an action.";
        }

        // Combine sentences into one Javadoc description
        StringBuilder comment = new StringBuilder("This method ");
        for (int i = 0; i < sentences.size(); i++) {
            if (i > 0) {
                if (i == sentences.size() - 1) {
                    comment.append(" and ");
                } else {
                    comment.append(", ");
                }
            }
            comment.append(sentences.get(i));
        }
        comment.append(".");

        // Capitalize first letter
        return comment.substring(0, 1).toUpperCase() + comment.substring(1);
    }

    private static boolean isIgnored(String varName) {
        return varName.equalsIgnoreCase("driver") ||
               varName.equalsIgnoreCase("actions") ||
               varName.equalsIgnoreCase("by") ||
               varName.equalsIgnoreCase("propertyreader");
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/main/java/com/example/MySeleniumClass.java";
        updateComments(filePath);
        System.out.println("Selenium Javadoc comments updated using only By locator references!");
    }
}