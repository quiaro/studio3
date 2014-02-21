/* global define */

define(['require',
        'globals',
        'directives',
        'css!./login'], function( require, globals ) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', '$state', '$log',
        function(NgRegistry, $state, $log) {

        NgRegistry
            .addController('SignInCtrl', ['$scope',
                '$state',
                'AuthService', function ($scope, $state, AuthService) {

                $scope.signin = function signin (existingUser) {
                    var defaultSite;
                    $log.log('Logging in user: ', existingUser);
                    AuthService.logIn();

                    // TODO: replace with method that gets the default site
                    defaultSite = 'mango';
                    $state.go('studio.dashboard', { site: defaultSite });
                };
            }])

            .addController('SignUpCtrl', ['$scope', function ($scope) {
                $scope.signup = function signup (newUser) {
                    $log.log(newUser + ' signing up');
                };
            }])

            .addController('ModalCtrl', ['$scope', '$modalInstance', function ($scope, $modalInstance) {
                $scope.cancel = function cancel () {
                    $modalInstance.close();
                };
                $scope.reset = function reset () {
                    $modalInstance.close('Some data');
                };
            }])

            .addState('login', {
                url: '/login',
                templateUrl: require.toUrl('./templates/login.tpl.html')
            })

            .addState('login.recover', {
                url: '/recover',
                onEnter: function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: require.toUrl('./templates/recover.tpl.html'),
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
                },
                requireAuth: false
            });

    }]);

});
