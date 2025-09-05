public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/java/com/example/MyClass.java";

        try {
            ElementCommentUpdater.updateComments(filePath);
            System.out.println("Javadoc comments updated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}