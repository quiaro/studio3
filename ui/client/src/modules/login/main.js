'use strict';

angular.module('crafter.studio.login', ['ui.router'])

    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('login', {});
    }])

    // Application Controller: the omnipresent and omniscient controller
    // Handles route event logic and minor functions exposed throughout the whole app
    .controller('LoginCtrl', ['$scope', function ($scope) {

    }]);
