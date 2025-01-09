package main 

import (
	"fmt"
	"os"
	"os/exec"
)

func main() {
	fmt.Println("Compiling Java code...")

	// Compile all Java files in the 'parser' directory
	cmd := exec.Command("sh", "-c", "cd parser && javac *.java && cd ..")
	cmd.Stderr = os.Stderr

	err := cmd.Run()
	if err != nil {
		fmt.Println("Error during compilation:", err)
		return
	}
	fmt.Println("Compilation successful.")

	fmt.Println("Running Java Code...")
	// Run the main Java class in the 'parser' directory
	codeRun := exec.Command("sh", "-c", "cd parser && java Main && cd ..")

	output, err := codeRun.CombinedOutput()
	if err != nil {
		fmt.Println("Error during execution:", err)
		return
	}

	fmt.Println("Execution successful. Output:")
	fmt.Println(string(output))

	fmt.Println("Deleting .class files...")
	// Delete all .class files in the 'parser' directory
	deleteCmd := exec.Command("sh", "-c", "cd parser && rm -f *.class && cd ..")
	deleteOutput, err := deleteCmd.CombinedOutput()
	if err != nil {
		fmt.Println("Error during file deletion:", err)
		return
	}

	fmt.Println("Deletion successful.")
	fmt.Println(string(deleteOutput))
}
