'use strict';

angular.module('dashboard', ['common'])

    .directive('widgets',
        ['$compile', '$route', '$timeout', 'Widget', function ($compile, $route, $timeout, Widget) {

        return {
            restrict: 'E',
            terminal: true,
            link : function postLink (scope, element, attrs) {

                var widgetNamespace = Widget.getNamespace(),
                    widgetAsyncMethod = Widget.getAsyncMethodName();

                Widget.getWidgets(attrs.section).then( function (widgets) {

                    // Get model for each widget
                    widgets.forEach( function (widget) {

                        // Identify and load the prototype object for a widget by its URL
                        var widgetPrototype = $route.current.locals.loadPrototypes[widget.prototypeUrl],
                            widgetModel = widget.model;

                        if (angular.isObject(widgetPrototype)) {
                            // Create a widget's model based on its prototype object
                            scope[widgetNamespace][widget.name] = Widget.create(widgetPrototype);

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
                    $route.current.locals.loadTemplates.forEach( function (tpl) {
                        element.append(tpl);
                    });

                    // compile the widgets' templates and create the bindings
                    // between their models and their templates
                    $compile(element.contents())(scope);

                });
            }
        };
    }])

    // Filter items depending on their type: pages, components, assets or any
    .filter('typeFilter', function() {
        return function (items, type) {
            if (!type || '' === type) {
                return items;
            }

            return items.filter(function(element, index, array) {
                return element.type === type;
            });
        };
    })

    .controller('DashboardCtrl',
		['$scope', 'Widget', 'notifications', function($scope, Widget, notifications) {

        var widgetNamespace = Widget.getNamespace();

        $scope.notifications = notifications;

        // Create a namespace for the widgets in the scope
        $scope[widgetNamespace] = {};

	}]);

