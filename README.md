import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class SeleniumCommentGenerator {

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
        Set<String> actions = new LinkedHashSet<>();
        String elementName = extractElementName(method.getNameAsString());

        if (!method.getBody().isPresent()) {
            return "This method has no implementation yet.";
        }

        for (MethodCallExpr call : method.findAll(MethodCallExpr.class)) {
            String methodCall = call.getNameAsString().toLowerCase();

            // Special case: driver.get(url)
            if (methodCall.equals("get") && call.getScope().isPresent() &&
                    call.getScope().get().toString().equals("driver")) {
                actions.add("navigates to the specified URL");
                continue;
            }

            if (methodCall.contains("click")) {
                actions.add("clicks on");
            } else if (methodCall.contains("clear")) {
                actions.add("clears");
            } else if (methodCall.contains("sendkeys") || methodCall.contains("type") || methodCall.contains("settext")) {
                actions.add("enters text into");
            } else if (methodCall.contains("select")) {
                actions.add("selects a value from");
            } else if (methodCall.contains("wait")) {
                actions.add("waits for");
            } else if (methodCall.contains("movetoelement")) {
                actions.add("moves to");
            } else if (methodCall.contains("doubleclick")) {
                actions.add("double-clicks on");
            } else if (methodCall.contains("contextclick")) {
                actions.add("right-clicks on");
            }
        }

        // Fallback: if no actions found in body, use method name
        if (actions.isEmpty()) {
            if (method.getNameAsString().toLowerCase().contains("click")) {
                actions.add("clicks on");
            } else if (method.getNameAsString().toLowerCase().contains("enter") ||
                    method.getNameAsString().toLowerCase().contains("type")) {
                actions.add("enters text into");
            } else if (method.getNameAsString().toLowerCase().contains("select")) {
                actions.add("selects a value from");
            } else if (method.getNameAsString().toLowerCase().contains("check") ||
                    method.getNameAsString().toLowerCase().contains("validate")) {
                actions.add("validates");
            } else {
                actions.add("performs an action on");
            }
        }

        // Join multiple actions
        StringBuilder actionText = new StringBuilder();
        int i = 0;
        for (String action : actions) {
            if (i > 0) {
                if (i == actions.size() - 1) {
                    actionText.append(" and ");
                } else {
                    actionText.append(", ");
                }
            }
            actionText.append(action);
            i++;
        }

        return "This method " + actionText +
                (actionText.toString().contains("URL") ? "." : " the " + elementName + ".");
    }

    private static String extractElementName(String methodName) {
        String[] words = methodName.replaceAll("([a-z])([A-Z])", "$1 $2")
                .toLowerCase().split("\\s+");
        Set<String> actionWords = Set.of(
                "click", "clear", "enter", "type", "sendkeys", "select",
                "wait", "get", "set", "move", "double", "context", "check", "validate"
        );
        Set<String> ignoredWords = Set.of("on", "the", "and", "into", "from", "for", "element", "field");

        StringBuilder element = new StringBuilder();
        for (String word : words) {
            if (!actionWords.contains(word) && !ignoredWords.contains(word)) {
                if (element.length() > 0) element.append(" ");
                element.append(word);
            }
        }
        return element.toString().trim();
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/main/java/com/example/MySeleniumClass.java";
        updateComments(filePath);
        System.out.println("Selenium Javadoc comments updated using method name and body!");
    }
}