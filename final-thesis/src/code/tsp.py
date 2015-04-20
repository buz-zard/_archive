# -*- coding: utf-8 -*-
from copy import deepcopy
import json
import os
import time

from progressbar import ProgressBar, Bar, Percentage

import matplotlib.pyplot as plt
import networkx as nwx


CONDITION_TIME = "CONDITION_TIME"
CONDITION_LENGTH = "CONDITION_LENGTH"
CONDITION_GENERATION = "CONDITION_GENERATION"
CONDITION_NO_PROGRESS = "CONDITION_NO_PROGRESS"


class TSPSolver(object):

    def __init__(self, graph, conditions={}, absolute_mute=False):
        self.graph = deepcopy(graph)
        self.show_progress = False
        self.display_method = None
        self.display_parameters = None
        self.conditions = conditions
        self.solution_history = []
        self._solution_ = None
        self._last_solution_ = None
        self._time_elapsed_ = 0
        self._started_solving_ = False
        self._finished_solving_ = False
        self._generation_ = 0
        self._no_progress_counter_ = 0
        self._absolute_mute_ = absolute_mute

    def start_solving(self):
        if not self._absolute_mute_:
            print "Started solving using {0}...".\
                format(self.__class__.__name__)
        self._started_solving_ = True
        self._time_start_ = time.time()
        time.clock()

    def conditions_met(self):
        if self.condition_met(CONDITION_TIME,
                              self.time_elapsed(),
                              False):
            return True
        if self._solution_ is not None:
            if self.condition_met(CONDITION_LENGTH,
                                  self._solution_['distance'] - 1,
                                  True):
                return True
        if self.condition_met(CONDITION_GENERATION,
                              self._generation_,
                              False):
            return True
        if self.condition_met(CONDITION_NO_PROGRESS,
                              self._no_progress_counter_,
                              False):
            return True
        return False

    def condition_met(self, condition, value, is_less):
        if condition in self.conditions:
            if is_less:
                if value < self.conditions[condition]:
                    return True
            else:
                if value > self.conditions[condition]:
                    return True
        return False

    def update_progress(self, text=""):
        self._generation_ += 1
        if self.show_progress and not self._absolute_mute_:
            print "> {0} | {1}".format(str(self._generation_), text)
        if self._solution_ is not None:
            self.solution_history.append(self._solution_)
            if self._last_solution_ is not None:
                if (self._solution_['distance'] <
                        self._last_solution_['distance']):
                    self._no_progress_counter_ = 0
                else:
                    self._no_progress_counter_ += 1
            self._last_solution_ = self._solution_

    def end_solving(self):
        if not self._started_solving_:
            raise Exception("Invalid method invocation")
        self._time_elapsed_ = round(self.time_elapsed(), 5)
        self._finished_solving_ = True
        if self._solution_ is not None:
            self._solution_['time'] = self.time_elapsed()
        if not self._absolute_mute_:
            print "Finished solving using {0} in {1} seconds.\nResult: {2}\n".\
                format(self.__class__.__name__,
                       self.time_elapsed(),
                       self.get_solution()['result'])

    def time_elapsed(self):
        if self._started_solving_ and not self._finished_solving_:
            self._time_elapsed_ = time.time() - self._time_start_
        return self._time_elapsed_

    def set_solution(self, route, full_route):
        if route is None or full_route is None:
            return
        distance = self.graph.route_distance(full_route)
        if (self._solution_ is None
                or self._solution_ is not None
                and distance < self._solution_['distance']):
            self._solution_ = {
                'route': tuple(route),
                'full_route': tuple(full_route),
                'distance': distance
            }

    def get_solution(self):
        self._solution_['generations'] = self._generation_
        solution = {
            'result': self._solution_
        }
        if (self.display_method is not None
                or self.display_parameters is not None):
            solution['info'] = {}
            if self.display_method is not None:
                solution['info']['method'] = self.display_method
            if self.display_parameters is not None:
                solution['info']['params'] = self.display_parameters
        return solution

    def nodes_to_solution(self, route):
        last_node = route[0]
        actual_route = [last_node]
        full_route = [last_node]
        for next_node in route:
            if next_node in actual_route:
                continue
            path = self.graph.path_between(last_node, next_node)[1:]
            for val in path:
                if val not in actual_route:
                    actual_route.append(val)
            full_route += path
            last_node = next_node
        actual_route.append(actual_route[0])
        path = self.graph.path_between(last_node, actual_route[0])[1:]
        full_route += path
        return (actual_route, full_route)

    def show_solution(self, filename="name", output_folder='/'):
        self.save_solution(True, filename, output_folder)

    def save_solution(self, show_solution=False,
                      filename="name",
                      output_folder='/'):
        plt.clf()
        edgelist = []
        if self._solution_ is not None:
            last_node = None
            for node in self._solution_['full_route']:
                if last_node is not None:
                    edgelist.append((last_node, node))
                last_node = node
        if output_folder != '/':
            output_folder = '/' + output_folder + '/'
        file_path = 'output{0}{1}_{2}.png'
        subname = self.display_method
        if subname is None:
            subname += self.__class__.__name__
        file_path = file_path.format(output_folder,
                                     subname.lower().replace(' ', '_'),
                                     filename)
        if (not os.path.exists('output' + output_folder)
                and output_folder != '/'):
            os.makedirs('output' + output_folder)
        nwx.draw(self.graph, self.graph.coords, width=1.0, alpha=0.5)
        nwx.draw_networkx_edges(self.graph, self.graph.coords,
                                edgelist=edgelist, width=1.2, alpha=0.9,
                                edge_color='r')
        plt.savefig(file_path)
        if show_solution:
            os.system('eog ' + file_path + ' &')


