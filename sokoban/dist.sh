#!/bin/sh

# Workspace
workspace_dir="workspace/Sokoban"
output_dir="livraison"

# Clean up previous version
[ -d ${output_dir} ] || mkdir ${output_dir}
rm -rf ${output_dir}/*

# 1. Copy source files
basedir=$(pwd)
cd ${workspace_dir}
find . -name "*.java" -exec rsync -R {} ${basedir}/${output_dir}/ \;
cd - > /dev/null

# 2. Copy scripts files
mkdir ${output_dir}/scripts
cp -f scripts/*.sh ${output_dir}/scripts/

# 3. Copy readme file
cp README.txt ${output_dir}/

# 4. Copy report files (.pdf, .tex and .bib)
mkdir ${output_dir}/report
cp -f report/LaTeX/*.pdf report/LaTeX/*.tex report/LaTeX/*.bib ${output_dir}/report/

# Make all files in dist readonly so as to avoid editing them
find ${output_dir} -type f -exec chmod u-w {} \;
