'use strict';

angular.module('common')

	.directive('navigation', function () {
		return {
			restrict: 'E',
			replace: true,
			templateUrl: '/templates/navigation.tpl.html',
            controller: 'NavigationCtrl'
		};
	})

	.directive('search', function () {
		return {
			restrict: 'E',
			replace: true,
			template: '<div class="search">' +
                '  <form class="form-inline" role="form">' +
                '    <div class="form-group">' +
				'      <input type="search" class="form-control input-sm" placeholder="search..." />' +
				'      <button type="button" class="btn btn-primary btn-sm" ng-click="search()">' +
				'        <span class="glyphicon glyphicon-search"></span>' +
				'      </button>' +
				'    </div>' +
				'  </form>' +
				'</div>'
		};
	})

	.directive('fileview', function () {
		return {
			restrict: 'E',
			replace: true,
			template: '<div class="file-view"></div>'
		};
	});
