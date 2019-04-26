from rest_framework import viewsets
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView

from core.models.typechambre import TypeChambre
from core.models.typeuser import TypeUser
from core.serializers.typechambreserializer import TypeChambreSerializer
from core.serializers.typeuserserializer import TypeUserSerializer

class TypeUserList(APIView):
    permission_classes = (IsAuthenticated,)
    def get(self, request):
        typesuser = TypeUser.objects.all()
        serializer = TypeUserSerializer(typesuser, many=True)
        return Response(serializer.data)