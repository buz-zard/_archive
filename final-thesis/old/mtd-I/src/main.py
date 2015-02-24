# -*- coding: utf-8 -*-
from matplotlib.font_manager import FontProperties
from pylab import figure, savefig, axis, tight_layout

from ant import AntGraph, AntColonySystem, ACSSA
from sa import TSPSA
import tsp_benchmark

OPTIMAL_ANTS = 10
OPTIMAL_ANT_ITERATIONS = 1


def test_brute_force_tsp():
    print '\ntest_brute_force_tsp pradžia'
    for t in xrange(2, 9):
        print t, 'gijos'
        bf, bft = [], []
        cities = xrange(4, 11)
        for i in cities:
            print '\t', i, 'miestai'
            graph = AntGraph.generate_complete_graph([1000, 1000], i)
            bf.append(graph.brute_force_tsp()['duration'])
            bft.append(graph.brute_force_tsp(True, t)['duration'])

        max_x = bft[len(bft) - 1]
        if max_x < bf[len(bf) - 1]:
            max_x = bf[len(bf) - 1]

        fig = figure(t + 100)
        g1 = fig.add_subplot(211)
        g1.set_title(u'Perrinkimo metodas (be giju)')
        g1.set_xlabel(u'Miestu skaičius')
        g1.set_ylabel(u'Laikas (s)')
        g1.axis([cities[0], cities[len(cities) - 1], 0, max_x])
        g1.plot(cities, bf)
        g1.grid(True)

        g2 = fig.add_subplot(212)
        g2.set_title(u'Perrinkimo metodas (' + str(t) + ' gijos)')
        g2.set_xlabel(u'Miestu skaičius')
        g2.set_ylabel(u'Laikas (s)')
        g2.axis([cities[0], cities[len(cities) - 1], 0, max_x])
        g2.plot(cities, bft)
        g2.grid(True)
        tight_layout()
        savefig(
            'output/test_brute_force_tsp/test_brute_force_tsp_' + str(t) + '_threads.png')
    print 'test_brute_force_tsp pabaiga\n'


