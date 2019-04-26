import uuid
from django.db import models
from django.db.models.signals import pre_save
from django.dispatch import receiver

from core.models.loue import Loue
from core.models.reserve import Reserve
from core.models.statutreservation import StatutReservation


class Paiement(models.Model):
    reference = models.CharField(max_length=20)
    montant = models.DecimalField(max_digits=5,decimal_places=2)
    datepayer = models.DateTimeField(auto_now=True)
    reserve = models.ForeignKey(Reserve, on_delete=models.CASCADE, blank=True, null=True)
    loue = models.ForeignKey(Loue, on_delete=models.CASCADE, blank=True, null=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return "%s" % self.reference

@receiver(pre_save, sender=Paiement)
def preSavePaiement(sender, instance, **kwargs):
    instance.reference = str(str(uuid.uuid4().fields[-1])[:10])

@receiver(pre_save, sender=Paiement)
def postSavePaiement(sender, instance, **kwargs):
    # if(instance.reserve!=None):
    print("dd")
    #instance.reserve
    #statutreservation = StatutReservation.objects.get(id=3)