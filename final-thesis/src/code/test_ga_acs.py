# -*- coding: utf-8 -*-
from copy import deepcopy
from analysis import GenericFig
from ant import AntColonySystem
from data import g_9, g_15, g_23
from genetic import (Genetic,
                     SELECTION_ROULETE, SELECTION_RANK,
                     CROSSOVER_OX, MUTATION_SWAP)
from tsp import TSPSRunner, CONDITION_TIME, CONDITION_GENERATION


def cond(value=60):
    return {
        CONDITION_TIME: value
    }


base_folder = "genetic_acs/"


# ################################################################################################
# ################################################################################################
def _genetic_acs_test_type_1_(graph, runs, ants, chromosomes, iterations,
                              ant_iterations=1, subf="a"):
    cities = len(graph.nodes())
    filename = str(cities) + "_genetic_acs_test"
    output_folder = base_folder + "1/" + subf

    c1 = cond()
    c1[CONDITION_GENERATION] = iterations

    c2 = cond()
    c2[CONDITION_GENERATION] = ant_iterations

    ap = {
        'beta': 30,
        'alpha': 0.1,
        'ro': 0.1,
        'tau0': 10
    }
    ap1 = deepcopy(ap)
    ap1['ants'] = ants
    acs1 = AntColonySystem(graph, ap1, c2, absolute_mute=True)
    ap2 = deepcopy(ap)
    ap2['ants'] = int(ants / 2)
    acs2 = AntColonySystem(graph, ap2, c2, absolute_mute=True)

    gp = {
        'chromosomes': chromosomes,
        'cr': 0.1,
        'mr': 0.5,
        'crossover_method': CROSSOVER_OX,
        'mutation_method': MUTATION_SWAP
    }

    gp11 = deepcopy(gp)
    gp11['selection_method'] = SELECTION_ROULETE
    gp11['use_acs'] = acs1
    gp12 = deepcopy(gp)
    gp12['selection_method'] = SELECTION_ROULETE
    gp12['use_acs'] = acs2
    gp13 = deepcopy(gp)
    gp13['selection_method'] = SELECTION_ROULETE

    gp21 = deepcopy(gp)
    gp21['selection_method'] = SELECTION_RANK
    gp21['use_acs'] = acs1
    gp22 = deepcopy(gp)
    gp22['selection_method'] = SELECTION_RANK
    gp22['use_acs'] = acs2
    gp23 = deepcopy(gp)
    gp23['selection_method'] = SELECTION_RANK

    runner = TSPSRunner(graph)
    runner.add_tsps(Genetic, gp11, c1, runs, 'ga-acs-1.0-roulete')
    runner.add_tsps(Genetic, gp12, c1, runs, 'ga-acs-0.5-roulete')
    runner.add_tsps(Genetic, gp13, c1, runs, 'ga-roulete')
    runner.add_tsps(Genetic, gp21, c1, runs, 'ga-acs-1.0-rank')
    runner.add_tsps(Genetic, gp22, c1, runs, 'ga-acs-0.5-rank')
    runner.add_tsps(Genetic, gp23, c1, runs, 'ga-rank')

    runner.run()
    runner.save_results(filename=filename, output_folder=output_folder)

    fig = GenericFig(ylabel="Vidutinis atstumas", size=120)
    fig.next_subplot("GA-ACS, miestu - " + str(cities) +
                     ", ant-iteration = " + str(ant_iterations) +
                     ",n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-1.0-roulete']
                                   ['all_distances'], line='r-',
                                   label='roulete_ants_' + str(ants))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-0.5-roulete']
                                   ['all_distances'], line='r--',
                                   label='roulete_ants_' + str(ants / 2))
    fig.add_averaged_x_plot_values(runner.results['ga-roulete']
                                   ['all_distances'], line=':r',
                                   label='roulete')
    fig.add_averaged_x_plot_values(runner.results['ga-acs-1.0-rank']
                                   ['all_distances'], line='g-',
                                   label='rank_ants_' + str(ants))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-0.5-rank']
                                   ['all_distances'], line='g--',
                                   label='rank_ants_' + str(ants / 2))
    fig.add_averaged_x_plot_values(runner.results['ga-rank']
                                   ['all_distances'], line=':g',
                                   label='rank')
    fig.set_legend()
    fig.set_ylabel('')
    fig.next_subplot()
    fig.add_averaged_x_plot_values(runner.results['ga-acs-1.0-roulete']
                                   ['all_distances'], line='r-',
                                   label='roulete_ants_' + str(ants))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-0.5-roulete']
                                   ['all_distances'], line='r--',
                                   label='roulete_ants_' + str(ants / 2))
    fig.add_averaged_x_plot_values(runner.results['ga-roulete']
                                   ['all_distances'], line=':r',
                                   label='roulete')
    fig.add_averaged_x_plot_values(runner.results['ga-acs-1.0-rank']
                                   ['all_distances'], line='g-',
                                   label='rank_ants_' + str(ants))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-0.5-rank']
                                   ['all_distances'], line='g--',
                                   label='rank_ants_' + str(ants / 2))
    fig.add_averaged_x_plot_values(runner.results['ga-rank']
                                   ['all_distances'], line=':g',
                                   label='rank')
    fig.set_xscale('log')
    fig.save(filename=filename, output_folder=output_folder)

    fig = GenericFig(ylabel="Vidutinis atstumas", size=120)
    fig.next_subplot("GA-ACS, miestu - " + str(cities) +
                     ", ant-iteration = " + str(ant_iterations) +
                     ",n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-1.0-roulete']
                                   ['all_distances'], line='r-',
                                   label='roulete_ants_' + str(ants))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-0.5-roulete']
                                   ['all_distances'], line='r--',
                                   label='roulete_ants_' + str(ants / 2))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-1.0-rank']
                                   ['all_distances'], line='g-',
                                   label='rank_ants_' + str(ants))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-0.5-rank']
                                   ['all_distances'], line='g--',
                                   label='rank_ants_' + str(ants / 2))
    fig.set_legend()
    fig.set_ylabel('')
    fig.next_subplot()
    fig.add_averaged_x_plot_values(runner.results['ga-acs-1.0-roulete']
                                   ['all_distances'], line='r-',
                                   label='roulete_ants_' + str(ants))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-0.5-roulete']
                                   ['all_distances'], line='r--',
                                   label='roulete_ants_' + str(ants / 2))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-1.0-rank']
                                   ['all_distances'], line='g-',
                                   label='rank_ants_' + str(ants))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-0.5-rank']
                                   ['all_distances'], line='g--',
                                   label='rank_ants_' + str(ants / 2))
    fig.set_xscale('log')
    fig.save(filename=filename + "_b", output_folder=output_folder)


def genetic_acs_test_1():
    run = _genetic_acs_test_type_1_
    run(g_9(), 30, 8, 8, 100, 1, "a")
    run(g_15(), 100, 10, 14, 150, 1, "a")
    run(g_23(), 100, 10, 30, 150, 1, "a")
    print 'genetic_acs_test_1 done'


def genetic_acs_test_2():
    run = _genetic_acs_test_type_1_
    run(g_15(), 100, 10, 14, 150, 2, "b")
    run(g_23(), 100, 10, 30, 150, 2, "b")


def genetic_acs_test_3():
    run = _genetic_acs_test_type_1_
    run(g_15(), 100, 10, 14, 150, 4, "c")
    run(g_23(), 100, 10, 30, 150, 4, "c")
    print 'genetic_acs_test_2 done'

# genetic_acs_test_1()
