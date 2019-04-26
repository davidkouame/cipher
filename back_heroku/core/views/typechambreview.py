from rest_framework import status
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView

from core.models.typechambre import TypeChambre
from core.serializers.typechambreserializer import TypeChambreSerializer

from django.http import Http404


class TypeChambreList(APIView):
    permission_classes = (IsAuthenticated,)
    def get(self, request):
        typechambres = TypeChambre.objects.all()
        # the many param informs the serializer that it will be serializing more than a single article.
        serializer = TypeChambreSerializer(typechambres, many=True)
        return Response(serializer.data)

    # def post(self, request):
    #     typechambre = request.data.get('typechambre')
    #     serializer = TypeChambreSerializer(data=typechambre)
    #     if serializer.is_valid(raise_exception=True):
    #         type_chambre_saved = serializer.save()
    #     # data = {"statut": 1, "data": type_chambre_saved}
    #     # return Response({"success": "Article '{}' created successfully".format(type_chambre_saved.name)})
    #     # return Response({"success": type_chambre_saved})
    #     return Response(data)

    def post(self, request, format=None):
        serializer = TypeChambreSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class TypeChambreDetail(APIView):
    permission_classes = (IsAuthenticated,)
    """
    Retrieve, update or delete a snippet instance.
    """
    def get_object(self, pk):
        try:
            return TypeChambre.objects.get(pk=pk)
        except TypeChambre.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        typechambre = self.get_object(pk)
        serializer = TypeChambreSerializer(typechambre)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        typechambre = self.get_object(pk)
        serializer = TypeChambreSerializer(typechambre, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        typechambre = self.get_object(pk)
        typechambre.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
