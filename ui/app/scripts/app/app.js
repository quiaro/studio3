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

    return $scope.AppCtrl = this;

  }])

  // Application services
  .factory("notifications", function($rootScope) {
    var queue = [];

    toastr.options.timeOut = 3500;
    toastr.options.closeButton = true;
    
    return {
      set: function(message) {
        var msg = message;
        queue.push(msg);

      },
      pop: function(message) {
        switch(message.type) {
          case 'success':
            toastr.success(message.body, message.title);
            break;
          case 'info':
            toastr.info(message.body, message.title);
            break;
          case 'warning':
            toastr.warning(message.body, message.title);
            break;
          case 'error':
            toastr.error(message.body, message.title);
            break;
          default:
            break;
        }
      }
    };
  })

  // Initialize the application
  .run(function (util) {
    // In real life, when the UI loads, siteName will be passed as a parameter in the URL
    util.setEnvProperty('siteName', 'pebbles');
  });
