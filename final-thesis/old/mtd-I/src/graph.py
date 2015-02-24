# -*- coding: utf-8 -*-
from collections import OrderedDict
import itertools
import math
import os
from random import randint
from threading import Thread
import time

import matplotlib.pyplot as plt
import networkx as nwx


class CoordGraph(nwx.Graph):

    """
    Base graph class.

    Nodes must contain coordinates.
    """

    def __init__(self, *args, **kwargs):
        super(CoordGraph, self).__init__(*args, **kwargs)
        self.coords = {}

    def add_edge(self, u, v):
        if u == v:
            return
        super(CoordGraph, self).add_edge(u, v)
        self[u][v]['distance'] = distance_between(self.node[u], self.node[v])

    def add_coord_node(self, n, (x, y)):
        self.add_node(n)
        self.node[n]['pos'] = (x, y)
        self.coords[n] = (x, y)

    def save(self, filename="name"):
        plt.clf()
        nwx.draw(self, self.coords)
        plt.title('Economic Cost over Time')
        plt.savefig('output/tsp_graphs/' + filename + '.png')

    def show(self, filename="name"):
        nwx.draw(self, self.coords)
        plt.savefig('output/tsp_graphs/' + filename + '.png')
        os.system('eog output/tsp_graphs/' + filename + '.png &')
        # plt.show()

    def brute_force_tsp(self, use_threading=False, num_threads=1):
        start_time = time.clock()
        all_nodes = range(0, len(self.nodes()))
        combinations = itertools.permutations(all_nodes)
        combination_hash = {}
        if use_threading is False:
            for combination in combinations:
                self.__set_path_length(combination, combination_hash)
        else:
            all_combinations = []
            for combination in combinations:
                all_combinations.append(combination)
            if num_threads == 0:
                num_threads = len(all_combinations)
            for i in xrange(0, len(all_combinations), num_threads):
                thrds = []
                for j in xrange(i, i + num_threads):
                    if j >= len(all_combinations):
                        break
                    thr = Thread(
                        target=self.__set_path_length, args=[all_combinations[j], combination_hash])
                    thrds.append(thr)
                    thr.start()
                for t in thrds:
                    t.join()
        ordered_combinations = OrderedDict(
            sorted(combination_hash.items(), None, None, True))
        distance, route = ordered_combinations.popitem()
        method_name = "brute force"
        if use_threading is True:
            method_name += " with " + str(num_threads) + " threads"
        else:
            method_name += " (no threading)"
        return {'method': method_name,
                'distance': distance,
                'route': route.values()[0],
                'duration': time.clock() - start_time}

    def __set_path_length(self, nodes, hash):
        length = route_to_distance(self, nodes)
        hash[length] = {'route': list(nodes)}

    @staticmethod
    def tsp_from_nodes(graph, route):
        g = CoordGraph()
        g.add_nodes_from(graph.nodes())
        for n in range(0, len(graph.nodes())):
            g.node[n]['pos'] = graph.node[n]['pos']
            g.coords[n] = graph.coords[n]
        u = None
        for v in route:
            if u == None:
                u = v
                continue
            g.add_edge(u, v)
            u = v
        return g

    @staticmethod
    def route_length(graph, route, join_ends=False):
        length = 0
        if len(route) > 1:
            last = route[0]
            firs_iteration = False
            for n in route:
                if firs_iteration is True:
                    firs_iteration = False
                    continue
                length += distance_between(graph.node[last], graph.node[n])
                last = n
            if join_ends is True:
                length += distance_between(
                    graph.node[last], graph.node[route[0]])
        return length


def route_to_distance(g, nodes, join_ends=True):
    last_node, length = None, 0
    if len(nodes) > 1:
        for node in nodes:
            if last_node == None:
                last_node = node
                continue
            length += distance_between(g.node[last_node], g.node[node])
            last_node = node
        if join_ends is True:
            length += distance_between(g.node[last_node], g.node[nodes[0]])
    return length


def distance_between(u, v):
    return int(math.ceil(math.sqrt(math.pow(u['pos'][0] - v['pos'][0], 2) + math.pow(u['pos'][1] - v['pos'][1], 2))))
