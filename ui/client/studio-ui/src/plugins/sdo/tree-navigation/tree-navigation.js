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
                                 'StudioServices',
                                    function ($scope, $el, $attrs, $transclude, $timeout, StudioServices) {

                        var tree, treeData, assetsData, descriptorsData, templatesData, loadingStr = 'loading ...',

                            // TO-DO: Instantiate global providers from app configuration
                            // and save references to these providers in the 'globals' variable
                            contentProviders = {};

                        contentProviders['StudioServices'] = StudioServices;

                        console.log("Tree config: ", config);

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

                            if (!contentProviders[section.content.provider]) {
                                throw new Error('Content provider: ' + section.content.provider + ' has not been registered');
                            }

                            if (!section.content.service) {
                                throw new Error('No content service specified');
                            }

                            contentProviders[section.content.provider].Asset.list().then( function(data) {
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
