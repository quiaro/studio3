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

    .directive('sdoFlexPanel', ['Preferences', function(Preferences) {

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
                    offset: +($attrs.offset) || 0,
                    prefKey: 'tools_panel'
                };

                this.get = function get (id) {
                    return data[id];
                };
            }],
            link: function($scope, $element, $attrs, ctrl) {

                var $adjacent = $(ctrl.get('adjacent')),
                    side = ctrl.get('side'),
                    sizeCache = Preferences.get(ctrl.get('prefKey')) || +($attrs.default) || 200;

                function closeAnimation($el, $adj, side, length) {
                    var objEl = {},
                        objAdj = {},
                        speed = 150;

                    objEl[length] = objAdj[side] = 0;

                    $adj.css(objAdj);
                    $el.animate(objEl, speed, function() {
                        $el.hide();
                    });
                }

                function openAnimation($el, $adj, side, length, restoreVal) {
                    var objEl = {},
                        objAdj = {},
                        speed = 150;

                    objEl[length] = objAdj[side] = restoreVal;

                    $el.show();
                    $el.animate(objEl, speed, function() {
                        $adjacent.css(objAdj);
                    });
                }

                $scope.$watch($attrs.hideIf, function(close, initVal) {

                    if (close === initVal) {
                        // first run
                        if (!close) {
                            openAnimation($element, $adjacent, side, 'height', sizeCache);
                        }
                        return;
                    }

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

    .directive('sdoDivider', ['$document', 'Preferences', function($document, Preferences) {

        return {
            restrict: 'C',
            require: '^sdoFlexPanel',
            link: function($scope, $element, $attrs, ctrl) {

                var $self = $element.parent(),
                    $adjacent = $(ctrl.get('adjacent')),
                    side = ctrl.get('side'),
                    offset = ctrl.get('offset'),
                    overlayClass = 'resize-overlay',
                    overlay,
                    max,
                    moveFn;

                function getOverlayString($el, overlayClass, side) {
                    var opposite,
                        dimension,
                        styleObj = {
                            position: 'absolute',
                            top: 0,
                            right: 0,
                            bottom: 0,
                            left: 0,
                            'z-index': 1000
                        },
                        start = '<div class="' + overlayClass + '" style="',
                        end = '"></div>';

                    switch (side) {
                        case 'top':
                            opposite = 'bottom';
                            dimension = 'height';
                            break;
                        case 'right':
                            opposite = 'left';
                            dimension = 'width';
                            break;
                        case 'bottom':
                            opposite = 'top';
                            dimension = 'height';
                            break;
                        case 'left':
                            opposite = 'right';
                            dimension = 'width';
                            break;
                    }

                    styleObj[opposite] = $el.css(dimension);
                    return start + JSON.stringify(styleObj).replace(/,/g,';').replace(/\{|\}|"/g, '') + end;
                }

                function generateMoveFn (side, minVal, maxVal, offset) {
                    var dimension,
                        winProp,
                        getDistance,
                        min,
                        max;

                    switch (side) {
                        case 'top':
                            dimension = 'height';
                            getDistance = function (event, length, offset) {
                                return event.pageY - offset;
                            };
                            break;
                        case 'right':
                            dimension = 'width';
                            getDistance = function (event, length, offset) {
                                return length - event.pageX;
                            };
                            break;
                        case 'bottom':
                            dimension = 'height';
                            getDistance = function (event, length, offset) {
                                return length - event.pageY;
                            };
                            break;
                        case 'left':
                            dimension = 'width';
                            getDistance = function (event, length, offset) {
                                return event.pageX - offset;
                            };
                            break;
                    }
                    winProp = 'inner' + dimension.charAt(0).toUpperCase() + dimension.slice(1);
                    min = minVal || 0;
                    max = maxVal || window[winProp] - offset;

                    return function (event, $el, $adj) {
                        var length = window[winProp] - offset,
                            distance = getDistance(event, length, offset),
                            lowerLimit = Math.min(length, min),
                            upperLimit = Math.min(length, max),
                            objEl = {},
                            objAdj = {};

                        distance = (distance > lowerLimit) ? distance : lowerLimit;
                        distance = (distance < upperLimit) ? distance : upperLimit;

                        Preferences.set(ctrl.get('prefKey'), distance);
                        distance += 'px';

                        objEl[dimension] = objAdj[side] = distance;

                        $el.css(objEl);
                        $adj.css(objAdj);
                    }
                }

                overlay = getOverlayString($element, overlayClass, side);
                moveFn = generateMoveFn(side, +($attrs.min), +($attrs.max), offset);

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
                    moveFn(event, $self, $adjacent);
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
