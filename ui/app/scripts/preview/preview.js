'use strict';

angular.module('preview', ['common'])

  .controller('PreviewCtrl', 
  	['$scope', 'notifications', function($scope, notifications) {

  	$scope.notifications = notifications;

		$scope.test2 = {
			anotherProperty: 'Charles Barkley'
		};

	}]);

