from rest_framework import viewsets
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView

from core.models.categorie import Categorie
from core.models.souscategorie import SousCategorie
from core.models.typechambre import TypeChambre
from core.models.typeuser import TypeUser
from core.serializers.categorieserializer import CategorieSerializer
from core.serializers.souscategorieserializer import SousCategorieSerializer
from core.serializers.typechambreserializer import TypeChambreSerializer
from core.serializers.typeuserserializer import TypeUserSerializer

class SousCategorieList(APIView):
    permission_classes = (IsAuthenticated,)
    def get(self, request):
        souscategories = SousCategorie.objects.all()
        serializer = SousCategorieSerializer(souscategories, many=True)
        return Response(serializer.data)