
from rest_framework import routers, serializers, viewsets

from core.models.categorie import Categorie
from core.models.souscategorie import SousCategorie
from core.models.typechambre import TypeChambre


# class TypeChambreSerializer(serializers.HyperlinkedModelSerializer):
from core.serializers.souscategorieserializer import SousCategorieSerializer


class CategorieSerializer(serializers.Serializer):
    name = serializers.CharField(max_length=200)
    id = serializers.IntegerField()
    souscategories = SousCategorieSerializer(many=True, read_only=True)

    class Meta:
        model = Categorie
        fields = ('__all__')