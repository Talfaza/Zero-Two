import java.util.HashMap;
import java.util.Map;

public class Main {
    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    public static void main(String[] args) {
        Map<String, Double> variables = new HashMap<>();

        String[] testCases = {
                "X := 60;",
                "y := X / 2 + 2;", // Changed to include required ';'
                "z := 1 + 1;",     // Changed "print" to a simple assignment
                "print(1);",
                "print(X);",       // Ensure variable name matches exactly
                "1;"               // Expression-only input
        };

        for (String testCase : testCases) {
            System.out.println("Testing: " + testCase);
            TokenManager tm = new TokenManager(testCase);
            Parser parser = new Parser(tm, variables);

            try {
                parser.parse();
                System.out.println(GREEN + "Result: Success" + RESET + "\n");
            } catch (RuntimeException e) {
                System.out.println(RED + "Result: Error - " + e.getMessage() + RESET + "\n");
            }
        }

        String cmd = "print(z + y);";
        System.out.println("Testing: " + cmd);
        TokenManager tm = new TokenManager(cmd);
        Parser parser = new Parser(tm, variables);

        try {
            parser.parse();
            System.out.println(GREEN + "Result: Success" + RESET);
        } catch (RuntimeException e) {
            System.out.println(RED + "Result: Error - " + e.getMessage() + RESET);
        }
    }
}
