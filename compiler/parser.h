#ifndef PARSER_H
#define PARSER_H

#include "tokenizer.h"
#include <unordered_map>

class Parser {
public:
    Parser(const std::vector<Token>& tokens);  // Changed to const reference
    void parse();
private:
    std::vector<Token> tokens;
    size_t pos;
    std::unordered_map<std::string, int> variables;

    Token currentToken();
    void advance();
    void parseAfficher();
    void parseAssignment();
    int parseExpression();
    int parseTerm();
    int parseFactor();
};

#endif // PARSER_H
