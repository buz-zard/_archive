# -*- coding: utf-8 -*-
from genetic import GeneticArgs
import genetic
from graph.data import g_23, g_48, vilniaus_senamiestis
from tsp import TSPSRunner
from analysis import GenericFig

from . import condition, TestRunResult, add_solver, red_color, green_color


base_folder = 'initial_population'


def run_all():
    # run_0()  # -
    # run_1()  # -
    # run_2()  # +
    # run_3()  # -
    pass


def base_args():
    args = GeneticArgs()
    args.set_chr(10)
    args.set_cr(0.3)
    args.set_mr(0.7)
    args.set_method_initial_population(genetic.INITIAL_POPULATION_RANDOM)
    args.set_method_select(genetic.SELECTION_RANK)
    args.set_method_cross(genetic.CROSSOVER_OX)
    args.set_method_mutate(genetic.MUTATION_SA)
    return args


def _run_inner(prefix, test_name, runs, g, c, args1, args2, args3, args4,
               args5, args6, args7):
    runner = TSPSRunner(g)
    add_solver(runner, args1, c, runs)
    add_solver(runner, args2, c, runs)
    add_solver(runner, args3, c, runs)
    add_solver(runner, args4, c, runs)
    add_solver(runner, args5, c, runs)
    add_solver(runner, args6, c, runs)
    add_solver(runner, args7, c, runs)
    runner.run()

    fig = GenericFig(size=120)
    result = TestRunResult(runner, fig, test_name + prefix, base_folder)

    def plot():
        result.averaged_x_values(args1.key, '-b')
        result.averaged_x_values(args2.key, red_color(0))
        result.averaged_x_values(args3.key, red_color(2))
        result.averaged_x_values(args4.key, red_color(4))
        result.averaged_x_values(args5.key, green_color(0))
        result.averaged_x_values(args6.key, green_color(2))
        result.averaged_x_values(args7.key, green_color(4))

    result.next_subplot(test_name)
    plot()
    result.legend()
    result.ylabel()
    result.next_log_subplot(u"(log skaleje)")
    plot()
    result.save()


def _pre_run_inner(prefix, test_name, runs, g, c, args1, args2, ants):
    def make_args(val):
        args = GeneticArgs(args2)
        args.set_initial_ants(val)
        args.set_key('initial-pop-', args.get_initial_ants)
        return args

    args3 = make_args(ants[2])
    args4 = make_args(ants[3])
    args5 = make_args(ants[4])
    args6 = make_args(ants[5])
    args7 = make_args(ants[6])
    _run_inner(prefix, test_name, runs, g, c, args1, args2, args3, args4,
               args5, args6, args7)


def run_0():
    prefix = '_80'
    test_name = u"Atsitiktine ir generuota pradine populiacija"
    runs = 1
    g = vilniaus_senamiestis()
    c = condition(60)
    ants = [3, 7, 11, 22, 32, 44, 55]
    args1 = base_args()
    args1.set_chr(40)
    args1.set_key('plain')
    args2 = GeneticArgs(args1)
    args2.set_method_initial_population(genetic.INITIAL_POPULATION_SEMIACS)
    args2.set_initial_ants(ants[0])
    args2.set_key('initial-pop-', args2.get_initial_ants)
    _pre_run_inner(prefix, test_name, runs, g, c, args1, args2, ants)


def run_1():
    prefix = '_48'
    test_name = u"Atsitiktine ir generuota pradine populiacija"
    runs = 10
    g = g_48()
    c = condition(100)
    ants = [5, 11, 17, 22, 29, 35, 40]
    args1 = base_args()
    args1.set_chr(40)
    args1.set_key('plain')
    args2 = GeneticArgs(args1)
    args2.set_method_initial_population(genetic.INITIAL_POPULATION_SEMIACS)
    args2.set_initial_ants(ants[0])
    args2.set_key('initial-pop-', args2.get_initial_ants)
    _pre_run_inner(prefix, test_name, runs, g, c, args1, args2, ants)


def run_2():
    prefix = '_23'
    test_name = u"Atsitiktine ir generuota pradine populiacija"
    runs = 10
    g = g_23()
    c = condition(100)
    ants = [2, 5, 8, 11, 14, 17, 20]
    args1 = base_args()
    args1.set_chr(20)
    args1.set_key('plain')
    args2 = GeneticArgs(args1)
    args2.set_method_initial_population(genetic.INITIAL_POPULATION_SEMIACS)
    args2.set_initial_ants(ants[0])
    args2.set_key('initial-pop-', args2.get_initial_ants)
    _pre_run_inner(prefix, test_name, runs, g, c, args1, args2, ants)


def run_3():
    prefix = '_202'
    test_name = u"Atsitiktine ir generuota pradine populiacija"
    runs = 1
    g = vilniaus_senamiestis()
    c = condition(60)
    ants = [5, 11, 17, 22, 29, 35, 40]
    args1 = base_args()
    args1.set_chr(40)
    args1.set_key('plain')
    args2 = GeneticArgs(args1)
    args2.set_method_initial_population(genetic.INITIAL_POPULATION_SEMIACS)
    args2.set_initial_ants(ants[0])
    args2.set_key('initial-pop-', args2.get_initial_ants)
    _pre_run_inner(prefix, test_name, runs, g, c, args1, args2, ants)
