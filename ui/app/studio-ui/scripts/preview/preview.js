'use strict';

angular.module('preview', ['common', 'ngEventBridge'])

    .controller('PreviewCtrl',
		['$scope', 'notifications', function($scope, notifications) {

		$scope.notifications = notifications;

        $scope.authoring = {
            site: '/sites/crafter_community.html',
            tools: {
                state: 'off',
                height: 0,
                tabs: [{
                    name: 'content',
                    title: 'Content',
                    contentUrl: '/templates/preview/tabs/content.html'
                }, {
                    name: 'template',
                    title: 'Template',
                    contentUrl: '/templates/preview/tabs/template.html'
                }, {
                    name: 'revisions',
                    title: 'Revisions',
                    contentUrl: '/templates/preview/tabs/revisions.html'
                }, {
                    name: 'info',
                    title: 'Info',
                    contentUrl: '/templates/preview/tabs/info.html'
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
