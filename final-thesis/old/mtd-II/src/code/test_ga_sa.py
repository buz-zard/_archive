# -*- coding: utf-8 -*-
from analysis import GenericFig
from data import g_9, g_15, g_23
from genetic import (Genetic,
                     SELECTION_ROULETE, SELECTION_RANK,
                     CROSSOVER_OX,
                     MUTATION_SWAP, MUTATION_SA,
                     MUTATION_SA_INSERT, MUTATION_SA_REVERSE)
from tsp import TSPSRunner, CONDITION_TIME, CONDITION_GENERATION


def cond(value=60):
    return {
        CONDITION_TIME: value
    }


def g_pars(chromosomes, mutation, selection, mr):
    return {
        'chromosomes': chromosomes,
        'cr': 0.1,
        'mr': mr,
        'selection_method': selection,
        'crossover_method': CROSSOVER_OX,
        'mutation_method': mutation
    }

base_folder = "genetic_sa/"


# ################################################################################################
# ################################################################################################
def _genetic_sa_test_type_1_(graph, runs, iterations, chromosomes, selection,
                             subf="a", mr=0.5):
    cities = len(graph.nodes())
    filename = str(cities) + "_genetic_sa_test_1_" + selection
    output_folder = base_folder + "1/" + subf

    c = cond()
    c[CONDITION_GENERATION] = iterations
    gp1 = g_pars(chromosomes, MUTATION_SWAP, selection, mr)
    gp2 = g_pars(chromosomes, MUTATION_SA, selection, mr)
    gp3 = g_pars(chromosomes, MUTATION_SA_INSERT, selection, mr)
    gp4 = g_pars(chromosomes, MUTATION_SA_REVERSE, selection, mr)

    runner = TSPSRunner(graph)
    runner.add_tsps(Genetic, gp1, c, runs, 'm-swap')
    runner.add_tsps(Genetic, gp2, c, runs, 'm-sa')
    runner.add_tsps(Genetic, gp3, c, runs, 'm-sa-i')
    runner.add_tsps(Genetic, gp4, c, runs, 'm-sa-r')

    runner.run()
    runner.save_results(filename=filename, output_folder=output_folder)

    fig = GenericFig(ylabel="Vidutinis atstumas", size=120)
    fig.next_subplot("GA-SA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['m-swap']
                                   ['all_distances'], line='r-',
                                   label='m-swap')
    fig.add_averaged_x_plot_values(runner.results['m-sa']
                                   ['all_distances'], line='g-',
                                   label='m-sa')
    fig.add_averaged_x_plot_values(runner.results['m-sa-i']
                                   ['all_distances'], line='c-',
                                   label='m-sa-i')
    fig.add_averaged_x_plot_values(runner.results['m-sa-r']
                                   ['all_distances'], line='y-',
                                   label='m-sa-r')
    fig.set_legend()
    fig.set_ylabel('')
    fig.next_subplot("GA-SA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['m-swap']
                                   ['all_distances'], line='r-',
                                   label='m-swap')
    fig.add_averaged_x_plot_values(runner.results['m-sa']
                                   ['all_distances'], line='g-',
                                   label='m-sa')
    fig.add_averaged_x_plot_values(runner.results['m-sa-i']
                                   ['all_distances'], line='c-',
                                   label='m-sa-i')
    fig.add_averaged_x_plot_values(runner.results['m-sa-r']
                                   ['all_distances'], line='y-',
                                   label='m-sa-r')
    fig.set_xscale('log')
    fig.save(filename=filename, output_folder=output_folder)


def genetic_sa_test_1():
    run = _genetic_sa_test_type_1_
    run(g_9(), 200, 100, 8, SELECTION_ROULETE, "a")
    run(g_15(), 200, 100, 14, SELECTION_ROULETE, "a")
    run(g_23(), 200, 100, 22, SELECTION_ROULETE, "a")
    print 'genetic_sa_test_1a done'


def genetic_sa_test_1b():
    run = _genetic_sa_test_type_1_
    run(g_9(), 200, 100, 8, SELECTION_RANK, "b")
    run(g_15(), 200, 100, 14, SELECTION_RANK, "b")
    run(g_23(), 200, 100, 22, SELECTION_RANK, "b")
    print 'genetic_sa_test_1b done'
