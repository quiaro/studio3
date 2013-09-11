'use strict';

angular.module('s2doApp', [
    'dashboard',
    'preview',
    'dialogs',
    'services.repo',
    'resources.util',
    'resources.toastr',
    'pascalprecht.translate',
    'ngCookies'
  ])

  .config(['$routeProvider', '$translateProvider', function ($routeProvider, $translateProvider) {

    $routeProvider
      .when('/', {
        templateUrl: 'scripts/app/dashboard/dashboard.tpl.html',
        controller: 'AppCtrl'
      })
      .when('/preview', {
        templateUrl: 'scripts/app/preview/preview.tpl.html',
        controller: 'AppCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });

    $translateProvider.useStaticFilesLoader({
      prefix: 'i18n/locale_',
      suffix: '.json'
    });
    // load 'en' table on startup
    $translateProvider.preferredLanguage('en');
    $translateProvider.useLocalStorage();

  }])

  // Application Controller
  .controller('AppCtrl', ['$scope', 'notifications', '$translate', function ($scope, notifications, $translate) {

    this.notifications = notifications;

    this.changeLanguage = function changeLanguage (langKey) {
      $translate.uses(langKey);
    };

    // Expose to the (global) scope
    $scope.AppCtrl = this;

  }])

  // Application services
  .factory('notifications', ['toastr', function (toastr) {
    var queue = [];

    return {
      set: function(message) {
        var msg = message;
        queue.push(msg);

      },
      pop: function(message) {
        if (message.type in toastr && typeof toastr[message.type] === 'function') {
          toastr[message.type](message.body, message.title);
        }
      }
    };
  }])

  // Initialize the application
  .run(function (util) {
    // In real life, when the UI loads, siteName will be passed as a parameter in the URL
    util.setEnvProperty('siteName', 'pebbles');
  });
