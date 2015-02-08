angular.module('rlate', ['angular-datepicker']);
angular.module('rlate', []).controller('RlateController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {
    $scope.people = [];
    $scope.formPerson = null;
    $scope.relationships = null;
    $scope.alert = null;

    // ==========================================================
    // Inner
    // ==========================================================

    var VALIDATION_MSG = "Form validation error(s) found.";

    var hideAlert = function() {
        $scope.alert = null;
    };

    var setAlert = function(type, text) {
        hideAlert();
        $scope.alert = {
            "type": type,
            "text": text
        };
        if (type !== "error") {
            $timeout(function() {
                $scope.alert = null;
            }, 10000);
        }
    };

    var unselectPerson = function() {
        $scope.formPerson = null;
        $scope.relationships = null;
    };

    var onDatepickerChange = function() {
        var date = $('#inputDate').val();
        if (new Date(date) !== "Invalid Date" && !isNaN(new Date(date)) && new Date(date) <= new Date()) {
            $scope.formPerson = $scope.formPerson || {};
            $scope.formPerson.dateOfBirth = date;
        }
        $scope.pForm.date.$validate();
    };

    // ==========================================================
    // Scope
    // ==========================================================

    $scope.init = function() {
        $('.datepicker').datepicker({
            format: 'yyyy-mm-dd',
            weekStart: 1
        });
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
        $http.get('/person/all.json').success(function(data) {
            if (data.status === "OK") {
                $scope.people = data.data;
            } else {
                setAlert("error", data.data);
            }
        }).error(function(data) {
            setAlert("error", data);
        });
    };

    $scope.savePerson = function() {
        hideAlert();
        onDatepickerChange();
        // timeout workaround for form validation to kick-in
        $timeout(function() {
            var form = $scope.formPerson || {};
            if ($.isEmptyObject($scope.pForm.$error)) {
                var person = {
                    name: form.name,
                    surname: form.surname,
                    dateOfBirth: form.dateOfBirth
                };
                $http.post('/person/save.json', person).success(function(data) {
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
        });
    };

    $scope.updatePerson = function(errors) {
        hideAlert();
        onDatepickerChange();
        // timeout workaround for form validation to kick-in
        $timeout(function() {
            if ($.isEmptyObject($scope.pForm.$error)) {
                $http.post('/person/update.json', $scope.formPerson).success(function(data) {
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
        });
    };

    $scope.deletePerson = function() {
        hideAlert();
        $http.post('/person/delete/' + $scope.formPerson.id + '.json').success(function(data) {
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

    var loadRelationships = function(id) {
        $http.get('/person/relationships/' + id + '.json').success(function(data) {
            if (data.status === "OK") {
                $scope.relationships = data.data;
            } else {
                setAlert("error", data.data);
            }
        }).error(function(data) {
            setAlert("error", data);
        });
    };

}]);