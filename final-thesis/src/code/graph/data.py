# -*- coding: utf-8 -*-
from graph import Graph2D, osm

graph_5 = {
    'nodes': [(400, 100), (150, 220), (500, 350), (600, 950),
              (770, 80)],
    'edges': [[4], [3, 2], [3, 4]],
    'distance': 3347
}

graph_7 = {
    'nodes': [(400, 100), (444, 544), (363, 320), (600, 950),
              (770, 80), (500, 500), (200, 420)],
    'edges': [[2, 4, 5], [2, 5, 6], [5, 6], [5, 6], [5]],
    'distance': 2555
}

graph_9 = {
    'nodes': [(342, 729), (120, 101), (460, 958), (819, 490),
              (926, 517), (382, 438), (467, 615),
              (884, 148), (296, 511)],
    'edges': [[1, 2, 6, 5], [5, 7], [4, 6], [4, 6, 5, 7], [7], [8, 6, 7]],
    'distance': 3146
}

graph_15 = {'nodes': [(336, 334), (444, 464), (722, 364), (737, 298),
                      (896, 479), (803, 194), (479, 938), (809, 779),
                      (143, 101), (909, 586), (909, 64), (617, 203),
                      (746, 456), (645, 335), (628, 896)],
            'edges': [[1, 11, 6], [2, 7, 11, 12, 14], [3, 4], [5, 13],
                      [9, 10, 5], [8], [8, 14], [12, 14], [10], [12],
                      [], [13]]}

graph_23 = {'nodes': [(571, 88), (880, 321), (622, 247), (173, 232),
                      (900, 576), (858, 364), (377, 762), (310, 484),
                      (890, 894), (283, 714), (117, 125), (670, 683),
                      (569, 137), (127, 70), (697, 712), (889, 874),
                      (344, 418), (227, 489), (57, 602), (652, 892),
                      (321, 620), (407, 922), (911, 437)],
            'edges': [[12, 1, 13, 16], [12, 2, 5, 22], [12, 16, 6, 11, 4],
                      [18, 13, 10, 17, 16], [5, 22, 14, 15], [2],
                      [9, 20, 11, 14, 21, 19], [17, 16, 20], [15, 19, 14],
                      [18, 17, 20, 21], [18], [], [], [], [], [], [], [],
                      [21], [21]]}


graph_48_us = {
    'nodes': [(6734, 1453), (2233, 10), (5530, 1424), (401, 841),
              (3082, 1644), (7608, 4458), (7573, 3716), (7265, 1268),
              (6898, 1885), (1112, 2049), (5468, 2606), (5989, 2873),
              (4706, 2674), (4612, 2035), (6347, 2683), (6107, 669),
              (7611, 5184), (7462, 3590), (7732, 4723), (5900, 3561),
              (4483, 3369), (6101, 1110), (5199, 2182), (1633, 2809),
              (4307, 2322), (675, 1006), (7555, 4819), (7541, 3981),
              (3177, 756), (7352, 4506), (7545, 2801), (3245, 3305),
              (6426, 3173), (4608, 1198), (23, 2216), (7248, 3779),
              (7762, 4595), (7392, 2244), (3484, 2829), (6271, 2135),
              (4985, 140), (1916, 1569), (7280, 4899), (7509, 3239),
              (10, 2676), (6807, 2993), (5185, 3258), (3023, 1942)],
    'edges': [[8, 7, 21, 22, 2], [25, 28, 9, 4, 40, 3], [21, 22, 33, 40, 13],
              [25], [41, 47, 38, 13, 33, 24], [27, 36, 29, 35], [17, 27],
              [37, 21, 8], [39, 37, 45, 30, 22], [44, 23, 25, 41],
              [11, 12, 22, 39, 46], [14, 45, 19], [24, 46, 31],
              [24, 22, 33], [39], [40, 21, 7], [26, 42], [43, 45, 35],
              [26, 36], [32, 46, 29, 35],
              [46, 31, 12, 42], [], [], [41, 31, 47, 44],
              [], [], [], [], [33], [42, 35], [43, 37], [38], [45],
              [40], [44], [45], [], [], [47]]
}


def make_graph_from_data(data):
    g = Graph2D()
    for n in xrange(0, len(data['nodes'])):
        g.add_coord_node(n, data['nodes'][n])
    for n in xrange(0, len(data['edges'])):
        for m in data['edges'][n]:
            g.add_double_edge(n, m)
    return g


def _graph_demaker_(nodes):
    graph = Graph2D.generate_random(nodes, 2)
    _print_graph_data_(graph)
    graph.show()


def _print_graph_data_(graph):
    data = {}
    coords = []
    edges = []
    for n in graph.nodes():
        edges.append([])
        coords.append(graph.node[n]['pos'])
    data['nodes'] = coords
    for edge in graph.edges():
        edges[edge[0]].append(edge[1])
    data['edges'] = edges
    print data


def g_9():
    return make_graph_from_data(graph_9)


def g_15():
    return make_graph_from_data(graph_15)


def g_23():
    return make_graph_from_data(graph_23)


def g_48():
    return make_graph_from_data(graph_48_us)


def _simplify_graph(g):
    removed = []
    for n in g.nodes():
        if n in removed:
            continue
        ns1 = g.neighbors(n)
        if len(ns1) == 2:
            for neighbor in ns1:
                ns2 = g.neighbors(neighbor)
                ns2.remove(n)
                if len(ns2) == 1:
                    removed.append(neighbor)
                    g.remove_node(neighbor)
                    g.add_double_edge(n, ns2[0])
    ng = Graph2D()
    for n in g.nodes():
        ng.add_coord_node(n, g.node[n]['pos'])
    for edge in g.edges():
        ng.add_double_edge(edge[0], edge[1])
    return ng

'''
graph_8 = {
    'nodes': [(400, 100), (444, 544), (363, 320), (600, 950),
              (770, 80), (500, 500), (200, 420)],
    'edges': [[2, 4], [3, 5], [6], [5, 6], [5]],
    'distance': 2555
}
gr = make_graph_from_data(graph_8)
gr = _simplify_graph(gr)
gr.show('test')
'''


def vilniaus_senamiestis(use_small=True):
    if use_small:
        g = osm.read_osm("graph/vilniaus_senamiestis (smaller).osm", True)
    else:
        g = osm.read_osm("graph/vilniaus_senamiestis (bigger).osm", True)
    first_node = None
    to_delete = []
    for n in g.nodes_iter():
        if first_node is None:
            first_node = n
            continue
        if g.path_between(first_node, n) is None:
            to_delete.append(n)
    [g.remove_node(n) for n in to_delete]
    g = _simplify_graph(g)
    return g