def test_acs_sa_wrapper():
    print '\ntest_acs_sa_wrapper pradžia'
    rez_dist_b, rez_dist_w = [], []
    rez_dura_b, rez_dura_w = [], []
    rez_ants_b, rez_ants_w = [], []
    rez_iter_b, rez_iter_w = [], []
    rez_alfa_b, rez_alfa_w = [], []
    rez_beta_b, rez_beta_w = [], []

    cities = [6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 25, 30]
    for c in cities:
        print '\t', c, 'miestai'
        graph = AntGraph.generate_complete_graph((1000, 1000), c)
        acsim = ACSSA(graph, 30, 60)
        acsim.decrement = 0.99
        acsim.simulate(True)
        print '\t\tworst', acsim.worst_solution
        print '\t\tbest', acsim.best_solution

        rez_dist_b.append(acsim.best_solution['distance'])
        rez_dist_w.append(acsim.worst_solution['distance'])

        rez_dura_b.append(acsim.best_solution['duration'])
        rez_dura_w.append(acsim.worst_solution['duration'])

        rez_ants_b.append(acsim.best_solution['parameters']['ants'])
        rez_ants_w.append(acsim.worst_solution['parameters']['ants'])

        rez_iter_b.append(acsim.best_solution['parameters']['iterations'])
        rez_iter_w.append(acsim.worst_solution['parameters']['iterations'])

        rez_alfa_b.append(acsim.best_solution['parameters']['alfa'])
        rez_alfa_w.append(acsim.worst_solution['parameters']['alfa'])

        rez_beta_b.append(acsim.best_solution['parameters']['beta'])
        rez_beta_w.append(acsim.worst_solution['parameters']['beta'])

    fig = figure(200)
    g1 = fig.add_subplot(211)
    g1.set_title(
        u'Geriausio (žalia) ir blogiausio (raudona) maršruto algoritmo atstumai.')
    g1.set_xlabel(u'Miestu skaičius')
    g1.set_ylabel(u'Atstumas')
    g1.plot(cities, rez_dist_b, 'g-', cities, rez_dist_w, 'r--')
    g1.grid(True)

    g2 = fig.add_subplot(212)
    g2.set_title(
        u'Geriausio (žalia) ir blogiausio (raudona) maršruto algoritmo laikai.')
    g2.set_xlabel(u'Miestu skaičius')
    g2.set_ylabel(u'Laikas (s)')
    g2.plot(cities, rez_dura_b, 'g-', cities, rez_dura_w, 'r--')
    g2.grid(True)
    tight_layout()
    savefig('output/test_acs_sa_wrapper/test_acs_sa_wrapper_dist_dur.png')

    fig = figure(201)
    g1 = fig.add_subplot(211)
    g1.set_title(
        u'Geriausio (žalia) ir blogiausio (raudona) maršruto algoritmo skrudeliu skaičius.')
    g1.set_xlabel(u'Miestu skaičius')
    g1.set_ylabel(u'Skruzdeles')
    g1.axis([cities[0], cities[len(cities) - 1], 0.0, OPTIMAL_ANTS * 2])
    g1.plot(cities, rez_ants_b, 'g-', cities, rez_ants_w, 'r--')
    g1.grid(True)

    g2 = fig.add_subplot(212)
    g2.set_title(
        u'Geriausio (žalia) ir blogiausio (raudona) maršruto algoritmo iteraciju skaičius.')
    g2.set_xlabel(u'Miestu skaičius')
    g2.set_ylabel(u'Iteracijos')
    g2.axis(
        [cities[0], cities[len(cities) - 1], 0.0, OPTIMAL_ANT_ITERATIONS * 2])
    g2.plot(cities, rez_iter_b, 'g-', cities, rez_iter_w, 'r--')
    g2.grid(True)
    tight_layout()
    savefig('output/test_acs_sa_wrapper/test_acs_sa_wrapper_ant_iter.png')

    fig = figure(202)
    g1 = fig.add_subplot(211)
    g1.set_title(
        u'Geriausio (žalia) ir blogiausio (raudona) maršruto algoritmo alfa parametras.')
    g1.set_xlabel(u'Miestu skaičius')
    g1.set_ylabel(u'alfa')
    g1.axis([cities[0], cities[len(cities) - 1], 0.0, 1.0])
    g1.plot(cities, rez_alfa_b, 'g-', cities, rez_alfa_w, 'r--')
    g1.grid(True)

    g2 = fig.add_subplot(212)
    g2.set_title(
        u'Geriausio (žalia) ir blogiausio (raudona) maršruto algoritmo beta parametras.')
    g2.set_xlabel(u'Miestu skaičius')
    g2.set_ylabel(u'beta')
    g2.axis([cities[0], cities[len(cities) - 1], 0.0, 75.0])
    g2.plot(cities, rez_beta_b, 'g-', cities, rez_beta_w, 'r--')
    g2.grid(True)
    tight_layout()
    savefig('output/test_acs_sa_wrapper/test_acs_sa_wrapper_alfa_beta.png')
    print 'test_acs_sa_wrapper pabaiga\n'


