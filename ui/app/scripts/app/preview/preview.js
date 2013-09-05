'use strict';

angular.module('preview', ['directives.navigation'])

  .controller('PreviewCtrl', ['$scope', function($scope) {

		$scope.test2 = {
			anotherProperty: 'Charles Barkley'
		};

	}]);

