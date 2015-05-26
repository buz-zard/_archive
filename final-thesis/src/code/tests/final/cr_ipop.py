# -*- coding: utf-8 -*-
from genetic import GeneticArgs
import genetic
from graph.data import g_vln
from tsp import TSPSRunner
from analysis import GenericFig

from .. import condition, TestRunResult, add_solver, green_color, red_color

base_folder = 'final_cr_ipop'
c = condition(100)


def run_all():
    # run_base(0.1)  # +
    # run_base(0.2)  # +
    # run_base(0.3)  # +
    run_base(0.4)  # -
    run_base(0.5)  # -
    run_base(0.6)  # -
    run_base(0.7)  # -
    run_base(0.8)  # -
    # run_base(0.9)  # +
    pass


def runner():
    return TSPSRunner(g_vln())


def base_args(cr, ipop):
    args = GeneticArgs()
    args.set_chr(20)
    args.set_mr(0.15)
    args.set_cr(cr)
    if ipop > 0:
        args.set_method_initial_population(genetic.INITIAL_POPULATION_SEMIACS)
        args.set_initial_ants(ipop)
        args.set_key('ipop_', args.get_initial_ants)
    else:
        args.set_key('random')
        args.set_method_initial_population(genetic.INITIAL_POPULATION_RANDOM)
    args.set_method_select(genetic.SELECTION_RANK)
    args.set_method_cross(genetic.CROSSOVER_OX)
    args.set_method_mutate(genetic.MUTATION_SA)
    return args


def run_base(cr):
    prefix = " cr = {}".format(cr)
    test_name = u"Rekombinacijos koef., pradinė populiacija"
    runs = 10
    args1 = base_args(cr, 6)
    args2 = base_args(cr, 8)
    args3 = base_args(cr, 10)
    args4 = base_args(cr, 12)
    args5 = base_args(cr, 14)
    args6 = base_args(cr, 16)

    r = runner()
    add_solver(r, args1, c, runs)
    add_solver(r, args2, c, runs)
    add_solver(r, args3, c, runs)
    add_solver(r, args4, c, runs)
    add_solver(r, args5, c, runs)
    add_solver(r, args6, c, runs)
    r.run()

    fig = GenericFig(size=120)
    result = TestRunResult(r, fig, test_name + prefix, base_folder)

    def plot():
        result.averaged_x_values(args1.key, 'r--')
        result.averaged_x_values(args2.key, red_color(1))
        result.averaged_x_values(args3.key, red_color(4))
        result.averaged_x_values(args4.key, green_color(4))
        result.averaged_x_values(args5.key, green_color(1))
        result.averaged_x_values(args6.key, 'g--')

    result.next_subplot(test_name)
    plot()
    result.legend()
    result.ylabel()
    result.next_log_subplot(u"(log skaleje)")
    plot()
    result.save()
