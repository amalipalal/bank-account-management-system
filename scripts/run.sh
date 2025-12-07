#!/bin/bash

echo "Compiling tests..."
mvn test-compile

if [ $? -ne 0 ]; then
    echo "Test compilation failed. Exiting."
    exit 1
fi

echo "Running Main..."
mvn exec:java -Dexec.mainClass="Main" -Dexec.classpathScope="test"
