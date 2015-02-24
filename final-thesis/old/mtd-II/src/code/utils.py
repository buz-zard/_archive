# -*- coding: utf-8 -*-
from __future__ import division

from itertools import permutations
from random import randint


def randint_exept(a, b, c):
    if a == b:
        raise Exception(
            "Invalid arguments (interval too small): a = %s, b = %s, c = %s" %
            (a, b, c))
    the_int = c
    while the_int == c:
        the_int = randint(a, b)
    return the_int


def permutations_from_to(orig_list, from_num, to_num):
    if len(orig_list) < 3:
        raise Exception("List must be minimum size of 3")
    for n in xrange(3, len(orig_list) + 1):
        for p in permutations(orig_list, n):
            if p[0] == from_num and p[len(p) - 1] == to_num:
                yield p


def list_diff(a, b):
    b = set(b)
    return [aa for aa in a if aa not in b]


def to_ordinal(items):
    new_items = []
    left_items = sorted(items)
    for val in items:
        new_items.append(left_items.index(val))
        del left_items[left_items.index(val)]
    return new_items


def from_ordinal(items):
    new_items = []
    left_items = range(0, len(items))
    for val in items:
        new_items.append(left_items[val])
        del left_items[val]
    return new_items


def n_slices(n, list_):
    for i in xrange(len(list_) + 1 - n):
        yield list_[i:i + n]


def is_sublist(list_, sub_list):
    for slice_ in n_slices(len(sub_list), list_):
        if slice_ == sub_list:
            return True
    return False


def average_out_lists(items):
    averaged_items = []
    num_values = len(items)
    for i in range(len(items[0])):
        values_sum = 0
        for item in items:
            values_sum += item[i]
        averaged_items.append(values_sum / num_values)
    return averaged_items


def trange(*args):
    return tuple(range(*args))


def extract_d_param(dparams, key, i):
    pars = dparams[key]
    for key, value in pars['dynamic'].items():
        if i in key:
            return value
    return pars['default']
