from itertools import permutations


class Graph(object):

    def __init__(self):
        self._nodes = {}
        self._edges = {}

    def nodes(self):
        '''
        Get nodes' names from graph.
        '''
        return list(self._nodes.keys())

    def add_node(self, name):
        '''
        Add node to graph.

        :param name: Node name.
        '''
        if name not in self._nodes:
            self._nodes[name] = True

    def contains_of_nodes(self, expected_nodes='abcde'):
        '''
        Check if graph contains of given nodes.

        :param expected_nodes: Expected graph node names as string.
        '''
        nodes = self.nodes()
        expected_nodes = list(expected_nodes)
        return nodes.sort() == expected_nodes.sort() and len(nodes) == len(expected_nodes)

    def edges(self):
        '''
        Get edges from graph.
        '''
        edges = []
        for a in self._edges.keys():
            for b in self._edges[a].keys():
                edges.append((a, b, self._edges[a][b]))
        return edges

    def add_edge(self, a, b, distance):
        '''
        Add edge between two nodes.

        :param a: From node.
        :param b: To node.
        :param distance: Distance between given two nodes.
        '''
        if a != b:
            self.add_node(a)
            self.add_node(b)
            if a not in self._edges:
                self._edges[a] = {}
            self._edges[a][b] = distance

    def route_to_distance(self, nodes):
        '''
        Find distance of a given route.

        :param nodes: list of node names
        '''
        if len(nodes) <= 1 or nodes[0] not in self._edges:
            return None
        distance = 0
        last_node = nodes[0]
        for i in range(1, len(nodes)):
            node = nodes[i]
            if node not in self._edges or node not in self._edges[last_node]:
                return None
            distance += self._edges[last_node][node]
            last_node = node
        if distance == 0:
            return None
        return distance

    def find_trips_by_stops(self, a, b, max_stops, stop_at_max_only=False, return_len_of_results_only=True):
        '''
        Find all possible trips between nodes with less or equal, or exactly given max stops.

        :param a: From node.
        :param b: To node.
        :param max_stops: Maximum number of possible stops.
        :param stop_at_max_only: Accepts trips only with given maximum stops.
        '''
        results = []
        if a in self._edges:
            self._find_trips_by_stops_traverse(results, [a],
                                               a, b, max_stops, stop_at_max_only)
        if return_len_of_results_only:
            return len(results)
        else:
            if results:
                return results
            return None

    def _find_trips_by_stops_traverse(self, results, route, current_stop, final_stop, max_stops, stop_at_max_only):
        if current_stop in self._edges and not len(route) - 1 >= max_stops:
            for neighbour in self._edges[current_stop].keys():
                new_route = route[:]
                new_route.append(neighbour)
                if neighbour is final_stop and new_route not in results:
                    if stop_at_max_only and len(new_route) - 1 == max_stops:
                        results.append(new_route)
                    elif not stop_at_max_only:
                        results.append(new_route)
                self._find_trips_by_stops_traverse(results, new_route,
                                                   neighbour, final_stop,
                                                   max_stops, stop_at_max_only)

    def find_shortest_distance(self, a, b, return_distance_only=True):
        '''
        Find shortest shortest path and distance between two nodes nodes.

        :param a: From node.
        :param b: To node.
        '''
        if a not in self._nodes:
            return None
        if b not in self._nodes:
            return None
        if b in self._edges[a]:
            return self._edges[a][b]
        starting_length = 3
        if a == b:
            starting_length = 2
        if len(self._nodes) < starting_length:
            return None
        shortest_path = None
        shortest_distance = None
        for i in range(starting_length, len(self._nodes)):
            for combination in permutations(list(self._nodes.keys()), i):
                if a == b:
                    combination = list(combination) + [b]
                if combination[0] == a and combination[len(combination) - 1] == b:
                    distance = self.route_to_distance(combination)
                    if distance and (not shortest_distance or distance < shortest_distance):
                        shortest_distance = distance
                        shortest_path = combination
        if return_distance_only:
            return shortest_distance
        return shortest_path, shortest_distance

    def find_trips_by_distance(self, a, b, max_distance, return_len_of_results_only=True):
        '''
        Find all possible trips between nodes with distance less than given.

        :param a: From node.
        :param b: To node.
        :param max_distance: Max distance threshold.
        '''
        results = []
        if a in self._edges:
            self._find_trips_by_distance_traverse(results, [a],
                                                  a, b, max_distance)
        if return_len_of_results_only:
            return len(results)
        else:
            if results:
                return results
            return None

    def _find_trips_by_distance_traverse(self, results, route, current_stop, final_stop, max_distance):
        if current_stop in self._edges:
            for neighbour in self._edges[current_stop].keys():
                new_route = route[:]
                new_route.append(neighbour)
                if self.route_to_distance(new_route) < max_distance:
                    if neighbour is final_stop and new_route not in results:
                        results.append(new_route)
                    self._find_trips_by_distance_traverse(results, new_route,
                                                          neighbour, final_stop,
                                                          max_distance)
