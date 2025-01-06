import java.util.Map;

public class Parser {
    private TokenManager tm;
    private String tc;
    private Map<String, Double> variables;
    private ASTNode root; // Root node for the AST

    public Parser(TokenManager tm, Map<String, Double> variables) {
        this.tm = tm;
        this.variables = variables;
        avancer();
    }

public ASTNode parse() {
    root = new ASTNode("Program");
    while (!tc.equals("#")) {
        if (tc.equals("print")) {
            root.addChild(Print());
        } else {
            root.addChild(Variables());
        }
    }
    return root;
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

    private ASTNode Variables() {
        if (isVariable(tc)) {
            String varName = tc;
            consommer(tc);
            consommer(":=");
            ASTNode assignmentNode = new ASTNode("Assignment");
            assignmentNode.name = varName;
            assignmentNode.addChild(Calculat());
            consommer(";");
            return assignmentNode;
        } else {
            throw new RuntimeException("Erreur: attendu une commande ou une expression");
        }
    }

    private ASTNode Print() {
        consommer("print");
        consommer("(");
        ASTNode printNode = new ASTNode("Print");
        printNode.addChild(Calculat());
        consommer(")");
        consommer(";");
        return printNode;
    }

    private ASTNode Calculat() {
        return Expression();
    }

    private ASTNode Expression() {
        ASTNode left = Term();
        while (tc.equals("+") || tc.equals("-")) {
            String op = tc;
            consommer(op);
            ASTNode expressionNode = new ASTNode("Expression");
            expressionNode.operator = op;
            expressionNode.addChild(left);
            expressionNode.addChild(Term());
            left = expressionNode;
        }
        return left;
    }

    private ASTNode Term() {
        ASTNode left = Factor();
        while (tc.equals("*") || tc.equals("/")) {
            String op = tc;
            consommer(op);
            ASTNode termNode = new ASTNode("Term");
            termNode.operator = op;
            termNode.addChild(left);
            termNode.addChild(Factor());
            left = termNode;
        }
        return left;
    }

    private ASTNode Factor() {
        ASTNode base = Base();
        if (tc.equals("^")) {
            consommer("^");
            ASTNode factorNode = new ASTNode("Factor");
            factorNode.operator = "^";
            factorNode.addChild(base);
            factorNode.addChild(Factor());
            return factorNode;
        }
        return base;
    }

    private ASTNode Base() {
        if (tc.equals("(")) {
            consommer("(");
            ASTNode result = Expression();
            consommer(")");
            return result;
        } else if (isNumber(tc)) {
            return new ASTNode("Literal") {{
                value = Double.parseDouble(tc);
                consommer(tc);
            }};
        } else if (isVariable(tc)) {
            String varName = tc;
            consommer(tc);
            ASTNode variableNode = new ASTNode("Variable");
            variableNode.name = varName;
            return variableNode;
        } else {
            throw new RuntimeException("Erreur: attendu un nombre, '(', ou 'print', mais trouvé '" + tc + "'");
        }
    }

    private boolean isNumber(String token) {
        return token.matches("[0-9]+(\\.[0-9]+)?");
    }

    private boolean isVariable(String token) {
        return token.matches("[A-Za-z_][A-Za-z0-9_]*");
    }
}
