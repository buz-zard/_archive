# -*- coding: utf-8 -*-
from random import randint

from utils import from_ordinal, to_ordinal


def crossover_ordinal_1p(parent_a, parent_b):
    point = randint(1, len(parent_a) - 1)
    parent_a = to_ordinal(parent_a)
    parent_b = to_ordinal(parent_b)
    child_a = parent_a[:point]
    child_a += parent_b[point:]
    child_b = parent_b[:point]
    child_b += parent_a[point:]
    return from_ordinal(child_a), from_ordinal(child_b)


def crossover_ox(parent_a, parent_b):
    cross_range = randint(2, len(parent_a) - 2)
    point = randint(1, len(parent_a) - cross_range - 1)
    return _crossover_ox_child(parent_a, parent_b, point, cross_range), \
        _crossover_ox_child(parent_b, parent_a, point, cross_range)


def _crossover_ox_child(initial, other, point, cross_range):
    chrom = initial[:point], initial[
        point:point + cross_range], initial[point + cross_range:]
    other_chrom = other[:point], other[
        point:point + cross_range], other[point + cross_range:]
    child = [[], chrom[1][:], []]
    values = chrom[1]
    arr = other_chrom[2] + other_chrom[0] + other_chrom[1]
    for _ in xrange(0, len(chrom[2])):
        val = None
        j = 0
        while val is None:
            if arr[j] not in values:
                val = arr[j]
            j += 1
        values.append(val)
        child[2].append(val)
    for _ in xrange(0, len(chrom[0])):
        val = None
        j = 0
        while val is None:
            if arr[j] not in values:
                val = arr[j]
            j += 1
        values.append(val)
        child[0].append(val)
    return child[0] + child[1] + child[2]
