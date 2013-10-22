'use strict';

angular.module('common')

	.directive('navigation', function () {
		return {
			restrict: 'E',
			replace: true,
			templateUrl: 'studio-ui/templates/navigation.tpl.html',
            controller: 'NavigationCtrl'
		};
	})

	.directive('fileview', function () {
		return {
			restrict: 'E',
			replace: true,
			template: '<div class="file-view"></div>'
		};
	});
