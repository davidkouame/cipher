from django.db import models

from core.models.categorie import Categorie


class SousCategorie(models.Model):
    categorie = models.ForeignKey(Categorie, related_name="souscategories", on_delete=models.CASCADE)
    name = models.CharField(max_length=255)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)