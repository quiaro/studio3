define(['module'], function(module) {

    (function() {

        'use strict';

        var injector = angular.element(module.config().domRoot).injector();

        injector.invoke(['NgRegistry', '$state', '$log',
            function(NgRegistry, $state, $log) {

                $log.info("Config info for module: ", module.config());

                var dashboard_base_url = '/studio-ui/modules/dashboard/';

                NgRegistry
                    .addState('studio.dashboard', {
                        url: '/dashboard/:site',
                        templateUrl: dashboard_base_url + 'templates/dashboard.tpl.html',

                        // TODO: Use robust authentication mechanism
                        requireAuth: true,
                        rolesAllowed: ['admin', 'editor']
                    });

            }
        ]);

    })();

});
