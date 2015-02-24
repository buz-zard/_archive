# -*- coding: utf-8 -*-
from analysis import GenericFig
from ant import AntColonySystem
from data import g_9, g_15, g_23
from genetic import (Genetic,
                     SELECTION_ROULETE, SELECTION_RANK,
                     CROSSOVER_OX,
                     MUTATION_SWAP, MUTATION_SA_REVERSE)
from tsp import TSPSRunner, CONDITION_TIME, CONDITION_GENERATION


def cond(value=60):
    return {
        CONDITION_TIME: value
    }


def g_pars(chromosomes, selection, mr, mutation=MUTATION_SWAP):
    return {
        'chromosomes': chromosomes,
        'cr': 0.1,
        'mr': mr,
        'selection_method': selection,
        'crossover_method': CROSSOVER_OX,
        'mutation_method': mutation
    }


def a_pars(ants):
    return {
        'beta': 30,
        'alpha': 0.1,
        'ro': 0.1,
        'tau0': 10,
        'ants': ants
    }


def ga_pars(graph, iterations, chromosomes, selection, mr):
    gc = cond()
    gc[CONDITION_GENERATION] = iterations
    gp = g_pars(chromosomes, selection, mr, MUTATION_SA_REVERSE)
    ap = a_pars(int(chromosomes / 3))

    ac = cond()
    ac[CONDITION_GENERATION] = int(iterations / 10) + 1
    acs = AntColonySystem(graph, ap, ac, absolute_mute=True)
    gp['use_acs'] = acs
    return gc, gp


base_folder = "genetic_acs_sa/"


# ################################################################################################
# ################################################################################################
def _genetic_acs_sa_test_type_1_(graph, runs, iterations, chromosomes,
                                 mr, selection, subf="a"):
    cities = len(graph.nodes())
    filename = str(cities) + "_genetic_acs_sa_test_1_" + selection
    output_folder = base_folder + "1/" + subf

    c0 = cond()
    c0[CONDITION_GENERATION] = iterations
    g0 = g_pars(chromosomes, selection, mr)

    c1, g1 = ga_pars(graph, iterations, chromosomes, selection, mr)

    runner = TSPSRunner(graph)
    runner.add_tsps(Genetic, g0, c0, runs, 'ga')
    runner.add_tsps(Genetic, g1, c1, runs, 'ga-acs-sa')

    runner.run()
    runner.save_results(filename=filename, output_folder=output_folder)

    fig = GenericFig(ylabel="Vidutinis atstumas", size=120)
    fig.next_subplot("GA-ACS-SA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['ga']
                                   ['all_distances'], line='r-',
                                   label='ga')
    fig.add_averaged_x_plot_values(runner.results['ga-acs-sa']
                                   ['all_distances'], line='g-',
                                   label='ga-acs-sa')
    fig.set_legend()
    fig.set_ylabel('')
    fig.next_subplot("GA-ACS-SA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['ga']
                                   ['all_distances'], line='r-',
                                   label='ga')
    fig.add_averaged_x_plot_values(runner.results['ga-acs-sa']
                                   ['all_distances'], line='g-',
                                   label='ga-acs-sa')
    fig.set_xscale('log')
    fig.save(filename=filename, output_folder=output_folder)


def genetic_sa_test_1():
    run = _genetic_acs_sa_test_type_1_
    run(g_9(), 200, 100, 8, 0.1, SELECTION_ROULETE, "a")
    run(g_9(), 200, 100, 8, 0.1, SELECTION_RANK, "a")
    run(g_15(), 200, 100, 14, 0.1, SELECTION_ROULETE, "a")
    run(g_15(), 200, 100, 14, 0.1, SELECTION_RANK, "a")
    run(g_23(), 200, 100, 22, 0.1, SELECTION_ROULETE, "a")
    run(g_23(), 200, 100, 22, 0.1, SELECTION_RANK, "a")
    print 'genetic_sa_test_1a done'


# ################################################################################################
# ################################################################################################
def _genetic_acs_sa_test_type_2_(graph, runs, iterations, chromosomes,
                                 selection, subf="a"):
    cities = len(graph.nodes())
    filename = str(cities) + "_genetic_acs_sa_test_1_" + selection
    output_folder = base_folder + "2/" + subf

    c1, g1 = ga_pars(graph, iterations, chromosomes, selection, 0.01)
    c2, g2 = ga_pars(graph, iterations, chromosomes, selection, 0.5)
    c3, g3 = ga_pars(graph, iterations, chromosomes, selection, 0.99)

    runner = TSPSRunner(graph)
    runner.add_tsps(Genetic, g1, c1, runs, 'ga-acs-sa1')
    runner.add_tsps(Genetic, g2, c2, runs, 'ga-acs-sa2')
    runner.add_tsps(Genetic, g3, c3, runs, 'ga-acs-sa3')

    runner.run()
    runner.save_results(filename=filename, output_folder=output_folder)

    fig = GenericFig(ylabel="Vidutinis atstumas", size=120)
    fig.next_subplot("GA-ACS-SA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-sa1']
                                   ['all_distances'], line='r-',
                                   label='mr-0.01')
    fig.add_averaged_x_plot_values(runner.results['ga-acs-sa2']
                                   ['all_distances'], line='g-',
                                   label='mr-0.5')
    fig.add_averaged_x_plot_values(runner.results['ga-acs-sa3']
                                   ['all_distances'], line='b-',
                                   label='mr-0.99')
    fig.set_legend()
    fig.set_ylabel('')
    fig.next_subplot("GA-ACS-SA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['ga-acs-sa1']
                                   ['all_distances'], line='r-',
                                   label='mr-0.01')
    fig.add_averaged_x_plot_values(runner.results['ga-acs-sa2']
                                   ['all_distances'], line='g-',
                                   label='mr-0.5')
    fig.add_averaged_x_plot_values(runner.results['ga-acs-sa3']
                                   ['all_distances'], line='b-',
                                   label='mr-0.99')
    fig.set_xscale('log')
    fig.save(filename=filename, output_folder=output_folder)


def genetic_sa_test_2():
    run = _genetic_acs_sa_test_type_2_
    run(g_15(), 100, 100, 14, SELECTION_ROULETE, "a")
    run(g_15(), 100, 100, 14, SELECTION_RANK, "a")
    run(g_23(), 100, 100, 14, SELECTION_ROULETE, "a")
    run(g_23(), 100, 100, 14, SELECTION_RANK, "a")


# genetic_sa_test_2()
