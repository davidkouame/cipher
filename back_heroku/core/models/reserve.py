import uuid
from django.db import models
# from django.contrib.auth.models import User
from django.db.models.signals import pre_save
from django.dispatch import receiver

from core.models.chambre import Chambre
from core.models.loue import Loue
from core.models.profile import Profile
from core.models.statutreservation import StatutReservation
from core.models.user import User


class Reserve(models.Model):
    reference = models.CharField(max_length=20)
    montant = models.DecimalField(max_digits=10,decimal_places=2)
    datereserve = models.DateTimeField(auto_now=False)
    chambre = models.ForeignKey(Chambre, on_delete=models.CASCADE)
    user = models.ForeignKey(User, related_name='reserves', on_delete=models.CASCADE)
    statutreservation = models.ForeignKey(StatutReservation, on_delete=models.CASCADE)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    # def __str__(self):
    #     return "%s" % self.reference

@receiver(pre_save, sender=Reserve)
def preSaveLoue(sender, instance, **kwargs):
    instance.statutreservation = StatutReservation.objects.get(id=1)
    instance.reference = str(str(uuid.uuid4().fields[-1])[:10])