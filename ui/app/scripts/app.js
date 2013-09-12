'use strict';

angular.module('studio-ui', [
    'dashboard',
    'preview',
    'common',
    'pascalprecht.translate',
    'ngCookies'
  ])

  .config(['$routeProvider',
           '$translateProvider',
           'I18N', function ($routeProvider, $translateProvider, I18N) {

    $routeProvider
      .when('/', {
        templateUrl: '/templates/dashboard.tpl.html',
        controller: 'AppCtrl'
      })
      .when('/preview', {
        templateUrl: '/templates/preview.tpl.html',
        controller: 'AppCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });

    $translateProvider.useStaticFilesLoader({
      prefix: I18N.prefix,
      suffix: I18N.suffix
    });
    // load 'en' table on startup
    $translateProvider.preferredLanguage('en');
    $translateProvider.useLocalStorage();

  }])

  // Application Controller
  .controller('AppCtrl', ['$scope', '$translate', function ($scope, $translate) {

    this.changeLanguage = function changeLanguage (langKey) {
      $translate.uses(langKey);
    };

    // Expose to the (global) scope
    $scope.AppCtrl = this;

  }])

  // Initialize the application
  .run(function (util) {
    // In real life, when the UI loads, siteName will be passed as a parameter in the URL
    util.setEnvProperty('siteName', 'pebbles');
  });
