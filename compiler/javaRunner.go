package main

import (
	"fmt"
	"os"
	"os/exec"
)

func main() {
	fmt.Println("Compiling Java code...")

	cmd := exec.Command("sh", "-c", "cd parser && javac -cp lib/gson-2.8.9.jar *.java && cd ..")
	cmd.Stderr = os.Stderr

	err := cmd.Run()
	if err != nil {
		fmt.Println("Error during compilation:", err)
		return
	}
	fmt.Println("Compilation successful.")

	fmt.Println("Running Java Code...")
	codeRun := exec.Command("sh", "-c", "cd parser && java -cp .:lib/gson-2.8.9.jar Main && cd ..")

	output, err := codeRun.CombinedOutput() 
	if err != nil {
		fmt.Println("Error during execution:", err)
		return
	}

	fmt.Println("Execution successful. Output:")
	fmt.Println(string(output))

	fmt.Println("Deleting .class files...")
	deleteCmd := exec.Command("sh", "-c", "cd parser && rm -f *.class && cd ..")
	deleteOutput, err := deleteCmd.CombinedOutput()
	if err != nil {
		fmt.Println("Error during file deletion:", err)
		return
	}

	fmt.Println("Deletion successful.")
	fmt.Println(string(deleteOutput))
}
