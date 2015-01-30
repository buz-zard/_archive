# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import django.utils.timezone
import django_extensions.db.fields


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Journalist',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created', django_extensions.db.fields.CreationDateTimeField(default=django.utils.timezone.now, verbose_name='created', editable=False, blank=True)),
                ('modified', django_extensions.db.fields.ModificationDateTimeField(default=django.utils.timezone.now, verbose_name='modified', editable=False, blank=True)),
                ('name', models.CharField(max_length=30, verbose_name='Vardas')),
                ('surname', models.CharField(max_length=30, verbose_name='Vardas')),
            ],
            options={
                'ordering': ['-created'],
                'verbose_name': '\u017durnalistas',
                'verbose_name_plural': '\u017durnalistai',
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Post',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created', django_extensions.db.fields.CreationDateTimeField(default=django.utils.timezone.now, verbose_name='created', editable=False, blank=True)),
                ('modified', django_extensions.db.fields.ModificationDateTimeField(default=django.utils.timezone.now, verbose_name='modified', editable=False, blank=True)),
                ('title', models.CharField(max_length=30, verbose_name='Antra\u0161t\u0117')),
                ('content', models.TextField(verbose_name='Tekstas')),
            ],
            options={
                'ordering': ['-created'],
                'verbose_name': 'Strapsnis',
                'verbose_name_plural': 'Strapsniai',
            },
            bases=(models.Model,),
        ),
    ]
