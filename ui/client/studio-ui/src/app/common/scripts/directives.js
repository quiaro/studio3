'use strict';

angular.module('crafter.studio.common')

	.directive('navigation', function () {
		return {
			restrict: 'E',
			replace: true,
			templateUrl: 'studio-ui/templates/navigation.tpl.html',
            controller: 'NavigationCtrl'
		};
	})

	.directive('fileview', function () {
		return {
			restrict: 'E',
			replace: true,
			template: '<div class="file-view"></div>'
		};
	})

    .directive('sdoSubmit', ['$parse', '$timeout', function($parse, $timeout) {
        return {
            restrict: 'A',
            require: ['sdoSubmit', '?form'],
            controller: ['$scope',
                function($scope) {
                    this.attempted = false;

                    var formController = null;

                    this.setAttempted = function() {
                        this.attempted = true;
                    };

                    this.setFormController = function(controller) {
                        formController = controller;
                    };

                    this.checkValidation = function(fieldModelController) {
                        if (!formController) return false;

                        if (fieldModelController) {
                            return fieldModelController.$invalid &&
                                (fieldModelController.$dirty || this.attempted);
                        } else {
                            return formController && formController.$invalid &&
                                (formController.$dirty || this.attempted);
                        }
                    };
                }
            ],
            compile: function(cElement, cAttributes, transclude) {
                return {
                    pre: function(scope, formElement, attributes, controllers) {

                        var submitController = controllers[0];
                        var formController = (controllers.length > 1) ? controllers[1] : null;

                        submitController.setFormController(formController);

                        scope.sdo = scope.sdo || {};
                        scope.sdo[attributes.name] = submitController;
                    },
                    post: function(scope, formElement, attributes, controllers) {

                        var submitController = controllers[0];
                        var formController = (controllers.length > 1) ? controllers[1] : null;
                        var fn = $parse(attributes.sdoSubmit);

                        formElement.bind('submit', function() {
                            $timeout(function() {
                                scope.$apply(function() {
                                    submitController.setAttempted();
                                });
                            });

                            if (!formController.$valid) {
                                return false;
                            } else {
                                scope.$apply(function() {
                                    fn(scope, {
                                        $event: event
                                    });
                                });
                            }
                        });
                    }
                };
            }
        };
    }])

    .directive( 'treeModel', ['$compile', function( $compile ) {
        return {
            restrict: 'A',
            link: function ( scope, element, attrs ) {
                //tree id
                var treeId = attrs.treeId;

                //tree model
                var treeModel = attrs.treeModel;

                //node id
                var nodeId = attrs.nodeId || 'id';

                //node label
                var nodeLabel = attrs.nodeLabel || 'label';

                //children
                var nodeChildren = attrs.nodeChildren || 'children';

                //tree template
                var template =
                    '<ul>' +
                        '<li data-ng-repeat="node in ' + treeModel + '">' +
                            '<i class="collapsed" data-ng-show="node.' + nodeChildren + '.length && ' +
                                'node.collapsed" data-ng-click="' + treeId + '.selectNodeHead(node)"></i>' +
                            '<i class="expanded" data-ng-show="node.' + nodeChildren + '.length && ' +
                                '!node.collapsed" data-ng-click="' + treeId + '.selectNodeHead(node)"></i>' +
                            '<i class="normal" data-ng-hide="node.' + nodeChildren + '.length"></i> ' +
                            '<span data-ng-class="node.selected" ' +
                                'data-ng-click="' + treeId + '.selectNodeLabel(node)">' +
                                '{{node.' + nodeLabel + '}}</span>' +
                            '<div data-ng-hide="node.collapsed" data-tree-id="' + treeId + '" ' +
                                'data-tree-model="node.' + nodeChildren + '" data-node-id=' + nodeId +
                                ' data-node-label=' + nodeLabel + ' data-node-children=' + nodeChildren + '></div>' +
                        '</li>' +
                    '</ul>';


                //check tree id, tree model
                if( treeId && treeModel ) {

                    //root node
                    if( attrs.angularTreeview ) {

                        //create tree object if not exists
                        scope[treeId] = scope[treeId] || {};

                        //if node head clicks,
                        scope[treeId].selectNodeHead = scope[treeId].selectNodeHead || function( selectedNode ){

                            //Collapse or Expand
                            selectedNode.collapsed = !selectedNode.collapsed;
                        };

                        //if node label clicks,
                        scope[treeId].selectNodeLabel = scope[treeId].selectNodeLabel || function( selectedNode ){

                            //remove highlight from previous node
                            if ( scope[treeId].currentNode && scope[treeId].currentNode.selected ) {
                                scope[treeId].currentNode.selected = undefined;
                            }

                            if ( scope[treeId].currentNode !== selectedNode) {
                                //set highlight to selected node
                                selectedNode.selected = 'selected';

                                //set currentNode
                                scope[treeId].currentNode = selectedNode;
                            } else {
                                scope[treeId].currentNode = null;
                            }
                        };
                    }

                    //Rendering template.
                    element.html('').append( $compile( template )( scope ) );
                }
            }
        };
    }]);
