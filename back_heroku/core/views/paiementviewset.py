from rest_framework import viewsets, status
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView

from core.models.paiement import Paiement
from core.serializers.paiementserializer import PaiementSerializer

from django.http import Http404

class PaiementList(APIView):
    permission_classes = (IsAuthenticated,)
    def get(self, request):
        paiements = Paiement.objects.all()
        serializer = PaiementSerializer(paiements, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = PaiementSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class PaiementDetail(APIView):
    permission_classes = (IsAuthenticated,)
    def get_object(self, pk):
        try:
            return Paiement.objects.get(pk=pk)
        except Paiement.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        paiement = self.get_object(pk)
        serializer = PaiementSerializer(paiement)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        paiement = self.get_object(pk)
        serializer = PaiementSerializer(paiement, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        paiement = self.get_object(pk)
        paiement.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
