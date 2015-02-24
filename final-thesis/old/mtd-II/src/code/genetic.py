# -*- coding: utf-8 -*-
from random import shuffle, randint, uniform

from tsp import TSPSolver
from utils import from_ordinal, to_ordinal, randint_exept


class Genetic(TSPSolver):

    '''
    TSP Genetic algorithm solver
    '''

    def __init__(self, graph, params, conditions, absolute_mute=False):
        TSPSolver.__init__(self, graph, conditions, absolute_mute)
        if params['chromosomes'] % 2 != 0:
            raise Exception("chromosomes % 2 != 0")
        self._chromosomes_ = params['chromosomes']
        self._cr_ = params['cr']
        self._mr_ = params['mr']
        self._selection_method_ = _method_map_[params['selection_method']]
        self._crossover_method_ = _method_map_[params['crossover_method']]
        self._mutation_method_ = _method_map_[params['mutation_method']]
        self._params_ = params
        self._acs_ = None
        if 'use_acs' in params:
            self._acs_ = params['use_acs']
        self._dynamic_params_ = None
        if 'dynamic_params' in params:
            self._dynamic_params_ = params['dynamic_params']

    def get_solution(self):
        self.display_method = "Genetic algorithm"
        self.display_parameters = {
            'cr': self._cr_,
            'mr': self._mr_,
            'chromosomes': self._chromosomes_,
            'selection_method': self._params_['selection_method'],
            'crossover_method': self._params_['crossover_method'],
            'mutation_method': self._params_['mutation_method']
        }
        return TSPSolver.get_solution(self)

    def solve(self, show_progress=False, absolute_mute=False):
        self.show_progress = show_progress
        TSPSolver.start_solving(self)
        population = None
        while not self._conditions_met_():
            TSPSolver.update_progress(self)
            if population is None:
                if self._acs_ is None:
                    population = []
                    for _ in xrange(0, self._chromosomes_):
                        population.append(self._generate_random_chromosome_())
                else:
                    population = self._initial_population_from_acs_()
            else:
                new_population = []
                while len(new_population) < len(population):
                    chrom_a, chrom_b = self._selection_method_(population)
                    if self._do_crossover_():
                        chrom_a, chrom_b = self._crossover_(chrom_a, chrom_b)
                        if self._do_mutation_():
                            chrom_a, chrom_b = self._mutate_(chrom_a, chrom_b)
                    new_population.append(chrom_a)
                    new_population.append(chrom_b)
                population = self._complete_generation_(population,
                                                        new_population)
        TSPSolver.end_solving(self)

    def _crossover_(self, a, b):
        route_a, route_b = self._crossover_method_(a[0], b[0])
        chrom_a = self._route_to_chromosome_(route_a)
        chrom_b = self._route_to_chromosome_(route_b)
        return chrom_a, chrom_b

    def _mutate_(self, a, b):
        chrom_a = self._route_to_chromosome_(self._mutation_method_(a[0]))
        chrom_b = self._route_to_chromosome_(self._mutation_method_(b[0]))
        return chrom_a, chrom_b

    def _complete_generation_(self, population, new_population):
        elite = sorted(population, key=lambda tup: tup[1])[0]
        solution = TSPSolver.nodes_to_solution(self, elite[0])
        TSPSolver.set_solution(self, solution[0], solution[1])
        s_population = sorted(new_population, key=lambda tup: tup[1])[::-1]
        new_population[new_population.index(s_population[0])] = elite
        return new_population

    def _conditions_met_(self):
        return TSPSolver.conditions_met(self)

    def _do_crossover_(self):
        return uniform(0, 1) >= self._cr_

    def _do_mutation_(self):
        return uniform(0, 1) >= self._mr_

    def _generate_random_chromosome_(self):
        route = self.graph.nodes()
        shuffle(route)
        solution = TSPSolver.nodes_to_solution(self, route)
        TSPSolver.set_solution(self, solution[0], solution[1])
        distance = self.graph.route_distance(solution[1])
        return solution[0][:-1], distance

    def _route_to_chromosome_(self, route):
        solution = TSPSolver.nodes_to_solution(self, route)
        TSPSolver.set_solution(self, solution[0], solution[1])
        return solution[0][:-1], self.graph.route_distance(solution[1])

    def _initial_population_from_acs_(self):
        self._acs_.solve()
        population = []
        for ant in self._acs_.ants.values():
            route = ant.route[:-1]
            solution = TSPSolver.nodes_to_solution(self, route)
            TSPSolver.set_solution(self, solution[0], solution[1])
            distance = self.graph.route_distance(solution[1])
            population.append((solution[0][:-1], distance))
        while len(population) < self._chromosomes_:
            population.append(self._generate_random_chromosome_())
        return population


