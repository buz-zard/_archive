# -*- coding: utf-8 -*-
from genetic import GeneticArgs
import genetic
from graph.data import g_23, g_48
from tsp import TSPSRunner
from analysis import GenericFig

from . import condition, TestRunResult, add_solver, red_color


base_folder = 'initial_population'


def run_all():
    # run_1()
    run_2()


def base_args():
    args = GeneticArgs()
    args.set_chr(10)
    args.set_cr(0.5)
    args.set_mr(0.5)
    args.set_method_initial_population(genetic.INITIAL_POPULATION_RANDOM)
    args.set_method_select(genetic.SELECTION_RANK)
    args.set_method_cross(genetic.CROSSOVER_OX)
    args.set_method_mutate(genetic.MUTATION_SA)
    return args


def run_inner(prefix, test_name, runs, g, c, ants, args1, args2, args3, args4,
              args5, args6):
    runner = TSPSRunner(g)
    add_solver(runner, args1, c, runs)
    add_solver(runner, args2, c, runs)
    add_solver(runner, args3, c, runs)
    add_solver(runner, args4, c, runs)
    add_solver(runner, args5, c, runs)
    add_solver(runner, args6, c, runs)

    runner.run()

    fig = GenericFig(size=120)
    result = TestRunResult(runner, fig, test_name + prefix, base_folder)

    def plot():
        result.averaged_x_values(args1.key, '-g')
        result.averaged_x_values(args2.key, red_color(0))
        result.averaged_x_values(args3.key, red_color(1))
        result.averaged_x_values(args4.key, red_color(2))
        result.averaged_x_values(args5.key, red_color(3))
        result.averaged_x_values(args6.key, red_color(4))

    result.next_subplot(test_name)
    plot()
    result.legend()
    result.ylabel()
    result.next_log_subplot(u"(log skaleje)")
    plot()
    result.save()


def run_1():
    prefix = '_48'
    test_name = u"Atsitiktine ir generuota pradine populiacija"
    runs = 10
    g = g_48()
    c = condition(100)
    ants = [5, 10, 15, 20, 25]

    args1 = base_args()
    args1.set_chr(40)
    args1.set_key('plain')

    args2 = GeneticArgs(args1)
    args2.set_method_initial_population(genetic.INITIAL_POPULATION_SEMIACS)
    args2.set_initial_ants(ants[0])
    args2.set_key('initial-pop-', args2.get_initial_ants)

    args3 = GeneticArgs(args2)
    args3.set_initial_ants(ants[1])
    args3.set_key('initial-pop-', args3.get_initial_ants)

    args4 = GeneticArgs(args2)
    args4.set_initial_ants(ants[2])
    args4.set_key('initial-pop-', args4.get_initial_ants)

    args5 = GeneticArgs(args2)
    args5.set_initial_ants(ants[3])
    args5.set_key('initial-pop-', args5.get_initial_ants)

    args6 = GeneticArgs(args2)
    args6.set_initial_ants(ants[4])
    args6.set_key('initial-pop-', args6.get_initial_ants)
    run_inner(prefix, test_name, runs, g, c, ants, args1, args2, args3, args4,
              args5, args6)


def run_2():
    prefix = '_23'
    test_name = u"Atsitiktine ir generuota pradine populiacija"
    runs = 10
    g = g_23()
    c = condition(100)
    ants = [3, 9, 12, 15, 18]

    args1 = base_args()
    args1.set_chr(20)
    args1.set_key('plain')

    args2 = GeneticArgs(args1)
    args2.set_method_initial_population(genetic.INITIAL_POPULATION_SEMIACS)
    args2.set_initial_ants(ants[0])
    args2.set_key('initial-pop-', args2.get_initial_ants)

    args3 = GeneticArgs(args2)
    args3.set_initial_ants(ants[1])
    args3.set_key('initial-pop-', args3.get_initial_ants)

    args4 = GeneticArgs(args2)
    args4.set_initial_ants(ants[2])
    args4.set_key('initial-pop-', args4.get_initial_ants)

    args5 = GeneticArgs(args2)
    args5.set_initial_ants(ants[3])
    args5.set_key('initial-pop-', args5.get_initial_ants)

    args6 = GeneticArgs(args2)
    args6.set_initial_ants(ants[4])
    args6.set_key('initial-pop-', args6.get_initial_ants)
    run_inner(prefix, test_name, runs, g, c, ants, args1, args2, args3, args4,
              args5, args6)
