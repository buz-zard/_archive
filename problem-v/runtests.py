import unittest

from graph import Graph
import utils


class ListAssertMixin(unittest.TestCase):

    def _assert_lists(self, a, b):
        self.assertEqual(len(a), len(b))
        self.assertEqual(True, a.sort() == b.sort())


class TestGraph(ListAssertMixin):

    '''
    Tests for graph.Graph.
    '''

    def test_nodes1(self):
        g = Graph()
        g.add_node('a')
        g.add_node('b')
        nodes = g.nodes()
        self._assert_lists(['a', 'b'], nodes)
        self.assertEqual(False, 'c' in nodes)

    def test_nodes2(self):
        g = Graph()
        g.add_edge('a', 'b', 1)
        nodes = g.nodes()
        self._assert_lists(['a', 'b'], nodes)
        self.assertEqual(False, 'c' in nodes)

    def test_nodes3(self):
        g = Graph()
        g.add_node('a')
        g.add_node('b')
        self.assertEqual(True, g.contains_of_nodes('ab'))
        self.assertEqual(True, g.contains_of_nodes('ba'))
        self.assertEqual(False, g.contains_of_nodes('abc'))

    def test_edges(self):
        g = Graph()
        g.add_edge('a', 'b', 1)
        g.add_edge('a', 'c', 2)
        edges = g.edges()
        expected_result = [('a', 'b', 1), ('a', 'c', 2)]
        self._assert_lists(expected_result, edges)

    def test_route_to_distance(self):
        g = make_graph()
        distance1 = g.route_to_distance(list('abc'))
        self.assertEqual(9, distance1)
        distance2 = g.route_to_distance(list('ad'))
        self.assertEqual(5, distance2)
        distance3 = g.route_to_distance(list('adc'))
        self.assertEqual(13, distance3)
        distance4 = g.route_to_distance(list('aebcd'))
        self.assertEqual(22, distance4)
        distance5 = g.route_to_distance(list('aed'))
        self.assertEqual(None, distance5)

    def test_find_trips_by_stops(self):
        g = make_graph()
        result1 = g.find_trips_by_stops('c', 'c', 3, False, False)
        result1b = g.find_trips_by_stops('c', 'c', 3)
        expected_result1 = mock_trips('cebc cdc')
        self._assert_lists(expected_result1, result1)
        self.assertEqual(len(expected_result1), result1b)
        result2 = g.find_trips_by_stops('a', 'c', 4, True, False)
        expected_result2 = mock_trips('abcdc adcdc adebc')
        self._assert_lists(expected_result2, result2)
        result3 = g.find_trips_by_stops('c', 'a', 6, False, False)
        result3b = g.find_trips_by_stops('c', 'a', 6)
        self.assertEqual(None, result3)
        self.assertEqual(0, result3b)

    def test_find_shortest_distance(self):
        g = make_graph()
        self.assertEqual(9, g.find_shortest_distance('a', 'c', False)[1])
        self.assertEqual(9, g.find_shortest_distance('a', 'c'))
        self.assertEqual(9, g.find_shortest_distance('b', 'b', False)[1])
        self.assertEqual(9, g.find_shortest_distance('d', 'b', False)[1])
        self.assertEqual(12, g.find_shortest_distance('b', 'd', False)[1])
        self.assertEqual(None, g.find_shortest_distance('c', 'a', False)[1])
        self.assertEqual(None, g.find_shortest_distance('c', 'a'))

    def test_find_trips_by_distance(self):
        g = make_graph()
        result1 = g.find_trips_by_distance('c', 'c', 30, False)
        expected_result1 = mock_trips('CDC CEBC CEBCDC CDCEBC CDEBC '
                                      'CEBCEBC CEBCEBCEBC')
        self._assert_lists(expected_result1, result1)
        result2 = g.find_trips_by_distance('a', 'c', 14, False)
        result2b = g.find_trips_by_distance('a', 'c', 14)
        expected_result2 = mock_trips('abc adc')
        self._assert_lists(expected_result2, result2)
        self.assertEqual(len(expected_result2), result2b)
        self.assertEqual(None, g.find_trips_by_distance('c', 'a', 10, False))
        self.assertEqual(0, g.find_trips_by_distance('c', 'a', 10))

    def _assert_lists(self, a, b):
        self.assertEqual(len(a), len(b))
        self.assertEqual(True, a.sort() == b.sort())


class TestUtils(ListAssertMixin):

    '''
    Tests for utils module.
    '''

    def test_get_args(self):
        args1 = utils.parse_args('AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7')
        expected_args = [('a', 'b', 5),
                         ('b', 'c', 4),
                         ('c', 'd', 8),
                         ('d', 'c', 8),
                         ('d', 'e', 6),
                         ('a', 'd', 5),
                         ('c', 'e', 2),
                         ('e', 'b', 3),
                         ('a', 'e', 7)]
        self._assert_lists(expected_args, args1)
        args2 = utils.parse_args(' ,,,, AB5,,  BC4 CD8,DC8 , DE6 ,AD5 , , , CE2, '
                                 'EB3, AE7,  , ')
        self._assert_lists(expected_args, args2)
        args3 = utils.parse_args('aB05, 8f5g, BC10, CD0, gg22, cc22, ce101')
        expected_args3 = [('a', 'b', 5),
                          ('b', 'c', 10),
                          ('c', 'e', 101)]
        self._assert_lists(expected_args3, args3)


def mock_trips(arg):
    return [list(trip) for trip in arg.lower().split()]


def make_graph():
    '''
    AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
    '''
    g = Graph()
    g.add_edge('a', 'b', 5)
    g.add_edge('b', 'c', 4)
    g.add_edge('c', 'd', 8)
    g.add_edge('d', 'c', 8)
    g.add_edge('d', 'e', 6)
    g.add_edge('a', 'd', 5)
    g.add_edge('c', 'e', 2)
    g.add_edge('e', 'b', 3)
    g.add_edge('a', 'e', 7)
    return g


if __name__ == '__main__':
    unittest.main()
