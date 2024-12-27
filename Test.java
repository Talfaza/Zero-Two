public class Test {
    public static void main(String[] args) {
        try {
            // Create the parser
            CursorParser parser = new CursorParser(new java.io.FileInputStream("test.txt"));
            // Call your start rule (replace "start" with your actual start rule name)
            parser.start();
            System.out.println("Parsing completed successfully!");
        } catch (Exception e) {
            System.out.println("Parsing failed:");
            System.out.println(e.getMessage());
        }
    }
} 