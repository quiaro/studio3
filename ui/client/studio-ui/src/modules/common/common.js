define(['globals'], function( globals ) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry',
        function(NgRegistry) {

        NgRegistry
            .addState('studio', {
                // With abstract set to true the state can not be explicitly activated.
                // It can only be implicitly activated by activating one of it's children.
                abstract: true,

                // prepend this path segment to of all its children
                url: '/studio',
                templateUrl: globals.templates_url + '/layout.tpl.html'
            })

            .addState(globals.unauthorized_state, {
                url: globals.unauthorized_url,
                templateUrl: globals.templates_url + '/unauthorized.tpl.html'
            })

            .addController('ToolbarCtrl', [
                '$scope', function ($scope) {

                $scope.menu = {
                    selected: null
                };

                $scope.showOptionsFor = function (menu) {
                    $scope.menu.selected = ($scope.menu.selected === menu) ? null : menu;
                };
            }]);

    }]);

});
