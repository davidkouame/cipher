# from django.contrib.auth.models import User
from core.models.user import User
from django.db import models

class Profile(models.Model):
    user = models.OneToOneField(User, related_name="profiles", on_delete=models.CASCADE)
    phone = models.CharField(
        max_length=15, blank=True, verbose_name="Téléphone")
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return "%s" % self.user