#!/bin/bash
mkdir -p out
javac -d out $(find src -name "*.java")
echo ""
echo "Build complete. Running MyBudget..."
echo ""
java -cp out mybudget.Main
