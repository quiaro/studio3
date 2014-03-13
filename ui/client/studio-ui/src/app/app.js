requirejs(['studioServices'], function (studioServices) {

    'use strict';

    var init_module = 'crafter.studio-ui',
        services = new studioServices({}),
        GLOBALS, CONFIG;

    angular.module(init_module, [
            'crafter.studio-ui.services.AppService',
            'crafter.studio-ui.services.AuditService',
            'crafter.studio-ui.services.AuthService',
            'crafter.studio-ui.services.UserService',
            'crafter.studio-ui.services.ConfigService',
            'crafter.studio-ui.NgRegistry',
            'crafter.studio-ui.Utils',
            'ui.router',
            'ui.bootstrap'
        ])

        // TO-DO: Read sitename from the url. Rename 'Env' to 'API' and define as .constant
        .value('Env', {
            siteName: 'mango',
            urlBase: 'api',
            apiVersion: '1'
        })

        .config(['$locationProvider',
            '$stateProvider',
            '$httpProvider',
            '$provide',
            function ($locationProvider, $stateProvider, $httpProvider, $provide) {

            // Expose configuration to angular app
            $provide.value( 'CONFIG', CONFIG );
            $provide.value( 'GLOBALS', GLOBALS );

            var logOutUserOn401 = ['$q', '$location',
                function($q, $location) {
                    var success = function(response) {
                        return response;
                    };

                    var error = function(response) {
                        if (response.status === 401) {
                            //redirect them back to the default url
                            $location.path(GLOBALS.default_url);

                            return $q.reject(response);
                        } else {
                            return $q.reject(response);
                        }
                    };

                    return function(promise) {
                        return promise.then(success, error);
                    };
                }];

            $httpProvider.responseInterceptors.push(logOutUserOn401);

            $locationProvider.html5Mode(true);

            // Avoid problem with CORS
            // http://stackoverflow.com/questions/16661032/
            // http-get-is-not-allowed-by-access-control-allow-origin-but-ajax-is
            delete $httpProvider.defaults.headers.common['X-Requested-With'];

        }])

        // Application Controller: the omnipresent and omniscient controller
        // Handles route event logic
        .controller('AppCtrl', [
            '$scope',
            '$log', function ($scope, $log) {

            // Error handling on state changes
            $scope.$on('$stateChangeError',
                function(event, toState, toParams, fromState, fromParams, error){
                    $log.error(error);
            });
        }])

        // Initialize the application
        /*jshint -W072 */
        .run(['$rootScope',
            '$location',
            '$state',
            '$controller',
            '$urlRouter',
            '$log',
            '$timeout',
            '$q',
            'AuthService',
            'UserService',
            'ConfigService',
            'Utils',
            'NgRegistry',
            'CONFIG',
            'GLOBALS',
            function ($rootScope, $location, $state, $controller, $urlRouter, $log, $timeout, $q,
                      AuthService, UserService, ConfigService, Utils, NgRegistry, CONFIG, GLOBALS) {

            var promiseList;

            $log.info('Config info for ' + init_module + ': ', CONFIG);

            if (!('globals' in CONFIG.requirejs.module_paths)) {
                $log.error('No path specified for globals module');
            }

            promiseList = Utils.loadModules(CONFIG.modules, CONFIG.base_url);

            $q.all(promiseList).then( function() {

                $log.log('The application ' + init_module + ' is now loaded');

                NgRegistry.setDefaultURL(GLOBALS.default_url);
                // After all the sections of the app have been loaded it is now
                // safe to do an update of the routes and load whatever section (page)
                // the user was requesting
                $timeout( function() {
                    $rootScope.$apply( function() {
                        $urlRouter.sync();
                    });
                });
            });

            // On route change, check if the user is allowed to access the state. If not,
            // redirect him to the default url
            $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
                var roles, roleIntersection;

                if (toState.requireAuth) {

                    if (!AuthService.isLoggedIn()) {
                        // The module requires authentication, but the user is not
                        // logged in => send user to the default state.
                        event.preventDefault();
                        $log.log('Not logged in ... sending user to state: ' + GLOBALS.default_state);
                        $state.go(GLOBALS.default_state);
                    } else {
                        // The module requires authentication and the user is logged in.

                        if (toState.rolesAllowed && toState.rolesAllowed.length) {
                            // The modules restricts access to only certain roles. Check if the user has
                            // any of these roles.
                            roles = UserService.getUserRoles();
                            roleIntersection = Utils.arrayIntersection(toState.rolesAllowed, roles);

                            if (!roleIntersection.length) {
                                event.preventDefault();
                                $log.log('Sorry! You do not have access to this module.');
                                $state.go(GLOBALS.unauthorized_state);
                            }
                        }
                    }
                }
            });

        }]);
        /*jshint +W072 */


    services.Config.getDescriptor(init_module).then( function(descriptor) {

        CONFIG = descriptor;
        GLOBALS = CONFIG.module_globals;

        if ('templates_url' in GLOBALS) {
            GLOBALS.templates_url = services.Utils.mergePath(CONFIG.base_url, GLOBALS.templates_url);
        }
        if ('plugins_url' in GLOBALS) {
            GLOBALS.plugins_url = services.Utils.mergePath(CONFIG.base_url, GLOBALS.plugins_url);
        }

        // Set app configuration
        require.config({
            baseUrl: CONFIG.base_url,
            map: {
                '*': CONFIG.requirejs.map
            },
            paths: CONFIG.requirejs.module_paths,
            config: {
                'globals': GLOBALS
            }
        });

        // Manual bootstrap
        angular.bootstrap(angular.element(GLOBALS.dom_root), [init_module]);

    }, function() {
        throw new Error('Unable to get descriptor for ' + init_module);
    });

});
