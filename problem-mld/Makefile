_venv := ./deployment/venv
_python := $(_venv)/bin/python3
_pip :=  $(_python) $(_venv)/bin/pip3
_eb := $(_python) $(_venv)/bin/eb


# ##############################################################################
# Main commands
# ##############################################################################

install: venv.install venv.update eb.init

deploy.api-dev: venv.update clean api.build api.bundle api.deploy-dev


# ##############################################################################
# Api commands
# ##############################################################################

api.deploy-dev:
	$(_eb) deploy mld-dev

api.build:
	cd ./api && yarn && yarn validate && yarn build

api.bundle:
	mkdir -p build
	cp ./api/package.json ./build/package.json
	cp -r ./api/build ./build/build
	cd build && zip -rq ../build.zip . *


# ##############################################################################
# Other
# ##############################################################################

venv.install:
	virtualenv -p python3 $(_venv)

venv.update:
	$(_pip) install -r ./deployment/requirements.txt

venv.freeze:
	$(_pip) freeze > ./deployment/requirements.txt

eb.init:
	$(_eb) init
	echo "\n\nAppend these lines at the bottom of .elasticbeanstalk/config.yml \ndeploy: \n  artifact: build.zip\n\n"

clean:
	rm -rf ./build
