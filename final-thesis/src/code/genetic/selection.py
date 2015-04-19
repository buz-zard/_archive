# -*- coding: utf-8 -*-
from random import shuffle, randint


def roulete_wheel_select(population):
    temp_population = population[:]
    chrom_a = _inner_roulete_wheel_selection(temp_population)
    chrom_b = _inner_roulete_wheel_selection(temp_population)
    return chrom_a, chrom_b


def rank_select(population):
    sorted_population = sorted(population, key=lambda tup: tup[1])[::-1]
    chrom_a = _inner_rank_selection(sorted_population)
    chrom_b = _inner_rank_selection(sorted_population)
    return chrom_a, chrom_b


def _inner_roulete_wheel_selection(population):
    sorted_population = sorted(population, key=lambda tup: tup[1])[::-1]
    shuffle(population)
    fitness_sum = 0
    longest_route = sorted_population[0][1]
    for val in population:
        fitness_sum += longest_route - val[1]
    ceil = randint(0, fitness_sum)
    temp_sum = 0
    for i in xrange(0, len(population)):
        val = population[i]
        temp_sum += longest_route - val[1]
        if temp_sum >= ceil:
            del population[i]
            return val


def _inner_rank_selection(population):
    fitness_sum = 0
    s_population = []
    for i in xrange(0, len(population)):
        fitness_sum += i
        s_population.append((population[i], i))
    ceil = randint(0, fitness_sum)
    shuffle(s_population)
    temp_sum = 0
    for i in xrange(0, len(s_population)):
        val = s_population[i]
        temp_sum += val[1]
        if temp_sum >= ceil:
            del population[population.index(val[0])]
            return val[0]
