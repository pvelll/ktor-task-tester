#!/bin/bash

if [ -f /app/shared/solution ]; then
    echo "Running tests..."
    for input in $(cat /app/shared/input.txt)
    do
        echo "Running solution with input: $input"
        echo "$input" | /app/shared/solution >> /app/shared/output.txt
    done
fi