angular.module('medicallife', [])
    .controller('Index', function ($scope, $http, $window) {

        $http.get('http://localhost:8080/utility/loggedrole').then(function (response) {
            $scope.userrole = response.data;

            switch (response.data.name) {
                case 'Admin':
                    $window.location.href = '/fragments/admin/admin_user_table.html';
                    break;
                case 'Doctor':
                    $window.location.href = '/fragments/doctor/doctor_visits_dashboard.html';
                    break;
                case 'Patient':
                    $window.location.href = '/fragments/patient/request_visit.html';
                    break;
                default:
                    console.log(response.data);
            }
            console.log(response);
        });
    });