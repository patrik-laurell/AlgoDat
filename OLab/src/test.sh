#!/bin/bash

for l in $(ls ../../algdes-labs/matching/data/*in.txt)
do
    name=${l##*/}
    echo "Running "$name
    name=${name%-*}-out.txt
    java match.MatchingApplication < $l > $name
    diff ${l%/*}/$name $name
done
