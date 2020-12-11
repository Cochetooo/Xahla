#!/bin/bash
# xmake.sh

for param in "$*"
do
    py XMake/make.py $param
done