def test_acs_sa_wrapper2():
    print '\ntest_acs_sa_wrapper2 pradžia'
    rez_dist_b, rez_dist_w = [], []
    rez_dura_b, rez_dura_w = [], []
    rez_alfa_b, rez_alfa_w = [], []
    rez_beta_b, rez_beta_w = [], []

    cities = [6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 25, 30]
    for c in cities:
        print '\t', c, 'miestai'
        graph = AntGraph.generate_complete_graph((1000, 1000), c)
        acsim = ACSSA(graph)
        acsim.ant_num = OPTIMAL_ANTS
        acsim.ant_interations = OPTIMAL_ANT_ITERATIONS
        acsim.decrement = 0.99
        acsim.simulate(True)
        print '\t\tworst', acsim.worst_solution
        print '\t\tbest', acsim.best_solution

        rez_dist_b.append(acsim.best_solution['distance'])
        rez_dist_w.append(acsim.worst_solution['distance'])

        rez_dura_b.append(acsim.best_solution['duration'])
        rez_dura_w.append(acsim.worst_solution['duration'])

        rez_alfa_b.append(acsim.best_solution['parameters']['alfa'])
        rez_alfa_w.append(acsim.worst_solution['parameters']['alfa'])

        rez_beta_b.append(acsim.best_solution['parameters']['beta'])
        rez_beta_w.append(acsim.worst_solution['parameters']['beta'])

    fig = figure(300)
    g1 = fig.add_subplot(211)
    g1.set_title(
        u'Geriausio (žalia) ir blogiausio (raudona) maršruto algoritmo atstumai.')
    g1.set_xlabel(u'Miestu skaičius')
    g1.set_ylabel(u'Atstumas')
    g1.plot(cities, rez_dist_b, 'g-', cities, rez_dist_w, 'r--')
    g1.grid(True)

    g2 = fig.add_subplot(212)
    g2.set_title(
        u'Geriausio (žalia) ir blogiausio (raudona) maršruto algoritmo laikai.')
    g2.set_xlabel(u'Miestu skaičius')
    g2.set_ylabel(u'Laikas (s)')
    g2.plot(cities, rez_dura_b, 'g-', cities, rez_dura_w, 'r--')
    g2.grid(True)
    tight_layout()
    savefig('output/test_acs_sa_wrapper2/test_acs_sa_wrapper_dist_dur.png')

    fig = figure(301)
    g1 = fig.add_subplot(211)
    g1.set_title(
        u'Geriausio (žalia) ir blogiausio (raudona) maršruto algoritmo alfa parametras.')
    g1.set_xlabel(u'Miestu skaičius')
    g1.set_ylabel(u'alfa')
    g1.axis([cities[0], cities[len(cities) - 1], 0.0, 1.0])
    g1.plot(cities, rez_alfa_b, 'g-', cities, rez_alfa_w, 'r--')
    g1.grid(True)

    g2 = fig.add_subplot(212)
    g2.set_title(
        u'Geriausio (žalia) ir blogiausio (raudona) maršruto algoritmo beta parametras.')
    g2.set_xlabel(u'Miestu skaičius')
    g2.set_ylabel(u'beta')
    g2.axis([cities[0], cities[len(cities) - 1], 0.0, 75.0])
    g2.plot(cities, rez_beta_b, 'g-', cities, rez_beta_w, 'r--')
    g2.grid(True)
    tight_layout()
    savefig('output/test_acs_sa_wrapper2/test_acs_sa_wrapper_alfa_beta.png')
    print 'test_acs_sa_wrapper2 pabaiga\n'


