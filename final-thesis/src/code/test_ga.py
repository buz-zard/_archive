# -*- coding: utf-8 -*
from analysis import GenericFig
from data import g_9, g_15
from genetic import (Genetic,
                     SELECTION_ROULETE, SELECTION_RANK,
                     CROSSOVER_ORDINAL_1P, CROSSOVER_OX,
                     MUTATION_SWAP)
from tsp import TSPSRunner, CONDITION_TIME, CONDITION_GENERATION


def cond(value=60):
    return {
        CONDITION_TIME: value
    }


base_folder = "genetic/"


# ################################################################################################
# ################################################################################################
def _genetic_test_type_1_(graph, runs, iterations, chromosomes,
                          subf="a", crmr=(0.9, 0.1)):
    cities = len(graph.nodes())
    filename = str(cities) + "_genetic_test_1_crmr[" + str(crmr) + "]_"
    output_folder = base_folder + "1/" + subf

    c = cond()
    c[CONDITION_GENERATION] = iterations
    gp1 = {
        'chromosomes': chromosomes,
        'cr': crmr[0],
        'mr': crmr[1],
        'selection_method': SELECTION_ROULETE,
        'crossover_method': CROSSOVER_ORDINAL_1P,
        'mutation_method': MUTATION_SWAP
    }
    gp2 = {
        'chromosomes': chromosomes,
        'cr': crmr[0],
        'mr': crmr[1],
        'selection_method': SELECTION_RANK,
        'crossover_method': CROSSOVER_ORDINAL_1P,
        'mutation_method': MUTATION_SWAP
    }
    gp3 = {
        'chromosomes': chromosomes,
        'cr': crmr[0],
        'mr': crmr[1],
        'selection_method': SELECTION_ROULETE,
        'crossover_method': CROSSOVER_OX,
        'mutation_method': MUTATION_SWAP
    }
    gp4 = {
        'chromosomes': chromosomes,
        'cr': crmr[0],
        'mr': crmr[1],
        'selection_method': SELECTION_RANK,
        'crossover_method': CROSSOVER_OX,
        'mutation_method': MUTATION_SWAP
    }

    runner = TSPSRunner(graph)
    runner.add_tsps(Genetic, gp1, c, runs, 'roulete-ordinal')
    runner.add_tsps(Genetic, gp2, c, runs, 'rank-ordinal')
    runner.add_tsps(Genetic, gp3, c, runs, 'roulete-ox')
    runner.add_tsps(Genetic, gp4, c, runs, 'rank-ox')

    runner.run()
    runner.save_results(filename=filename, output_folder=output_folder)

    fig = GenericFig(ylabel="Vidutinis atstumas", size=120)
    fig.next_subplot("GA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['roulete-ordinal']
                                   ['all_distances'], line='r-',
                                   label='roulete-ordinal')
    fig.add_averaged_x_plot_values(runner.results['rank-ordinal']
                                   ['all_distances'], line='g-',
                                   label='rank-ordinal')
    fig.add_averaged_x_plot_values(runner.results['roulete-ox']
                                   ['all_distances'], line='c-',
                                   label='roulete-ox')
    fig.add_averaged_x_plot_values(runner.results['rank-ox']
                                   ['all_distances'], line='y-',
                                   label='rank-ox')
    fig.set_legend()
    fig.set_ylabel('')
    fig.next_subplot("GA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['roulete-ordinal']
                                   ['all_distances'], line='r-',
                                   label='roulete-ordinal')
    fig.add_averaged_x_plot_values(runner.results['rank-ordinal']
                                   ['all_distances'], line='g-',
                                   label='rank-ordinal')
    fig.add_averaged_x_plot_values(runner.results['roulete-ox']
                                   ['all_distances'], line='c-',
                                   label='roulete-ox')
    fig.add_averaged_x_plot_values(runner.results['rank-ox']
                                   ['all_distances'], line='y-',
                                   label='rank-ox')
    fig.set_xscale('log')
    fig.save(filename=filename, output_folder=output_folder)


