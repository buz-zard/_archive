# -*- coding: utf-8 -*-
from django.contrib import admin
from sorl.thumbnail.admin import AdminImageMixin


from . import models


class JournalistAdmin(admin.ModelAdmin):
    list_display = ('name', 'surname', 'created')
    search_fields = ['name', 'surname']


class PostImageInlineAdmin(AdminImageMixin, admin.TabularInline):
    model = models.PostImage


class PostAdmin(admin.ModelAdmin):
    inlines = [PostImageInlineAdmin]
    list_display = ('title', 'created')
    search_fields = ['title']


admin.site.register(models.Journalist, JournalistAdmin)
admin.site.register(models.Post, PostAdmin)
