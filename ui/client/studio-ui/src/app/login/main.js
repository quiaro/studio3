'use strict';

angular.module('crafter.studio.login',
    ['crafter.studio.common', 'ui.router', 'ui.bootstrap'])

    .constant('LOGIN', {
        baseUrl: '/studio-ui/src/app/login/'
    })

    .config(['$stateProvider',
        '$urlRouterProvider',
        'LOGIN', function ($stateProvider, $urlRouterProvider, LOGIN) {

        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: LOGIN.baseUrl + 'templates/login.tpl.html'
            })
            .state('login.recover', {
                url: '/recover',
                onEnter: function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: LOGIN.baseUrl + 'templates/recover.tpl.html',
                        controller: 'ModalCtrl'
                    }).result
                        .then(function (result) {
                            if (result) {
                                // If something is returned, then process it
                                return $state.transitionTo('login');
                            } else {
                                // User cancelled
                                return $state.transitionTo('login');
                            }
                        });
                }
            });
    }])

    .controller('SignInCtrl', ['$scope',
        '$state',
        'AuthenticationService', function ($scope, $state, AuthenticationService) {

        $scope.signin = function signin (existingUser) {
            var defaultSite;
            console.log('Logging in user: ', existingUser);
            AuthenticationService.logIn();

            // TODO: replace with method that gets the default site
            defaultSite = 'mango';
            $state.go('studio.dashboard', { site: defaultSite });
        };
    }])

    .controller('SignUpCtrl', ['$scope', function ($scope) {
        $scope.signup = function signup (newUser) {
            console.log(newUser);
        };
    }])

    .controller('ModalCtrl', ['$scope', '$modalInstance', function ($scope, $modalInstance) {
        $scope.cancel = function cancel () {
            $modalInstance.close();
        };
        $scope.reset = function reset () {
            $modalInstance.close('Some data');
        };
    }]);
