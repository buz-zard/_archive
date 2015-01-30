# -*- coding: utf-8 -*-
from django.utils import timezone
from django.views.generic.list import ListView

from models import Post


class PostsView(ListView):
    template_name = 'news/posts.html'
    model = Post
