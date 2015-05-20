# -*- coding: utf-8 -*
from tests import initial_population, ox_params, mr_params
from tests.final import chrom, cr, mr, ipop, cr_mr, chr_cr, chr_mr, chr_ipop


if __name__ == '__main__':
    print 'Starting...'
    ox_params.run_all()
    mr_params.run_all()
    initial_population.run_all()

    chrom.run_all()
    mr.run_all()
    cr.run_all()

    chr_ipop.run_all()
    '''
    chr_cr.run_all()
    chr_mr.run_all()
    cr_mr.run_all()
    #ipop.run_all()
    print '\ndone.\n'
    '''
