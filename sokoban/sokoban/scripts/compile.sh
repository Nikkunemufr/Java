#!/bin/sh

cd $(dirname $0)/..
[ -d bin ] || mkdir -p bin
javac -d bin src/main/java/sokoban/*.java src/main/java/sokoban/*/*.java
