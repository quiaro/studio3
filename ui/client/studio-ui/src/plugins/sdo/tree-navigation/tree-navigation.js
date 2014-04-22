/* global define */

define(['require', 'globals', 'module'], function(require, globals, module){

    'use strict';

    var config = module.config().config,
        injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', function(NgRegistry) {

        NgRegistry
            .addDirective('sdoTreeNavigation', function($timeout, ServiceProviders, Utils){

                var loadingStr = 'loading ...';

                return {
                    restrict: 'E',
                    scope: {
                        onSelect: '&',
                        treeControl: '=?'
                    },
                    replace: true,
                    templateUrl: require.toUrl('./templates/tree-navigation.tpl.html'),
                    controller: ['$scope', function ($scope) {

                        // Initialize scope values needed by the abn-tree (child) directive
                        $scope.treeData = [];
                        $scope.treeControl = $scope.treeControl || {};

                        // Set all the first-level nodes
                        config.sections.forEach( function(section) {

                            var node = {
                                label: section.label,
                                children: [loadingStr],
                                folder: true
                            };

                            $scope.treeData.push(node);
                        });

                    }],
                    link: function postLink(scope, el, attrs) {

                        var tree = scope.treeControl;

                        scope.branchSelected = function(branch) {

                            if (branch.service && branch.children[0].label === loadingStr) {
                                // Load the children, if they haven't been loaded
                                branch.service.method.apply(branch.service.context, [branch.id.itemId]).then( function(data) {
                                    data.forEach( function(item) {
                                        if (item.folder) {
                                            item.children = [loadingStr];
                                            item.service = branch.service;
                                        }
                                        // TO-DO: remove once contentType value is returned in the metadata
                                        item.contentType = branch.contentType;
                                    });
                                    $timeout( function() {
                                        scope.$apply( function() {
                                            branch.children = data;
                                        });
                                    });
                                });
                            }
                            if (scope.onSelect !== null && typeof scope.onSelect == 'function') {
                                $timeout( function() {
                                    scope.onSelect({
                                       branch: branch
                                    });
                                });
                            }
                        };

                        // Get the children for each one of the tree nav sections
                        config.sections.forEach( function(section, idx) {

                            var node,
                                serviceProvider,
                                serviceStr,
                                serviceContext,
                                serviceMethod,

                                // TO-DO: remove once contentType value is returned in the metadata
                                contentType;

                            node = scope.treeData[idx];

                            if (section.content) {
                                serviceProvider = section.content.serviceProvider,
                                serviceStr = section.content.service;
                                contentType = section.content.type;
                                node.contentType = contentType;
                            } else {
                                throw new Error('Content information missing for navigation section');
                            }

                            if (ServiceProviders[serviceProvider]) {
                                serviceProvider = ServiceProviders[serviceProvider];
                            } else {
                                throw new Error('Service provider \'' + serviceProvider + '\' not found');
                            }

                            if (!serviceStr) {
                                throw new Error('No content service specified');
                            }

                            serviceContext = Utils.getContext(serviceProvider, serviceStr);
                            serviceMethod = Utils.getMethod(serviceProvider, serviceStr);

                            serviceMethod.apply(serviceContext, []).then( function(data) {

                                data.forEach( function(item) {
                                    if (item.folder) {
                                        // Extend the folder items with service & children information
                                        item.children = [loadingStr];
                                        item.service = {
                                            context: serviceContext,
                                            method: serviceMethod
                                        };
                                    }
                                    // TO-DO: remove once contentType value is returned in the metadata
                                    item.contentType = contentType;
                                });
                                $timeout( function() {
                                    scope.$apply( function() {
                                        node.children = data;
                                    });
                                });
                            });

                        });
                    }
                };
            });

    }]);

    return '<sdo-tree-navigation></sdo-tree-navigation>';

});
