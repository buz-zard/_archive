# -*- coding: utf-8 -*-
from contextlib import contextmanager
import os

from fabric.api import local, prefix


# ==============================================================================
# Vars & stuff
# ==============================================================================
ENV_DIR = 'venv'
PROJECT_DIR = os.path.dirname(os.path.realpath(__file__))
REQUIREMENTS_FILE = "requirements.txt"


@contextmanager
def virtualenv():
    with prefix('. ' + os.path.join(PROJECT_DIR, ENV_DIR, 'bin/activate')):
        yield


# ==============================================================================
# Fabric commands
# ==============================================================================
def setup():
    local('virtualenv -p python3 ' + ENV_DIR)
    local('sudo apt-get build-dep python-pygame')
    local('sudo apt-get install python-dev')
    local('sudo apt-get install mercurial')
    with virtualenv():
        local('pip install hg+http://bitbucket.org/pygame/pygame')


def go():
    with virtualenv():
        local('python3 main.py')


def freeze():
    with virtualenv():
        local('pip freeze > ' + REQUIREMENTS_FILE)
