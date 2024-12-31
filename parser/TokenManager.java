import java.util.ArrayList;
import java.util.List;

public class TokenManager {
    // Had class kaydir manage dyal tokens li katjibhom mn string li dkhal user
    private List<String> tokens; // Hadchi list li ghatkhzin kol token f string
    private int currentIndex; // Had variable kay indicate l index dyal token li hna

    // Constructor, fhadna katbda hadchi mn string li dkhal user
    public TokenManager(String input) {
        tokens = tokenize(input); // Kan'aamlo tokenize bach nfrqo string l tokens
        currentIndex = 0; // Index ybda mn zero
    }

    // Hna katdir tokenize: katfraq string l parts mn input
    private List<String> tokenize(String input) {
        List<String> result = new ArrayList<>(); // Khzini tokens f list
        String regex = "\\s*([A-Za-z]+|[0-9]+|\\+|\\-|\\*|\\/|\\^|\\(|\\)|:=|#)\\s*";
        // Regex hadi katqabatch l tokens mgharba (letters, numbers, operators...)
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(input);
        while (matcher.find()) { // Kul token katchado matcher
            result.add(matcher.group(1)); // Khzini token f list
        }
        result.add("#"); // Add had symbol '#' bach ynhit end input
        return result; // Return list dyal tokens
    }

    // Hadi function katraj3 lina token mn list, b currentIndex
    public String suivant() {
        if (currentIndex < tokens.size()) { // Ila mazal tokens
            return tokens.get(currentIndex++); // Return token li kayna
        }
        return null; // Null ila khlassat tokens
    }
}

