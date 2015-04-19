# -*- coding: utf-8 -*-
import os

from pylab import figure, show
from scipy.interpolate import griddata

import matplotlib.pyplot as plt
import numpy as np
from utils import average_out_lists


fig_hash = {
    1: True
}


def get_fig():
    key = fig_hash.keys()[len(fig_hash) - 1]
    fig_hash[key + 1] = True
    fig_hash[key] = figure(key)
    return fig_hash[key]


def get_subplot(fig, subplot_id=111, ylabel="", xlabel="", title=""):
    subplot = fig.add_subplot(subplot_id)
    subplot.set_title(title)
    subplot.set_xlabel(xlabel)
    subplot.set_ylabel(ylabel)
    return subplot


def sh_to_contour_heatmap_scatter(values, resolution=100, return_maxs=False):
    max_val = 0
    min_val = values[0][0]
    val_len = 0
    for val in values:
        if max_val < max(val):
            max_val = max(val)
        if min_val > min(val):
            min_val = min(val)
        if val_len < len(val):
            val_len = len(val)
    dx = int(val_len / resolution)
    if val_len < resolution:
        dx = 1
    dy = int((max_val - min_val) / resolution)
    if (max_val - min_val) < resolution:
        dy = 1
    last_base_x = None
    counter = {}
    for k in xrange(val_len):
        base_k = k - k % dx
        if base_k not in counter:
            counter[base_k] = {}
        if last_base_x is None:
            last_base_x = base_k
        for val in values:
            if k < len(val):
                i = val[k]
                base_i = i - i % dy
                if base_i in counter[base_k]:
                    counter[last_base_x][base_i] += 1
                else:
                    counter[last_base_x][base_i] = 1
            else:
                counter[last_base_x][base_i] = 0
        if last_base_x < base_k or k == val_len - 1:
            counter[last_base_x][max(counter[last_base_x].keys()) + dy] = 0
            last_base_x = base_k
    xs = []
    ys = []
    zs = []
    for x in counter.keys():
        for y in counter[x].keys():
            xs.append(x)
            ys.append(y)
            zs.append(counter[x][y])
    if return_maxs:
        return xs, ys, zs, (val_len, min_val, max_val)
    else:
        return xs, ys, zs


class GenericFig():

    def __init__(self, size=110, xlabel="Iteracijos", ylabel="Atstumas"):
        self._fig_ = get_fig()
        self._size_ = size
        self._xlabel_ = xlabel
        self._ylabel_ = ylabel
        self._next_subplot_id_ = 1
        self._subplots_ = {}
        self._current_subplot_ = None

    def set_ylabel(self, label):
        self._ylabel_ = label

    def next_subplot(self, title=""):
        subplot_id = self._size_ + self._next_subplot_id_
        subplot = get_subplot(fig=self._fig_,
                              subplot_id=subplot_id,
                              title=title,
                              xlabel=self._xlabel_,
                              ylabel=self._ylabel_)
        subplot.grid(True)
        self._current_subplot_ = subplot
        self._subplots_[self._next_subplot_id_] = subplot
        self._next_subplot_id_ += 1

    def current_subplot(self, subplot_id=1):
        self._current_subplot_ = self._subplots_[subplot_id]
        return self._current_subplot_

    def add_plot_values(self, x, y=None, line=None, label=''):
        if y is None:
            y = range(len(x))
        if line is None:
            self._current_subplot_.plot(y, x, label=label)
        else:
            self._current_subplot_.plot(y, x, line, label=label)

    def add_scatter(self, x, y, s=0.3, alpha=1):
        self._current_subplot_.scatter(x, y, s=s, alpha=alpha)

    def add_scatter_heatmap(self, x, y, z, rez=100, alpha=0.7):
        color = []
        for item in z:
            if item == 0:
                color.append('1.0')
            else:
                color.append(str((255. / item) / 255.))
        size = int(1000 / rez)
        self._current_subplot_.scatter(x, y, c=color, s=size, alpha=alpha)
        plt.xlim(0)

    def add_contour_heatmap(self, x, y, z, rez):
        xi = np.linspace(min(x), max(x), len(x))
        yi = np.linspace(min(y), max(y), len(y))
        zi = griddata((x, y), z,
                      (xi[None, :], yi[:, None]),
                      method='linear', fill_value=0)
        plt.contourf(xi, yi, zi, rez, cmap=plt.cm.jet,
                     vmax=abs(zi).max(), vmin=0)
        self.set_xlim(0)
        self.set_ylim(min(y), max(y))

    def set_xlim(self, *args):
        plt.xlim(*args)

    def set_ylim(self, *args):
        plt.ylim(*args)

    def set_yscale(self, val):
        self._current_subplot_.set_yscale(val)

    def set_xscale(self, val):
        self._current_subplot_.set_xscale(val)

    def set_legend(self, loc='upper right'):
        self._current_subplot_.legend(loc=loc)

    def add_averaged_x_plot_values(self, x_values_list, y=None, line=None,
                                   label=''):
        x = average_out_lists(x_values_list[::])
        self.add_plot_values(x, y, line, label)

    def show(self):
        show()

    def save(self, filename="figure", output_folder='/', do_show=False):
        if output_folder != '/':
            output_folder = '/' + output_folder + '/'
        if (not os.path.exists('output' + output_folder)
                and output_folder != '/'):
            os.makedirs('output' + output_folder)
        self._fig_.savefig('output' + output_folder + filename + '.png')
        if do_show:
            show()