def test_sa_tsp_wrapper():
    print '\ntest_sa_tsp_wrapper pradžia'
    cities = xrange(10, 101, 5)
    decrements = [0.99, 0.999, 0.9999]
    distances = {}
    distances_percent = {}
    times = {}
    i = 0
    for c in cities:
        print '\t', c, 'miestai'
        g = AntGraph.generate_complete_graph([1000, 1000], c)
        tspsa = TSPSA(g)
        shortest_dist = None
        for t in decrements:
            if t not in distances.keys():
                distances[t] = []
            if t not in times.keys():
                times[t] = []
            sasim = TSPSA(g)
            sasim.decrement = t
            sasim.simulate()

            dist = sasim.result['distance']
            distances[t].append(dist)
            times[t].append(sasim.result['duration'])
            if shortest_dist is None:
                shortest_dist = dist
            else:
                if dist < shortest_dist:
                    shortest_dist = dist
        for t in decrements:
            if t not in distances_percent.keys():
                distances_percent[t] = []
            t_dist = distances[t][i]
            val = int(t_dist / (shortest_dist / 100.0)) / 100.0
            distances_percent[t].append(val)
        i += 1

    fig = figure(400)
    g1 = fig.add_subplot(321)
    g1.set_title(u'SA rasti atstumai')
    g1.set_xlabel(u'Miestu skaičius')
    g1.set_ylabel(u'Atstumas')
    for tk in decrements:
        g1.plot(cities, distances[tk])
    g1.grid(True)

    g3 = fig.add_subplot(322)
    g3.set_title(u'SA atstumu tarpusavio palyginimai')
    g3.set_xlabel(u'Miestu skaičius')
    g3.set_ylabel(u'Santykis')
    for tk in decrements:
        g3.plot(cities, distances_percent[tk])
    g3.grid(True)

    g2 = fig.add_subplot(323)
    g2.set_title(u'SA algoritmo skaičiavimo laikai')
    g2.set_xlabel(u'Miestu skaičius')
    g2.set_ylabel(u'Laikas (s)')
    for tk in decrements:
        prc = 100.0 - tk * 100.0
        lbl = "Temperaturos mazejimas po " + str(prc) + "%"
        g2.plot(cities, times[tk], label=unicode(lbl))
        fontP = FontProperties()
        fontP.set_size('small')
        box2 = g2.get_position()
        g2.set_position([box2.x0, box2.y0, box2.width * 0.8, box2.height])
        lgd = g2.legend(prop=fontP, loc='center left', bbox_to_anchor=(1, 0.5))
    g2.grid(True)

    tight_layout()
    savefig('output/test_sa_tsp_wrapper/test_sa_tsp_wrapper.png',
            bbox_extra_artists=(lgd,), bbox_inches='tight')
    print 'test_sa_tsp_wrapper pabaiga\n'


def test_acs_sa_bf():
    print '\ntest_acs_sa_bf pradžia'
    bf = {'name': 'Pilnas perrinkimas'}
    acs = {'name': 'Skruzdeliu kolonijos'}
    sa = {'name': 'Simuliuoto \"atkaitinimo\"'}
    methods = [bf, acs, sa]
    cities = [6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 25, 30, 40, 50, 60, 80, 100]

    bf_dist = []
    bf_dur = []
    acs_dist = []
    acs_dur = []
    sa_dist = []
    sa_dur = []

    for c in cities:
        print '\t', c, 'miestai'
        g = AntGraph.generate_complete_graph([1000, 1000], c)
        if c <= 10:
            bf_rez = g.brute_force_tsp()
            bf_dist.append(bf_rez['distance'])
            bf_dur.append(bf_rez['duration'])

        acs_sim = AntColonySystem(
            g, OPTIMAL_ANTS, OPTIMAL_ANT_ITERATIONS, 0.5, 20)
        acs_sim.find_path()
        acs_rez = acs_sim.best_route
        acs_dist.append(acs_rez['distance'])
        acs_dur.append(acs_rez['duration'])

        sasim = TSPSA(g)
        sasim.t = 1000
        sasim.decrement = 0.9995
        sasim.simulate()
        sa_rez = sasim.result
        sa_dist.append(sa_rez['distance'])
        sa_dur.append(sa_rez['duration'])

    bf['distance'] = bf_dist
    bf['duration'] = bf_dur

    acs['distance'] = acs_dist
    acs['duration'] = acs_dur

    sa['distance'] = sa_dist
    sa['duration'] = sa_dur

    fig = figure(500)
    g1 = fig.add_subplot(211)
    g1.set_title(u'Rasti trumpiausi marsrutai')
    g1.set_xlabel(u'Miestu skaičius')
    g1.set_ylabel(u'Atstumas')
    for m in methods:
        lbl = "Paieskos metodas: " + m['name']
        g1.plot(cities[:len(m['distance'])], m['distance'], label=unicode(lbl))
        fontP = FontProperties()
        fontP.set_size('small')
        box1 = g1.get_position()
        g1.set_position([box1.x0, box1.y0, box1.width * 0.8, box1.height])
        lgd = g1.legend(prop=fontP, loc='center left', bbox_to_anchor=(1, 0.5))
    g1.grid(True)

    g2 = fig.add_subplot(212)
    g2.set_title(u'Algoritmu skaičiavo laikai')
    g2.set_xlabel(u'Miestu skaičius')
    g2.set_ylabel(u'Laikas (s)')
    for m in methods:
        lbl = "Paieskos metodas: " + m['name']
        g2.plot(cities[:len(m['duration'])], m['duration'], label=unicode(lbl))
        fontP = FontProperties()
        fontP.set_size('small')
        box2 = g2.get_position()
        g2.set_position([box2.x0, box2.y0, box2.width * 0.8, box2.height])
        lgd = g2.legend(prop=fontP, loc='center left', bbox_to_anchor=(1, 0.5))
    g2.grid(True)
    tight_layout()
    savefig('output/test_acs_sa_bf/test_acs_sa_bf.png',
            bbox_extra_artists=(lgd,), bbox_inches='tight')

    fig = figure(501)
    g3 = fig.add_subplot(111)
    g3.set_title(u'Algoritmu skaičiavo laikai')
    g3.set_xlabel(u'Miestu skaičius')
    g3.set_ylabel(u'Laikas (s)')
    for m in methods:
        if m['name'] == bf['name']:
            continue
        lbl = "Paieskos metodas: " + m['name']
        g3.plot(cities[:len(m['duration'])], m['duration'], label=unicode(lbl))
        fontP = FontProperties()
        fontP.set_size('small')
        box3 = g3.get_position()
        g3.set_position([box3.x0, box3.y0, box3.width * 0.8, box3.height])
        lgd = g3.legend(prop=fontP, loc='center left', bbox_to_anchor=(1, 0.5))
    g3.grid(True)
    tight_layout()
    savefig('output/test_acs_sa_bf/test_acs_sa_bf2.png',
            bbox_extra_artists=(lgd,), bbox_inches='tight')
    print 'test_acs_sa_bf pabaiga\n'


