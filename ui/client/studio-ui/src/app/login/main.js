'use strict';

angular.module('crafter.studio.login',
    ['crafter.studio.common', 'ui.router', 'ui.bootstrap'])

    .constant('CONFIG', {
        baseUrl: '/studio-ui/src/app/login/'
    })

    .config(['$stateProvider',
        '$urlRouterProvider',
        'CONFIG', function ($stateProvider, $urlRouterProvider, CONFIG) {

        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: CONFIG.baseUrl + 'templates/login.tpl.html',
                controller: 'LoginCtrl'
            })
            .state('login.recover', {
                url: '/recover',
                onEnter: function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: CONFIG.baseUrl + 'templates/recover.tpl.html',
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

    .controller('LoginCtrl', ['$scope', function ($scope) {

    }])

    .controller('ModalCtrl', ['$scope', '$modalInstance', function ($scope, $modalInstance) {
        $scope.cancel = function () {
            $modalInstance.close();
        };
        $scope.reset = function() {
            $modalInstance.close('Some data');
        }
    }]);
