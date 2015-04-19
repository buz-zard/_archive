# -*- coding: utf-8 -*-
from itertools import permutations

from graph import shortest_path

from tsp import TSPSolver


class BruteForce(TSPSolver):

    """
    TSP brute force solver
    """

    def __init__(self, graph, conditions={}):
        TSPSolver.__init__(self, graph, conditions)
        self.solution_canceled = False

    def get_solution(self):
        self.display_method = "Brute force"
        self.display_parameters = {
            'solution_canceled': self.solution_canceled
        }
        return TSPSolver.get_solution(self)

    def solve(self, show_progress=False):
        self.show_progress = show_progress
        TSPSolver.start_solving(self)
        all_nodes = range(0, len(self.graph.nodes()))
        combinations = permutations(all_nodes)
        for combination in combinations:
            TSPSolver.update_progress(self,
                                      "Combination - %s" % (combination,))
            if TSPSolver.conditions_met(self):
                self.solution_canceled = True
                break
            last_node = combination[0]
            full_route = [last_node]
            for i in xrange(1, len(combination)):
                next_node = combination[i]
                full_route += shortest_path(
                    self.graph, last_node, next_node)[1:]
                last_node = next_node
            route = list(combination)
            route.append(combination[0])
            full_route += shortest_path(
                self.graph, last_node, combination[0])[1:]
            TSPSolver.set_solution(self, route, full_route)
        TSPSolver.end_solving(self)
