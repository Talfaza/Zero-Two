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
                "6 * 10 + ((17 + 7) * 12) ",
                "((5681 + 876 * 7) + (777^3 - 76 + 76)) / (5681 + 876 * 7)",
                "() + 3",
                "d = (28802* 837782398 - 83889) / 12463 + 78873290-67373 + 2^(5363)"
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
