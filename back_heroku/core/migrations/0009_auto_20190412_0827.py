# Generated by Django 2.2 on 2019-04-12 08:27

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('core', '0008_image'),
    ]

    operations = [
        migrations.AlterField(
            model_name='publication',
            name='image',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='publications', to='core.Image'),
        ),
    ]
