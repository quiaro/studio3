(function () {

    'use strict';

    var ngRegistry = angular.module('crafter.studio-ui.NgRegistry', []);

    ngRegistry.config(['$controllerProvider',
        '$provide',
        '$compileProvider',
        '$filterProvider',
        function ($controllerProvider, $provide, $compileProvider, $filterProvider) {

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

        }]);

    // Make methods available to other modules
    ngRegistry.factory('NgRegistry', [
        function() {
            return ngRegistry;
        }
    ]);

})();
