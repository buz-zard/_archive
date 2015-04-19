# -*- coding: utf-8 -*-
from copy import deepcopy
from analysis import GenericFig, sh_to_contour_heatmap_scatter
from ant import AntColonySystem
from data import g_9, g_15
from tsp import (TSPSRunner,
                 CONDITION_TIME, CONDITION_GENERATION, CONDITION_NO_PROGRESS)


def cond(value=60):
    return {
        CONDITION_TIME: value
    }


base_folder = "acs/"


# ################################################################################################
# ################################################################################################
def _acs_test_type_1_(graph, runs, base_pars, beta, antz, iters, subf="a"):
    cities = len(graph.nodes())
    filename = str(cities) + "_acs_test_1__args[" + str(beta) + "][" + str(
        base_pars['alpha']) + "][" + str(base_pars['ro']) + "]"
    output_folder = base_folder + "1/" + subf

    base_pars['beta'] = beta
    c1 = cond()
    c1[CONDITION_GENERATION] = iters
    ap1 = deepcopy(base_pars)
    ap1['ants'] = antz[0]

    c2 = cond()
    c2[CONDITION_GENERATION] = iters
    ap2 = deepcopy(base_pars)
    ap2['ants'] = antz[1]

    runner = TSPSRunner(graph)
    runner.add_tsps(AntColonySystem, ap1, c1, runs, 'acs-1')
    runner.add_tsps(AntColonySystem, ap2, c2, runs, 'acs-2')
    runner.run()
    runner.save_results(filename=filename, output_folder=output_folder)

    fig = GenericFig(ylabel="Vidutinis atstumas", size=120)
    fig.next_subplot("ACS, miestu - " + str(cities) + ", n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['acs-1']
                                   ['all_distances'], line='r-',
                                   label="ants = " + str(antz[0]))
    fig.add_averaged_x_plot_values(runner.results['acs-2']
                                   ['all_distances'], line='g-',
                                   label="ants = " + str(antz[1]))
    fig.set_legend()
    fig.set_ylabel('')
    fig.next_subplot("ACS, miestu - " + str(cities) + ", n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['acs-1']
                                   ['all_distances'], line='r-',
                                   label="ants = " + str(antz[0]))
    fig.add_averaged_x_plot_values(runner.results['acs-2']
                                   ['all_distances'], line='g-',
                                   label="ants = " + str(antz[1]))
    fig.set_xscale('log')
    fig.save(filename=filename + "_a",
             output_folder=output_folder)


def acs_test_1():
    run = _acs_test_type_1_
    base_pars = {
        'alpha': 0.9,
        'ro': 0.9,
        'tau0': 10
    }
    runs1 = 100
    iters1 = 30
    run(g_9(), runs1, base_pars, 1, (9, 3), iters1)
    run(g_9(), runs1, base_pars, 30, (9, 3), iters1)
    runs2 = 50
    iters2 = 20
    run(g_15(), runs2, base_pars, 1, (15, 5), iters2)
    run(g_15(), runs2, base_pars, 30, (15, 5), iters2)
    print "acs_test_1a done"


def acs_test_1b():
    run = _acs_test_type_1_
    base_pars = {
        'alpha': 0.9,
        'ro': 0.1,
        'tau0': 10
    }
    runs1 = 100
    iters1 = 30
    run(g_9(), runs1, base_pars, 1, (9, 3), iters1, "b")
    run(g_9(), runs1, base_pars, 30, (9, 3), iters1, "b")
    runs2 = 50
    iters2 = 20
    run(g_15(), runs2, base_pars, 1, (15, 5), iters2, "b")
    run(g_15(), runs2, base_pars, 30, (15, 5), iters2, "b")
    print "acs_test_1b done"


def acs_test_1c():
    run = _acs_test_type_1_
    base_pars = {
        'alpha': 0.1,
        'ro': 0.1,
        'tau0': 10
    }
    runs1 = 100
    iters1 = 30
    run(g_9(), runs1, base_pars, 1, (9, 3), iters1, "c")
    run(g_9(), runs1, base_pars, 30, (9, 3), iters1, "c")
    runs2 = 50
    iters2 = 20
    run(g_15(), runs2, base_pars, 1, (15, 5), iters2, "c")
    run(g_15(), runs2, base_pars, 30, (15, 5), iters2, "c")
    print "acs_test_1c done"


def acs_test_1d():
    run = _acs_test_type_1_
    base_pars = {
        'alpha': 0.1,
        'ro': 0.9,
        'tau0': 10
    }
    runs1 = 100
    iters1 = 30
    run(g_9(), runs1, base_pars, 1, (9, 3), iters1, "d")
    run(g_9(), runs1, base_pars, 30, (9, 3), iters1, "d")
    runs2 = 50
    iters2 = 20
    run(g_15(), runs2, base_pars, 1, (15, 5), iters2, "d")
    run(g_15(), runs2, base_pars, 30, (15, 5), iters2, "d")
    print "acs_test_1d done"
