from rest_framework import status
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView

from core.models.loue import Loue
from core.serializers.loueserializer import LoueSerializer

from django.http import Http404

class RegisterList(APIView):
    def post(self, request, format=None):
        serializer = LoueSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

