# -*- coding: utf-8 -*-
from django.db import models
from django_extensions.db.models import TimeStampedModel

from sorl.thumbnail import ImageField


class Journalist(TimeStampedModel):
    name = models.CharField(verbose_name=u"Vardas", max_length=30)
    surname = models.CharField(verbose_name=u"Vardas", max_length=30)

    class Meta:
        ordering = ["-created"]
        verbose_name = "Žurnalistas"
        verbose_name_plural = "Žurnalistai"

    def __unicode__(self):
        return unicode(self.name) + " " + unicode(self.surname)


class Post(TimeStampedModel):
    title = models.CharField(verbose_name=u"Antraštė", max_length=30)
    content = models.TextField(verbose_name=u"Tekstas")
    author = models.ForeignKey(Journalist)

    class Meta:
        ordering = ["-created"]
        verbose_name = "Strapsnis"
        verbose_name_plural = "Strapsniai"

    def __unicode__(self):
        return unicode(self.title)

    def pictures(self):
        return PostImage.objects.filter(post=self)


class PostImage(TimeStampedModel):
    post = models.ForeignKey(Post, verbose_name="Straipsnis")
    image = ImageField(upload_to='images/', verbose_name="Paveikslėlis")

    class Meta:
        ordering = ["post", "-created"]
        verbose_name = "Straipsnio nuotrauka"
        verbose_name_plural = "Straipsnių nuotraukos"

    def __unicode__(self):
        return self.image.name
