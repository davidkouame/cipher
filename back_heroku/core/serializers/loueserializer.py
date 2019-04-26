
from rest_framework import routers, serializers, viewsets

from core.models.loue import Loue
from core.serializers.chambreserializer import ChambreSerializer


class LoueSerializer(serializers.ModelSerializer):
    reference = serializers.CharField(max_length=20,read_only=True)
    montant = serializers.DecimalField(max_digits=10,decimal_places=2)
    dateloue = serializers.DateTimeField()
    id = serializers.IntegerField(read_only=True)
    # chambre = ChambreSerializer()

    class Meta:
        model = Loue
        fields = ('__all__')

    def create(self, validated_data):
        return Loue.objects.create(**validated_data)

    def update(self, instance, validated_data):
        # instance.reference = validated_data.get('reference', instance.montant)
        instance.montant = validated_data.get('montant', instance.montant)
        instance.dateloue = validated_data.get('dateloue', instance.dateloue)
        instance.chambre = validated_data.get('chambre', instance.chambre)
        instance.user = validated_data.get('user', instance.user)
        instance.save()
        return instance