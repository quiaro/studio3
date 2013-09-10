'use strict';

angular.module('s2doApp', [
    'dashboard',
    'preview',
    'dialogs',
    'services.repo',
    'resources.util',
    'resources.toastr'
  ])

  .config(function ($routeProvider) {
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
  })

  // Application Controller
  .controller('AppCtrl', ['$scope', 'notifications', function($scope, notifications) {

    // TO-DO: Remove these default values
    this.msgTitle = 'Alert';
    this.msgBody  = 'Here is an important message!';
    this.msgType  = 'warning';

    this.notifications = notifications;

    // Expose to the (global) scope
    $scope.AppCtrl = this;

  }])

  // Application services
  .factory('notifications', ['toastr', function(toastr) {
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
