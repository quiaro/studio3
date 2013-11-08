'use strict';

angular.module('studio-ui', [
        'crafter.studio.login',
        'crafter.studio.dashboard',
        'crafter.studio.authoring',
        'crafter.studio.common',
        'pascalprecht.translate',
        'ngCookies'
    ])

    .config(['$locationProvider', function ($locationProvider) {

        // $routeProvider
        //     .when(APP_PATHS.dashboard, {
        //         templateUrl: 'studio-ui/templates/dashboard.tpl.html',
        //         controller: 'DashboardCtrl',
        //         resolve: {
        //             loadPrototypes : function loadPrototypes ($q, Widget) {
        //                 var deferred = $q.defer();
        //                 Widget.getPropertyAssets('dashboard', 'prototypeUrl', false, Widget.processPrototype)
        //                     .then( function (prototypes) {
        //                         deferred.resolve(prototypes);
        //                     });
        //                 return deferred.promise;
        //             },
        //             loadTemplates : function loadTemplates ($q, Widget) {
        //                 var deferred = $q.defer();
        //                 Widget.getPropertyAssets('dashboard', 'templateUrl', true, Widget.processTemplate)
        //                     .then( function (templates) {
        //                         deferred.resolve(templates);
        //                     });
        //                 return deferred.promise;
        //             }
        //         }
        //     })
        //     .when(APP_PATHS.preview, {
        //         templateUrl: 'studio-ui/templates/preview.tpl.html',
        //         controller: 'PreviewCtrl'
        //     })
        //     .otherwise({
        //         redirectTo: APP_PATHS.dashboard
        //     });

        $locationProvider.html5Mode(true);

        // $translateProvider
        //     .useStaticFilesLoader({
        //         prefix: I18N.prefix,
        //         suffix: I18N.suffix
        //     })
        //     // load 'en' table on startup
        //     .preferredLanguage('en')
        //     .useLocalStorage();
    }])

    // Application Controller: the omnipresent and omniscient controller
    // Handles route event logic and minor functions exposed throughout the whole app
    .controller('AppCtrl', [
        '$scope',
        '$translate', function ($scope, $translate) {

        // $scope.AppCtrl.changeLanguage = function changeLanguage (langKey) {
        //   $translate.uses(langKey);
        // };
    }]);

