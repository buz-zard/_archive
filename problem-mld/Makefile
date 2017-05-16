_venv := ./deployment/venv
_python := $(_venv)/bin/python3
_pip :=  $(_python) $(_venv)/bin/pip3
_eb := $(_python) $(_venv)/bin/eb
_aws := $(_python) $(_venv)/bin/aws


# ##############################################################################
# Main commands
# ##############################################################################

install: venv.install venv.update aws.ini eb.init

deploy: deploy.api-dev deploy.web-dev

deploy.api-dev: venv.update clean api.build api.bundle api.deploy-dev

deploy.web-dev: venv.update web.build web.deploy-dev


# ##############################################################################
# Api commands
# ##############################################################################

api.build:
	cd ./api && yarn && yarn validate && yarn build

api.bundle:
	mkdir -p build
	cp ./api/package.json ./build/package.json
	cp ./api/yarn.lock ./build/yarn.lock
	cp ./api/.build.sequelizerc ./build/.sequelizerc
	cp -r ./api/build ./build/build
	cp -r .ebextensions ./build/.ebextensions
	cd build && zip -rq ../build.zip . *

api.deploy-dev:
	$(_eb) deploy mld-dev


# ##############################################################################
# Web commands
# ##############################################################################

web.build:
	cd ./web && yarn && yarn validate && yarn build

web.deploy-dev:
	$(_aws) s3 rm s3://prototype-mld-dev --recursive --region eu-central-1
	$(_aws) s3 cp ./web/build s3://prototype-mld-dev --recursive --region eu-central-1 --acl public-read \
		--cache-control "public, max-age=86400" --exclude "index.html" --exclude "static/*" --exclude "fonts/*"
	$(_aws) s3 cp ./web/build/index.html s3://prototype-mld-dev/index.html --region eu-central-1 --acl public-read \
		--cache-control "private, no-cache, no-store, must-revalidate"
	$(_aws) s3 cp ./web/build/fonts s3://prototype-mld-dev/fonts --recursive --region eu-central-1 --acl public-read \
		--cache-control "public, max-age=604800"
	$(_aws) s3 cp ./web/build/static s3://prototype-mld-dev/static --recursive --region eu-central-1 --acl public-read \
		--cache-control "public, max-age=31536000"
	$(_aws) configure set preview.cloudfront true
	$(_aws) cloudfront create-invalidation --distribution-id E1HL6LIWUFDOAY --paths "/*"


# ##############################################################################
# Other
# ##############################################################################

venv.install:
	virtualenv -p python3 $(_venv)

venv.update:
	$(_pip) install -r ./deployment/requirements.txt

venv.freeze:
	$(_pip) freeze > ./deployment/requirements.txt

aws.init:
	$(_aws) configure

eb.init:
	$(_eb) init
	echo "\n\nAppend these lines at the bottom of .elasticbeanstalk/config.yml \ndeploy: \n  artifact: build.zip\n\n"

clean:
	rm -rf ./build
	rm -f ./build.zip
