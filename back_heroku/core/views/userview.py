# from django.contrib.auth.models import User
from core.models.user import User
from django.http import Http404
from rest_framework import generics, status
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny, IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView

from core.serializers.userserializer import UserSerializer


class UserListView(generics.ListCreateAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer

class UserDetail(APIView):
    permission_classes = (IsAuthenticated,)
    def get_object(self, pk):
        try:
            return User.objects.get(pk=pk)
        except User.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        user = self.get_object(pk)
        serializer = UserSerializer(instance=user)
        return Response(serializer.data)

class UserDetailByUsername(APIView):
    permission_classes = (IsAuthenticated,)

    def get(self, request, username, format=None):
        try:
            user = User.objects.get(username=username)
            serializer = UserSerializer(instance=user)
            return Response(serializer.data)
        except User.DoesNotExist:
            raise Http404


@api_view(['POST'])
@permission_classes((AllowAny,))
def create_user(request):
    permission_classes = (IsAuthenticated,)
    serialized = UserSerializer(data=request.data)
    if serialized.is_valid():
        serialized.save()
        return Response(serialized.data, status=status.HTTP_201_CREATED)
    else:
        return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)