define(['globals'], 
    function( globals ) {

    'use strict';

    // Get the app's injector
    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', '$log',
        function(NgRegistry, $log) {

            $log.log('Plugin almond is now loaded');

            // Use NgRegistry to register a new controller within the app
            // NgRegistry
            //     .addController('NewCtrl', ['$scope', function ($scope) {
            //         $scope.newMethod = function (myVar) {
            //             $log.log("newMethod called with param: ", myVar);
            //         };
            //     }]);

        }
    ]);
});