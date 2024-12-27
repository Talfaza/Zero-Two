package main

import (
    "fmt"
    "os/exec"
    "log"
)

func main() {
    cmd := exec.Command("javacc")
    // TODO : add javacc full command later 

    output, err := cmd.CombinedOutput()
    if err != nil {
        log.Fatal(err)
    }
    fmt.Println("Parsing output:", string(output))
}
