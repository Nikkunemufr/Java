#!/bin/sh

cd $(dirname $0)/..
sh scripts/compile.sh
[ -d jar ] || mkdir jar
cd bin
echo "Main-Class: sokoban.Main" > MANIFEST.mf
jar cfm ../jar/sokoban.jar MANIFEST.mf .
