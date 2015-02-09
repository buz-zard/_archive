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
    local('virtualenv ' + ENV_DIR)
    with virtualenv():
        local('pip install -r ' + REQUIREMENTS_FILE)


def go():
    with virtualenv():
        local('./main.py')


def freeze():
    with virtualenv():
        local('pip freeze > ' + REQUIREMENTS_FILE)

