'use strict';

angular.module('studio-ui', [
        'crafter.studio.login',
        'crafter.studio.dashboard',
        'crafter.studio.authoring',
        'crafter.studio.common',
        'pascalprecht.translate',
        'ngCookies',
        'ui.router'
    ])

    .config(['$locationProvider',
        '$urlRouterProvider',
        '$httpProvider', function ($locationProvider, $urlRouterProvider, $httpProvider) {

        var logOutUserOn401 = ['$q', '$location',
            function($q, $location) {
                var success = function(response) {
                    return response;
                };

                var error = function(response) {
                    if (response.status === 401) {
                        //redirect them back to login page
                        $location.path('/login');

                        return $q.reject(response);
                    } else {
                        return $q.reject(response);
                    }
                };

                return function(promise) {
                    return promise.then(success, error);
                };
            }];

        $httpProvider.responseInterceptors.push(logOutUserOn401);

        $urlRouterProvider.otherwise('/dashboard');
        $locationProvider.html5Mode(true);
    }])

    // Application Controller: the omnipresent and omniscient controller
    // Handles route event logic and minor functions exposed throughout the whole app
    .controller('AppCtrl', [
        '$scope',
        '$translate', function ($scope, $translate) {

    }])

    // Initialize the application
    .run(['$rootScope',
        '$location',
        '$state',
        'AuthenticationService', function ($rootScope, $location, $state, AuthenticationService) {

        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
            if (toState.requireAuth && !AuthenticationService.isLoggedIn()) {

                event.preventDefault();

                console.log("Sorry! Not logged in.");

                $state.go('login');
            }
        });

    }]);

