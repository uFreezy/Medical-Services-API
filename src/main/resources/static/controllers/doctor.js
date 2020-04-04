angular.module('medicallife', [])
    .controller('Doctor', function ($scope, $http, $window, $rootScope) {
        console.log($window.location.search);
        $scope.processVisitId = window.location.search.split('=')[1];
        $http.get('http://localhost:8080/utility/loggedrole').then(function (response) {
            $scope.userrole = response.data;
            console.log(response);
        });

        $scope.date = new Date();

        $scope.pendingVisits = function () {
            $http.get('http://localhost:8080/doctor/pendingvisits').then(function (response) {
                $scope.pendingVisits = response.data;
                console.log(response);
            });
        };

        $scope.processVisitLoad = function (index) {
            $rootScope.processVisitId = index;
            $window.location.href = '/fragments/doctor/process_visit_form.html?index=' + index;
        };

        $scope.processVisit = function (index, diagnosis, sick_leave_from, sick_leave_to, treatment) {
            let param = 'visitId=' + parseInt(index);

            if (diagnosis) param += '&diagnosis=' + diagnosis;
            if (sick_leave_from) param += '&sickLeaveFrom=' + sick_leave_from.toISOString();
            if (sick_leave_to) param += '&sickLeaveTo=' + sick_leave_to.toISOString();
            if (treatment) param += '&treatment=' + treatment;

            $http.put('http://localhost:8080/doctor/processvisit?' + param).then(function (response) {
                $scope.processMessage = response.data;
                console.log(response);
                $rootScope.processVisitId = null;
            });

            $window.location.href = "/fragments/doctor/doctor_visits_dashboard.html";
        };

        $scope.declineVisit = function (index) {
            $http.put('http://localhost:8080/doctor/declinevisit?visitId=' + index).then(function (response) {
                $scope.declineMessage = response.data;
                $window.location.reload();
            });
        };

        $scope.pastVisits = function () {
            $http.get('http://localhost:8080/doctor/pastvisits').then(function (response) {
                $scope.pastVisits = response.data;
                console.log(response);
            });
        };

        $scope.getInfoForDiagnoses = function () {
            $http.get('http://localhost:8080/doctor/getdiagnosesummary').then(function (response) {
                $scope.diagnosesSummaries = response.data;
                console.log(response);
            });
        };

        $scope.getManagedUsers = function () {
            $http.get('http://localhost:8080/doctor/managed').then(function (response) {
                $scope.managedUsers = response.data;
                console.log(response);
            });
        };

        $scope.getUserInfo = function () {
            $http.get('http://localhost:8080/doctor/patientinfo').then(function (response) {
                $scope.users = response.data;
                console.log(response);
            });
        };

        $scope.addManagedUser = function (userId) {
            $http.put('http://localhost:8080/doctor/addmanagedpatient?patientUserId=' + userId).then(function (response) {
                $scope.responseMessage = response.data;
                console.log(response);
            });
        };

        $scope.removeManagedUser = function (userId) {
            $http.put('http://localhost:8080/doctor/removemanagedpatient?patientUserId=' + userId).then(function (response) {
                $scope.responseMessage = response.data;
                console.log(response);
            });
        }

    });