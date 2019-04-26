from django.db import models

from core.models.typechambre import TypeChambre
from core.models.typehotel import TypeHotel


class Hotel(models.Model):
    name = models.CharField(max_length=200)
    typehotel = models.ForeignKey(TypeHotel, on_delete=models.CASCADE)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)