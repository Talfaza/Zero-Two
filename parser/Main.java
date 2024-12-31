import java.util.HashMap;
import java.util.Map;
/****
 *
 *  TODO: Add ";" support
 *  TODO: Add a JSON AST Generation
 *
 *
 *
 * ****/
public class Main {
    public static void main(String[] args) {
        Map<String, Double> variables = new HashMap<>();

        String[] testCases = {
                "X := 60",
                "y := X/2 + 2",
                "z := print(1+1)",
                "print(1)"
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

        String cmd = "print(z + y)";
        TokenManager tm = new TokenManager(cmd);
        Parser parser = new Parser(tm, variables);
        parser.Print();
    }
}
