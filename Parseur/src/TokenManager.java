import java.util.ArrayList;
import java.util.List;

public class TokenManager {
    private List<String> tokens;
    private int currentIndex;

    public TokenManager(String input) {
        tokens = tokenize(input);
        currentIndex = 0;
    }

    private List<String> tokenize(String input) {
        List<String> result = new ArrayList<>();
        String regex = "\\s*([A-Za-z]+|[0-9]+|\\+|\\-|\\*|\\/|\\^|\\(|\\)|=|#)\\s*";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(input);
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        result.add("#");
        return result;
    }

    public String suivant() {
        if (currentIndex < tokens.size()) {
            return tokens.get(currentIndex++);
        }
        return null;
    }
}
