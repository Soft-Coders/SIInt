#/bin/bash/!

echo "# $1" > date.md;
cat *.md > $1.md;
rm date.md;