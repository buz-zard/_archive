# -*- coding: utf-8 -*-
import xml.sax
import copy
from graph import Graph2D


def read_osm(filename_or_stream, only_roads=True):
    """Read graph in OSM format from file specified by name or by stream object.

    Parameters
    ----------
    filename_or_stream : filename or stream object

    Returns
    -------
    G : Graph

    Examples
    --------
    >>> G=nx.read_osm(nx.download_osm(-122.33,47.60,-122.31,47.61))
    >>> plot([G.node[n]['data'].lat for n in G],
             [G.node[n]['data'].lon for n in G], ',')

    """
    osm = OSM(filename_or_stream)
    G = Graph2D()
    G2 = Graph2D()

    for w in osm.ways.itervalues():
        if only_roads and 'highway' not in w.tags:
            continue
        G.add_path(w.nds, id=w.id, data=w)
        G.add_path(w.nds[::-1], id=w.id, data=w)
    for n_id in G.nodes_iter():
        n = osm.nodes[n_id]
        G.node[n_id] = dict(data=n)
        G2.add_coord_node(n.id, (int(n.lat * 10000000), int(n.lon * 10000000)))
    for n_id in G.nodes_iter():
        for neighbor in G.neighbors(n_id):
            G2.add_double_edge(n_id, neighbor)
    return G2


class Node:

    def __init__(self, _id, lon, lat):
        self.id = _id
        self.lon = lon
        self.lat = lat
        self.tags = {}


class Way:

    def __init__(self, _id, osm):
        self.osm = osm
        self.id = _id
        self.nds = []
        self.tags = {}

    def split(self, dividers):
        def slice_array(ar, dividers):
            for i in range(1, len(ar) - 1):
                if dividers[ar[i]] > 1:
                    left = ar[:i + 1]
                    right = ar[i:]
                    rightsliced = slice_array(right, dividers)
                    return [left] + rightsliced
            return [ar]

        slices = slice_array(self.nds, dividers)
        ret = []
        i = 0
        for _slice in slices:
            littleway = copy.copy(self)
            littleway.id += "-%d" % i
            littleway.nds = _slice
            ret.append(littleway)
            i += 1
        return ret


class OSM:

    def __init__(self, filename_or_stream):
        """ File can be either a filename or stream/file object."""
        nodes = {}
        ways = {}

        superself = self

        class OSMHandler(xml.sax.ContentHandler):

            @classmethod
            def setDocumentLocator(self, loc):
                pass

            @classmethod
            def startDocument(self):
                pass

            @classmethod
            def endDocument(self):
                pass

            @classmethod
            def startElement(self, name, attrs):
                if name == 'node':
                    self.currElem = Node(
                        attrs['id'], float(attrs['lon']), float(attrs['lat']))
                elif name == 'way':
                    self.currElem = Way(attrs['id'], superself)
                elif name == 'tag':
                    self.currElem.tags[attrs['k']] = attrs['v']
                elif name == 'nd':
                    self.currElem.nds.append(attrs['ref'])

            @classmethod
            def endElement(self, name):
                if name == 'node':
                    nodes[self.currElem.id] = self.currElem
                elif name == 'way':
                    ways[self.currElem.id] = self.currElem

            @classmethod
            def characters(self, chars):
                pass

        xml.sax.parse(filename_or_stream, OSMHandler)

        self.nodes = nodes
        self.ways = ways

        # count times each node is used
        node_histogram = dict.fromkeys(self.nodes.keys(), 0)
        for way in self.ways.values():
            # if a way has only one node, delete it out of the osm collection
            if len(way.nds) < 2:
                del self.ways[way.id]
            else:
                for node in way.nds:
                    node_histogram[node] += 1

        # use that histogram to split all ways, replacing the member set of
        # ways
        new_ways = {}
        for _, way in self.ways.iteritems():
            split_ways = way.split(node_histogram)
            for split_way in split_ways:
                new_ways[split_way.id] = split_way
        self.ways = new_ways
