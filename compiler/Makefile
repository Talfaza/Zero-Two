# Compiler and flags
CXX = g++
CXXFLAGS = -std=c++11 -Wall -g

# Directories
SRC_DIR = .
OBJ_DIR = obj
SCRIPT_DIR = scripts

# Source files
SRC_FILES = main.cpp tokenizer.cpp parser.cpp

# Object files corresponding to the source files
OBJ_FILES = $(SRC_FILES:%.cpp=$(OBJ_DIR)/%.o)

# Output binary name
TARGET = $(SRC_DIR)/zerotwo

# Default target
all: $(TARGET)

# Rule to link the object files and generate the final executable
$(TARGET): $(OBJ_FILES)
	$(CXX) $(CXXFLAGS) $^ -o $@

# Rule to compile source files into object files
$(OBJ_DIR)/%.o: %.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Rule to clean up build files
clean:
	rm -rf $(OBJ_DIR)/*.o $(BIN_DIR)/test_parse

# Rule to create necessary directories
$(OBJ_DIR) $(BIN_DIR):
	mkdir -p $@

.PHONY: all clean
