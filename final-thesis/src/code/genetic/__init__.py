# -*- coding: utf-8 -*-
from random import shuffle, randint, uniform
import math

from tsp import TSPSolver
from utils import from_ordinal, to_ordinal, randint_exept
from .ant import AntGraph, Ant

from . import selection, crossover, mutation


# ############################################################################
# METHODS
# ############################################################################
INITIAL_POPULATION_RANDOM = "INITIAL_POPULATION_RANDOM"
INITIAL_POPULATION_SEMIACS = "INITIAL_POPULATION_SEMIACS"
SELECTION_ROULETE = "SELECTION_ROULETE"
SELECTION_RANK = "SELECTION_RANK"
CROSSOVER_ORDINAL_1P = "CROSSOVER_ORDINAL_1P"
CROSSOVER_OX = "CROSSOVER_OX"
MUTATION_SWAP = "MUTATION_SWAP"
MUTATION_SA = "MUTATION_SA"
MUTATION_SA_REVERSE = "MUTATION_SA_REVERSE"
MUTATION_SA_INSERT = "MUTATION_SA_INSERT"


def _get_method(method_name):
    return {
        INITIAL_POPULATION_RANDOM: INITIAL_POPULATION_RANDOM,
        INITIAL_POPULATION_SEMIACS: INITIAL_POPULATION_SEMIACS,
        SELECTION_ROULETE: selection.roulete_wheel_select,
        SELECTION_RANK: selection.rank_select,
        CROSSOVER_ORDINAL_1P: crossover.crossover_ordinal_1p,
        CROSSOVER_OX: crossover.crossover_ox,
        MUTATION_SWAP: mutation.mutate_swap,
        MUTATION_SA: mutation.mutate_sa,
        MUTATION_SA_REVERSE: mutation.mutate_reverse,
        MUTATION_SA_INSERT: mutation.mutate_sa
    }[method_name]


class GeneticArgs(object):

    def __init__(self, g_args=None):
        self.args = {}
        if g_args:
            self.args = g_args.args.copy()

    def get(self, key):
        if key in self.args:
            return self.args[key]
        return None

    def set(self, key, value):
        self.args[key] = value

    def get_cr(self):
        return self.get('cr')

    def set_cr(self, value):
        self.args['cr'] = value

    def get_mr(self):
        return self.get('mr')

    def set_mr(self, value):
        self.args['mr'] = value

    def get_chr(self):
        return self.get('chr')

    def set_chr(self, value):
        self.args['chr'] = value

    def get_method_select(self):
        return self.get('method_select')

    def set_method_select(self, value):
        self.args['method_select'] = value

    def get_method_cross(self):
        return self.get('method_cross')

    def set_method_cross(self, value):
        self.args['method_cross'] = value

    def get_method_mutate(self):
        return self.get('method_mutate')

    def set_method_mutate(self, value):
        self.args['method_mutate'] = value

    def get_method_initial_population(self):
        return self.get('method_initial_population')

    def set_method_initial_population(self, value):
        self.args['method_initial_population'] = value

    def get_initial_ants(self):
        return self.get('initial_ants')

    def set_initial_ants(self, value):
        self.args['initial_ants'] = value

    def get_initial_beta(self):
        return self.get('initial_beta')

    def set_initial_beta(self, value):
        self.args['initial_beta'] = value


