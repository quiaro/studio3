/* global define */

define(['require',
        'globals',
        'common',
        'directives',
        'css!./dashboard'], function( require, globals ) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', '$state', '$log',
        function(NgRegistry, $state, $log) {

            NgRegistry
                .addState('studio.dashboard', {
                    url: '/dashboard/:site',
                    templateUrl: require.toUrl('./templates/dashboard.tpl.html'),

                    // TODO: Use robust authentication mechanism
                    requireAuth: true,
                    rolesAllowed: ['admin', 'editor']
                });

        }
    ]);

});
