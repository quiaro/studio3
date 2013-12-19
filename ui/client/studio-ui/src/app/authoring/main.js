'use strict';

angular.module('crafter.studio.authoring', ['crafter.studio.common'])

    .constant('AUTHORING', {
        baseUrl: '/studio-ui/src/app/authoring/'
    })

    .config(['$stateProvider',
        'AUTHORING', function ($stateProvider, AUTHORING) {

        $stateProvider
            .state('studio.authoring', {
                url: '/author/*path',
                templateUrl: AUTHORING.baseUrl + 'templates/authoring.tpl.html',
                controller: 'AuthoringCtrl'
                // TODO: Re-enable and use robust authentication mechanism
                // requireAuth: true,
                // rolesAllowed: ['admin', 'editor']
            });
    }])

    .value('editorId', 'editor-iframe')

    // The name of the PubSub module comes from the require config in editor/main.js
    .value('PubSubModule', 'pubsub')

    .directive('editorIframe',
        ['eventBridge', '$rootScope', 'editorId', function(eventBridge, $rootScope, editorId) {
        return {
            restrict: 'E',
            replace: true,
            template: '<iframe id="' + editorId + '">'
        };
    }])

    .factory('eventBridge',
        ['$rootScope', 'util', 'editorId', 'PubSubModule', 'REGISTRY',
            function($rootScope, util, editorId, PubSubModule, REGISTRY) {

        var registryPromise = util.getRegistry();

        registryPromise.then(function (registryObj) {

            // Get all the events from the registry that we want to pass on
            // from angular to the editor
            var bridgedEvents = registryObj[REGISTRY.bridgedEventsKey],
                    iframeCache, requireFuncCache;

            function getIframe() {
                iframeCache = iframeCache || angular.element('#' + editorId);
                return iframeCache;
            }

            function getRequireFunc(iframe) {
                if (!requireFuncCache && iframe.length) {
                    requireFuncCache = iframe[0].contentWindow.require;
                }
                return requireFuncCache;
            }

            bridgedEvents.forEach( function(evt) {
                // For each event registered in the registry, create a listener.
                // When the event is published in angular, this listener will
                // publish it on the editor as well (using the PubSub module).
                $rootScope.$on(evt, function(event, data) {
                    var iframe, requireFunc;

                    // We're going to add a property to the message data "cancelBridge"
                    // to check if we should pass on an event or not and avoid creating
                    // an endless loop
                    if (!data.cancelBridge) {
                        data.cancelBridge = true;

                        iframe = getIframe();
                        requireFunc = getRequireFunc(iframe);

                        requireFunc([PubSubModule], function (EditorPubSub) {
                            EditorPubSub.publish(evt, data);
                        });
                    }
                });
            });
        });

    }])

    .controller('AuthoringCtrl',
		['$scope', '$stateParams', 'AUTHORING', function($scope, $stateParams, AUTHORING) {

        $scope.authoring = {
            site: $stateParams.path + '.html',
            tools: {
                state: 'off',
                height: 0,
                tabs: [{
                    name: 'content',
                    title: 'Content',
                    contentUrl: AUTHORING.baseUrl + 'templates/tabs/content.html'
                }, {
                    name: 'template',
                    title: 'Template',
                    contentUrl: AUTHORING.baseUrl + 'templates/tabs/template.html'
                }, {
                    name: 'revisions',
                    title: 'Revisions',
                    contentUrl: AUTHORING.baseUrl + 'templates/tabs/revisions.html'
                }, {
                    name: 'info',
                    title: 'Info',
                    contentUrl: AUTHORING.baseUrl + 'templates/tabs/info.html'
                }],
                activeTab: 'content'
            },
            setActiveTab: function setActiveTab (tabName) {
                this.tools.activeTab = tabName;
            }
        };

		$scope.selectedElement = 'none';

		$scope.$on('editor/element/edit', function (event, args) {
			$scope.$apply(function () {
				$scope.authoring.tools.state = 'on';
                $scope.authoring.tools.height = 30;
			});
		});

        $scope.$on('editor/element/select', function (event, args) {
            $scope.$apply(function () {
                $scope.selectedElement = args.id;
            });
        });

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
            }
        ];

	}]);


/* TODO : Remove the controllers below. These are here only to test communication from
 * the angular app to the editor
 */
function OneCtrl ($scope, $rootScope) {
	$scope.locked = {};
	$scope.locked.value = true;

	$scope.lockView = function lockView() {
		$rootScope.$broadcast('app/element/update', {
			id: $scope.selectedElement,
			state: 'locked'
		});
	};
}

function TwoCtrl ($scope, $rootScope) {
	$scope.readonly = {};
	$scope.readonly.value = false;

	$scope.readOnlyView = function readOnlyView() {
		$rootScope.$broadcast('app/element/update', {
			id: $scope.selectedElement,
			state: 'read'
		});
	};
}

function ThreeCtrl ($scope, $rootScope) {
	$scope.edit = {};
	$scope.edit.value = true;

	$scope.editView = function editView() {
		$rootScope.$broadcast('app/element/update', {
			id: $scope.selectedElement,
			state: 'edit'
		});
	};
}

OneCtrl.$inject = ['$scope', '$rootScope'];

TwoCtrl.$inject = ['$scope', '$rootScope'];

ThreeCtrl.$inject = ['$scope', '$rootScope'];
