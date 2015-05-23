# -*- coding: utf-8 -*
from tests import initial_population, ox_params, mr_params
from tests.final import chrom, cr, mr, cr_mr, chr_cr, chr_mr, chr_ipop, cr_mr_10, cr_mr_20, cr_mr_30, cr_mr_40, mr_cr_20


if __name__ == '__main__':
    print 'Starting...'
    # ox_params.run_all()
    # mr_params.run_all()
    # initial_population.run_all()

    # chrom.run_all()
    # mr.run_all()
    # cr.run_all()

    # cr_mr_10.run_all()
    # cr_mr_20.run_all()
    mr_cr_20.run_all()
    cr_mr_30.run_all()
    cr_mr_40.run_all()

    chr_ipop.run_all()

    chr_mr.run_all()
    chr_cr.run_all()
    cr_mr.run_all()
    print '\ndone.\n'
