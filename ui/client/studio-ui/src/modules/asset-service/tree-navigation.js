define(['require', 'globals'], function(require, globals){

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', function(NgRegistry) {

        NgRegistry
            .addController('AbnTestController', function($scope, $timeout) {
                var tree, treedata;
                $scope.my_tree_handler = function(branch) {
                    var _ref;
                    $scope.output = "You selected: " + branch.label;
                    if ((_ref = branch.data) != null ? _ref.description : void 0) {
                        return $scope.output += '(' + branch.data.description + ')';
                    }
                };

                treedata = [{
                    label: 'Assets',
                    children: ['... loading']
                }, {
                    label: 'Descriptors',
                    children: ['... loading']
                }, {
                    label: 'Templates',
                    children: ['... loading']
                }];

                $scope.tree = {};

                $scope.tree.data = treedata;

                $scope.tree.inst = tree = {};

                $scope.try_async_load = function() {
                    $scope.tree.data = [];
                    $scope.doing_async = true;
                    return $timeout(function() {
                        $scope.tree.data = treedata;
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
                        onSelect: function(branch) {
                            return $scope.output = "Vegetable: " + branch.data.definition;
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
