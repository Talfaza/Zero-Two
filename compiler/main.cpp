#include <iostream>
#include <fstream>
#include <string>
#include "tokenizer.h"
#include "parser.h"

bool hasZTExtension(const std::string& filename) {
    return filename.size() >= 3 && filename.substr(filename.size() - 3) == ".zt";
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        std::cerr << "Usage: " << argv[0] << " <filename>\n";
        return 1;
    }

    std::string filename = argv[1];
    if (!hasZTExtension(filename)) {
        std::cerr << "Error: The file must have a .zt extension.\n";
        return 1;
    }

    std::ifstream file(filename);
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
