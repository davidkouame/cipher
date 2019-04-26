from rest_framework import status
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView

from core.models.loue import Loue
from core.serializers.loueserializer import LoueSerializer

from django.http import Http404

class LoueList(APIView):
    permission_classes = (IsAuthenticated,)
    def get(self, request):
        loues = Loue.objects.all()
        serializer = LoueSerializer(loues, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = LoueSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class LoueDetail(APIView):
    permission_classes = (IsAuthenticated,)
    def get_object(self, pk):
        try:
            return Loue.objects.get(pk=pk)
        except Loue.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        loue = self.get_object(pk)
        serializer = LoueSerializer(loue)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        loue = self.get_object(pk)
        serializer = LoueSerializer(loue, data=request.data)
        if serializer.is_valid():
             serializer.save()
             return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        loue = self.get_object(pk)
        loue.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

