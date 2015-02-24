# -*- coding: utf-8 -*-
import math
from random import randint
import random
import time

from graph import CoordGraph


class SimulatedAnnealingBase(object):

    '''
    Base class to implement to simulate annealing.
    '''

    def __init__(self):
        self.t = 100
        self.t_min = 1
        self.decrement = 0.99
        self.best_solution = None
        self.worst_solution = None
        self.result = {}

    def simulate(self, show_progress=False):
        initial = self.new_solution()
        self.best_solution = initial
        self.worst_solution = initial
        while self.t > 1:
            if show_progress is True:
                print "Temp. =", self.t, ' ' * 10, initial
            new_solution = self.new_solution(initial)
            diff = self.get_diff(initial, new_solution)
            if self.__accept(diff) is True:
                initial = new_solution
            self.t = self.t * self.decrement

    def __accept(self, diff):
        if diff < 0:
            return True
        else:
            if math.exp(- abs(diff) / self.t) > random.uniform(0.0, 1.0):
                return True
        return False


class TSPSA(SimulatedAnnealingBase):

    '''
    Traveling salesman problem simulated annealing implementation
    '''

    def __init__(self, graph):
        self.graph = graph
        self.start_time = None
        self.end_time = None
        super(TSPSA, self).__init__()

    def simulate(self, show_progress=False):
        self.start_time = time.clock()
        super(TSPSA, self).simulate(show_progress)
        self.end_time = time.clock()
        self.result = {'method': 'TSP simulated annealing',
                       'duration': self.end_time - self.start_time,
                       'distance': CoordGraph.route_length(self.graph, self.best_solution),
                       'route': self.best_solution}

    def new_solution(self, initial=None):
        route = []
        if initial is None:
            nodes = self.graph.nodes()
            for _ in xrange(0, len(nodes)):
                i = random.randint(0, len(nodes) - 1)
                route.append(nodes[i])
                del nodes[i]
        else:
            route = initial[:]
            del route[len(route) - 1]
            x = randint(0, len(route) - 1)
            y = x
            while y == x:
                y = randint(0, len(route) - 1)
            route[x], route[y] = route[y], route[x]
        route.append(route[0])
        return route

    def get_diff(self, initial, new_solution):
        l1 = CoordGraph.route_length(self.graph, initial)
        l2 = CoordGraph.route_length(self.graph, new_solution)
        if l2 < l1:
            self.best_solution = new_solution
        return l2 - l1

    def save(self, filename="sa"):
        g = CoordGraph.tsp_from_nodes(self.graph, self.result['route'])
        g.save(filename)

    def show(self, filename="sa"):
        g = CoordGraph.tsp_from_nodes(self.graph, self.result['route'])
        g.show(filename)
