const { ipcRenderer } = require("electron");

// Function to trigger the executable with a file path
function runExecutable() {
  const filePath = "/path/to/your/file.txt"; // The file path to be passed to the executable

  ipcRenderer
    .invoke("run-executable", filePath)
    .then((output) => {
      // Display the output in the app (e.g., in a div or textarea)
      document.getElementById("output").innerText = output;
    })
    .catch((error) => {
      document.getElementById("output").innerText = `Error: ${error.message}`;
    });
}

// Example: Trigger the executable when a button is clicked
document.getElementById("runButton").addEventListener("click", runExecutable);
