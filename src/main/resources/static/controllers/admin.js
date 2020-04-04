angular.module('medicallife', [])
    .controller('Admin', function ($scope, $http, $window, $rootScope) {

        $scope.parseUserId = window.location.search.split('=')[1];

        $http.get('http://localhost:8080/utility/loggedrole').then(function (response) {
            $scope.userrole = response.data;
            console.log(response);
        });

        $scope.date = new Date();

        $scope.patientList = function () {
            $http.get('http://localhost:8080/admin/patientlist').then(function (response) {
                $scope.patientList = response.data;
                console.log(response);
            });
        };

        $scope.doctorList = function () {
            $http.get('http://localhost:8080/admin/doctorlist').then(function (response) {
                $scope.doctorList = response.data;
                console.log(response);
            });
        };

        $scope.deleteUser = function (id) {
            $http.delete('http://localhost:8080/admin/removeuser?userId=' + id).then(function (response) {
                $scope.resultMessage = response.data;
                console.log(response);
            });
        };

        $scope.switchRole = function (userId, roleId) {
            $http.put('http://localhost:8080/admin/userrole?userId=' + userId + '&roleId=' + roleId)
                .then(function (response) {
                    $scope.resultMessage = response.data;
                    console.log(response);
                });
        };

        $scope.loadProfilePage = function (id, type) {
            switch (type) {
                case 0:
                    $window.location.href = "/fragments/admin/admin_patient_profile.html?id=" + id;
                    break;
                case 1:
                    $window.location.href = "/fragments/admin/admin_doctor_profile.html?id=" + id;
                    break;
            }
        };

        $scope.getprofile = function () {
            $http.get('http://localhost:8080/admin/profile?userId=' + $scope.parseUserId).then(function (response) {
                $scope.profileData = response.data;
                console.log(response);
            });
        };

        $scope.editPatientProfile = function (first_name, last_name, egn, doctorId) {
            if (first_name === undefined && last_name === undefined && egn === undefined && doctorId === undefined) {
                $window.location.reload();
            }
            let data = {
                id: parseInt($scope.parseUserId),
                first_name: first_name,
                last_name: last_name,
                egn: egn,
                doctor_id: parseInt(doctorId)
            };
            console.log(data);
            $http.put('http://localhost:8080/admin/editprofile', JSON.stringify(data)).then(function (response) {
                $scope.message = response.data;
            });
        };

        $scope.editDoctorProfile = function (first_name, last_name, specialty) {
            debugger;
            if (first_name === undefined && last_name === undefined && specialty === undefined) {
                $window.location.reload();
            }
            let data = {
                id: parseInt($scope.parseUserId),
                first_name: first_name,
                last_name: last_name,
                specialty: specialty
            };
            console.log(data);
            $http.put('http://localhost:8080/admin/editprofile', JSON.stringify(data)).then(function (response) {
                $scope.message = response.data;
            });
        };

        $scope.doctors = function () {
            $http.get('http://localhost:8080/utility/docinfo').then(function (response) {
                console.log(response);
                $scope.doctors = response.data;
            });
        };

        $scope.dataSummary = function () {
            $http.get('http://localhost:8080/admin/summary').then(function (response) {
                console.log(response);
                $scope.dataSummary = response.data;
            });
        };

        $scope.createUser = function (first_name, last_name, username, account_type, paid_insurance, egn, doc_id, specialty) {
            let data;
            if (parseInt(account_type) === 0) {
                data = {
                    first_name: first_name,
                    last_name: last_name,
                    username: username,
                    account_type: parseInt(account_type),
                    paid_insurance: parseInt(paid_insurance),
                    egn: egn,
                    doc_id: parseInt(doc_id)
                }
            } else if (parseInt(account_type) === 1) {
                data = {
                    first_name: first_name,
                    last_name: last_name,
                    username: username,
                    account_type: parseInt(account_type),
                    specialty: specialty
                }
            }

            $http.post('http://localhost:8080/admin/registeruser', JSON.stringify(data)).then(function (response) {
                $scope.message = response.data;
                console.log(response);
            });
        };


    });