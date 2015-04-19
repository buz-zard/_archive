# -*- coding: utf-8 -*-
from analysis import GenericFig
from ant import AntColonySystem
from data import g_9, g_15, g_23, g_48
from genetic import (Genetic,
                     SELECTION_ROULETE,
                     CROSSOVER_OX,
                     MUTATION_SWAP, MUTATION_SA_REVERSE)
from tsp import TSPSRunner, CONDITION_TIME, CONDITION_GENERATION


def cond(value=60):
    return {
        CONDITION_TIME: value
    }


def g_p(chromosomes):
    return {
        'chromosomes': chromosomes,
        'cr': 0.1,
        'mr': 0.25,
        'selection_method': SELECTION_ROULETE,
        'crossover_method': CROSSOVER_OX,
        'mutation_method': MUTATION_SWAP
    }


def g_pars(chromosomes):
    return {
        'chromosomes': chromosomes,
        'cr': 0.1,
        'mr': 0.25,
        'selection_method': SELECTION_ROULETE,
        'crossover_method': CROSSOVER_OX,
        'mutation_method': MUTATION_SA_REVERSE
    }


def a_pars(ants):
    return {
        'beta': 30,
        'alpha': 0.1,
        'ro': 0.1,
        'tau0': 10,
        'ants': ants
    }


def ga_pars(graph, iterations, chromosomes, just_acs=False):
    gc = cond()
    gc[CONDITION_GENERATION] = iterations
    if just_acs:
        gp = g_p(chromosomes)
    else:
        gp = g_pars(chromosomes)
    ap = a_pars(int(chromosomes / 3))

    ac = cond()
    ac[CONDITION_GENERATION] = int(iterations / 10) + 1
    acs = AntColonySystem(graph, ap, ac, absolute_mute=True)
    gp['use_acs'] = acs
    return gc, gp


base_folder = "test_all/"


# ################################################################################################
# ################################################################################################
def _test_type_1_(graph, runs, iterations, chromosomes, subf="a"):
    cities = len(graph.nodes())
    filename = str(cities) + "_all_test_1"
    output_folder = base_folder + "1/" + subf

    c = cond()
    c[CONDITION_GENERATION] = iterations

    gp = g_p(chromosomes)

    gasc, gasp = ga_pars(graph, iterations, chromosomes)

    gac, gap = ga_pars(graph, iterations, chromosomes, just_acs=True)

    gsp = g_pars(chromosomes)

    runner = TSPSRunner(graph)
    runner.add_tsps(Genetic, gp, c, runs, 'ga')
    runner.add_tsps(Genetic, gasp, gasc, runs, 'ga-acs-sa')
    runner.add_tsps(Genetic, gap, gac, runs, 'ga-acs')
    runner.add_tsps(Genetic, gsp, c, runs, 'ga-sa')

    runner.run()
    runner.save_results(filename=filename, output_folder=output_folder)

    fig = GenericFig(ylabel="Vidutinis atstumas")
    fig.next_subplot("Visi algoritmai, miestai = " + str(cities) + ", n = " +
                     str(runs))
    fig.add_averaged_x_plot_values(runner.results['ga']
                                   ['all_distances'], line='r-',
                                   label='ga')
    fig.add_averaged_x_plot_values(runner.results['ga-acs-sa']
                                   ['all_distances'], line='g-',
                                   label='ga-acs-sa')
    fig.add_averaged_x_plot_values(runner.results['ga-acs']
                                   ['all_distances'], line='b-',
                                   label='ga-acs')
    fig.add_averaged_x_plot_values(runner.results['ga-sa']
                                   ['all_distances'], line='k-',
                                   label='ga-sa')
    fig.set_legend()
    fig.save(filename=filename, output_folder=output_folder)


def test_1():
    run = _test_type_1_
    run(g_9(), 100, 100, 8, "a")
    run(g_15(), 100, 100, 14, "a")
    run(g_23(), 100, 100, 22, "a")
    print 'test_1 done'
