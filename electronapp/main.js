const { app, BrowserWindow, ipcMain } = require("electron");
const { spawn } = require("child_process");
const path = require("path");

let mainWindow;

function createWindow() {
  mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      nodeIntegration: true,
      preload: path.join(__dirname, "preload.js"), // Preload script if needed
    },
  });

  mainWindow.loadFile("index.html");
}

app.whenReady().then(() => {
  createWindow();

  // Path to Zerotwo executable
  const executablePath = path.join(__dirname, "../compiler/zerotwo"); // Adjust this path based on your compiled executable
  const inputFile = path.join(__dirname, "../compiler/main.zt"); // Path to your .zt file

  // Spawn the child process to run Zerotwo with the .zt file
  const child = spawn(executablePath, [inputFile]);

  // Capture stdout from the Zerotwo compiler
  child.stdout.on("data", (data) => {
    console.log(`stdout: ${data}`);
    // Send the stdout to renderer (front-end)
    mainWindow.webContents.send("output", data.toString());
  });

  // Capture stderr from the Zerotwo compiler
  child.stderr.on("data", (data) => {
    console.error(`stderr: ${data}`);
    // Send the stderr (error messages) to renderer
    mainWindow.webContents.send("error", data.toString());
  });

  // Capture exit code when the compiler finishes
  child.on("close", (code) => {
    console.log(`Child process exited with code ${code}`);
    mainWindow.webContents.send(
      "exit",
      `Child process exited with code ${code}`,
    );
  });
});

app.on("window-all-closed", () => {
  if (process.platform !== "darwin") {
    app.quit();
  }
});
