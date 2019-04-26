from django.db import models
from django.contrib.auth.models import AbstractUser

from core.models.typeuser import TypeUser


class User(AbstractUser):
    typeuser = models.ForeignKey(TypeUser, related_name="users", on_delete=models.CASCADE, null=True)