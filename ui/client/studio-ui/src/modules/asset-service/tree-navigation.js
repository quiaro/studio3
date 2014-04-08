define(['require', 'globals'], function(require, globals){

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', 'StudioServices', function(NgRegistry, StudioServices) {

        NgRegistry
            .addController('AbnTestController', function($scope, $timeout) {
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
                        })
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
                        })
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
                        })
                    });
                });

                $scope.try_async_load = function() {
                    $scope.tree.data = [];
                    $scope.doing_async = true;
                    return $timeout(function() {
                        $scope.tree.data = treeData;
                        $scope.doing_async = false;
                        return tree.expand_all();
                    }, 1000);
                };
                return $scope.try_adding_a_branch = function() {
                    var b;
                    b = tree.get_selected_branch();
                    return tree.add_branch(b, {
                        label: 'Vegetable',
                        data: {
                            definition: "A plant or part of a plant used as food, typically as accompaniment to meat or fish, such as a cabbage, potato, carrot, or bean.",
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
            });

    }]);

})
