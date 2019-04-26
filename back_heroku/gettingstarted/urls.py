from django.conf.urls import url
from django.urls import path, include
from django.conf.urls.static import static

from django.contrib import admin

from gettingstarted import settings

admin.autodiscover()

import hello.views
import core.urls.base

# To add a new path, first import the app:
# import blog
#
# Then add the new path:
# path('blog/', blog.urls, name="blog")
#
# Learn more here: https://docs.djangoproject.com/en/2.1/topics/http/urls/

urlpatterns = [
    path("", hello.views.index, name="index"),
    path("db/", hello.views.db, name="db"),
    path("admin/", admin.site.urls),
    # path('/', include('core.urls.base')),
    url(r'^', include('core.urls.base')),
    url(r'^file/', include('core.urls.file')),
    url(r'^api/', include('core.urls.image')),

]

if settings.DEBUG:
  urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
