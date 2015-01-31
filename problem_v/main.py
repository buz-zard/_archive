from graph import Graph
import tasks
import utils


if __name__ == '__main__':
    raw_input = utils.read_input('Enter graph edges separated by space or comma '
                                 '(eg. AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7) and press Enter: ')
    if raw_input:
        args = utils.parse_args(raw_input)
        if args:
            print('Graph edges parsed: {0}.\n'.format(args))
            g = Graph()
            for arg in args:
                g.add_edge(*arg)
            if tasks.is_graph_valid(g):
                tasks.task_1(g)
                tasks.task_2(g)
                tasks.task_3(g)
                tasks.task_4(g)
                tasks.task_5(g)
                tasks.task_6(g)
                tasks.task_7(g)
                tasks.task_8(g)
                tasks.task_9(g)
                tasks.task_10(g)
            else:
                print('Not enough graph nodes to run all taks.')
        else:
            print('0 valid arguments parsed.')
    else:
        print('No input given.')
