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

    /*jshint -W072 */
    .directive('sdoPlugins',
        ['$q',
         '$compile',
         '$timeout',
         '$log',
         'ServiceProviders',
         'DefaultServiceProvider',
         'Utils',
         'GLOBALS',
         function ($q, $compile, $timeout, $log, ServiceProviders, DefaultServiceProvider, Utils, GLOBALS) {

        return {
            restrict: 'E',
            scope: {},
            link : function postLink (scope, element, attrs) {

                var containerId;

                if (!attrs.pluginContainer) {
                    $log.warn('Plugins directive with id "' + attrs.id +
                        '" is missing plugin-container attribute');
                    return;
                } else {
                    containerId = attrs.pluginContainer;
                }

                $log.log('Plugin container with id: "' + containerId + '"');

                ServiceProviders[DefaultServiceProvider].Config.getPlugins(containerId)
                    .then( function (data) {

                        var pluginList = data.plugins,
                            promiseList;

                        $log.log('Plugins found for "' + containerId + '":', pluginList);

                        promiseList = Utils.loadModules(pluginList, GLOBALS.plugins_url);

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
    /*jshint +W072 */

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

    .directive('sdoFlexPanel', ['$document', function($document) {

        return {
            restrict: 'E',
            require: 'sdoFlexPanel',
            controller: ['$scope', '$element', '$attrs',
                function controller ($scope, $element, $attrs) {

                var side = $attrs.class.match(/\b(top|right|bottom|left)\b/);
                side = (side && side.length) ? side[0] : null;

                // Make these elements available to other directives
                var data = {
                    adjacent : $attrs.adjacent,
                    side : side,
                    offset: +($attrs.offset) || 0
                };

                this.get = function get (id) {
                    return data[id];
                };
            }],
            link: function($scope, $element, $attrs, ctrl) {

                var $adjacent = $(ctrl.get('adjacent')),
                    side = ctrl.get('side'),
                    sizeCache;

                function closeAnimation($el, $adj, side, length) {
                    var objEl = {},
                        objAdj = {},
                        speed = 150;

                    objEl[length] = 0;
                    objAdj[side] = 0;

                    $adj.css(objAdj);
                    $el.animate(objEl, speed, function() {
                        $el.hide();
                    });
                }

                function openAnimation($el, $adj, side, length, restoreVal) {
                    var objEl = {},
                        objAdj = {},
                        speed = 150;

                    objEl[length] = restoreVal;
                    objAdj[side] = restoreVal;

                    $el.show();
                    $el.animate(objEl, speed, function() {
                        $adjacent.css(objAdj);
                    });
                }

                $scope.$watch($attrs.hideIf, function(close, initVal) {

                    if (close === initVal) { return; } // first run

                    if (close) {
                        switch (side) {
                            case 'top':
                            case 'bottom':
                                sizeCache = $element.css('height');
                                closeAnimation($element, $adjacent, side, 'height');
                                break;
                            case 'right':
                            case 'left':
                                sizeCache = $element.css('width');
                                closeAnimation($element, $adjacent, side, 'width');
                                break;
                        }
                    } else {
                        switch (side) {
                            case 'top':
                            case 'bottom':
                                openAnimation($element, $adjacent, side, 'height', sizeCache);
                                break;
                            case 'right':
                            case 'left':
                                openAnimation($element, $adjacent, side, 'width', sizeCache);
                                break;
                        }
                    }
                });
            }
        };
    }])

    .directive('sdoDivider', ['$document', function($document) {

        return {
            restrict: 'C',
            require: '^sdoFlexPanel',
            link: function($scope, $element, $attrs, ctrl) {

                var $self = $element.parent(),
                    $adjacent = $(ctrl.get('adjacent')),
                    side = ctrl.get('side'),
                    offset = ctrl.get('offset'),
                    overlayClass = 'resize-overlay',
                    zIndex = 1000,
                    min = +($attrs.min) || 0,
                    overlay,
                    max,
                    moveFn;

                switch (side) {
                    case 'top':
                        overlay = '<div class="' + overlayClass + '"' +
                                  ' style="position: absolute; top: 0; left: 0; right: 0;' +
                                  ' bottom: ' + $element.css('height') + '; z-index: ' + zIndex + '"></div>';

                        max = +($attrs.max) || window.innerHeight - offset;

                        moveFn = function (event) {
                            var fullLength = window.innerHeight - offset,
                                distance = event.pageY - offset,
                                realMax = max || fullLength,
                                lowerLimit = Math.min(fullLength, min),
                                upperLimit = Math.min(fullLength, realMax);

                            distance = (distance > lowerLimit) ? distance : lowerLimit;
                            distance = (distance < upperLimit) ? distance : upperLimit;
                            distance += 'px';

                            $self.css({ height: distance });
                            $adjacent.css({ top: distance });
                        };
                        break;

                    case 'right':
                        overlay = '<div class="' + overlayClass + '"' +
                                  ' style="position: absolute; top: 0; bottom: 0; right: 0;' +
                                  ' left: ' + $element.css('width') + '; z-index: ' + zIndex + '"></div>';

                        max = +($attrs.max) || window.innerWidth - offset;

                        moveFn = function (event) {
                            var fullLength = window.innerWidth - offset,
                                distance = fullLength - event.pageX,
                                realMax = max || fullLength,
                                lowerLimit = Math.min(fullLength, min),
                                upperLimit = Math.min(fullLength, realMax);

                            distance = (distance > lowerLimit) ? distance : lowerLimit;
                            distance = (distance < upperLimit) ? distance : upperLimit;
                            distance += 'px';

                            $self.css({ width: distance });
                            $adjacent.css({ right: distance });
                        };
                        break;

                    case 'bottom':
                        overlay = '<div class="' + overlayClass + '"' +
                                  ' style="position: absolute; bottom: 0; left: 0; right: 0;' +
                                  ' top: ' + $element.css('height') + '; z-index: ' + zIndex + '"></div>';

                        max = +($attrs.max) || null;

                        moveFn = function (event) {
                            var fullLength = window.innerHeight - offset,
                                distance = fullLength - event.pageY,
                                realMax = max || fullLength,
                                lowerLimit = Math.min(fullLength, min),
                                upperLimit = Math.min(fullLength, realMax);

                            distance = (distance > lowerLimit) ? distance : lowerLimit;
                            distance = (distance < upperLimit) ? distance : upperLimit;
                            distance += 'px';

                            $self.css({ height: distance });
                            $adjacent.css({ bottom: distance });
                        };
                        break;

                    case 'left':
                        overlay = '<div class="' + overlayClass + '"' +
                                  ' style="position: absolute; top: 0; bottom: 0; left: 0;' +
                                  ' right: ' + $element.css('width') + '; z-index: ' + zIndex + '"></div>';

                        max = +($attrs.max) || window.innerWidth - offset;

                        moveFn = function (event) {
                            var fullLength = window.innerWidth - offset,
                                distance = event.pageX - offset,
                                realMax = max || fullLength,
                                lowerLimit = Math.min(fullLength, min),
                                upperLimit = Math.min(fullLength, realMax);

                            distance = (distance > lowerLimit) ? distance : lowerLimit;
                            distance = (distance < upperLimit) ? distance : upperLimit;
                            distance += 'px';

                            $self.css({ width: distance });
                            $adjacent.css({ left: distance });
                        };
                        break;
                }

                $element.on('mousedown', function(event) {
                    event.preventDefault();

                    // Put an overlay over the top and bottom elements so that any mouse over
                    // events in them do not interfere with the resizing
                    $self.append(overlay);
                    $adjacent.append(overlay);

                    $document.on('mousemove', mousemove);
                    $document.on('mouseup', mouseup);
                });

                function mousemove(event) {
                    moveFn(event);
                }

                function mouseup() {
                    // Remove the overlays
                    $self.children().remove('.' + overlayClass);
                    $adjacent.children().remove('.' + overlayClass);

                    $document.unbind('mousemove', mousemove);
                    $document.unbind('mouseup', mouseup);
                }
            }
        };
    }]);
