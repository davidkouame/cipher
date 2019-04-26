

from django.db.models.signals import pre_save

from core.models.loue import Loue


def preSaveLoue(sender, instance, **kwargs):
    print("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")


pre_save.connect(preSaveLoue, sender=Loue)