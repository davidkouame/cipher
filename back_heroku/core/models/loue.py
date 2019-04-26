# from django.contrib.auth.models import User
from django.db import models

import uuid

from core.models.chambre import Chambre
from django.db.models.signals import pre_save
from django.dispatch import receiver
from core.models.user import User

from core.models.profile import Profile


class Loue(models.Model):
    montant = models.DecimalField(max_digits=5,decimal_places=2)
    dateloue = models.DateTimeField(auto_now=False)
    chambre = models.ForeignKey(Chambre, on_delete=models.CASCADE)
    user = models.ForeignKey(User, related_name='loues', on_delete=models.CASCADE)
    reference = models.CharField(max_length=20)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return "%s" % self.reference

@receiver(pre_save, sender=Loue)
def preSaveLoue(sender, instance, **kwargs):
    instance.reference = str(str(uuid.uuid4().fields[-1])[:10])