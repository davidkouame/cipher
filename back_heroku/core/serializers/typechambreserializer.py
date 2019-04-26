
from rest_framework import routers, serializers, viewsets

from core.models.typechambre import TypeChambre


# class TypeChambreSerializer(serializers.HyperlinkedModelSerializer):
class TypeChambreSerializer(serializers.Serializer):
    name = serializers.CharField(max_length=200)
    id = serializers.IntegerField()
    # fields = ('__all__')
    # class Meta:
    #     model = TypeChambre
    #     # fields = ('__all__')
    #     title = serializers.CharField(max_length=120)

    def create(self, validated_data):
        return TypeChambre.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name = validated_data.get('name', instance.name)
        instance.save()
        return instance