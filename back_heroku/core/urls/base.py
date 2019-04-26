from rest_framework import routers
from rest_framework.authtoken import views

from core.views.categorieviewset import CategorieList
from core.views.loueviewset import LoueList, LoueDetail
from core.views.paiementviewset import PaiementList, PaiementDetail
from core.views.publicationviewset import PublicationDetail, PublicationList
from core.views.reservationview import ReservationList, ReservationDetail
from core.views.souscategorieviewset import SousCategorieList
from core.views.typechambreview import TypeChambreList, TypeChambreDetail
from core.views.typeuserview import TypeUserList
from core.views.typeviewset import TypeChambreViewSet
# from core.views.userview import UserViewSet
from django.conf.urls import url, include
from django.urls import path

from core.views.userview import UserListView, create_user, UserDetail, UserDetailByUsername

router = routers.DefaultRouter()
# router.register(r'users', UserViewSet)
# router.register(r'paiements', PaiementViewSet)
# router.register(r'types-chambre', TypeChambreViewSet)
# router.register(r'types-chambre', TypeChambreView.as_view())

urlpatterns = [
    url(r'^api', include(router.urls)),
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    path('api/types-user/', TypeUserList.as_view()),
    path('api/categories/', CategorieList.as_view()),
    path('api/sous-categories/', SousCategorieList.as_view()),
    path('api/publications/', PublicationList.as_view()),
    path('api/publication/<int:pk>/', PublicationDetail.as_view()),
    # path('api/reservations/', ReservationList.as_view()),
    # path('api/reservation/<int:pk>/', ReservationDetail.as_view()),
    # path('api/loues/', LoueList.as_view()),
    # path('api/loue/<int:pk>/', LoueDetail.as_view()),
    url(r'^api-token-auth/', views.obtain_auth_token),
    path('api/users/', create_user),
    path('api/user/<int:pk>/', UserDetail.as_view()),
    path('api/user/<str:username>/', UserDetailByUsername.as_view())
]