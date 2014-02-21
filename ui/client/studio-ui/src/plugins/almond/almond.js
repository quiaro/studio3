/* global define */

define(['globals',
    'text!./templates/almond.tpl.html',
    'less!./almond'],
    function( globals, html ) {

    'use strict';

    // Get the app's injector
    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', '$log',
        function(NgRegistry, $log) {

            // Register the plugin controller
            NgRegistry
                .addController('NewCtrl',
                    ['$scope', '$timeout', function ($scope, $timeout) {

                    $timeout( function() {
                        $scope.$apply( function() {
                            // Make sure the templates are updated with the values in the scope
                            $scope.idValue = 'myModuleId';
                            $scope.newMethod = function (myVar) {
                                $log.log('newMethod called with param: ', myVar);
                            };
                        });
                    });

                }]);

        }
    ]);

    return html;
});
