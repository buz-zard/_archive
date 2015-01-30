from django.conf import settings
from django.conf.urls import patterns, include, url
from django.conf.urls.static import static
from django.contrib import admin
from django.contrib.staticfiles.urls import staticfiles_urlpatterns
from django.views.generic.base import RedirectView
admin.autodiscover()

urlpatterns = static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
#urlpatterns += static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)

urlpatterns += patterns('',
                        url(r'^$', RedirectView.as_view(url='/news/')),
                        url(r'^news/', include('cloud.news.urls')),
                        url(r'^admin/', include(admin.site.urls)),
                        )
'''
urlpatterns += staticfiles_urlpatterns()
urlpatterns += patterns('',
                        (r'^static/(?P<path>.*)$', 'django.views.static.serve',
                         {'document_root': settings.STATIC_ROOT}))
'''
