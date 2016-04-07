import fileinput
import argparse
import sys
from collections import defaultdict
from heapq import heappush, heappop
from random import choice as randomchoice

def main():
    #nodes, vertices = parseInput(sys.argv[1])
    return

def parseInput(inputfile):
    i = 0
    for line in fileinput.input(inputfile):
        line = line.rstrip()
        vertices = defaultdict(tuple)
        if line[-1] == ']':
            cities = line.split(" ").split("--")
            cities = [city.replace('"', '') for city in cities]
            distance = int(line.split(" ")[1][1:-1])
            heappush(vertices[cities[0]], (distance, cities[1]))
            vertices[cities[0]][
            heappush(vertices[cities[1]], (distance, cities[0]))
        else:
            nodes.append(line[1:-1] if line[0]=='"' else line)

    return nodes, vertices

def prim(vertices):
    vertexHeap = []
    for v in nodes[city]:
        heappush(vertexHeap, v)
    del nodes[city]

    while nodes:
        v = heappop(vertexHeap)
        if v[1] in nodes:
            for x in nodes[v[1]]:
                heappush()



    return



