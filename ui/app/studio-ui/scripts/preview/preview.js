'use strict';

angular.module('preview', ['common', 'ngEventBridge'])

    .controller('PreviewCtrl',
		['$scope', 'notifications', function($scope, notifications) {

		$scope.notifications = notifications;

        $scope.authoring = {
            site: '/site/mango/crafter_community.html',
            tools: {
                state: 'off',
                height: 0,
                tabs: [{
                    name: 'content',
                    title: 'Content',
                    contentUrl: '/studio-ui/templates/preview/tabs/content.html'
                }, {
                    name: 'template',
                    title: 'Template',
                    contentUrl: '/studio-ui/templates/preview/tabs/template.html'
                }, {
                    name: 'revisions',
                    title: 'Revisions',
                    contentUrl: '/studio-ui/templates/preview/tabs/revisions.html'
                }, {
                    name: 'info',
                    title: 'Info',
                    contentUrl: '/studio-ui/templates/preview/tabs/info.html'
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
                "roleName": "User",
                "roleId": "role1",
                "children": [{
                    "roleName": "subUser1",
                    "roleId": "role11",
                    "children": []
                }, {
                    "roleName": "subUser2",
                    "roleId": "role12",
                    "children": [{
                        "roleName": "subUser2-1",
                        "roleId": "role121",
                        "children": [{
                            "roleName": "subUser2-1-1",
                            "roleId": "role1211",
                            "children": []
                        }, {
                            "roleName": "subUser2-1-2",
                            "roleId": "role1212",
                            "children": []
                        }]
                    }]
                }]
            },

            {
                "roleName": "Admin",
                "roleId": "role2",
                "children": []
            },

            {
                "roleName": "Guest",
                "roleId": "role3",
                "children": []
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
