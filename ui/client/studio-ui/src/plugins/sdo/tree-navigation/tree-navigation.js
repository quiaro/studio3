/* global define */

define(['require', 'globals', 'module'], function(require, globals, module){

    'use strict';

    var config = module.config().config,
        injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', function(NgRegistry) {

        NgRegistry
            .addDirective('sdoTreeNavigation', function($timeout, ServiceProviders, Utils){
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

                    }],
                    link: function postLink(scope, el, attrs) {

                        var tree = scope.treeControl,
                            loadingStr = 'loading ...';

                        scope.branchSelected = function(branch) {

                            console.log("You selected: " + branch.label);

                            if (!branch.children ||
                                (Array.isArray(branch.children) && !branch.children.length)) {

                                console.log('Yup, this is a LEAF!');

                            } else if (branch.service && branch.children[0].label === loadingStr) {
                                branch.service.method.apply(branch.service.context, [branch.id.itemId]).then( function(data) {
                                    data.forEach( function(item) {
                                        if (item.folder) {
                                            item.children = [loadingStr];
                                            item.service = branch.service;
                                        }
                                    });
                                    $timeout( function() {
                                        scope.$apply( function() {
                                            branch.children = data;
                                        });
                                    });
                                });
                            }
                        };

                        // Get all the first-level children for each one of
                        // the tree nav sections
                        config.sections.forEach( function(section) {

                            var node = {
                                    label: section.label,
                                    children: [loadingStr]
                                },
                                serviceProvider,
                                serviceStr,
                                serviceContext,
                                serviceMethod;

                            scope.treeData.push(node);

                            if (section.content) {
                                serviceProvider = section.content.serviceProvider,
                                serviceStr = section.content.service;
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
