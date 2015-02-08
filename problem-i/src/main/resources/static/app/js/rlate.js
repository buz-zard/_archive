angular.module('rlate', ['ui.bootstrap']);
angular.module('rlate').controller('RlateController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {
    $scope.people = [];
    $scope.formPerson = null;
    $scope.relationships = null;
    $scope.alert = null;
    $scope.parent = {dateOfBirth:''};

    // Inner

    var VALIDATION_MSG = "Form validation error(s) found.";

    var hideAlert = function() {
        $scope.alert = null;
    };

    var setAlert = function(type, text) {
        $scope.alert = {
            "type": type,
            "text": text
        };
        if (type !== "error") {
            $timeout(function() {
                $scope.alert = null;
            }, 5000);
        }
    };

    var unselectPerson = function() {
        $scope.formPerson = null;
        $scope.relationships = null;
    };

    var isPersonValid = function(person) {
        return true;
    };

    var loadRelationships = function(id) {
        hideAlert();
        $http.get('/person/relationships/' + id).success(function(data) {
            if (data.status === "OK") {
                $scope.relationships = data.data;
            } else {
                setAlert("error", data.data);
            }
        });
    };

    var isDateValid = function(val) {
        console.log(val);
        return new Date(val) < new Date();
    }

    // Scope

    $scope.init = function() {
        $scope.loadPeople();
    };

    $scope.showAlert = function() {
        return $scope.alert != null;
    };

    $scope.newPerson = function() {
        unselectPerson();
    };

    $scope.selectPerson = function(person) {
        $scope.formPerson = $.extend({}, person);
        loadRelationships(person.id);
    };

    $scope.isPersonSelected = function() {
        return $scope.formPerson !== null && typeof $scope.formPerson.id !== 'undefined';
    };

    $scope.displayRelationships = function() {
        return $scope.isPersonSelected() && $scope.relationships !== null && $scope.relationships.length > 0;
    };

    $scope.saveDisabled = function() {
        return $scope.isPersonSelected();
    };

    $scope.updateDisabled = function() {
        return !$scope.isPersonSelected();
    };

    $scope.deleteDisabled = function() {
        return !$scope.isPersonSelected();
    };

    // REST api actions

    $scope.loadPeople = function() {
        $http.get('/person/all').success(function(data) {
            if (data.status === "OK") {
                $scope.people = data.data;
            } else {
                setAlert("error", data.data);
            }
        }).error(function(data) {
            setAlert("error", data);
        });
    };

    $scope.savePerson = function(errors) {
        hideAlert();
        console.log($scope.parent);
        var form = $scope.formPerson || {};
        if ($.isEmptyObject(errors) && isDateValid(form.dateOfBirth)) {
            var person = {
                name: form.name,
                surname: form.surname,
                dateOfBirth: form.dateOfBirth
            };
            $http.post('/person/save', person).success(function(data) {
                if (data.status === "OK") {
                    unselectPerson();
                    setAlert("success", data.data);
                    $scope.loadPeople();
                } else {
                    setAlert("error", data.data);
                }
            }).error(function(data) {
                setAlert("error", data);
            });
        } else {
            setAlert("error", VALIDATION_MSG);
        }
    };

    $scope.updatePerson = function(errors) {
        hideAlert();
        if ($.isEmptyObject(errors)) {
            $http.post('/person/update', $scope.formPerson).success(function(data) {
                if (data.status === "OK") {
                    unselectPerson();
                    setAlert("success", data.data);
                    $scope.loadPeople();
                } else {
                    setAlert("error", data.data);
                }
            }).error(function(data) {
                setAlert("error", data);
            });
        } else {
            setAlert("error", VALIDATION_MSG);
        }
    };

    $scope.deletePerson = function() {
        hideAlert();
        $http.post('/person/delete/' + $scope.formPerson.id).success(function(data) {
            console.log(data);
            if (data.status === "OK") {
                unselectPerson();
                setAlert("success", data.data);
                $scope.loadPeople();
            } else {
                setAlert("error", data.data);
            }
        }).error(function(data) {
            setAlert("error", data);
        });
    };

}]);