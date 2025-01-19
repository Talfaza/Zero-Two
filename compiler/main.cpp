#include <iostream>
#include <fstream>
#include <string>
#include "tokenizer.h"
#include "parser.h"

int main(int argc, char* argv[]) {
    if (argc != 2) {
        std::cerr << "Usage: " << argv[0] << " <filename>\n";
        return 1;
    }

    std::ifstream file(argv[1]);
    if (!file.is_open()) {
        std::cerr << "Error opening file\n";
        return 1;
    }

    std::string code;
    std::string line;
    while (std::getline(file, line)) {
        code += line + "\n";
    }
    file.close();

    Tokenizer tokenizer(code);
    std::vector<Token> tokens = tokenizer.tokenize();

    Parser parser(tokens);
    parser.parse();

    return 0;
}
