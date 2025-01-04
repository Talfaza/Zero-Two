import java.util.Map;

public class Parser {
    private TokenManager tm;
    private String tc;
    private Map<String, Double> variables;

    public Parser(TokenManager tm, Map<String, Double> variables) {
        this.tm = tm;
        this.variables = variables;
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
            if (tc.equals("print")) {
                Print();
            } else {
                Variables();
            }
            if (!tc.equals("#")) {
                throw new RuntimeException("Erreur: caractères restants après l'analyse");
            }
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private void Variables() {
        if (tc.equals("print")) {
            Print();
        } else if (isVariable(tc)) {
            String varName = tc;
            System.out.println("variable: " + varName);
            consommer(tc);
            if (tc.equals(":=")) {
                consommer(":=");
                double value = Calculat();
                variables.put(varName, value);
                consommer(";");
            } else {
                throw new RuntimeException("Erreur: attendu ':=', mais trouvé '" + tc + "'");
            }
        } else {
            throw new RuntimeException("Erreur: attendu une commande ou une expression");
        }
    }

    public void Print() {
        consommer("print");
        consommer("(");
        double result = Calculat();
        consommer(")");
        System.out.println("Result: " + result);
        consommer(";");
    }

    private double Calculat() {
        return Expression();
    }

    private double Expression() {
        double result = Term();
        while (tc.equals("+") || tc.equals("-")) {
            String op = tc;
            consommer(op);
            double value = Term();
            if (op.equals("+")) {
                result += value;
            } else if (op.equals("-")) {
                result -= value;
            }
        }
        return result;
    }

    private double Term() {
        double result = Factor();
        while (tc.equals("*") || tc.equals("/")) {
            String op = tc;
            consommer(op);
            double value = Factor();
            if (op.equals("*")) {
                result *= value;
            } else if (op.equals("/")) {
                result /= value;
            }
        }
        return result;
    }

    private double Factor() {
        double result = Base();
        if (tc.equals("^")) {
            consommer("^");
            result = Math.pow(result, Factor());
        }
        return result;
    }

    private double Base() {
        if (tc.equals("(")) {
            consommer("(");
            double result = Expression();
            consommer(")");
            return result;
        } else if (isNumber(tc)) {
            return Number();
        } else if (isVariable(tc)) {
            String varName = tc;
            consommer(tc);
            if (!variables.containsKey(varName)) {
                return 0.0;
            }
            return variables.get(varName);
        } else {
            throw new RuntimeException("Erreur: attendu un nombre, '(', ou 'print', mais trouvé '" + tc + "'");
        }
    }

    private double Number() {
        if (isNumber(tc)) {
            String num = tc;
            consommer(tc);
            return Double.parseDouble(num);
        } else {
            throw new RuntimeException("Erreur: attendu un nombre, mais trouvé '" + tc + "'");
        }
    }

    private boolean isNumber(String token) {
        return token.matches("[0-9]+(\\.[0-9]+)?");
    }

    private boolean isVariable(String token) {
        return token.matches("[A-Za-z_][A-Za-z0-9_]*");
    }
}
