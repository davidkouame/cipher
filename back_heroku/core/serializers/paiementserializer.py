
from rest_framework import routers, serializers, viewsets

from core.models.paiement import Paiement
from core.serializers.reserveserializer import ReserveSerializer


class PaiementSerializer(serializers.ModelSerializer):
    reference = serializers.CharField(max_length=20,read_only=True)
    montant = serializers.DecimalField(max_digits=5,decimal_places=2)
    datepayer = serializers.DateTimeField()
    id = serializers.IntegerField(read_only=True)
    #reserve = serializers.StringRelatedField(many=False) 

    class Meta:
        model = Paiement
        fields = ('__all__')

    def create(self, validated_data):
        return Paiement.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.datepayer = validated_data.get('datepayer', instance.datepayer)
        instance.loue = validated_data.get('loue', instance.loue)
        instance.reserve = validated_data.get('reserve', instance.reserve)
        instance.save()
        return instance