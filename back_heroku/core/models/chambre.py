from django.db import models

from core.models.hotel import Hotel
from core.models.typechambre import TypeChambre


class Chambre(models.Model):
    name = models.CharField(max_length=200)
    typechambre = models.ForeignKey(TypeChambre, on_delete=models.CASCADE)
    hotel = models.ForeignKey(Hotel, on_delete=models.CASCADE)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)