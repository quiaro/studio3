'use strict';

angular.module('crafter.studio.dashboard', ['crafter.studio.common', 'ui.router'])

    .constant('CONFIG', {
        baseUrl: '/src/modules/dashboard/'
    })

    .config(['$stateProvider',
        '$urlRouterProvider',
        'CONFIG', function ($stateProvider, $urlRouterProvider, CONFIG) {

        $stateProvider
            .state('dashboard', {
                url: '/dashboard',
                templateUrl: CONFIG.baseUrl + 'templates/dashboard.tpl.html',
                controller: 'DashboardCtrl',
                requireAuth: true,
                rolesAllowed: ['admin', 'editor']
            });
    }])

    .controller('DashboardCtrl',
		['$scope', 'NotificationService', function($scope, NotificationService) {

	}]);

