import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.Type;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class SeleniumCommentGeneratorFilteredIgnoreList {

    // List of variables/types to ignore in comments
    private static final Set<String> IGNORE_VARIABLES = Set.of(
            "driver", "actions", "By", "propertyReader"
    );

    // Track By locator variable names
    private static final Map<String, String> LOCATOR_MAP = new HashMap<>();

    public static void updateComments(String filePath) throws IOException {
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Collect all By locators first
        collectLocators(cu);

        for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
            String commentText = generateComment(method);
            method.setJavadocComment(new JavadocComment(commentText));
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(cu.toString());
        }
    }

    // Collect all By locator variable declarations
    private static void collectLocators(CompilationUnit cu) {
        for (VariableDeclarator var : cu.findAll(VariableDeclarator.class)) {
            Type type = var.getType();
            if (type.isClassOrInterfaceType() &&
                type.asClassOrInterfaceType().getNameAsString().equals("By")) {
                LOCATOR_MAP.put(var.getNameAsString(), ""); // track locator variable name only
            }
        }
    }

    private static String generateComment(MethodDeclaration method) {
        Set<String> actions = new LinkedHashSet<>();
        Set<String> elementNames = new LinkedHashSet<>();

        if (!method.getBody().isPresent()) {
            return "This method has no implementation yet.";
        }

        for (MethodCallExpr call : method.findAll(MethodCallExpr.class)) {
            String methodCall = call.getNameAsString().toLowerCase();

            // Determine action based on method name
            if (methodCall.contains("click")) actions.add("clicks on");
            else if (methodCall.contains("clear")) actions.add("clears");
            else if (methodCall.contains("sendkeys") || methodCall.contains("type") || methodCall.contains("settext"))
                actions.add("enters text into");
            else if (methodCall.contains("select")) actions.add("selects a value from");
            else if (methodCall.contains("waitfor") || methodCall.contains("wait")) actions.add("waits for");
            else if (methodCall.contains("movetoelement")) actions.add("moves to");

            // Collect only variable references that are not in the ignore list
            for (Expression arg : call.getArguments()) {
                if (arg.isNameExpr()) {
                    String varName = arg.asNameExpr().getNameAsString();
                    if (!IGNORE_VARIABLES.contains(varName)) {
                        if (LOCATOR_MAP.containsKey(varName)) {
                            elementNames.add(varName); // ✅ only variable name
                        } else {
                            elementNames.add(varName);
                        }
                    }
                }
            }

            // Also include the scope if it is a variable and not in ignore list
            if (call.getScope().isPresent() && call.getScope().get() instanceof NameExpr) {
                String varName = ((NameExpr) call.getScope().get()).getNameAsString();
                if (!IGNORE_VARIABLES.contains(varName)) {
                    if (LOCATOR_MAP.containsKey(varName)) {
                        elementNames.add(varName); // ✅ only variable name
                    } else {
                        elementNames.add(varName);
                    }
                }
            }
        }

        // Fallback to method name if no element detected
        if (elementNames.isEmpty()) {
            elementNames.add(extractElementName(method.getNameAsString()));
        }

        // Fallback on action from method name if empty
        if (actions.isEmpty()) {
            String lowerName = method.getNameAsString().toLowerCase();
            if (lowerName.contains("click")) actions.add("clicks on");
            else if (lowerName.contains("enter") || lowerName.contains("type")) actions.add("enters text into");
            else if (lowerName.contains("select")) actions.add("selects a value from");
            else if (lowerName.contains("check") || lowerName.contains("validate")) actions.add("validates");
            else actions.add("performs an action on");
        }

        // Build grammatically correct sentence
        String[] actionArray = actions.toArray(new String[0]);
        StringBuilder actionText = new StringBuilder();
        for (int i = 0; i < actionArray.length; i++) {
            if (i > 0) {
                if (i == actionArray.length - 1) actionText.append(" and ");
                else actionText.append(", ");
            }
            actionText.append(actionArray[i]);
        }

        String elementText = String.join(", ", elementNames);

        String sentence;
        if (actionText.toString().contains("into") || actionText.toString().contains("on")) {
            sentence = "This method " + actionText.toString() + " " + elementText + ".";
        } else {
            sentence = "This method " + actionText.toString() + " the " + elementText + ".";
        }

        // Capitalize first letter
        sentence = sentence.substring(0, 1).toUpperCase() + sentence.substring(1);

        return sentence;
    }

    private static String extractElementName(String methodName) {
        String[] words = methodName.replaceAll("([a-z])([A-Z])", "$1 $2")
                .toLowerCase().split("\\s+");
        Set<String> actionWords = Set.of(
                "click", "clear", "enter", "type", "sendkeys", "select",
                "wait", "get", "set", "move", "double", "context", "check", "validate", "login"
        );
        Set<String> ignoredWords = Set.of("on", "the", "and", "into", "from", "for", "element", "field", "with");

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
        System.out.println("Selenium Javadoc comments updated using filtered element references!");
    }
}