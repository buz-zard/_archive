# -*- coding: utf-8 -*-
from random import randint, uniform

from ..utils import randint_exept


def mutate_swap(parent):
    point_a = randint(0, len(parent) - 1)
    point_b = randint_exept(0, len(parent) - 1, point_a)
    child = parent[:]
    child[point_a], child[point_b] = child[point_b], child[point_a]
    return child


def mutate_reverse(items):
    cross_range = randint(2, len(items) - 2)
    point = randint(1, len(items) - cross_range - 1)
    new_items = items[:point], items[
        point:point + cross_range], items[point + cross_range:]
    return new_items[0] + new_items[1][::-1] + new_items[2]


def mutate_insert(items):
    cross_range = randint(2, len(items) - 2)
    point = randint(1, len(items) - cross_range - 1)
    new_items = items[:point] + items[point + cross_range:]
    sub_tour = items[point:point + cross_range]
    new_point = randint_exept(0, point, len(new_items))
    return new_items[:new_point] + sub_tour + new_items[new_point:]


def mutate_sa(items):
    if uniform(0.0, 1.0) < 0.5:
        return mutate_reverse(items)
    else:
        return mutate_insert(items)
