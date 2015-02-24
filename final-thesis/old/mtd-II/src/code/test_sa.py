# -*- coding: utf-8 -*-
from analysis import GenericFig, sh_to_contour_heatmap_scatter
from data import g_9, g_15
from sa import SimulatedAnnealing
from tsp import TSPSRunner, CONDITION_TIME, CONDITION_NO_PROGRESS


def cond(value=60):
    return {
        CONDITION_TIME: value
    }


base_folder = "sa/"


# ################################################################################################
# ################################################################################################
def _sa_test_type_1_(graph, runs):
    cities = len(graph.nodes())
    filename = str(cities) + "_sa_test_sa-1"
    output_folder = base_folder + "1"
    c = cond()
    sp1 = {
        'initial_temp': 100,
        'decrement': 0.99
    }
    sp2 = {
        'initial_temp': 100,
        'decrement': 0.9999
    }

    runner = TSPSRunner(graph)
    runner.add_tsps(SimulatedAnnealing, sp1, c, runs, 'sa-1')
    runner.add_tsps(SimulatedAnnealing, sp2, c, runs, 'sa-2')
    runner.run()
    runner.save_results(filename=filename, output_folder=output_folder)

    fig = GenericFig(size=120)
    fig.next_subplot("SA, miestu - " + str(cities) + ", n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['sa-1']
                                   ['all_distances'], line='r-',
                                   label='decrement-0.99')
    fig.add_averaged_x_plot_values(runner.results['sa-2']
                                   ['all_distances'], line='g-',
                                   label='decrement-0.9999')
    fig.set_legend()
    fig.set_ylabel('')
    fig.next_subplot(
        "GA, " + str(cities) + " miestu, n = " + str(runs))
    fig.add_averaged_x_plot_values(runner.results['sa-1']
                                   ['all_distances'], line='r-',
                                   label='decrement-0.99')
    fig.add_averaged_x_plot_values(runner.results['sa-2']
                                   ['all_distances'], line='g-',
                                   label='decrement-0.9999')
    fig.set_xscale('log')
    fig.save(filename=filename, output_folder=output_folder)


def sa_test_1():
    run = _sa_test_type_1_
    run(g_9(), 500)
    run(g_15(), 500)
    print "sa_test_1 done"


# ################################################################################################
# ################################################################################################
def _sa_test_type_2_(graph, runs, decrement, no_progress):
    cities = len(graph.nodes())
    filename = str(cities) + "_sa_test_sa-1_" + \
        str(decrement) + "_no_progress_" + str(no_progress) + "_"
    output_folder = base_folder + "2"
    c = cond()
    c[CONDITION_NO_PROGRESS] = no_progress
    sp1 = {
        'initial_temp': 100,
        'decrement': decrement
    }

    runner = TSPSRunner(graph)
    runner.add_tsps(SimulatedAnnealing, sp1, c, runs, 'sa-1')
    runner.run()
    runner.save_results(filename=filename, output_folder=output_folder)
    rez = 15
    xs, ys, zs, maxs = sh_to_contour_heatmap_scatter(
        runner.results['sa-1']['all_distances'], rez, True)
    fig = GenericFig()
    fig.next_subplot("SA, miestu - " + str(cities) + ", alpha = " +
                     str(decrement) + ", n = " + str(runs))
    fig.add_scatter_heatmap(xs, ys, zs, rez)
    fig.set_xlim(0, maxs[0])
    fig.save(filename=filename + "_a",
             output_folder=output_folder)

    fig = GenericFig()
    fig.next_subplot("SA, miestu - " + str(cities) + ", alpha = " +
                     str(decrement) + ", n = " + str(runs))
    fig.add_contour_heatmap(xs, ys, zs, rez)
    fig.save(filename=filename + "_b",
             output_folder=output_folder)


def sa_test_2():
    run = _sa_test_type_2_
    run(g_9(), 1000, 0.99, 300)
    run(g_9(), 1000, 0.99, 50)
    run(g_15(), 1000, 0.99, 300)
    run(g_15(), 1000, 0.99, 50)
    print "sa_test_2 done"
