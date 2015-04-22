# -*- coding: utf-8 -*-
from genetic import Genetic, GeneticArgs
import genetic
from graph.data import g_9, g_15, g_23, g_48
from tsp import TSPSRunner
from analysis import GenericFig

from . import condition, TestRunResult


base_folder = 'initial_population'


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


def run_1():
    c = condition(100)
    args1 = base_args()
    args1.set_chr(20)

    args2 = GeneticArgs(args1)
    args2.set_method_initial_population(genetic.INITIAL_POPULATION_SEMIACS)
    args2.set_initial_ants(5)
    args2.set_initial_beta(0.00001)

    g = g_23()

    runs = 1

    runner = TSPSRunner(g)
    runner.add_tsps(Genetic, args1, c, runs, 'simple')
    runner.add_tsps(Genetic, args2, c, runs, 'with-initial')

    runner.run()
    fig = GenericFig(size=120)

    result = TestRunResult(runner, fig, 'test_1', base_folder)
    result.next_subplot("stuff 1")
    result.averaged_x_values('simple', '-r')
    result.averaged_x_values('with-initial', 'g-')
    result.legend()
    result.ylabel()
    result.next_log_subplot("stuff 2")
    result.averaged_x_values('simple', '-r')
    result.averaged_x_values('with-initial', 'g-')
    result.save()


def run_all():
    run_1()
