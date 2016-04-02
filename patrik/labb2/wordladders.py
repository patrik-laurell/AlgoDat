import fileinput
import sys
from itertools import permutations

def main():
    d = dict()
    wmap = dict()
    for line in fileinput.input(sys.argv[1]):
        line = line.rstrip()
        w = Word(line)
        wmap[line] = w
        for key in set(["".join(sorted(x)) for x in [''.join(p) for p in permutations(line, 4)]]):
            if key in d:
                d[key].append(w)
            else:
                d[key] = [w]

    for line in fileinput.input(sys.argv[2]):
        words = line.split(" ")
        print(bfs(d, wmap, words[0], words[1].rstrip()))
        for key, word in wmap.items():
            word.discovered = False
            word.distance = 0


def bfs(d, wmap, start, goal):
    if start == goal:
        return 0
    wmap[start].discovered = True
    q = [wmap[start]]
    while q:
        current = q.pop()
        if current.word == goal and current.word != start:
            return current.distance
        key = "".join(sorted(current.word[1:]))
        for neighbour in d[key]:
            if not neighbour.discovered:
                neighbour.discovered = True
                neighbour.distance = current.distance + 1
                q.insert(0, neighbour)
    return -1

class Word:
    def __init__(self, word):
        self.word = word
        self.discovered = False
        self.distance = 0

if __name__ == "__main__":
    main()
