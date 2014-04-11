/* global define */

define(['require', 'globals', 'module'], function(require, globals, module){

    'use strict';

    var config = module.config().config,
        injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', function(NgRegistry) {

        NgRegistry
            .addDirective('sdoTreeNavigation', function($timeout, $log){
                return {
                    restrict: 'E',
                    scope: {},
                    replace: true,
                    templateUrl: require.toUrl('./templates/tree-navigation.tpl.html'),
                    controller: ['$scope',
                                 '$element',
                                 '$attrs',
                                 '$transclude',
                                 '$timeout',
                                 'ServiceProviders',
                                    function ($scope, $el, $attrs, $transclude, $timeout, ServiceProviders) {

                        var tree, treeData, assetsData, descriptorsData, templatesData, loadingStr = 'loading ...';

                        $scope.my_tree_handler = function(branch) {
                            // console.log("You selected: " + branch.label);
                            // console.log("Created By: " + branch.createdBy);
                        };

                        $scope.tree = {
                            data: [],
                            control: {}
                        };

                        tree = $scope.tree.control;

                        config.sections.forEach( function(section) {

                            var node = {
                                label: section.label,
                                children: [loadingStr]
                            };

                            $scope.tree.data.push(node);

                            if (!section.content) {
                                throw new Error('Content information missing for navigation section');
                            }

                            if (!ServiceProviders[section.content.serviceProvider]) {
                                throw new Error('Content provider: ' + section.content.serviceProvider + ' has not been registered');
                            }

                            if (!section.content.service) {
                                throw new Error('No content service specified');
                            }

                            ServiceProviders[section.content.serviceProvider].Asset.list().then( function(data) {
                                data.forEach( function(item) {
                                    if (item.folder) {
                                        item.children = [loadingStr];
                                    }
                                });
                                $timeout( function() {
                                    $scope.$apply( function() {
                                        node.children = data;
                                    });
                                });
                            });

                        });

                        $scope.try_adding_a_branch = function() {
                            var b;
                            b = tree.get_selected_branch();
                            return tree.add_branch(b, {
                                label: 'Vegetable',
                                data: {
                                    definition: 'A plant',
                                    data_can_contain_anything: true
                                },
                                children: [{
                                    label: 'Oranges'
                                }, {
                                    label: 'Apples',
                                    children: [{
                                        label: 'Granny Smith'
                                    }, {
                                        label: 'Red Delicous'
                                    }, {
                                        label: 'Fuji'
                                    }]
                                }]
                            });
                        };
                    }]
                }
            });

    }]);

    return '<sdo-tree-navigation></sdo-tree-navigation>';

});
