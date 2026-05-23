@echo off
rem Build and run script for MIDGAME project
setlocal

rem Ensure Java is on PATH
java -version >nul 2>&1
if errorlevel 1 (
    echo Java is not installed or not on PATH. Please install JDK 21 and ensure java is accessible.
    exit /b 1
)

rem Define directories
set SRC_DIR=src
set BIN_DIR=bin
set LIB_DIR=lib

rem Create bin directory if it doesn't exist
if not exist %BIN_DIR% mkdir %BIN_DIR%

rem Build classpath: include all jars in lib folder
set CP=%BIN_DIR%;%LIB_DIR%\*

echo Compiling Java sources...
javac -cp "%CP%" -d %BIN_DIR% %SRC_DIR%\**\*.java
if errorlevel 1 (
    echo Compilation failed. Check errors above.
    exit /b 1
)

rem Run the main class
echo Running the game...
java -cp "%CP%" main.MainClass

endlocal