# ############################################################################
#
# METHODS
#
# ############################################################################
SELECTION_ROULETE = "SELECTION_ROULETE"
SELECTION_RANK = "SELECTION_RANK"

CROSSOVER_ORDINAL_1P = "CROSSOVER_ORDINAL_1P"
CROSSOVER_OX = "CROSSOVER_OX"

MUTATION_SWAP = "MUTATION_SWAP"
MUTATION_SA = "MUTATION_SA"
MUTATION_SA_REVERSE = "MUTATION_SA_REVERSE"
MUTATION_SA_INSERT = "MUTATION_SA_INSERT"


# ############################################################################
# SELECTION
# ############################################################################
def _genetic_roulete_wheel_selection_(population):
    temp_population = population[:]
    chrom_a = _roulete_wheel_selection_(temp_population)
    chrom_b = _roulete_wheel_selection_(temp_population)
    return chrom_a, chrom_b


def _roulete_wheel_selection_(population):
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


def _genetic_rank_selection_(population):
    sorted_population = sorted(population, key=lambda tup: tup[1])[::-1]
    chrom_a = _rank_selection_(sorted_population)
    chrom_b = _rank_selection_(sorted_population)
    return chrom_a, chrom_b


def _rank_selection_(population):
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


# ############################################################################
# CROSSOVER
# ############################################################################


def _crossover_ordinal_1p_(parent_a, parent_b):
    point = randint(1, len(parent_a) - 1)
    parent_a = to_ordinal(parent_a)
    parent_b = to_ordinal(parent_b)
    child_a = parent_a[:point]
    child_a += parent_b[point:]
    child_b = parent_b[:point]
    child_b += parent_a[point:]
    return from_ordinal(child_a), from_ordinal(child_b)


def _crossover_ox_(parent_a, parent_b):
    cross_range = randint(2, len(parent_a) - 2)
    point = randint(1, len(parent_a) - cross_range - 1)
    return _crossover_ox_child_(parent_a, parent_b, point, cross_range), \
        _crossover_ox_child_(parent_b, parent_a, point, cross_range)


def _crossover_ox_child_(initial, other, point, cross_range):
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


# ############################################################################
# MUTATION
# ############################################################################
def _mutation_swap_(parent):
    point_a = randint(0, len(parent) - 1)
    point_b = randint_exept(0, len(parent) - 1, point_a)
    child = parent[:]
    child[point_a], child[point_b] = child[point_b], child[point_a]
    return child


def _mutation_reverse_(items):
    cross_range = randint(2, len(items) - 2)
    point = randint(1, len(items) - cross_range - 1)
    new_items = items[:point], items[
        point:point + cross_range], items[point + cross_range:]
    return new_items[0] + new_items[1][::-1] + new_items[2]


def _mutation_insert_(items):
    cross_range = randint(2, len(items) - 2)
    point = randint(1, len(items) - cross_range - 1)
    new_items = items[:point] + items[point + cross_range:]
    sub_tour = items[point:point + cross_range]
    new_point = randint_exept(0, point, len(new_items))
    return new_items[:new_point] + sub_tour + new_items[new_point:]


def _mutattion_sa_(items):
    if uniform(0.0, 1.0) < 0.5:
        return _mutation_reverse_(items)
    else:
        return _mutation_insert_(items)


_method_map_ = {
    SELECTION_ROULETE: _genetic_roulete_wheel_selection_,
    SELECTION_RANK: _genetic_rank_selection_,
    CROSSOVER_ORDINAL_1P: _crossover_ordinal_1p_,
    CROSSOVER_OX: _crossover_ox_,
    MUTATION_SWAP: _mutation_swap_,
    MUTATION_SA: _mutattion_sa_,
    MUTATION_SA_REVERSE: _mutation_reverse_,
    MUTATION_SA_INSERT: _mutation_insert_
}
