# -*- coding: utf-8 -*-
from collections import namedtuple
from copy import deepcopy
import math
from random import randint
from random import uniform
import time

from graph import CoordGraph
from kd1.sa import SimulatedAnnealingBase
import networkx as nwx


class AntGraph(CoordGraph):

    """
    Graph used for ant colony system algorithm.
    """

    def __init__(self, *args, **kwargs):
        super(AntGraph, self).__init__(*args, **kwargs)

    def add_edge(self, u, v, pheromone=1.0):
        super(AntGraph, self).add_edge(u, v)
        self[u][v]['pheromone'] = pheromone

    @classmethod
    def generate_complete_graph(cls, grid_size, node_count):
        c_graph = nwx.complete_graph(node_count)
        graph = cls()
        graph.add_nodes_from(c_graph.nodes())
        for n in range(0, node_count):
            x = randint(1, grid_size[0] - 1)
            y = randint(1, grid_size[1] - 1)
            graph.node[n]['pos'] = (x, y)
            graph.coords[n] = (x, y)
        for edge in c_graph.edges():
            graph.add_edge(edge[0], edge[1])
        return graph


class Ant(object):

    """
    Ant agent class.
    """

    def __init__(self, cities):
        self.city_repo = cities
        self.route = []
        self.location = None
        self.restore_cities()

    def visit(self, c):
        self.cities.pop(c)
        self.route.append(c)
        self.location = c

    def contains(self, c):
        if c in self.cities.keys():
            return True
        else:
            return False

    def restore_cities(self):
        self.cities = dict((c, 0) for c in self.city_repo)

    def finish_loop(self):
        if len(self.route) > 1:
            self.route.append(self.route[0])


class AntColonySystem(object):

    """
    Ant colony system algorithm representation class.
    """

    def __init__(self, graph, ants, iterations, alpha, beta):
        if beta <= 0:
            raise Exception("Variable beta should be > 0 !")
        if alpha <= 0 or alpha >= 1:
            raise Exception("Variable alpha should be > 0 and < 1 !")
        self.ants = {}
        for a in range(0, ants):
            self.ants[a] = Ant(graph.nodes())
        self.graph = graph
        self.cities = graph.nodes()
        self.beta = beta
        self.alpha = alpha
        self.iterations = iterations
        self.best_route = {'method': "ACS", 'parameters': {
            'alfa': alpha, 'beta': beta, 'ants': ants, 'iterations': iterations}}

    def find_path(self):
        """
        Finds shortest ant path for given cities.
        """
        start_time = time.clock()
        city_count = 0
        for _ in range(0, self.iterations):
            for ant in self.ants.itervalues():
                ant.restore_cities()
                ant.visit(city_count % (len(self.cities) - 1))
                for _ in range(0, len(self.cities) - 1):
                    solutions = {}
                    divisor = self.__probability_divisor__(ant, ant.location)
                    for city in ant.cities.keys():
                        solutions[self._random_proportion_probability_(ant,
                                                                       ant.location, city, divisor)] = city
                    ant.visit(solutions[sorted(solutions)[len(solutions) - 1]])
                ant.finish_loop()
                city_count += 1
            self._update_pheromone_()
        self.best_route['duration'] = time.clock() - start_time

    def save(self, filename="acs"):
        g = AntGraph.tsp_from_nodes(self.graph, self.best_route['route'])
        g.save(filename)

    def show(self, filename="acs"):
        """
        Displays all nodes and shortest ant path.
        """
        g = AntGraph.tsp_from_nodes(self.graph, self.best_route['route'])
        g.show(filename)

    def _update_pheromone_(self):
        """
        Global pheromone update algorithm.
        """
        for ant in self.ants.itervalues():
            distance = 0
            u = None
            for v in ant.route:
                if u == None:
                    u = v
                    continue
                distance += int(self.graph[u][v]['distance'])
                u = v
            if 'distance' not in self.best_route.keys():
                self.best_route['distance'] = distance
                self.best_route['route'] = ant.route
            elif self.best_route['distance'] > distance:
                self.best_route['distance'] = distance
                self.best_route['route'] = ant.route
            ant.route = []

        Edge = namedtuple("Edge", ["u", "v"])
        edge_hash = {}
        for edge in self.graph.edges():
            e = Edge(edge[0], edge[1])
            if e not in edge_hash.keys():
                val1 = self._get_pheromone_(e.u, e.v)
                val2 = 0.0
                for k in self.ants.keys():
                    val2 += self._delta_pheromone_(k, e.u, e.v)
                self._set_pheromone_(e.u, e.v, val1 + val2)
                edge_hash[e] = 0
                edge_hash[Edge(edge[1], edge[0])] = 0

    def _random_proportion_probability_(self, ant_k, r, s, divisor=None):
        """
        Calculates probability for ant k in city r to visit city s.
        """
        if ant_k.contains(s):
            division = self._get_pheromone_(r, s) * \
                math.pow(self._niu_(r, s), self.beta)
            if divisor is None:
                divisor = 0.0
                for u in ant_k.cities:
                    divisor += self._get_pheromone_(r, u) * \
                        math.pow(self._niu_(r, u), self.beta)
            return division / divisor
        else:
            return 0.0

    def _get_pheromone_(self, u, v):
        return float(self.graph[u][v]['pheromone'])

    def _set_pheromone_(self, u, v, pheromone):
        self.graph[u][v]['pheromone'] = pheromone
        self.graph[v][u]['pheromone'] = pheromone

    def _niu_(self, u, v):
        return float(1.0 / self.graph[u][v]['distance'])

    def _delta_pheromone_(self, k, r, s):
        ant = self.ants[k]
        if r in ant.route and s in ant.route:
            return 1.0 / float(ant.distance)
        else:
            return 0.0

    def __probability_divisor__(self, ant_k, r):
        divisor = 0.0
        for u in ant_k.cities:
            divisor += self._get_pheromone_(r, u) * \
                math.pow(self._niu_(r, u), self.beta)
        return divisor

    def brute_force_tsp(self, use_threading=False, num_threads=1):
        return self.graph.brute_force_tsp(use_threading, num_threads)


