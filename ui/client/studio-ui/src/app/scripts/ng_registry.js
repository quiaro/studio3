(function () {

    'use strict';

    // Create a module that will be declared as a dependency of the application
    var ngRegistry = angular.module('crafter.studio-ui.NgRegistry', ['ui.router']);

    // In the config phase of this module, we save references to the different providers
    // which will let us register new elements with Angular after it has bootstrapped
    ngRegistry.config(['$controllerProvider',
        '$provide',
        '$compileProvider',
        '$filterProvider',
        '$stateProvider',
        '$urlRouterProvider',
        function ($controllerProvider, $provide, $compileProvider,
                  $filterProvider, $stateProvider, $urlRouterProvider) {

            ngRegistry.addController = function( name, constructor ) {
                $controllerProvider.register( name, constructor );
                return( this );
            };

            ngRegistry.addService = function( name, constructor ) {
                $provide.service( name, constructor );
                return( this );
            };

            ngRegistry.addFactory = function( name, factory ) {
                $provide.factory( name, factory );
                return( this );
            };

            ngRegistry.addValue = function( name, value ) {
                $provide.value( name, value );
                return( this );
            };

            ngRegistry.addDirective = function( name, directive ) {
                $compileProvider.directive( name, directive );
                return( this );
            };

            ngRegistry.addFilter = function( name, filter ) {
                $filterProvider.filter( name, filter );
                return( this );
            };

            ngRegistry.addState = function ( name, config ) {
                $stateProvider.state( name, config);
                return( this );
            };

            ngRegistry.setDefaultURL = function (url) {
                $urlRouterProvider.otherwise(url);
            };

        }]);

    // We create a service to access the different providers saved previously
    // through the service's API (instead of creating a global variable).
    ngRegistry.factory('NgRegistry', [
        function() {
            return ngRegistry;
        }
    ]);

})();
