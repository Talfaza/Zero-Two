#include "parser.h"
#include <iostream>
#include <sstream>

Parser::Parser(const std::vector<Token>& tokens) : tokens(tokens), pos(0) {}

Token Parser::currentToken() {
    return tokens[pos];
}

void Parser::advance() {
    if (pos < tokens.size()) {
        pos++;
    }
}

void Parser::parse() {
    while (pos < tokens.size()) {
        Token token = currentToken();

        if (token.type == TokenType::KEYWORD && token.value == "afficher") {
            parseAfficher();
        } else if (token.type == TokenType::IDENTIFIER) {
            parseAssignment();
        } else {
            std::cerr << "Invalid token: " << token.value << std::endl;
            advance();
        }
    }
}

void Parser::parseAfficher() {
    advance(); // Skip "afficher"
    if (currentToken().type == TokenType::LEFT_PAREN) {
        advance(); // Skip '('
        
        // Check if the next token is a string or an expression
        if (currentToken().type == TokenType::STRING) {
            std::cout << currentToken().value << std::endl; // Print string
            advance(); // Skip string
        } else {
            // Evaluate the expression
            int value = parseExpression();
            std::cout << value << std::endl; // Print the evaluated result
        }

        if (currentToken().type == TokenType::RIGHT_PAREN) {
            advance(); // Skip ')'
        } else {
            std::cerr << "Error: Expected ')' after expression in afficher." << std::endl;
        }
    } else {
        std::cerr << "Error: Expected '(' after afficher." << std::endl;
    }
    
    if (currentToken().type == TokenType::SEMICOLON) {
        advance(); // Skip ';'
    } else {
        std::cerr << "Error: Expected ';' at the end of afficher statement." << std::endl;
    }
}

void Parser::parseAssignment() {
    std::string varName = currentToken().value;
    advance(); // Skip variable name
    if (currentToken().type == TokenType::ASSIGNMENT) {
        advance(); // Skip ":="
        int value = parseExpression();
        variables[varName] = value; // Store the value in the variable map
    }
    if (currentToken().type == TokenType::SEMICOLON) {
        advance(); // Skip ';'
    } else {
        std::cerr << "Error: Expected ';' at the end of assignment statement." << std::endl;
    }
}

int Parser::parseExpression() {
    int value = parseTerm();
    while (currentToken().type == TokenType::OPERATOR && (currentToken().value == "+" || currentToken().value == "-")) {
        std::string op = currentToken().value;
        advance(); // Skip operator
        int rightValue = parseTerm();
        if (op == "+") {
            value += rightValue;
        } else if (op == "-") {
            value -= rightValue;
        }
    }
    return value;
}

int Parser::parseTerm() {
    int value = parseFactor();
    while (currentToken().type == TokenType::OPERATOR && (currentToken().value == "*" || currentToken().value == "/" || currentToken().value == "%")) {
        std::string op = currentToken().value;
        advance(); // Skip operator
        int rightValue = parseFactor();
        if (op == "*") {
            value *= rightValue;
        } else if (op == "/") {
            if (rightValue == 0) {
                std::cerr << "Error: Division by zero." << std::endl;
                return 0; // Handle division by zero
            }
            value /= rightValue;
        } else if (op == "%") {
            value %= rightValue;
        }
    }
    return value;
}

int Parser::parseFactor() {
    int value = 0;
    if (currentToken().type == TokenType::NUMBER) {
        value = std::stoi(currentToken().value);
        advance(); // Skip number
    } else if (currentToken().type == TokenType::IDENTIFIER) {
        std::string varName = currentToken().value;
        if (variables.find(varName) != variables.end()) {
            value = variables[varName]; // Get the value of the variable
        } else {
            std::cerr << "Error: Undefined variable: " << varName << std::endl;
        }
        advance(); // Skip identifier
    } else {
        std::cerr << "Error: Unexpected token in factor." << std::endl;
    }
    return value;
}
