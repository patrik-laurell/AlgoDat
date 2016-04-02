#!/bin/bash

javac stablemarriage.java

for l in $(ls ../../algdes-labs/matching/data/*in.txt)
do
    name=${l##*/}
    echo "Running "$name
    name=${name%-*}-out.txt
    time (java StableMarriage < $l > $name)
    diff ${l%/*}/$name $name
done

rm *out.txt
