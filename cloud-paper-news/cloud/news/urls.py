# -*- coding: utf-8 -*-
from django.conf.urls import url, patterns

from views import PostsView

urlpatterns = patterns('',
                       url(r'^$', PostsView.as_view()),
                       )
