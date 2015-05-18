# -*- coding: utf-8 -*
from tests import initial_population, ox_params, mr_params
from tests.final import chrom, cr, mr, cr_mr, mr_cr


if __name__ == '__main__':
    print 'Starting...'
    ox_params.run_all()
    mr_params.run_all()
    initial_population.run_all()

    chrom.run_all()
    mr.run_all()
    cr.run_all()
    cr_mr.run_all()
    mr_cr.run_all()
    print '\ndone.\n'
