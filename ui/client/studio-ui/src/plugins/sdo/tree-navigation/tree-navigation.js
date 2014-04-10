/* global define */

define(['require', 'globals'], function(require, globals){

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

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

                        var tree, treeData, assetsData, descriptorsData, templatesData;

                        $scope.my_tree_handler = function(branch) {
                            // console.log("You selected: " + branch.label);
                            // console.log("Created By: " + branch.createdBy);
                        };

                        treeData = [];

                        // Tree data sections
                        assetsData = {
                            label: 'Assets',
                            children: ['... loading']
                        };
                        descriptorsData = {
                            label: 'Descriptors',
                            children: ['... loading']
                        };
                        templatesData = {
                            label: 'Templates',
                            children: ['... loading']
                        };

                        treeData.push(assetsData);
                        treeData.push(descriptorsData);
                        treeData.push(templatesData);

                        $scope.tree = {};

                        $scope.tree.data = treeData;

                        $scope.tree.inst = tree = {};

                        // Load data for the categories
                        StudioServices.Asset.list().then( function(data) {
                            data.forEach( function(item) {
                                if (item.folder) {
                                    item.children = ['... loading'];
                                }
                            });
                            $timeout( function() {
                                $scope.$apply( function() {
                                    assetsData.children = data;
                                });
                            });
                        });

                        StudioServices.Descriptor.list().then( function(data) {
                            data.forEach( function(item) {
                                if (item.folder) {
                                    item.children = ['... loading'];
                                }
                            });
                            $timeout( function() {
                                $scope.$apply( function() {
                                    descriptorsData.children = data;
                                });
                            });
                        });

                        StudioServices.Template.list().then( function(data) {
                            data.forEach( function(item) {
                                if (item.folder) {
                                    item.children = ['... loading'];
                                }
                            });
                            $timeout( function() {
                                $scope.$apply( function() {
                                    templatesData.children = data;
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
