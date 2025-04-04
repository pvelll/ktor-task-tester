#!/bin/bash
# Simple loop to check for new solution files and run tests
while true; do
if [ -f /app/shared/solution.cpp ]; then
echo "Found solution.cpp, running tests..."
ls -l /app/shared/
cp /app/shared/solution.cpp /usr/src/app/solution.cpp
ls -l /usr/src/app/
cat /usr/src/app/solution.cpp
/usr/src/app/run.sh $(cat /app/shared/input.txt) > /app/shared/output.txt 2>&1
echo "Tests completed, output saved to output.txt"
cat /app/shared/output.txt
if [ -f /app/shared/solution.cpp ]; then
rm /app/shared/solution.cpp
else
echo "Warning: solution.cpp not found for removal"
fi
fi
sleep 1
done