# ############################################################################
# GENETICS ALGORITHM IMPLEMENTATION
# ############################################################################
class Genetic(TSPSolver):

    '''
    TSP Genetic algorithm solver
    '''

    def __init__(self, graph, g_args, conditions, absolute_mute=False):
        TSPSolver.__init__(self, graph, conditions, absolute_mute)
        self._g_args = g_args
        self._chromosomes = g_args.get_chr()
        self._cr = g_args.get_cr()
        self._mr = g_args.get_mr()
        self._initial_population_method = _get_method(
            g_args.get_method_initial_population())
        self._selection_method = _get_method(g_args.get_method_select())
        self._crossover_method = _get_method(g_args.get_method_cross())
        self._mutation_method = _get_method(g_args.get_method_mutate())
        self._validate()

    def _validate(self):
        def no_none(value):
            if value is None:
                raise Exception("Genetetic parameter cannot be null.")
        no_none(self._chromosomes)
        no_none(self._cr)
        no_none(self._mr)
        no_none(self._selection_method)
        no_none(self._crossover_method)
        no_none(self._mutation_method)
        if self._chromosomes < 2 or self._chromosomes % 2 != 0:
            raise Exception("Invalid chromosomes")
        if self._cr < 0:
            raise Exception("Invalid cr")
        if self._mr < 0:
            raise Exception("Invalid mr")

    def get_solution(self):
        self.display_method = "Genetic"
        self.display_parameters = {
            'cr': self._cr,
            'mr': self._mr,
            'chromosomes': self._chromosomes,
            'initial_population_method':
            self._g_args.get_method_initial_population(),
            'selection_method': self._g_args.get_method_select(),
            'crossover_method': self._g_args.get_method_cross(),
            'mutation_method': self._g_args.get_method_mutate()
        }
        if self._g_args.get_method_initial_population() == INITIAL_POPULATION_SEMIACS:
            self.display_parameters['initial_ants'] = \
                self._g_args.get_initial_ants()
            self.display_parameters['initial_beta'] = \
                self._g_args.get_initial_beta()
        return TSPSolver.get_solution(self)

    def solve(self, show_progress=False, absolute_mute=False):
        self.show_progress = show_progress
        TSPSolver.start_solving(self)
        population = None
        while not TSPSolver.conditions_met(self):
            TSPSolver.update_progress(self)
            if population is None:
                population = self._generate_initial_population()
                continue
            new_population = []
            while len(new_population) < len(population):
                chrom_a, chrom_b = self._select(population)
                if self._do_crossover():
                    chrom_a, chrom_b = self._crossover(chrom_a, chrom_b)
                    if self._do_mutation():
                        chrom_a, chrom_b = self._mutate(chrom_a, chrom_b)
                new_population.append(chrom_a)
                new_population.append(chrom_b)
            population = self._complete_generation(population, new_population)
        TSPSolver.end_solving(self)

    # SELECTION
    def _select(self, population):
        return self._selection_method(population)

    # CROSSOVER
    def _do_crossover(self):
        return uniform(0, 1) >= self._cr

    def _crossover(self, a, b):
        route_a, route_b = self._crossover_method(a[0], b[0])
        chrom_a = self._route_to_chromosome(route_a)
        chrom_b = self._route_to_chromosome(route_b)
        return chrom_a, chrom_b

    # MUTATION
    def _do_mutation(self):
        return uniform(0, 1) >= self._mr

    def _mutate(self, a, b):
        chrom_a = self._route_to_chromosome(self._mutation_method(a[0]))
        chrom_b = self._route_to_chromosome(self._mutation_method(b[0]))
        return chrom_a, chrom_b

    # POPULATION
    def _generate_initial_population(self):
        if self._initial_population_method == INITIAL_POPULATION_RANDOM:
            return self._initial_population_random()
        else:
            return self._initial_population_using_ants(
                self._g_args.get_initial_ants(),
                self._g_args.get_initial_beta())

    def _initial_population_random(self):
        population = []
        for _ in xrange(0, self._chromosomes):
            population.append(self._generate_random_chromosome())
        return population

    def _generate_random_chromosome(self):
        route = self.graph.nodes()
        shuffle(route)
        solution = TSPSolver.nodes_to_solution(self, route)
        TSPSolver.set_solution(self, solution[0], solution[1])
        distance = self.graph.route_distance(solution[1])
        return solution[0][:-1], distance

    def _initial_population_using_ants(self, num_ants, beta):
        def niu(u, v):
            return float(1.0 / self.graph.route_distance(
                self.graph.path_between(u, v)))

        def niu_beta(u, v):
            return math.pow(niu(u, v), beta)

        def try_to_visit_city(ant_k, r, s):
            if not ant_k.contains_city(s):
                division = niu_beta(r, s)
                divisor = 0.0
                for u in ant_k.remaining_cities():
                    divisor += niu_beta(r, u)
                probability = division / divisor
                # print 'probability', probability
                if probability > uniform(0.0, 1.0):
                    ant_k.visit_city(s)

        population = []
        ants = {}
        for a in xrange(0, num_ants):
            ants[a] = Ant(self.graph, self.graph.nodes())
        for _ in xrange(num_ants, self._chromosomes):
            population.append(self._generate_random_chromosome())
        for ant in ants.itervalues():
            ant.start()
            while len(ant.remaining_cities()) > 0:
                try_to_visit_city(ant,
                                  ant.location,
                                  ant.random_remaining_city())
            route = ant.route[:]
            solution = TSPSolver.nodes_to_solution(self, route)
            TSPSolver.set_solution(self, solution[0], solution[1])
            distance = self.graph.route_distance(solution[1])
            population.append((solution[0][:-1], distance))
        return population

    # OTHER
    def _complete_generation(self, population, new_population):
        elite = sorted(population, key=lambda tup: tup[1])[0]
        solution = TSPSolver.nodes_to_solution(self, elite[0])
        TSPSolver.set_solution(self, solution[0], solution[1])
        s_population = sorted(new_population, key=lambda tup: tup[1])[::-1]
        new_population[new_population.index(s_population[0])] = elite
        return new_population

    def _route_to_chromosome(self, route):
        solution = TSPSolver.nodes_to_solution(self, route)
        TSPSolver.set_solution(self, solution[0], solution[1])
        return solution[0][:-1], self.graph.route_distance(solution[1])
