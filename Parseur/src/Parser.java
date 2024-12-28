public class Parser {
    private TokenManager tm;
    private String tc;

    public Parser(TokenManager tm) {
        this.tm = tm;
        avancer();
    }

    private void avancer() {
        tc = tm.suivant();
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
            Variables();
            if (!tc.equals("#")) {
                throw new RuntimeException("Erreur: caractères restants après l'analyse");
            }
        } catch (RuntimeException e) {
            throw e;
        }
    }

    // Variables -> Variable "=" Expression | Expression
    private void Variables() {
        if (isVariable(tc)) {
            System.out.println("variable: " + tc);
            consommer(tc);
            if (tc.equals("=")) {
                consommer("=");
                Calculat();
            } else {
                throw new RuntimeException("Erreur: attendu '=', mais trouvé '" + tc + "'");
            }
        } else {
            Calculat();
        }
    }

    // Calculat -> Expression
    private void Calculat() {
        Expression();
    }

    // Expression -> Term (( "+" | "-" ) Term)*
    private void Expression() {
        Term();
        while (tc.equals("+") || tc.equals("-")) {
            String op = tc;
            consommer(op);
            Term();
        }
    }

    // Term -> Factor (( "*" | "/" ) Factor)*
    private void Term() {
        Factor();
        while (tc.equals("*") || tc.equals("/")) {
            String op = tc;
            consommer(op);
            Factor();
        }
    }

    // Factor -> Base ("^" Factor)?
    private void Factor() {
        Base();
        if (tc.equals("^")) {
            consommer("^");
            Factor();
        }
    }

    // Base -> Number | "(" Expression ")" | Variable
    private void Base() {
        if (tc.equals("(")) {
            consommer("(");
            Expression();
            consommer(")");
        } else if (isNumber(tc)) {
            Number();
        } else if (isVariable(tc)) { // Handle variables
            consommer(tc);
        } else {
            throw new RuntimeException("Erreur: attendu un nombre ou '(', mais trouvé '" + tc + "'");
        }
    }

    // Number -> [0-9]+
    private void Number() {
        if (isNumber(tc)) {
            consommer(tc);
            while (isNumber(tc)) {
                consommer(tc);
            }
        } else {
            throw new RuntimeException("Erreur: attendu un nombre, mais trouvé '" + tc + "'");
        }
    }

    // Helper methods to check token types
    private boolean isNumber(String token) {
        return token.matches("[0-9]+");
    }

    private boolean isVariable(String token) {
        return token.matches("[A-Za-z_][A-Za-z0-9_]*");
    }
}
