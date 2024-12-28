public class Parser {
    private TokenManager tm;
    private String tc;

    public Parser(TokenManager tm) {
        this.tm = tm;
        avancer();
    }

    private void avancer() {
        tc = tm.suivant();  // Move to the next token
    }

    private void consommer(String attendu) {
        if (tc.equals(attendu)) {
            avancer();
        } else {
            throw new RuntimeException("Erreur: attendu '" + attendu + "' mais trouvé '" + tc + "'");
        }
    }

    public void parse() {
        try {
            Calculat();  // Start parsing the calculation
            if (!tc.equals("#")) {  // Check for leftover tokens after parsing
                throw new RuntimeException("Erreur: caractères restants après l'analyse");
            }
        } catch (RuntimeException e) {
            throw e;  // Propagate the error to the main method
        }
    }
    // (Expression) | (Expression symbol Expretion) +
    // Expression -> Number | "(" Expression ")"
    private void Expression() {
        if (tc.equals("(")) {
            consommer("(");
            Expression();
            Symbol();
            Expression();
            while (isSymbol(tc)){
                Symbol();
                Expression();
            }
            if (tc.equals(")") ){
                consommer(")");
                 while (isSymbol(tc)){
                     Symbol();
                     Expression();
                 }
            }
        } else {
            Number();
        }
    }

    // Calculat -> Expression (Symbol Expression)*
    private void Calculat() {
        Expression();
        Symbol();
        Expression();

        while (isSymbol(tc)){
            Symbol();
            Expression();
        }
    }

    // Handle the symbol (operator) parsing
    private void Symbol() {
        if (isSymbol(tc)){
            consommer(tc);
        }
        
    }

    // Number -> [0-9]+
    private void Number() {
        if (isNumber(tc)){
            consommer(tc);
            while (isNumber(tc)){
                consommer(tc);
            }
        }
    }

    private boolean isNumber(String token) {
        return token.matches("[0-9]+");
    }

    private boolean isSymbol(String token) {
        return token.matches("[\\+\\-\\*/\\^]");  // Match one of the operators
    }
}
