/*'use strict';

angular.module('crafter.studio.dashboard', ['crafter.studio-ui.common', 'ui.router'])

    .constant('DASHBOARD', {
        baseUrl: '/studio-ui/src/app/dashboard/'
    })

    .config(['$stateProvider',
        'DASHBOARD', function ($stateProvider, DASHBOARD) {

        $stateProvider
            .state('studio.dashboard', {
                url: '/dashboard/:site',
                templateUrl: DASHBOARD.baseUrl + 'templates/dashboard.tpl.html',
                resolve: {
                    loadPrototypes : function loadPrototypes ($q, WidgetService) {
                        var deferred = $q.defer();
                        WidgetService.getPropertyAssets('dashboard', 'prototypeUrl',
                            false, WidgetService.processPrototype)
                            .then( function (prototypes) {
                                deferred.resolve(prototypes);
                            });
                        return deferred.promise;
                    },
                    loadTemplates : function loadTemplates ($q, WidgetService) {
                        var deferred = $q.defer();
                        WidgetService.getPropertyAssets('dashboard', 'templateUrl',
                            true, WidgetService.processTemplate)
                            .then( function (templates) {
                                deferred.resolve(templates);
                            });
                        return deferred.promise;
                    }
                },
                controller: function ($scope, loadPrototypes, loadTemplates) {
                    $scope.prototypes = loadPrototypes;
                    $scope.templates = loadTemplates;
                },
                // TODO: Use robust authentication mechanism
                requireAuth: true,
                rolesAllowed: ['admin', 'editor']
            });
    }])

    .directive('widgets',
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

    .controller('TreeNavigatorCtrl',
		['$scope', function($scope) {

        $scope.navigatorModel = [{
                'roleName': 'User',
                'roleId': 'role1',
                'children': [{
                    'roleName': 'subUser1',
                    'roleId': 'role11',
                    'children': []
                }, {
                    'roleName': 'subUser2',
                    'roleId': 'role12',
                    'children': [{
                        'roleName': 'subUser2-1',
                        'roleId': 'role121',
                        'children': [{
                            'roleName': 'subUser2-1-1',
                            'roleId': 'role1211',
                            'children': []
                        }, {
                            'roleName': 'subUser2-1-2',
                            'roleId': 'role1212',
                            'children': []
                        }]
                    }]
                }]
            },

            {
                'roleName': 'Admin',
                'roleId': 'role2',
                'children': []
            },

            {
                'roleName': 'Guest',
                'roleId': 'role3',
                'children': []
            },
            {
                'roleName': 'User',
                'roleId': 'role1',
                'children': [{
                    'roleName': 'subUser1',
                    'roleId': 'role11',
                    'children': []
                }, {
                    'roleName': 'subUser2',
                    'roleId': 'role12',
                    'children': [{
                        'roleName': 'subUser2-1',
                        'roleId': 'role121',
                        'children': [{
                            'roleName': 'subUser2-1-1',
                            'roleId': 'role1211',
                            'children': []
                        }, {
                            'roleName': 'subUser2-1-2',
                            'roleId': 'role1212',
                            'children': []
                        }]
                    }]
                }]
            },

            {
                'roleName': 'Admin',
                'roleId': 'role2',
                'children': []
            },
            {
                'roleName': 'User',
                'roleId': 'role1',
                'children': [{
                    'roleName': 'subUser1',
                    'roleId': 'role11',
                    'children': []
                }, {
                    'roleName': 'subUser2',
                    'roleId': 'role12',
                    'children': [{
                        'roleName': 'subUser2-1',
                        'roleId': 'role121',
                        'children': [{
                            'roleName': 'subUser2-1-1',
                            'roleId': 'role1211',
                            'children': []
                        }, {
                            'roleName': 'subUser2-1-2',
                            'roleId': 'role1212',
                            'children': []
                        }]
                    }]
                }]
            },

            {
                'roleName': 'Admin',
                'roleId': 'role2',
                'children': []
            },
            {
                'roleName': 'User',
                'roleId': 'role1',
                'children': [{
                    'roleName': 'subUser1',
                    'roleId': 'role11',
                    'children': []
                }, {
                    'roleName': 'subUser2',
                    'roleId': 'role12',
                    'children': [{
                        'roleName': 'subUser2-1',
                        'roleId': 'role121',
                        'children': [{
                            'roleName': 'subUser2-1-1',
                            'roleId': 'role1211',
                            'children': []
                        }, {
                            'roleName': 'subUser2-1-2',
                            'roleId': 'role1212',
                            'children': []
                        }]
                    }]
                }]
            },

            {
                'roleName': 'Admin',
                'roleId': 'role2',
                'children': []
            },

            {
                'roleName': 'User',
                'roleId': 'role1',
                'children': [{
                    'roleName': 'subUser1',
                    'roleId': 'role11',
                    'children': []
                }, {
                    'roleName': 'subUser2',
                    'roleId': 'role12',
                    'children': [{
                        'roleName': 'subUser2-1',
                        'roleId': 'role121',
                        'children': [{
                            'roleName': 'subUser2-1-1',
                            'roleId': 'role1211',
                            'children': []
                        }, {
                            'roleName': 'subUser2-1-2',
                            'roleId': 'role1212',
                            'children': []
                        }]
                    }]
                }]
            },

            {
                'roleName': 'Admin',
                'roleId': 'role2',
                'children': []
            },

            {
                'roleName': 'User',
                'roleId': 'role1',
                'children': [{
                    'roleName': 'subUser1',
                    'roleId': 'role11',
                    'children': []
                }, {
                    'roleName': 'subUser2',
                    'roleId': 'role12',
                    'children': [{
                        'roleName': 'subUser2-1',
                        'roleId': 'role121',
                        'children': [{
                            'roleName': 'subUser2-1-1',
                            'roleId': 'role1211',
                            'children': []
                        }, {
                            'roleName': 'subUser2-1-2',
                            'roleId': 'role1212',
                            'children': []
                        }]
                    }]
                }]
            },

            {
                'roleName': 'Admin',
                'roleId': 'role2',
                'children': []
            }
        ];

	}]);

*/
