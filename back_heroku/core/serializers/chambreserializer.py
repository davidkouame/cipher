
from rest_framework import routers, serializers, viewsets

from core.models.loue import Chambre


class ChambreSerializer(serializers.ModelSerializer):

    class Meta:
        model = Chambre
        fields = ('__all__')