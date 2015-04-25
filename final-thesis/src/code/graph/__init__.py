# -*- coding: utf-8 -*-
import math
import os
from random import randint

import matplotlib.pyplot as plt
import networkx as nwx


from utils import randint_exept, permutations_from_to


class Graph2D(nwx.Graph):

    """
    Base graph class.

    Nodes must contain coordinates.
    """

    def __init__(self, use_caching=True, * args, **kwargs):
        super(Graph2D, self).__init__(*args, **kwargs)
        self.coords = {}
        self.use_caching = use_caching
        self.shortest_paths = {}
        self.route_distances = {}

    def add_edge(self, u, v):
        if u == v:
            return
        super(Graph2D, self).add_edge(u, v)
        self[u][v]['distance'] = distance_between(self.node[u], self.node[v])

    def add_double_edge(self, u, v):
        if u == v:
            return
        distance = distance_between(self.node[u], self.node[v])
        super(Graph2D, self).add_edge(u, v)
        super(Graph2D, self).add_edge(v, u)
        self[u][v]['distance'] = distance
        self[v][u]['distance'] = distance

    def add_coord_node(self, n, (x, y)):
        self.add_node(n)
        self.node[n]['pos'] = (x, y)
        self.coords[n] = (x, y)

    def save(self, filename="name"):
        plt.clf()
        nwx.draw(self, self.coords)
        plt.savefig('output/' + filename + '.png')

    def show(self, filename="name"):
        plt.clf()
        nwx.draw(self, self.coords)
        plt.savefig('output/' + filename + '.png')
        os.system('eog output/' + filename + '.png &')

    def show_edges(self, filename="name"):
        plt.clf()
        nwx.draw_networkx_edges(self, self.coords)
        print 'Number of nodes:', len(self.node)
        plt.savefig('output/' + filename + '.png')
        os.system('eog output/' + filename + '.png &')

    def is_valid_route(self, route):
        last_node = None
        for next_node in route:
            if last_node is not None:
                if next_node not in self.neighbors(last_node):
                    return False
            last_node = next_node
        return True

    def path_between(self, a, b):
        path = None
        if (a, b) in self.shortest_paths.keys():
            path = self.shortest_paths[(a, b)]
        else:
            if a in self.neighbors(b):
                path = [a, b]
            else:
                path = shortest_path(self, a, b)
            if path is not None:
                self._hash_path_between_(a, b, path)
        return path

    def route_distance(self, route):
        distance = 0
        if (route) in self.route_distances.keys():
            distance = self.route_distances[tuple(route)]
        else:
            distance = route_to_distance(self, route)
            if self.use_caching:
                self.route_distances[tuple(route)] = distance
                self.route_distances[tuple(reversed(route))] = distance
        return distance

    def _hash_path_between_(self, a, b, path):
        if self.use_caching and path is not None and len(path) != 0:
            self.shortest_paths[(a, b)] = path
            self.shortest_paths[(b, a)] = list(reversed(path))

    @staticmethod
    def generate_random(node_count, edge_coeff):
        g = Graph2D()
        for n in xrange(0, node_count):
            g.add_coord_node(n, (randint(0, 1000), randint(0, 1000)))
        for n in xrange(0, node_count):
            min_nodes = 0
            if n > 0:
                min_nodes = 1
                g.add_double_edge(n, randint(0, n - 1))
            for _ in xrange(min_nodes, randint(min_nodes, edge_coeff)):
                g.add_double_edge(n, randint_exept(0, node_count - 1, n))
        return g

    @staticmethod
    def tsp_from_route(graph, route):
        g = Graph2D()
        g.add_nodes_from(graph.nodes())
        for n in range(0, len(graph.nodes())):
            g.node[n]['pos'] = graph.node[n]['pos']
            g.coords[n] = graph.coords[n]
        u = None
        for v in route:
            if u is None:
                u = v
                continue
            g.add_edge(u, v)
            u = v
        return g


# ############################################################################
#
# METHODS
#
# ############################################################################
def route_to_distance(g, nodes):
    last_node, length = None, 0
    if len(nodes) > 1:
        for node in nodes:
            if last_node is None:
                last_node = node
                continue
            length += distance_between(g.node[last_node], g.node[node])
            last_node = node
    return length


def distance_between(u, v):
    return int(math.ceil(math.sqrt(math.pow(u['pos'][0] - v['pos'][0], 2)
                                   + math.pow(u['pos'][1] - v['pos'][1], 2))))


def shortest_path_brute_force(graph, a, b):
    if a in graph.neighbors(b):
        return [a, b]
    else:
        shortest_path = None
        shortest_distance = None
        for p in permutations_from_to(graph.nodes(), a, b):
            if not graph.is_valid_route(p):
                continue
            distance = route_to_distance(graph, p)
            if (shortest_distance is None
                    or shortest_distance is not None
                    and distance < shortest_distance):
                shortest_distance = distance
                shortest_path = p
        return shortest_path


def shortest_path(graph, a, b):
    try:
        return nwx.shortest_path(graph, a, b)
    except nwx.exception.NetworkXNoPath:
        return None
