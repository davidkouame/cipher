#!/usr/bin/env python
# -*- coding: utf-8 -*-

from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
# from django.contrib.auth.models import User
from core.models.categorie import Categorie
from core.models.file import File
from core.models.image import Image
from core.models.publication import Publication
from core.models.souscategorie import SousCategorie
from core.models.typeuser import TypeUser
from core.models.user import User

from core.forms.userform import UserCreationForm, UserChangeForm
from core.models.chambre import Chambre
from core.models.hotel import Hotel
from core.models.loue import Loue
from core.models.paiement import Paiement
from core.models.profile import Profile
from core.models.reserve import Reserve
from core.models.statutreservation import StatutReservation
from core.models.typechambre import TypeChambre
from core.models.typehotel import TypeHotel


class CustomUserAdmin(UserAdmin):
    add_form = UserCreationForm
    form = UserChangeForm
    model = User
    list_display = ['email', 'username']

# admin.site.register(Hotel)
admin.site.register(User, CustomUserAdmin)
admin.site.register(TypeUser)
# admin.site.register(TypeHotel)
# admin.site.register(Profile)
# admin.site.register(Chambre)
# admin.site.register(Paiement)
# admin.site.register(Loue)
admin.site.register(Publication)
admin.site.register(Categorie)
admin.site.register(SousCategorie)
admin.site.register(File)
admin.site.register(Image)
# admin.site.register(StatutReservation)