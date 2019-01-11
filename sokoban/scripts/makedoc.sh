#!/bin/sh

cd $(dirname $0)/..
[ -d doc ] || mkdir doc
javadoc -d doc src/main/java/sokoban/*.java src/main/java/sokoban/*/*.java
