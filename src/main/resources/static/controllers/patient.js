angular.module('medicallife', [])
    .controller('Patient', function ($scope, $http, $window) {
        $http.get('http://localhost:8080/utility/loggedrole').then(function (response) {
            $scope.userrole = response.data;
            console.log(response);
        });

        $scope.date = new Date();

        $scope.profile = function () {
            $http.get('http://localhost:8080/utility/loggedrole').then(function (response) {
                $scope.userrole = response.data;
                console.log(response);
            });

            $http.get('http://localhost:8080/patient/profile').then(function (response) {
                $scope.patientProfile = response.data;
                console.log(response);
            });
        };

        $scope.editProfile = function (first_name, last_name, egn) {
            if (first_name === undefined || last_name === undefined || egn === undefined) {
                $window.location.reload();
            }
            let data = {
                first_name: first_name,
                last_name: last_name,
                egn: egn
            };
            console.log(data);
            $http.put('http://localhost:8080/patient/editprofile', JSON.stringify(data)).then(function (response) {
                $window.location.reload();
            });
        };

        $scope.visits = function () {
            $http.get('http://localhost:8080/patient/visits').then(function (response) {
                console.log(response);
                $scope.personalVisits = response.data;
            });
        };


        $scope.doctors = function () {
            $http.get('http://localhost:8080/utility/docinfo').then(function (response) {
                console.log(response);
                $scope.doctors = response.data;
            });
        };

        $scope.requestVisit = function (visitDate, doctorId) {
            $http.post('http://localhost:8080/patient/visit?visitDate=' + visitDate.toISOString() + "&docId=" + doctorId).then(function (response) {
                console.log(response);
                $scope.response = response.data;
            });
        };
    });