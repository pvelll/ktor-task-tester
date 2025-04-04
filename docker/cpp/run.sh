#!/bin/bash
g++ -o solution solution.cpp

for input in "$@"
do
  echo "$input" | ./solution
done