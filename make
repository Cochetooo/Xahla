#!/bin/bash
# xmake.sh

for param in "$*"
do
    python vendor/XMake/make.py $param
done
