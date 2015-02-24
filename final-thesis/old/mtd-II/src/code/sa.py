# -*- coding: utf-8 -*-
import math
from random import randint, uniform
import random

from tsp import TSPSolver

from utils import randint_exept


_defaul_params_ = {
    'initial_temp': 100,
    'decrement': 0.99
}


class SimulatedAnnealing(TSPSolver):

    '''
    Traveling salesman problem simulated annealing implementation
    '''

    def __init__(self, graph, params=_defaul_params_, conditions={}, absolute_mute=False):
        TSPSolver.__init__(self, graph, conditions, absolute_mute)
        self.initial_temp = _defaul_params_['initial_temp']
        self.decrement = _defaul_params_['decrement']
        self.t = self.initial_temp

    def get_solution(self):
        self.display_method = "Simulated annealing"
        self.display_parameters = {
            'initial_temp': self.initial_temp,
            'decrement': self.decrement
        }
        return TSPSolver.get_solution(self)

    def solve(self, show_progress=False):
        self.show_progress = show_progress
        TSPSolver.start_solving(self)
        last_solution = self._new_solution_()
        while not self._conditions_met_():
            TSPSolver.update_progress(self,
                                      "> Temp. = {0}, last solution - {1}".
                                      format(round(self.t, 3), last_solution))
            new_solution = self._new_solution_(last_solution)
            TSPSolver.set_solution(self, new_solution[0], new_solution[1])
            if self._accept_(last_solution, new_solution) is True:
                last_solution = new_solution
            self.t = self.t * self.decrement
        TSPSolver.end_solving(self)

    def _conditions_met_(self):
        if TSPSolver.conditions_met(self) is True:
            return True
        if self.t <= 1:
            return True
        return False

    def _new_solution_(self, last_solution=None):
        nodes = []
        if last_solution is None:
            for node in self.graph.nodes():
                nodes.insert(randint(0, len(nodes)), node)
        else:
            if uniform(0.0, 1.0) < 0.5:
                nodes = _reproduce_reverse_(last_solution[0])
            else:
                nodes = _reproduce_insert_(last_solution[0])
        return self.nodes_to_solution(nodes)

    def _accept_(self, last_solution, new_solution):
        diff = self._get_diff_(last_solution, new_solution)
        if diff < 0:
            return True
        else:
            if math.exp(- abs(diff) / self.t) < random.uniform(0.0, 1.0):
                return True
        return False

    def _get_diff_(self, last_solution, new_solution):
        l1 = self.graph.route_distance(last_solution[1])
        l2 = self.graph.route_distance(new_solution[1])
        return l2 - l1


def _reproduce_reverse_(items):
    cross_range = randint(2, len(items) - 2)
    point = randint(1, len(items) - cross_range - 1)
    new_items = items[:point], items[
        point:point + cross_range], items[point + cross_range:]
    return new_items[0] + new_items[1][::-1] + new_items[2]


def _reproduce_insert_(items):
    cross_range = randint(2, len(items) - 2)
    point = randint(1, len(items) - cross_range - 1)
    new_items = items[:point] + items[point + cross_range:]
    sub_tour = items[point:point + cross_range]
    new_point = randint_exept(0, point, len(new_items))
    return new_items[:new_point] + sub_tour + new_items[new_point:]
