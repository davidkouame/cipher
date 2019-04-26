from django.db import models

class Image(models.Model):
    image = models.ImageField(upload_to='images/%Y/%m/%d/')
    timestamp = models.DateTimeField(auto_now_add=True)

    # Django is using this method to display an object in the Django admin site
    # def __str__(self):
    # 	 return self.image