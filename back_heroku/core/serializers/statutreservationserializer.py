
from rest_framework import routers, serializers, viewsets

from core.models.statutreservation import StatutReservation


class StatutReservationSerializer(serializers.Serializer):
    name = serializers.CharField(max_length=200)
    id = serializers.IntegerField(read_only=True)

    def create(self, validated_data):
        return StatutReservation.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name = validated_data.get('name', instance.name)
        instance.save()
        return instance