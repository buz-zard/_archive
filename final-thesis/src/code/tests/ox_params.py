# -*- coding: utf-8 -*-
from genetic import GeneticArgs
import genetic
from graph.data import g_23, g_48, g_100
from tsp import TSPSRunner
from analysis import GenericFig

from . import condition, TestRunResult, add_solver, green_color, red_color


base_folder = 'ox_params'


def run_all():
    # run_0()  # +
    # run_1() # +
    # run_2() # +
    pass


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


def _run_inner(prefix, test_name, runs, g, c, args1, args2, args3, args4,
               args5, args6, args7, args8, args9):
    runner = TSPSRunner(g)
    add_solver(runner, args1, c, runs)
    add_solver(runner, args2, c, runs)
    add_solver(runner, args3, c, runs)
    add_solver(runner, args4, c, runs)
    add_solver(runner, args5, c, runs)
    add_solver(runner, args6, c, runs)
    add_solver(runner, args7, c, runs)
    add_solver(runner, args8, c, runs)
    add_solver(runner, args9, c, runs)
    runner.run()

    fig = GenericFig(size=120)
    result = TestRunResult(runner, fig, test_name + prefix, base_folder)

    def plot():
        result.averaged_x_values(args1.key, '-b')
        result.averaged_x_values(args2.key, red_color(0))
        result.averaged_x_values(args3.key, red_color(1))
        result.averaged_x_values(args4.key, red_color(2))
        result.averaged_x_values(args5.key, red_color(3))
        result.averaged_x_values(args6.key, red_color(4))
        result.averaged_x_values(args7.key, green_color(0))
        result.averaged_x_values(args8.key, green_color(2))
        result.averaged_x_values(args9.key, green_color(4))

    result.next_subplot(test_name)
    plot()
    result.legend()
    result.ylabel()
    result.next_log_subplot(u"(log skaleje)")
    plot()
    result.save()


def _pre_run_inner(prefix, test_name, runs, g, c, args1):
    def make_args(val):
        args = GeneticArgs(args1)
        args.set_mr(val)
        args.set_key('ox_mr_', args.get_mr)
        return args

    args2 = make_args(0.2)
    args3 = make_args(0.3)
    args4 = make_args(0.4)
    args5 = make_args(0.5)
    args6 = make_args(0.6)
    args7 = make_args(0.7)
    args8 = make_args(0.8)
    args9 = make_args(0.9)
    _run_inner(prefix, test_name, runs, g, c, args1, args2, args3, args4,
               args5, args6, args7, args8, args9)


def run_0():
    prefix = '_100'
    test_name = u"OX rekombinacijos cr parametras"
    runs = 10
    g = g_100()
    c = condition(100)
    args1 = base_args()
    args1.set_chr(40)
    args1.set_mr(0.1)
    args1.set_key('ox_cr_', args1.get_mr)
    _pre_run_inner(prefix, test_name, runs, g, c, args1)


def run_1():
    prefix = '_48'
    test_name = u"OX rekombinacijos cr parametras"
    runs = 10
    g = g_48()
    c = condition(100)
    args1 = base_args()
    args1.set_chr(40)
    args1.set_mr(0.1)
    args1.set_key('ox_cr_', args1.get_mr)
    _pre_run_inner(prefix, test_name, runs, g, c, args1)


def run_2():
    prefix = '_23'
    test_name = u"OX rekombinacijos cr parametras"
    runs = 10
    g = g_23()
    c = condition(100)
    args1 = base_args()
    args1.set_chr(20)
    args1.set_mr(0.1)
    args1.set_key('ox_cr_', args1.get_mr)
    _pre_run_inner(prefix, test_name, runs, g, c, args1)
