(function() {

    'use strict';

    // TODO: Move 'studio-ui' and all other 'globals' in the app to a separate module
    var injector = angular.element('#studio-ui').injector();

    injector.invoke(['NgRegistry', '$state', '$log',
        function(NgRegistry, $state, $log) {

        var login_base_url = '/studio-ui/modules/login/';

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
                    console.log(newUser);
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
                templateUrl: login_base_url + 'templates/login.tpl.html'
            })

            .addState('login.recover', {
                url: '/recover',
                onEnter: function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: login_base_url + 'templates/recover.tpl.html',
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

})();
