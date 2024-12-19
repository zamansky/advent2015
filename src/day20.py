from functools import reduce
from sympy import divisors

def factors(n):
    return set(reduce(
        list.__add__,
        ([i, n//i] for i in range(1, int(n**0.5) + 1) if n % i == 0)))

def part1():
    i=0
    while True:
        gifts = sum(factors(i+1))*11
        if gifts>=36000000:
            print(gifts,i+1)
            break
        i=i+1

# def numgifts(h):
#     facts = factors(h)
#     f = [x for x in facts if h//x<50]
#     s = sum(f)*11
#     return s

def part2(part_a_ans):
    h = part_a_ans
    while True:
        g = 0
        f = divisors(h)
        for  i in f[::-1]:
            if i*50>h:
                g += i*11
            else:
                break
        if g>36000000:
            return h
        else:
            h=h+1

puzzle_input = 36000000
