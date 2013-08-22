'use strict';

angular.module('s2doApp', [
    'dashboard',
    'preview',
    'dialogs',
    'services.repo',
    'resources.util'
  ])

  .config(function ($routeProvider) {
    $routeProvider
      .when('/dashboard', {
        templateUrl: 'scripts/app/dashboard/dashboard.tpl.html',
        controller: 'AppCtrl'
      })
      .when('/preview', {
        templateUrl: 'scripts/app/preview/preview.tpl.html',
        controller: 'AppCtrl'
      })
      .otherwise({
        redirectTo: '/preview'
      });
  })

  .controller('AppCtrl', ['$scope', function($scope) {

    $scope.test = {
      property: 'Mary Kate'
    };

  }])

  // Initialize the app
  .run(function (util) {
    // In real life, when the UI loads, siteName will be passed as a parameter in the URL
    util.setEnvProperty('siteName', 'pebbles');
  });
