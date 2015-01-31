from functools import wraps


def task_printer(task_id):
    def decorator(f):
        @wraps(f)
        def wrapper(*args, **kwargs):
            value = f(*args, **kwargs)
            if not value:
                value = 'NO SUCH ROUTE'
            print('#{0}: {1}'.format(task_id, value))
        return wrapper
    return decorator


def is_graph_valid(graph):
    '''
    Validate that graph contains 'abcde' nodes.
    '''
    return graph.contains_of_nodes('abcde')


@task_printer(1)
def task_1(graph):
    '''
    The distance of the route A-B-C.
    '''
    return graph.route_to_distance(list('abc'))


@task_printer(2)
def task_2(graph):
    '''
    The distance of the route A-D.
    '''
    return graph.route_to_distance(list('ad'))


@task_printer(3)
def task_3(graph):
    '''
    The distance of the route A-D-C.
    '''
    return graph.route_to_distance(list('adc'))


@task_printer(4)
def task_4(graph):
    '''
    The distance of the route A-E-B-C-D.
    '''
    return graph.route_to_distance(list('aebcd'))


@task_printer(5)
def task_5(graph):
    '''
    The distance of the route A-E-D.
    '''
    return graph.route_to_distance(list('aed'))


@task_printer(6)
def task_6(graph):
    '''
    The number of trips starting at C and ending at C with a maximum of 3 stops.
    '''
    return graph.find_trips_by_stops('c', 'c', 3)


@task_printer(7)
def task_7(graph):
    '''
    The number of trips starting at A and ending at C with exactly 4 stops.
    '''
    return graph.find_trips_by_stops('a', 'c', 4, True)


@task_printer(8)
def task_8(graph):
    '''
    The length of the shortest route (in terms of distance to travel) from A to C.
    '''
    return graph.find_shortest_distance('a', 'c')


@task_printer(9)
def task_9(graph):
    '''
    The length of the shortest route (in terms of distance to travel) from B to B.
    '''
    return graph.find_shortest_distance('b', 'b')


@task_printer(10)
def task_10(graph):
    '''
    The number of different routes from C to C with a distance of less than 30.
    '''
    return graph.find_trips_by_distance('c', 'c', 30)
