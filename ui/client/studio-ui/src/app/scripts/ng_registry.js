(function () {

    'use strict';

    var ngRegistry = angular.module('crafter.studio-ui.NgRegistry', ['ui.router']);

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

    // Make methods available to other modules
    ngRegistry.factory('NgRegistry', [
        function() {
            return ngRegistry;
        }
    ]);

})();
