# -*- coding: utf-8 -*-
from tsp import CONDITION_TIME, CONDITION_GENERATION
from __builtin__ import file


def condition(generations):
    return {CONDITION_GENERATION: generations, CONDITION_TIME: 600}


class TestRunResult(object):

    def __init__(self, runner, fig, filename, foldername):
        self.runner = runner
        self.fig = fig
        self.filename = filename
        self.foldername = foldername

    def save(self):
        self.runner.save_results(self.filename, self.foldername)
        self.fig.save(self.filename, self.foldername)

    def next_subplot(self, title=""):
        self.fig.next_subplot(title)

    def next_log_subplot(self, title=""):
        self.next_subplot(title)
        self.fig.set_xscale('log')

    def legend(self):
        self.fig.set_legend()

    def ylabel(self, value=""):
        self.fig.set_ylabel(value)

    def averaged_x_values(self, run_name, line, label=None):
        if not label:
            label = run_name
        self.fig.add_averaged_x_plot_values(self.runner.results[run_name]
                                            ['all_distances'], line=line,
                                            label=label)
