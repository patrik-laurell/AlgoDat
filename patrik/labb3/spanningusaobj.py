import fileinput
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
        nodes = defaultdict(set)
        vertices = dict()
        if line[-1] == ']':
            cities = line.split(" ").split("--")
            cities = [city.replace('"', '') for city in cities]
            distance = int(line.split(" ")[1][1:-1])
            v = Vertex(tuple(cities), distance)
            vertices[i] = v
            nodes[cities[0]].add(i)
            nodes[cities[1]].add(i)
        else:
            nodes.append(line[1:-1] if line[0]=='"' else line)

    return nodes, vertices

def prim(nodes, vertices):
    city = randomchoice(list(nodes.keys()))
    F = {nodes[city]}
    nodes[city] = 0
    Q = []
    for v in nodes:
        heappush(Q, v)

    return

class Vertex:

    def __init__(self, nodes, weight):
        self.nodes = tuple(sorted(nodes))
        self.weight = weight

    def __eq__(self, vertex):
        return self.nodes==vertex.nodes and isinstance(vertex, self.__class__)

    def connects(node):
        return node in self.nodes


class Node:

    def __init__(self):
        self.vertices = set()

    def addVertex(v):
        self.vertices.add(v)

    def getVertices():
        return self.vertices





