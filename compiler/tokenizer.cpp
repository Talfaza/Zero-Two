#include "tokenizer.h"
#include <cctype>
#include <iostream>

Tokenizer::Tokenizer(const std::string& code) : code(code), pos(0) {}

char Tokenizer::currentChar() {
    return code[pos];
}

void Tokenizer::advance() {
    pos++;
}

Token Tokenizer::getNextToken() {
    while (pos < code.size()) {
        if (std::isspace(currentChar())) {
            advance();
            continue;
        }

        if (std::isalpha(currentChar())) {
            std::string identifier;
            while (pos < code.size() && (std::isalnum(currentChar()) || currentChar() == '_')) {
                identifier += currentChar();
                advance();
            }
            if (identifier == "afficher") {
                return {TokenType::KEYWORD, identifier};
            }
            return {TokenType::IDENTIFIER, identifier};
        }

        if (std::isdigit(currentChar())) {
            std::string number;
            while (pos < code.size() && std::isdigit(currentChar())) {
                number += currentChar();
                advance();
            }
            return {TokenType::NUMBER, number};
        }

        if (currentChar() == ':') {
            advance(); // Skip ':'
            if (currentChar() == '=') {
                advance(); // Skip '='
                return {TokenType::ASSIGNMENT, ":="};
            }
        }

        if (currentChar() == ';') {
            advance();
            return {TokenType::SEMICOLON, ";"};
        }

        if (currentChar() == '(') {
            advance();
            return {TokenType::LEFT_PAREN, "("};
        }

        if (currentChar() == ')') {
            advance();
            return {TokenType::RIGHT_PAREN, ")"};
        }

        if (currentChar() == '"') {
            advance(); // Skip opening quote
            std::string value;
            while (pos < code.size() && currentChar() != '"') {
                value += currentChar();
                advance();
            }
            if (currentChar() == '"') {
                advance(); // Skip closing quote
                return {TokenType::STRING, value};
            } else {
                std::cerr << "Error: Unterminated string literal" << std::endl;
                return {TokenType::INVALID, ""}; // Handle unterminated string
            }
        }

        if (currentChar() == '+' || currentChar() == '-' || currentChar() == '*' || currentChar() == '/' || currentChar() == '%') {
            std::string op(1, currentChar());
            advance();
            return {TokenType::OPERATOR, op};
        }

        std::cerr << "Invalid character: " << currentChar() << std::endl; // Debugging output
        advance(); // Skip invalid character
    }

    return {TokenType::END, ""}; // End of input
}

std::vector<Token> Tokenizer::tokenize() {
    std::vector<Token> tokens;
    Token token = getNextToken();
    while (token.type != TokenType::END) {
        tokens.push_back(token);
        token = getNextToken();
    }
    return tokens;
}
