'use strict';

angular.module('crafter.studio-ui.Directives', [])

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

    .directive('sdoPlugins',
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

    .directive('sdoPluginSrc',
        ['$q',
         '$compile',
         '$log',
         'Utils',
         'GLOBALS', function($q, $compile, $log, Utils, GLOBALS) {

        return {
            restrict: 'A',
            priority: 1000,
            compile: function(element, attr) {

                var pluginName = attr.sdoPluginSrc,
                    promiseList;

                // Remove this directive so it doesn't create an endless loop
                // since it calls on the $compile function
                attr.$set('sdoPluginSrc', null);

                // Proceed to load the directive
                $log.log('Loading plugin ' + pluginName + '...');

                promiseList = Utils.loadModules([pluginName], GLOBALS.plugins_url);

                return function postLink (scope, element, attr) {

                    $q.all(promiseList).then( function() {
                        // After all the plugin's resources have been loaded,
                        // compile the plugin directive
                        $compile(element)(scope);
                    });
                };
            }
        };
    }])

    .directive('sdoYDivider', ['$document', function($document) {

        return {
            restrict: 'E',
            link: function($scope, $element, $attrs) {

                var $topEl = $($attrs.top),
                    $bottomEl = $($attrs.bottom),
                    bottomOffset = +($attrs.bottomOffset) || 0,
                    bottomMin = +($attrs.bottomMin) || 0,
                    dividerHeight = $element.height(),
                    overlayClass = 'resize-overlay',
                    overlay = '<div class="' + overlayClass + '"' +
                              ' style="position: absolute; top: 0; bottom: 0; left: 0; right: 0; z-index: 1000"></div>';

                $element.on('mousedown', function(event) {
                    event.preventDefault();

                    // Put an overlay over the top and bottom elements so that any mouse over
                    // events in them do not interfere with the resizing
                    $topEl.append(overlay);
                    $bottomEl.append(overlay);

                    $document.on('mousemove', mousemove);
                    $document.on('mouseup', mouseup);
                });

                function mousemove(event) {

                    var windowHeight = window.innerHeight - bottomOffset,
                        y = windowHeight - event.pageY,
                        bottomMax = +($attrs.bottomMax) || windowHeight,
                        bottomLowerLimit = Math.min(windowHeight, bottomMin),
                        bottomUpperLimit = Math.min(windowHeight, bottomMax);

                    y = (y > bottomLowerLimit) ? y : bottomLowerLimit;
                    y = (y < bottomUpperLimit) ? y : bottomUpperLimit;

                    $element.css({
                        bottom: y + 'px'
                    });

                    $topEl.css({
                        bottom: y + dividerHeight + 'px'
                    });

                    $bottomEl.css({
                        height: y + 'px'
                    });
                }

                function mouseup() {
                    // Remove the overlays
                    $topEl.children().remove('.' + overlayClass);
                    $bottomEl.children().remove('.' + overlayClass);

                    $document.unbind('mousemove', mousemove);
                    $document.unbind('mouseup', mouseup);
                }
            }
        };
    }]);
