'use strict';

angular.module('crafter.studio.dashboard', ['crafter.studio.common', 'ui.router'])

    .constant('DASHBOARD', {
        baseUrl: '/studio-ui/src/app/dashboard/'
    })

    .config(['$stateProvider',
        '$urlRouterProvider',
        'DASHBOARD', function ($stateProvider, $urlRouterProvider, DASHBOARD) {

        $stateProvider
            .state('dashboard', {
                url: '/dashboard',
                templateUrl: DASHBOARD.baseUrl + 'templates/dashboard.tpl.html',
                controller: 'DashboardCtrl',
                requireAuth: true,
                rolesAllowed: ['admin', 'editor']
            });
    }])

    .controller('DashboardCtrl',
		['$scope', 'NotificationService', function($scope, NotificationService) {

	}]);