class TSPSRunner(object):

    def __init__(self, graph):
        self.graph = graph
        self.solvers = {}
        self.results = {}

    def add_tsps(self, solver, params, conditions, reruns, tsps_key):
        if solver not in self.solvers:
            self.solvers[solver] = []
        self.solvers[solver].append((solver, params, conditions,
                                     reruns, tsps_key))

    def run(self):
        print '\n\n\n\n[' + '#' * 100 + ']\n'
        j = 0
        for solver_class, tspss in self.solvers.iteritems():
            for tsps in tspss:
                print 'Running {0} of {1}.'.format(tsps[3], tsps)
                results = []
                bar = ProgressBar(maxval=tsps[3],
                                  widgets=[Bar('=', '[', ']'),
                                           ' ',
                                           Percentage()])
                for i in xrange(tsps[3]):
                    bar.update(i)
                    solver = solver_class(self.graph,
                                          params=tsps[1],
                                          conditions=tsps[2],
                                          absolute_mute=True)
                    solver.solve()
                    results.append({
                        'solution': solver.get_solution()['result'],
                        'solution_history': solver.solution_history
                    })
                bar.finish()
                print ''
                self._handle_results_(results, tsps[4])
            if j != len(self.solvers) - 1:
                print '\n'
            j += 0
        print '[' + '#' * 100 + ']\n\n'

    def save_results(self, filename='data', output_folder='/',
                     hide_all_distances=True):
        if output_folder != '/':
            output_folder = '/' + output_folder + '/'
        if (not os.path.exists('output' + output_folder)
                and output_folder != '/'):
            os.makedirs('output' + output_folder)
        results = deepcopy(self.results)
        if hide_all_distances:
            for rez in results.itervalues():
                del rez['all_distances']
        with open('output' + output_folder + filename + '.json', 'wb') as fp:
            json.dump(results, fp, indent=4)
        self.show_results()

    def show_results(self, hide_all_distances=True):
        results = deepcopy(self.results)
        if hide_all_distances:
            for rez in results.itervalues():
                del rez['all_distances']
        print json.dumps(results, indent=4)

    def _handle_results_(self, results, key):
        data = {
            'distance': {},
            'time': {},
            'generations': {}
        }
        distance_sum = 0
        distance_best = None
        distance_worst = None
        time_sum = 0
        time_best = None
        time_worst = None
        generations_sum = 0
        generations_best = None
        generations_worst = None
        all_distances = []
        for result in results:
            distances = []
            for history in result['solution_history']:
                distances.append(history['distance'])
            all_distances.append(distances)
            distance = result['solution']['distance']
            time = result['solution']['time']
            generations = result['solution']['generations']
            distance_sum += distance
            time_sum += time
            generations_sum += generations
            if distance_best is None or distance_best > distance:
                distance_best = distance
            if distance_worst is None or distance_worst < distance:
                distance_worst = distance
            if time_best is None or time_best > time:
                time_best = time
            if time_worst is None or time_worst < time:
                time_worst = time
            if generations_best is None or generations_best > generations:
                generations_best = generations
            if generations_worst is None or generations_worst < generations:
                generations_worst = generations
        data['distance']['average'] = int(distance_sum / len(results))
        data['distance']['best'] = distance_best
        data['distance']['worst'] = distance_worst
        data['time']['average'] = round(time_sum / len(results), 5)
        data['time']['best'] = time_best
        data['time']['worst'] = time_worst
        data['generations']['average'] = int(generations_sum / len(results))
        data['generations']['best'] = generations_best
        data['generations']['worst'] = generations_worst
        data['all_distances'] = all_distances
        self.results[key] = data
