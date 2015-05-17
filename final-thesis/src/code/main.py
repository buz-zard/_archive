# -*- coding: utf-8 -*
from tests import initial_population, ox_params, mr_params


if __name__ == '__main__':
    print 'Starting...'
    ox_params.run_all()
    mr_params.run_all()
    initial_population.run_all()
    print '\ndone.\n'