def genetic_test_1():
    run = _genetic_test_type_1_
    run(g_9(), 500, 200, 8)
    run(g_15(), 500, 200, 14)
    print 'genetic_test_1 done'


def genetic_test_1b():
    run = _genetic_test_type_1_
    run(g_9(), 500, 200, 8, subf="b", crmr=(0.1, 0.9))
    run(g_15(), 500, 200, 14, subf="b", crmr=(0.1, 0.9))
    print 'genetic_test_1 done'


def genetic_test_1c():
    run = _genetic_test_type_1_
    run(g_9(), 500, 200, 8, subf="c", crmr=(0.1, 0.1))
    run(g_15(), 500, 200, 14, subf="c", crmr=(0.1, 0.1))
    print 'genetic_test_1 done'


def genetic_test_1d():
    run = _genetic_test_type_1_
    run(g_9(), 500, 200, 8, subf="d", crmr=(0.9, 0.9))
    run(g_15(), 500, 200, 14, subf="d", crmr=(0.9, 0.9))
    print 'genetic_test_1 done'


# ################################################################################################
# ################################################################################################
def _genetic_test_type_2_(graph, runs, iterations, chromosomes, mr, subf="a"):
    cities = len(graph.nodes())
    filename = str(cities) + "_genetic_test_2_pluslog_mr[" + str(mr) + "]_"
    output_folder = base_folder + "2/" + subf

    c = cond()
    c[CONDITION_GENERATION] = iterations
    gp3 = {
        'chromosomes': chromosomes,
        'cr': 0.05,
        'mr': mr,
        'selection_method': SELECTION_ROULETE,
        'crossover_method': CROSSOVER_OX,
        'mutation_method': MUTATION_SWAP
    }
    gp4 = {
        'chromosomes': chromosomes,
        'cr': 0.05,
        'mr': mr,
        'selection_method': SELECTION_RANK,
        'crossover_method': CROSSOVER_OX,
        'mutation_method': MUTATION_SWAP
    }

    runner = TSPSRunner(graph)
    runner.add_tsps(Genetic, gp3, c, runs, 'roulete-ox')
    runner.add_tsps(Genetic, gp4, c, runs, 'rank-ox')

    runner.run()
    runner.save_results(filename=filename, output_folder=output_folder)

    fig = GenericFig(ylabel="Vidutinis atstumas", size=120)
    fig.next_subplot(
        "GA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['roulete-ox']
                                   ['all_distances'], line='c-',
                                   label='roulete-ox')
    fig.add_averaged_x_plot_values(runner.results['rank-ox']
                                   ['all_distances'], line='y-',
                                   label='rank-ox')
    fig.set_legend()
    fig.set_ylabel('')
    fig.next_subplot(
        "GA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['roulete-ox']
                                   ['all_distances'], line='c-',
                                   label='roulete-ox')
    fig.add_averaged_x_plot_values(runner.results['rank-ox']
                                   ['all_distances'], line='y-',
                                   label='rank-ox')
    fig.set_xscale('log')
    fig.save(filename=filename, output_folder=output_folder)


def genetic_test_2():
    run = _genetic_test_type_2_
    run(g_9(), 500, 150, 8)
    run(g_15(), 500, 150, 14)
    print 'genetic_test_2a done'


def genetic_test_2b():
    run = _genetic_test_type_2_
    run(g_9(), 500, 150, 8, subf="b", crmr=(0.1, 0.9))
    run(g_15(), 500, 150, 14, subf="b", crmr=(0.1, 0.9))
    print 'genetic_test_2b done'


def genetic_test_2c():
    run = _genetic_test_type_2_
    run(g_9(), 500, 150, 8, subf="c", crmr=(0.1, 0.1))
    run(g_15(), 500, 150, 14, subf="c", crmr=(0.1, 0.1))
    print 'genetic_test_2c done'


def genetic_test_2d():
    run = _genetic_test_type_2_
    run(g_9(), 500, 150, 8, subf="d", crmr=(0.9, 0.9))
    run(g_15(), 500, 150, 14, subf="d", crmr=(0.9, 0.9))
    print 'genetic_test_2d done'
