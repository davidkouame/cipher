
from rest_framework import routers, serializers, viewsets

from core.models.souscategorie import SousCategorie
from core.models.typechambre import TypeChambre


# class TypeChambreSerializer(serializers.HyperlinkedModelSerializer):
from core.serializers.publicationserializer import PublicationSerializer


class SousCategorieSerializer(serializers.Serializer):
    name = serializers.CharField(max_length=200)
    id = serializers.IntegerField()
    publications = PublicationSerializer(many=True, read_only=True)

    class Meta:
        model = SousCategorie
        fields = ('__all__')