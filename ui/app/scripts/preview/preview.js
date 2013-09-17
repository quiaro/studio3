'use strict';

angular.module('preview', ['common', 'editor'])

  .controller('PreviewCtrl',
		['$scope', 'notifications', function($scope, notifications) {

		$scope.notifications = notifications;

		$scope.selectedElement = 'none';

		$scope.$on('editor/element/select', function (event, args) {
			$scope.$apply(function () {
				$scope.selectedElement = args.id;
			});
		});

	}]);


/* TODO : Remove the controllers below. These are here only to test communication from
 * the angular app to the editor
 */
function OneCtrl ($scope, editorService) {
	$scope.locked = {};
	$scope.locked.value = true;

	$scope.lockView = function lockView() {
		editorService.publish('app/element/update', {
			id: $scope.selectedElement,
			state: 'locked'
		});
	};
}

function TwoCtrl ($scope, editorService) {
	$scope.readonly = {};
	$scope.readonly.value = false;

	$scope.readOnlyView = function readOnlyView() {
		editorService.publish('app/element/update', {
			id: $scope.selectedElement,
			state: 'read'
		});
	};
}

function ThreeCtrl ($scope, editorService) {
	$scope.edit = {};
	$scope.edit.value = true;

	$scope.editView = function editView() {
		editorService.publish('app/element/update', {
			id: $scope.selectedElement,
			state: 'edit'
		});
	};
}

OneCtrl.$inject = ['$scope', 'editorService'];

TwoCtrl.$inject = ['$scope', 'editorService'];

ThreeCtrl.$inject = ['$scope', 'editorService'];