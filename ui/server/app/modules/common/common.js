define(['globals'], function( globals ) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', '$state',
        function(NgRegistry, $state, $log) {

        NgRegistry
            .addState('studio', {
                // With abstract set to true the state can not be explicitly activated.
                // It can only be implicitly activated by activating one of it's children.
                abstract: true,

                // prepend this path segment to of all its children
                url: '/studio',
                templateUrl: globals.templates_url + '/layout.tpl.html'
            })

            .addState('unauthorized', {
                url: '/unauthorized',
                templateUrl: globals.templates_url + '/unauthorized.tpl.html'
            })

            .addDirective('sdoSubmit', ['$parse', '$timeout', function($parse, $timeout) {
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
                                if (!formController) {
                                    return false;
                                }

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

            .addController('ToolbarCtrl', [
                '$scope', function ($scope) {

                $scope.menu = {
                    selected: null
                };

                $scope.showOptionsFor = function (menu) {
                    $scope.menu.selected = ($scope.menu.selected === menu) ? null : menu;
                };
            }]);

    }]);

});
