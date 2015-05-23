# -*- coding: utf-8 -*-
from genetic import GeneticArgs
import genetic
from graph.data import g_vln
from tsp import TSPSRunner
from analysis import GenericFig

from .. import condition, TestRunResult, add_solver, green_color, red_color

base_folder = 'final_cr_mr_10chr'
c = condition(100)


def run_all():
    print base_folder
    # run_base(0.1)  # +
    # run_base(0.2)  # +
    # run_base(0.3)  # +
    # run_base(0.4)  # +
    # run_base(0.5)  # +
    # run_base(0.6)  # +
    # run_base(0.7)  # +
    # run_base(0.8)  # +
    # run_base(0.9)  # +
    pass


def runner():
    return TSPSRunner(g_vln())


def base_args(cr, mr):
    args = GeneticArgs()
    args.set_chr(10)
    args.set_cr(cr)
    args.set_mr(mr)
    args.set_key("mr_{}".format(mr))
    args.set_method_initial_population(genetic.INITIAL_POPULATION_RANDOM)
    args.set_method_select(genetic.SELECTION_RANK)
    args.set_method_cross(genetic.CROSSOVER_OX)
    args.set_method_mutate(genetic.MUTATION_SA)
    return args


def run_base(cr):
    prefix = " cr = {}".format(cr)
    test_name = u"Rekombinacijos, mutacijos koef."
    runs = 10
    args1 = base_args(cr, 0.1)
    args2 = base_args(cr, 0.2)
    args3 = base_args(cr, 0.3)
    args4 = base_args(cr, 0.4)
    args5 = base_args(cr, 0.5)
    args6 = base_args(cr, 0.6)
    args7 = base_args(cr, 0.7)
    args8 = base_args(cr, 0.8)
    args9 = base_args(cr, 0.9)

    r = runner()
    add_solver(r, args1, c, runs)
    add_solver(r, args2, c, runs)
    add_solver(r, args3, c, runs)
    add_solver(r, args4, c, runs)
    add_solver(r, args5, c, runs)
    add_solver(r, args6, c, runs)
    add_solver(r, args7, c, runs)
    add_solver(r, args8, c, runs)
    add_solver(r, args9, c, runs)
    r.run()

    fig = GenericFig(size=120)
    result = TestRunResult(r, fig, test_name + prefix, base_folder)

    def plot():
        result.averaged_x_values(args1.key, 'r--')
        result.averaged_x_values(args2.key, red_color(0))
        result.averaged_x_values(args3.key, red_color(2))
        result.averaged_x_values(args4.key, red_color(4))
        result.averaged_x_values(args5.key, green_color(4))
        result.averaged_x_values(args6.key, green_color(2))
        result.averaged_x_values(args7.key, green_color(1))
        result.averaged_x_values(args8.key, green_color(0))
        result.averaged_x_values(args9.key, 'g--')

    result.next_subplot(test_name)
    plot()
    result.legend()
    result.ylabel()
    result.next_log_subplot(u"(log skaleje)")
    plot()
    result.save()
