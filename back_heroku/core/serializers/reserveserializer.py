
from rest_framework import routers, serializers, viewsets

from core.models.reserve import Reserve
from core.serializers.statutreservationserializer import StatutReservationSerializer


class ReserveSerializer(serializers.ModelSerializer):
    reference = serializers.CharField(max_length=20,read_only=True)
    montant = serializers.DecimalField(max_digits=10,decimal_places=2)
    datereserve = serializers.DateTimeField()
    id = serializers.IntegerField(read_only=True)
    statutreservation = StatutReservationSerializer(read_only=True)

    class Meta:
        model = Reserve
        fields = ('__all__')

    def create(self, validated_data):
        return Reserve.objects.create(**validated_data)

    def update(self, instance, validated_data):
        # instance.reference = validated_data.get('reference', instance.montant)
        instance.montant = validated_data.get('montant', instance.montant)
        instance.datereserve = validated_data.get('datereserve', instance.datereserve)
        instance.chambre = validated_data.get('chambre', instance.chambre)
        instance.user = validated_data.get('user', instance.user)
        instance.save()
        return instance