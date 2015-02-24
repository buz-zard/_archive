# -*- coding: utf-8 -*-
import math
from random import randint, uniform

from graph import Graph2D
from tsp import TSPSolver
from utils import list_diff, is_sublist


class AntGraph(Graph2D):

    """
    Graph used for ant colony system algorithm.
    """

    def __init__(self, graph, pheromone=1.0):
        Graph2D.__init__(self)
        for node in graph.nodes():
            self.add_coord_node(node, graph.node[node]['pos'])
        for edge in graph.edges():
            self.add_edge(edge[0], edge[1], pheromone)

    def add_edge(self, u, v, pheromone=1.0):
        Graph2D.add_edge(self, u, v)
        self[u][v]['pheromone'] = pheromone

    def add_double_edge(self, u, v, pheromone=1.0):
        Graph2D.add_double_edge(self, u, v)
        self[u][v]['pheromone'] = pheromone
        self[v][u]['pheromone'] = pheromone


class Ant(object):

    """
    Ant agent class.
    """

    def __init__(self, graph, cities):
        self.graph = graph
        self.all_cities = cities
        self.start()

    def start(self):
        self.route = []
        self.full_route = []
        self.location = None
        self.visit_city(self.random_remaining_city())

    def visit_city(self, city):
        if self.location is not None:
            path = self.graph.path_between(self.location, city)[1:]
            for node in path:
                if node not in self.route:
                    self.route.append(node)
            self.full_route += self.graph.path_between(self.location, city)[1:]
        else:
            self.route.append(city)
            self.full_route.append(city)
        self.location = city

    def contains_city(self, city):
        if city in self.full_route:
            return True
        return False

    def remaining_cities(self):
        return list_diff(self.all_cities, self.full_route)

    def random_remaining_city(self):
        return self.remaining_cities()[
            randint(0, len(self.remaining_cities()) - 1)]

    def finish(self):
        self.visit_city(self.route[0])
        self.route.append(self.route[0])


class AntColonySystem(TSPSolver):

    """
    Ant colony system algorithm representation class.
    """

    def __init__(self, graph, params, conditions, absolute_mute=False):
        TSPSolver.__init__(self, AntGraph(graph), conditions, absolute_mute)
        if params['beta'] <= 0:
            raise Exception("Variable beta should be > 0 !")
        if params['alpha'] <= 0 or params['alpha'] >= 1:
            raise Exception("Variable alpha should be > 0 and < 1 !")
        if params['ro'] <= 0 or params['ro'] >= 1:
            raise Exception("Variable ro should be > 0 and < 1 !")
        # Relative importance between pheromone and distance (>0)
        self.beta = params['beta']
        # Global pheromone decay (0, 1)
        self.alpha = params['alpha']
        # Local pheromone decay (0, 1)
        self.ro = params['ro']
        self.tau0 = params['tau0']
        self.ants = {}
        self.last_ants = {}
        for a in xrange(0, params['ants']):
            self.ants[a] = Ant(graph, graph.nodes())
        self._tau_hash_ = {}
        self._niu_hash_ = {}
        self._tau_niu_beta_hash_ = {}

    def get_solution(self):
        self.display_method = "Ant colony system"
        self.display_parameters = {
            'alpha': self.alpha,
            'beta': self.beta,
            'ro': self.ro,
            'ants': len(self.ants.keys())
        }
        return TSPSolver.get_solution(self)

    def solve(self, show_progress=False):
        self.show_progress = show_progress
        TSPSolver.start_solving(self)
        while not self._conditions_met_():
            TSPSolver.update_progress(self)
            best_ant = None
            best_distance = None
            for ant in self.ants.itervalues():
                ant.start()
                while len(ant.remaining_cities()) > 0:
                    self._try_to_visit_(ant,
                                        ant.location,
                                        ant.random_remaining_city())
                ant.finish()
                distance = self.graph.route_distance(ant.full_route)
                if best_distance is None or best_distance > distance:
                    best_ant = ant
                    best_distance = distance
                TSPSolver.set_solution(self, ant.route, ant.full_route)
            self._update_pheromone_(best_ant, best_distance)
        TSPSolver.end_solving(self)

        edge_hash = {}
        for u, v in self.graph.edges():
            if (u, v) not in edge_hash:
                edge_hash[(u, v)] = True
                edge_hash[(v, u)] = True

    def _conditions_met_(self):
        return TSPSolver.conditions_met(self)

    def _try_to_visit_(self, ant_k, r, s):
        """
        Random proportional rule

        Calculates probability for ant k in city r to visit city s and
        visits it.
        """
        if not ant_k.contains_city(s):
            division = self._tau_niu_beta_(r, s)
            divisor = 0.0
            for u in ant_k.remaining_cities():
                divisor += self._tau_niu_beta_(r, u)
            probability = division / divisor
            if probability > uniform(0.0, 1.0):
                ant_k.visit_city(s)

    def _update_pheromone_(self, best_ant, best_distance):
        edge_hash = {}
        for u, v in self.graph.edges():
            if (u, v) not in edge_hash:
                edge_hash[(u, v)] = True
                edge_hash[(v, u)] = True
                # Local update
                pheromone = self._local_pheromone_(u, v)
                # Global update
                is_best = is_sublist(best_ant.full_route, [u, v]) \
                    or is_sublist(best_ant.full_route, [v, u])
                pheromone = self._global_pheromone_(pheromone,
                                                    is_best,
                                                    best_distance)
                self.graph[u][v]['pheromone'] = pheromone
                self.graph[v][u]['pheromone'] = pheromone

    def _local_pheromone_(self, u, v):
        return (1.0 - self.ro) * self.graph[u][v]['pheromone'] + \
            self.ro * self.tau0

    def _global_pheromone_(self, pheromone, is_best, best_distance):
        delta_tau = 0.0
        if is_best:
            delta_tau = 1.0 / best_distance
        return (1.0 - self.alpha) * pheromone + self.alpha * delta_tau

    def _tau_(self, u, v):
        if (u, v) not in self._tau_hash_.keys():
            pheromone_sum = 0.0
            path = self.graph.path_between(u, v)
            last_node = None
            for node in path:
                if last_node is None:
                    last_node = node
                    continue
                pheromone_sum += self.graph[last_node][node]['pheromone']
                last_node = node
            val = float(pheromone_sum / (len(path) - 1))
            self._tau_hash_[(u, v)] = val
            self._tau_hash_[(v, u)] = val
        return self._tau_hash_[(u, v)]

    def _niu_(self, u, v):
        if (u, v) not in self._niu_hash_.keys():
            niu = float(1.0 /
                        self.graph.route_distance(self.graph.path_between(u,
                                                                          v)))
            self._niu_hash_[(u, v)] = niu
            self._niu_hash_[(v, u)] = niu
        return self._niu_hash_[(u, v)]

    def _tau_niu_beta_(self, u, v):
        if (u, v) not in self._tau_niu_beta_hash_.keys():
            value = self._tau_(u, v) * \
                math.pow(self._niu_(u, v), self.beta)
            self._tau_niu_beta_hash_[(u, v)] = value
            self._tau_niu_beta_hash_[(v, u)] = value
        return self._tau_niu_beta_hash_[(u, v)]
