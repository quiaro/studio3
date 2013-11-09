'use strict';

angular.module('crafter.studio.login', ['ui.router'])

    .constant('CONFIG', {
        baseUrl: '/src/modules/login/'
    })

    .config(['$stateProvider',
        '$urlRouterProvider',
        'CONFIG', function ($stateProvider, $urlRouterProvider, CONFIG) {

        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: CONFIG.baseUrl + 'templates/login.tpl.html',
                controller: 'LoginCtrl'
            });
    }])

    .controller('LoginCtrl', ['$scope', function ($scope) {

    }]);
