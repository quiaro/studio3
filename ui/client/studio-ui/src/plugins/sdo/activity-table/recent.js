/* global define */

define(['require', 'globals', './activity-table.js', 'less!./activity-table'],
    function( require, globals, activityTable) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', function(NgRegistry) {

            NgRegistry
                .addController('sdoActivityTableRecentCtrl',
                    ['$scope', '$timeout', 'AuditService', function ($scope, $timeout, AuditService) {

                    // Filter options for the data may be defined as directive attributes
                    var filterOpts = {
                            argumentA: false
                        };

                    var recentTable = {
                        // Extend specific attribute (angular's extend is not deep)
                        table: angular.extend(activityTable.table, {
                                    header: 'My Recent Activity',
                                    columns: [{
                                        name: 'name',
                                        header: 'Page Name',
                                        class: 'page-name'
                                    }, {
                                        'name': 'lastAuthor',
                                        header: 'Last Edited By',
                                        class: 'last-author'
                                    }, {
                                        'name': 'lastEdited',
                                        header: 'Last Edited',
                                        class: 'last-edit'
                                    }]
                                })
                    };

                    // Extend/override inherited scope
                    angular.extend($scope, activityTable, recentTable);

                    AuditService.activity(filterOpts).then(function(data) {

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
