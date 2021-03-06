/* global define */

define(['globals'], function( globals ) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', 'StudioServices', '$log',
        function(NgRegistry, StudioServices, $log) {

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

            .addDirective('sdoPlugins',
                ['$q',
                 '$compile',
                 '$timeout',
                 '$log',
                 'Utils', function ($q, $compile, $timeout, $log, Utils) {

                return {
                    restrict: 'E',
                    scope: {},
                    template: '<div class="sdo-plugins"></div>',
                    replace: true,
                    link : function postLink (scope, element, attrs) {

                            var containerId;

                            if (!attrs.pluginContainer) {
                                $log.warn('Plugins directive with id "' + attrs.id +
                                    '" is missing data-plugin-container attribute');
                                return;
                            } else {
                                containerId = attrs.pluginContainer;
                            }

                            $log.log('Plugin container with id: "' + containerId + '"');

                            StudioServices.Config.getPlugins(containerId)
                                .then( function (data) {

                                    var pluginList = data.plugins,
                                        promiseList;

                                    $log.log('Plugins found for "' + containerId + '":', pluginList);

                                    promiseList = Utils.loadModules(pluginList, globals.plugins_url);

                                    $q.all(promiseList).then( function(templates) {

                                        // Append templates to directive element
                                        templates.forEach( function (tpl) {
                                            element.append(tpl);
                                        });

                                        // compile the plugins' templates and create the bindings
                                        // between their models and their templates
                                        $compile(element.contents())(scope);
                                    });
                                });
                    }
                };
            }])

            .addDirective('sdoPluginSrc',
                ['$q',
                 '$compile',
                 '$log',
                 'Utils',
                 'GLOBALS', function($q, $compile, $log, Utils, GLOBALS) {

                return {
                    restrict: 'A',
                    compile: function(element, attr) {

                        var pluginName = attr.sdoPluginSrc,
                            promiseList;

                        if (!attr.sdoPluginLoaded) {

                            $log.log('Loading plugin ' + pluginName + ' from directive ...');

                            promiseList = Utils.loadModules([pluginName], GLOBALS.plugins_url);

                            return function (scope, element, attr) {

                                attr.$set('sdoPluginLoaded', true);

                                $q.all(promiseList).then( function() {
                                    // after all the plugin's resources have been loaded,
                                    // compile the plugin directive
                                    $compile(element)(scope);
                                });
                            };
                        }
                    }
                };
            }]);

    }]);

});
