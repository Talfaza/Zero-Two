import java.util.LinkedList;
import java.util.Queue;

public class TokenManager {
    private Queue<String> tokens;
    private String currentToken;

    public TokenManager(String entry) {
        tokens = new LinkedList<>();
        tokenize(entry);
        currentToken = null;
    }

    private void tokenize(String entry) {
        String regex = "([0-9]+|[\\+\\-\\*/\\^\\(\\)]|#)";
        entry = entry.replaceAll("\\s+", "");
        String[] tokenArray = entry.split("(?<=" + regex + ")|(?=" + regex + ")");
        for (String token : tokenArray) {
            tokens.add(token);
        }
    }

    public String suivant() {
        currentToken = tokens.isEmpty() ? "#" : tokens.poll();
        return currentToken;
    }
}
