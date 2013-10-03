'use strict';

angular.module('dashboard', ['common'])

    .directive('dashboardWidgets',
        ['$compile', 'Widget', function ($compile, Widget) {

        return {
            restrict: 'E',
            terminal: true,
            link : function postLink (scope, element) {

                Widget.getPropertyAssets('templateUrl', true, Widget.processTemplate)
                    .then( function (templates) {
                        templates.forEach( function (tpl) {
                            element.append(tpl);
                        });
                        // Compile the contents of the element to create bindings
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
        }
    })

    .controller('DashboardCtrl',
		['$scope', '$route', 'Widget', 'notifications', 'repo', function($scope, $route, Widget, notifications, repo) {

        $scope.notifications = notifications;

        Widget.getWidgets().then( function (widgets) {
            widgets && widgets.forEach( function (widget) {

                // Identify and load the prototype object for a widget by its URL
                var widgetPrototype = $route.current.locals.loadPrototypes[widget.prototypeUrl],
                    widgetModel = widget.model;

                if (angular.isObject(widgetPrototype)) {
                    // Create a widget's model based on its prototype object
                    $scope[widget.name] = Object.create(widgetPrototype);

                    // Extend the widget's model per the configuration file
                    Object.keys(widgetModel).forEach( function(modelKey) {
                        $scope[widget.name][modelKey] = widgetModel[modelKey];
                    });

                } else {
                    throw new Error ('Prototype object for ' + widget.name + ' is not an object');
                }
            });
        });

	}]);

