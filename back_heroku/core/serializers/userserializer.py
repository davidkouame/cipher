
from django.conf.urls import url, include
# from django.contrib.auth.models import User
from core.models.user import User
from rest_framework import routers, serializers, viewsets

# class UserSerializer(serializers.HyperlinkedModelSerializer):
#     class Meta:
#         model = User
#         fields = ('url', 'username', 'email', 'is_staff')
from core.serializers.loueserializer import LoueSerializer
from core.serializers.publicationserializer import PublicationSerializer
from core.serializers.reserveserializer import ReserveSerializer
from core.serializers.typeuserserializer import TypeUserSerializer


class UserSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)
    publications = PublicationSerializer(many=True, read_only=True)
    id = serializers.IntegerField(read_only=True)
    # typeuser = TypeUserSerializer()

    class Meta:
        model = User
        fields = ('first_name', 'last_name', 'email', 'password', "username", "publications", "typeuser","id")

    def create(self, validated_data):
        user = super(UserSerializer, self).create(validated_data)
        user.set_password(validated_data['password'])
        user.save()
        return user