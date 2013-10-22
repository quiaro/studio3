'use strict';

angular.module('studio-ui', [
        'navigation',
        'dashboard',
        'preview',
        'common',
        'pascalprecht.translate',
        'ngCookies'
    ])

    .config(['$routeProvider',
           '$translateProvider',
           'I18N',
           'APP_PATHS', function ($routeProvider, $translateProvider, I18N, APP_PATHS) {

        $routeProvider
            .when(APP_PATHS.dashboard, {
                templateUrl: '/templates/dashboard.tpl.html',
                controller: 'DashboardCtrl',
                resolve: {
                    loadPrototypes : function loadPrototypes ($q, Widget) {
                        var deferred = $q.defer();
                        Widget.getPropertyAssets('dashboard', 'prototypeUrl', false, Widget.processPrototype)
                            .then( function (prototypes) {
                                deferred.resolve(prototypes);
                            });
                        return deferred.promise;
                    },
                    loadTemplates : function loadTemplates ($q, Widget) {
                        var deferred = $q.defer();
                        Widget.getPropertyAssets('dashboard', 'templateUrl', true, Widget.processTemplate)
                            .then( function (templates) {
                                deferred.resolve(templates);
                            });
                        return deferred.promise;
                    }
                }
            })
            .when(APP_PATHS.preview, {
                templateUrl: '/templates/preview.tpl.html',
                controller: 'PreviewCtrl'
            })
            .otherwise({
                redirectTo: APP_PATHS.dashboard
            });

        // $locationProvider.html5Mode(true);

        $translateProvider
            .useStaticFilesLoader({
                prefix: I18N.prefix,
                suffix: I18N.suffix
            })
            // load 'en' table on startup
            .preferredLanguage('en')
            .useLocalStorage();
    }])

    // Application Controller: the omnipresent and omniscient controller
    // Handles route event logic and minor functions exposed throughout the whole app
    .controller('AppCtrl', [
        '$rootScope',
        '$scope',
        '$translate',
        '$location',
        '$log',
        'APP_PATHS', function ($rootScope, $scope, $translate, $location, $log, APP_PATHS) {

        // Namespace all things attached to the scope so it's easier to know
        // what values/functions were assigned through this controller.
        $scope.AppCtrl = {};
        $scope.AppCtrl.fullScreen = 'normal';

        // Error handling on route changes
        $rootScope.$on('$routeChangeError', function (evt, current, prev, rejection) {
            $log.error(rejection);
        });

        // Set app state on route change
        $rootScope.$on('$routeChangeSuccess', function (evt) {
            switch ($location.path()) {
                case APP_PATHS.dashboard:
                    $scope.AppCtrl.appState = 'dashboard';
                    $scope.AppCtrl.fullScreen = 'normal';
                    break;
                case APP_PATHS.preview:
                    $scope.AppCtrl.appState = 'preview';
                    $scope.AppCtrl.fullScreen = 'full';
                    break;
                default:
                    $scope.AppCtrl.appState = 'unknown';
            }
        });

        $scope.AppCtrl.changeLanguage = function changeLanguage (langKey) {
          $translate.uses(langKey);
        };

        $scope.AppCtrl.toggleView = function toggleView () {
            if ($location.path() === '/') {
                $location.path('/preview');
            } else {
                $location.path('/');
            }
        };

        $scope.AppCtrl.toggleFullScreen = function toggleFullScreen () {
            $scope.AppCtrl.fullScreen = ($scope.AppCtrl.fullScreen === 'normal') ? 'full' : 'normal';
        };
    }])

    // Initialize the application
    .run(function (util) {
        // In real life, when the UI loads, siteName will be passed as a parameter in the URL
        util.setEnvProperty('siteName', 'mango');
  });
