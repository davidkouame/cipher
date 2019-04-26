import uuid
from django.db import models
# from django.contrib.auth.models import User
from django.db.models.signals import pre_save
from django.dispatch import receiver

from core.models.chambre import Chambre
from core.models.file import File
from core.models.image import Image
from core.models.loue import Loue
from core.models.profile import Profile
from core.models.souscategorie import SousCategorie
from core.models.statutreservation import StatutReservation
from core.models.user import User


class Publication(models.Model):
    reference = models.CharField(max_length=20)
    souscategorie = models.ForeignKey(SousCategorie, related_name="publications", on_delete=models.CASCADE)
    # image = models.ForeignKey(File, related_name="publications", on_delete=models.CASCADE, null=True)
    image = models.ForeignKey(Image, related_name="publications", on_delete=models.CASCADE, null=True)
    user = models.ForeignKey(User, related_name='publications', on_delete=models.CASCADE)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    # def __str__(self):
    #     return "%s" % self.reference

@receiver(pre_save, sender=Publication)
def preSaveLoue(sender, instance, **kwargs):
    # instance.statutreservation = StatutReservation.objects.get(id=1)
    instance.reference = str(str(uuid.uuid4().fields[-1])[:10])