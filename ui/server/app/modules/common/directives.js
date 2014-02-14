define(['globals'], function( globals ) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', '$log',
        function(NgRegistry, $log) {

        NgRegistry
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

            .addDirective('plugins',
                ['$compile',
                 '$timeout', function ($compile, $timeout) {

                return {
                        restrict: "E",
                        template: "<div>I will load lots of plugins here!</div>"
                    };
            }])

            .addDirective('widgets',
                ['$compile',
                 '$timeout',
                 'WidgetService', function ($compile, $timeout, WidgetService) {

                return {
                    restrict: 'E',
                    terminal: true,
                    link : function postLink (scope, element, attrs) {

                        var widgetNamespace = WidgetService.getNamespace(),
                            widgetAsyncMethod = WidgetService.getAsyncMethodName();

                        WidgetService.getWidgets(attrs.section).then( function (widgets) {

                            // Create a namespace for the widgets in the scope
                            scope[widgetNamespace] = {};

                            // Get model for each widget
                            widgets.forEach( function (widget) {

                                // Identify and load the prototype object for a widget by its URL
                                var widgetPrototype = scope.prototypes[widget.prototypeUrl],
                                    widgetModel = widget.model;

                                if (angular.isObject(widgetPrototype)) {
                                    // Create a widget's model based on its prototype object
                                    scope[widgetNamespace][widget.name] = WidgetService.create(widgetPrototype);

                                    // Extend the widget's model per the configuration file
                                    Object.keys(widgetModel).forEach( function(modelKey) {
                                        scope[widgetNamespace][widget.name][modelKey] = widgetModel[modelKey];
                                    });

                                    if (widgetAsyncMethod in scope[widgetNamespace][widget.name]) {
                                        // If the widget relies on an async method to finish getting data for its
                                        // model, then call this method. It will be the method's responsibility to
                                        // use the $timeout service to make any model updates (so they will be
                                        // consumed safely within a digest cycle)
                                        scope[widgetNamespace][widget.name][widgetAsyncMethod]($timeout);
                                    }

                                } else {
                                    throw new Error ('Prototype object for ' + widget.name + ' is not an object');
                                }
                            });

                            // Append widget specific templates to directive element
                            scope.templates.forEach( function (tpl) {
                                element.append(tpl);
                            });

                            // compile the widgets' templates and create the bindings
                            // between their models and their templates
                            $compile(element.contents())(scope);

                        });
                    }
                };
            }]);

    }]);

});
