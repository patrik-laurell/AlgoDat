#!/bin/bash

for l in $(ls ../../algdes-labs/word-ladders/data/*in.txt)
do
    name=${l##*/}
    echo "Running "$name
    name=${name%-*}-out.txt
    time (python wordladders.py ${l%-*}.txt $l > $name)
    diff ${l%/*}/$name $name
done

#rm *out.txt
