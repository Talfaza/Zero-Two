import java.util.HashMap;
import java.util.Map;

public class Main {
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
                System.out.println("Result: Success\n");
            } catch (RuntimeException e) {
                System.out.println("Result: Error - " + e.getMessage() + "\n");
            }
        }

        String cmd = "print(z + y);";
        System.out.println("Testing: " + cmd);
        TokenManager tm = new TokenManager(cmd);
        Parser parser = new Parser(tm, variables);

        try {
            parser.parse();
        } catch (RuntimeException e) {
            System.out.println("Result: Error - " + e.getMessage());
        }
    }
}
