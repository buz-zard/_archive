# -*- coding: utf-8 -*-
from random import randint

from utils import list_diff


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
