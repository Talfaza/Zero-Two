#ifndef TOKENIZER_H
#define TOKENIZER_H

#include <string>
#include <vector>

enum class TokenType {
    KEYWORD, IDENTIFIER, NUMBER, STRING, ASSIGNMENT, OPERATOR, LEFT_PAREN, RIGHT_PAREN, SEMICOLON, END, INVALID
};

struct Token {
    TokenType type;
    std::string value;
};

class Tokenizer {
public:
    Tokenizer(const std::string& code);
    std::vector<Token> tokenize();
private:
    std::string code;
    size_t pos;

    char currentChar();
    void advance();
    Token getNextToken();
};

#endif // TOKENIZER_H
