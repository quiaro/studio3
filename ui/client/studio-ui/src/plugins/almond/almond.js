/* global define */

define(['require', 'globals', 'less!./almond'],
    function( require, globals ) {

    'use strict';

    // Get the app's injector
    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', function(NgRegistry) {

            NgRegistry
                .addController('AlmondCtrl',
                    ['$scope', '$timeout', function ($scope, $timeout) {

                    $timeout( function() {
                        $scope.$apply( function() {
                            // Make sure the templates are updated with the values in the scope
                            $scope.idValue = 'Alice';
                        });
                    });

                }])

                .addDirective('sdoPluginAlmond', [function() {

                    return {
                        restrict: 'E',
                        controller: 'AlmondCtrl',
                        replace: true,
                        scope: {},
                        templateUrl: require.toUrl('./templates/almond.tpl.html')
                    };

                }]);

        }
    ]);

    return '<sdo-plugin-almond></sdo-plugin-almond>';
});
