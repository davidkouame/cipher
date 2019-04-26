
from rest_framework import routers, serializers, viewsets

from core.models.publication import Publication
from core.models.reserve import Reserve
from core.serializers.statutreservationserializer import StatutReservationSerializer


class PublicationSerializer(serializers.ModelSerializer):
    reference = serializers.CharField(max_length=20,read_only=True)
    id = serializers.IntegerField(read_only=True)

    class Meta:
        model = Publication
        fields = ('__all__')

    def create(self, validated_data):
        return Publication.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.souscategorie = validated_data.get('souscategorie', instance.souscategorie)
        instance.user = validated_data.get('user', instance.user)
        instance.file = validated_data.get('file', instance.file)
        instance.save()
        return instance