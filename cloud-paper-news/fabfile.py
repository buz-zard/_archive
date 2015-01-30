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
# Vars & stuff
# ==============================================================================
def install():
    local('virtualenv ' + ENV_DIR)
    with virtualenv():
        local('pip install -r ' + REQUIREMENTS_FILE)


def deploy():
    local('heroku maintenance:on')
    local('git push heroku master')
    local('heroku maintenance:off')


def freeze():
    with virtualenv():
        local('pip freeze > ' + REQUIREMENTS_FILE)


def go():
    with virtualenv():
        local('./manage.py runserver')
