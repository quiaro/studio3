(function () {

  'use strict';

  var injector = angular.element('#studio-ui').injector();

  injector.invoke(['NgRegistry', '$state',
        function(NgRegistry, $state) {

        var dashboard_base_url = '/studio-ui/modules/dashboard/';

        NgRegistry
            .addState('studio.dashboard', {
                url: '/dashboard/:site',
                templateUrl: dashboard_base_url + 'templates/dashboard.tpl.html',

                // TODO: Use robust authentication mechanism
                requireAuth: true,
                rolesAllowed: ['admin', 'editor']
            });

    }]);

})();
