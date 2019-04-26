
from rest_framework import routers, serializers, viewsets

from core.models.typeuser import TypeUser


class TypeUserSerializer(serializers.Serializer):
    name = serializers.CharField(max_length=200)
    id = serializers.IntegerField()

    def create(self, validated_data):
        return TypeUser.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name = validated_data.get('name', instance.name)
        instance.save()
        return instance