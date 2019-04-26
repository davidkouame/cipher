from rest_framework import viewsets

from core.models.typechambre import TypeChambre
from core.serializers.typechambreserializer import TypeChambreSerializer


class TypeChambreViewSet(viewsets.ModelViewSet):
    queryset = TypeChambre.objects.all()
    serializer_class = TypeChambreSerializer

    def create(self, request, *args, **kwargs):
        return ""