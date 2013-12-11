'use strict';

angular.module('studio-ui', [
        'crafter.studio.login',
        'crafter.studio.dashboard',
        'crafter.studio.authoring',
        'crafter.studio.test-service',
        'crafter.studio.common',
        'pascalprecht.translate',
        'ngCookies',
        'ui.router'
    ])

    .config(['$locationProvider',
        '$stateProvider',
        '$urlRouterProvider',
        '$httpProvider',
        'COMMON', function ($locationProvider, $stateProvider, $urlRouterProvider, $httpProvider, COMMON) {

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

        $stateProvider
            .state('unauthorized', {
                url: '/unauthorized',
                templateUrl: COMMON.baseUrl + 'templates/unauthorized.tpl.html'
            });

        $urlRouterProvider.otherwise('/login');
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
        'AuthenticationService',
        'UserService',
        'UtilsService',
        function ($rootScope, $location, $state, AuthenticationService, UserService, UtilsService) {

        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
            var roles, roleIntersection;

            if (toState.requireAuth) {

                if (!AuthenticationService.isLoggedIn()) {
                    // The module requires authentication, but the user is not logged in => send user
                    // to login state.

                    event.preventDefault();
                    console.log("Sorry! Not logged in.");
                    $state.go('login');
                } else {
                    // The module requires authentication and the user is logged in.

                    if (toState.rolesAllowed && toState.rolesAllowed.length) {
                        // The modules restricts access to only certain roles. Check if the user has
                        // any of these roles.
                        roles = UserService.getUserRoles();
                        roleIntersection = UtilsService.arrayIntersection(toState.rolesAllowed, roles);

                        if (!roleIntersection.length) {
                            event.preventDefault();
                            console.log("Sorry! You do not have access to this module.");
                            $state.go('unauthorized');
                        }
                    }
                }
            }
        });

    }]);