def test_benchmarks():
    print '\ntest_benchmarks pradžia'
    g = tsp_benchmark.make_us_48_graph()

    acs_sim_best = None
    sa_sim_best = None
    sa_sim_worst = None
    for _ in xrange(0, 10):
        print _, 'iteracija'
        acs_sim = AntColonySystem(g, 10, 10, 0.5, 20)
        acs_sim.find_path()

        sasim = TSPSA(g)
        sasim.t = 1000
        sasim.decrement = 0.9995
        sasim.simulate()

        if acs_sim_best is None:
            acs_sim_best = acs_sim
        elif acs_sim_best.best_route['distance'] > acs_sim.best_route['distance']:
            acs_sim_best = acs_sim

        if sa_sim_best is None:
            sa_sim_best = sasim
        elif sa_sim_best.result['distance'] > sasim.result['distance']:
            sa_sim_best = sasim

        if sa_sim_worst is None:
            sa_sim_worst = sasim
        elif sa_sim_worst.result['distance'] < sasim.result['distance']:
            sa_sim_worst = sasim
        print 'acs ->', acs_sim.best_route
        print 'sa ->', sasim.result

    acs_sim_best.save('us_48_acs')
    sa_sim_best.save('us_48_sa')
    sg = AntGraph.tsp_from_nodes(
        g, tsp_benchmark.get_us_states_48_solution())
    sg.save('us_48_optimal')

    print '\n48 valstijos. Skruzdeliu kolonija:', acs_sim_best.best_route
    print '48 valstijos. Simuliuotas atkaitinimas:', sa_sim_best.result
    print '48 valstijos. Simuliuotas atkaitinimas blogiausias:', sa_sim_worst.result
    print '48 valstijos. Tikslus sprendimas', AntGraph.route_length(sg, tsp_benchmark.get_us_states_48_solution())

    print 'test_benchmarks pabaiga\n'


def main():
    test_brute_force_tsp()
    test_acs_sa_wrapper()
    test_acs_sa_wrapper2()
    test_sa_tsp_wrapper()
    test_acs_sa_bf()
    test_benchmarks()

if __name__ == '__main__':
    main()
