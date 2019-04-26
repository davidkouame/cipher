from rest_framework import status
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView

from core.models.reserve import Reserve
from core.serializers.reserveserializer import ReserveSerializer

from django.http import Http404

class ReservationList(APIView):
    permission_classes = (IsAuthenticated,)
    def get(self, request):
        reserves = Reserve.objects.all()
        serializer = ReserveSerializer(reserves, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = ReserveSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class ReservationDetail(APIView):
    def get_object(self, pk):
        try:
            return Reserve.objects.get(pk=pk)
        except Reserve.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        reserve = self.get_object(pk)
        serializer = ReserveSerializer(reserve)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        reserve = self.get_object(pk)
        serializer = ReserveSerializer(reserve, data=request.data)
        if serializer.is_valid():
             serializer.save()
             return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        reserve = self.get_object(pk)
        reserve.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

