/* global define */

define(['require', 'globals', 'module', './activity-table.js', 'less!./activity-table'],
    function( require, globals, module, activityTable) {

    'use strict';

    var config = module.config().config,
        injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', 'ServiceProviders', 'DefaultServiceProvider',
        function(NgRegistry, ServiceProviders, DefaultServiceProvider) {

        var serviceProvider = (config && config.service_provider) ?
                                ServiceProviders[config.service_provider] :
                                ServiceProviders[DefaultServiceProvider];

        NgRegistry
            .addController('sdoActivityTableRecentCtrl',
                ['$scope', '$timeout', 'AuditService', function ($scope, $timeout, AuditService) {

                // Filter options for the data
                // TO-DO: write a service from which to get these options (per user)
                var filterOpts = {};

                var recentTable = {
                    // Extend specific attribute (angular's extend is not deep)
                    table: angular.extend(activityTable.table, {
                                header: 'My Recent Activity',
                                columns: [{
                                    name: 'name',
                                    header: 'Page Name',
                                    class: 'page-name'
                                }, {
                                    name: 'lastAuthor',
                                    header: 'Last Edited By',
                                    class: 'last-author'
                                }, {
                                    name: 'lastEdited',
                                    header: 'Last Edited',
                                    class: 'last-edit'
                                }]
                            })
                };

                // Extend/override inherited scope
                angular.extend($scope, activityTable, recentTable);

                serviceProvider.Audit.getActivity(filterOpts).then(function(data) {

                    $timeout( function() {
                        $scope.$apply( function() {
                            var numResults = $scope.filterLength.value;

                            // Delay the updates to the model until it's safe
                            // to start a new digest cycle in Angular
                            $scope.table.data = data;
                            $scope.filterLength.value = (numResults) ? numResults : data.length;
                        });
                    });
                });

            }])

            .addDirective('sdoPluginActivityTableRecent', [function() {

                return {
                    restrict: 'E',
                    controller: 'sdoActivityTableRecentCtrl',
                    replace: true,
                    scope: {},
                    templateUrl: require.toUrl('./templates/recent.tpl.html')
                };

            }]);

        }
    ]);

    return '<sdo-plugin-activity-table-recent></sdo-plugin-activity-table-recent>';
});
