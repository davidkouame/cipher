from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns

from core.views.imageviewset import ImageList, ImageDetail, ImageCreate, ImageListSearchByUser

urlpatterns = [
    url(r'^images/$', ImageList.as_view()),
    url(r'^images/(?P<pk>[0-9]+)/$', ImageDetail.as_view()),
    url(r'^images/user/(?P<pk>[0-9]+)/$', ImageListSearchByUser.as_view()),
    url(r'^images/create/$', ImageCreate.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns, allowed=['json', 'html'])