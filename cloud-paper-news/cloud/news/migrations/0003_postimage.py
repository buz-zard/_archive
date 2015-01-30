# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import django.utils.timezone
import sorl.thumbnail.fields
import django_extensions.db.fields


class Migration(migrations.Migration):

    dependencies = [
        ('news', '0002_post_author'),
    ]

    operations = [
        migrations.CreateModel(
            name='PostImage',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created', django_extensions.db.fields.CreationDateTimeField(default=django.utils.timezone.now, verbose_name='created', editable=False, blank=True)),
                ('modified', django_extensions.db.fields.ModificationDateTimeField(default=django.utils.timezone.now, verbose_name='modified', editable=False, blank=True)),
                ('image', sorl.thumbnail.fields.ImageField(upload_to=b'images/', verbose_name=b'Paveiksl\xc4\x97lis')),
                ('post', models.ForeignKey(verbose_name=b'Straipsnis', to='news.Post')),
            ],
            options={
                'ordering': ['post', '-created'],
                'verbose_name': 'Straipsnio nuotrauka',
                'verbose_name_plural': 'Straipsni\u0173 nuotraukos',
            },
            bases=(models.Model,),
        ),
    ]
