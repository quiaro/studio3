define(['require', 'module'], function( require, module) {

    (function() {

        'use strict';

        var config = module.config(),
            injector = angular.element(config.domRoot).injector();

        injector.invoke(['NgRegistry', '$state', '$log',
            function(NgRegistry, $state, $log) {

                $log.info("Config info for module: ", config);

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

    })();

});
