#### [Assignment](https://github.com/buz-zard/_archive/blob/master/problem-mld/ASSIGNMENT.md) - quiz web app.

#### End result - [http://dmumd3bz0dmmc.cloudfront.net](http://dmumd3bz0dmmc.cloudfront.net) :point_left:

A quiz web app built using JavaScript frameworks and tools, deployed on AWS __*__.

---

Development
- `cd ./api && yarn dev` - start API development server :construction:
- `cd ./web && yarn dev` - start web development server :construction:

---

Deployment
- `make install` - install and setup deployment environment :wrench: __**__
- `make deploy.api-dev` - deploy API to AWS Eslatic Beanstalk [environment](http://mld-dev.eu-central-1.elasticbeanstalk.com) :rocket:
- `make deploy.web-dev` - deploy web UI to AWS S3 & CloudFront [environments](http://dmumd3bz0dmmc.cloudfront.net) :rocket:
- `make deploy` - deploys API & web resources :rocket:


---

__*__ AWS architecture schema:

![example](https://github.com/buz-zard/_archive/blob/master/problem-mld/deployment/aws-schema.png)


__**__ Manualy append above line at the bottom of `.elasticbeanstalk/config.yml` file:
```
deploy:
  artifact: build.zip
```
