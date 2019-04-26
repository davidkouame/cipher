import os
from django.conf import settings

# DRF modules
from rest_framework import status
from rest_framework.request import Request
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated

from rest_framework.generics import ListAPIView, RetrieveAPIView, CreateAPIView
from rest_framework.permissions import IsAdminUser

from core.models.image import Image
from core.serializers.imageserializer import ImageSerializer


class ImageList(ListAPIView):

    serializer_class = ImageSerializer
    # permission_classes = (IsAdminUser,)
    permission_classes = (IsAuthenticated,)
    queryset = Image.objects.all()

class ImageListSearchByUser(ListAPIView):

    serializer_class = ImageSerializer
    # permission_classes = (IsAdminUser,)
    permission_classes = (IsAuthenticated,)

    def get(self, request, pk):
        images = Image.objects.filter(publications__user__id=pk)
        serializer = ImageSerializer(images, many=True)
        return Response(serializer.data)

class ImageDetail(RetrieveAPIView):

    'Retrieve an image instance'

    serializer_class = ImageSerializer
    # permission_classes = (IsAdminUser,)
    permission_classes = (IsAuthenticated,)
    queryset =  Image.objects.all()

class ImageCreate(CreateAPIView):

    'Create a new image instance'
    permission_classes = (IsAuthenticated,)
    serializer_class = ImageSerializer

    def post(self, request):

        serializer = ImageSerializer(data=request.data)
        if serializer.is_valid():

            # Save request image in the database
            serializer.save()

            return Response(serializer.data, status=status.HTTP_201_CREATED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)