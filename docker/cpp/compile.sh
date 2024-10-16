#!/bin/bash

if [ -f /app/shared/solution.cpp ]; then
    echo "Compiling solution.cpp..."
    g++ -o /app/shared/solution /app/shared/solution.cpp 2> /app/shared/compile_errors.txt

    if [ $? -ne 0 ]; then
        echo "Compilation failed" > /app/shared/compile_status.txt
    else
        echo "Compilation succeeded" > /app/shared/compile_status.txt
    fi
fi