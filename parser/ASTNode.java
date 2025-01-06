import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    String type; // "Assignment", "Print", "Expression", etc.
    String name; // Variable name for assignments
    Double value; // Literal values
    String operator; // "+", "-", "*", "/", "^"
    List<ASTNode> children; // Child nodes for complex expressions

    public ASTNode(String type) {
        this.type = type;
        this.children = new ArrayList<>();
    }

    public void addChild(ASTNode child) {
        this.children.add(child);
    }

@Override
public String toString() {
    return toString(0);
}

private String toString(int indentLevel) {
    StringBuilder sb = new StringBuilder();
    String indent = "  ".repeat(indentLevel); // Indentation for readability
    sb.append(indent).append("ASTNode{\n");
    sb.append(indent).append("  type='").append(type).append("',\n");
    sb.append(indent).append("  name='").append(name).append("',\n");
    sb.append(indent).append("  value=").append(value).append(",\n");
    sb.append(indent).append("  operator='").append(operator).append("',\n");
    sb.append(indent).append("  children=[\n");
    for (ASTNode child : children) {
        sb.append(child.toString(indentLevel + 1)).append("\n");
    }
    sb.append(indent).append("  ]\n");
    sb.append(indent).append("}");
    return sb.toString();
}
}