class ACSSA(SimulatedAnnealingBase):

    '''
    Simulated annealing implementation to find best ant colony system
    parameters for given traveling salesman problem.
    '''

    def __init__(self, graph, max_ant_num=None, max_ant_iterations=None):
        self.graph = graph
        self.max_ant_num = max_ant_num
        self.max_ant_iterations = max_ant_iterations
        self.ant_num = None
        self.ant_interations = None
        self.worst_result = {}
        super(ACSSA, self).__init__()

    def simulate(self, show_progress=False):
        super(ACSSA, self).simulate(show_progress)
        self.result = self.best_solution

    def new_solution(self, initial=None):
        acs = AntColonySystem(deepcopy(self.graph),
                              self.__new_ant_num(initial),
                              self.__new_ant_iterations(initial),
                              self.__new_alfa(initial),
                              self.__new_beta(initial))
        acs.find_path()
        return acs.best_route

    def __new_alfa(self, initial=None):
        if initial is None:
            return uniform(0.0, 1.0)
        else:
            val = initial['parameters']['alfa']
            frac_m = 1.0 * 0.1
            fraction = uniform(-frac_m, frac_m)
            val += fraction
            if val <= 0.0:
                val = 0.01 + abs(fraction)
            if val >= 1.0:
                val = 1.0 - abs(fraction) - 0.01
            return val

    def __new_beta(self, initial=None):
        if initial is None:
            return uniform(0.0, 75.0)
        else:
            val = initial['parameters']['beta']
            frac_m = 75.0 * 0.1
            fraction = uniform(-frac_m, frac_m)
            val += fraction
            if val <= 0.0:
                val = 0.1 + abs(fraction)
            if val >= 75.0:
                val = 75.0 - abs(fraction) - 0.1
            return val

    def __new_ant_num(self, initial=None):
        if self.max_ant_num is None:
            return self.ant_num
        if initial is None:
            return randint(1, self.max_ant_num)
        else:
            val = initial['parameters']['ants']
            frac_m = math.ceil(self.max_ant_num * 0.1)
            fraction = randint(-frac_m, frac_m)
            val += fraction
            if val < 1:
                val = 1 + abs(fraction)
            if val >= self.max_ant_num:
                val = self.max_ant_num - abs(fraction)
            return val

    def __new_ant_iterations(self, initial=None):
        if self.max_ant_iterations is None:
            return self.ant_interations
        if initial is None:
            return randint(1, self.max_ant_iterations)
        else:
            val = initial['parameters']['iterations']
            frac_m = math.ceil(self.max_ant_iterations * 0.1)
            fraction = randint(-frac_m, frac_m)
            val += fraction
            if val < 1:
                val = 1 + abs(fraction)
            if val >= self.max_ant_iterations:
                val = self.max_ant_iterations - abs(fraction)
            return val

    def get_diff(self, initial, new_solution):
        distance1 = initial['distance']
        duration1 = initial['duration']
        distance2 = new_solution['distance']
        duration2 = new_solution['duration']

        if distance2 < self.best_solution['distance']:
            self.best_solution = new_solution
            print 'best', new_solution
        elif distance2 == self.best_solution['distance'] and duration2 < self.best_solution['duration']:
            self.best_solution = new_solution
            print 'best', new_solution

        if distance2 > self.worst_solution['distance']:
            self.worst_solution = new_solution
        elif distance2 == self.worst_solution['distance'] and duration2 > self.worst_solution['duration']:
            self.worst_solution = new_solution

        distance_dif = distance2 - distance1
        duration_dif = duration2 - duration1
        distance_dif_percent, duration_dif_percent = 0.0, 0.0
        if distance_dif != 0:
            distance_dif_percent = distance_dif / (distance1 / 100)
        if duration_dif != 0:
            duration_dif_percent = duration_dif / (duration1 / 100)

        if distance_dif_percent != 0:
            return distance_dif
        elif duration_dif_percent < 0:
            return duration_dif_percent
        else:
            return 0
