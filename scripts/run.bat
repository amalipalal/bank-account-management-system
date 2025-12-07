@echo off
echo Compiling tests...
mvn test-compile
IF %ERRORLEVEL% NEQ 0 (
    echo Test compilation failed. Exiting.
    exit /b %ERRORLEVEL%
)

echo Running Main...
mvn exec:java -Dexec.mainClass="Main" -Dexec.classpathScope="test"
pause
