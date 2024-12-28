public class Main {
    public static void main(String[] args) {
        String[] testCases = {
                "35+5",
                "(2+3)*4",
                "47^3+2",
                "(1+2)*(3-4)",
                "7+8*2#",
                "(5+3)",
                "45 + (777^3 - 76 + 76) / (5681 + 876 * 7)",
                "6 * 10 + () "
        };

        for (String testCase : testCases) {
            System.out.println("Testing: " + testCase);
            TokenManager tm = new TokenManager(testCase);
            Parser parser = new Parser(tm);

            try {
                parser.parse();
                System.out.println("Result: Success\n");
            } catch (RuntimeException e) {
                System.out.println("Result: Error - " + e.getMessage() + "\n");
            }
        }
    }
}
