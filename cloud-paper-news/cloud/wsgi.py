import os

from dj_static import Cling
from django.core.wsgi import get_wsgi_application

os.environ['DJANGO_SETTINGS_MODULE'] = 'cloud.settings'
application = Cling(get_wsgi_application())
