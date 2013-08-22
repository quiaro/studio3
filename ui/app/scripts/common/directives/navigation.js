'use strict';

angular.module('directives.navigation', [])

	.directive('navigation', function () {
		return {
			restrict: 'E',
			replace: true,
			template: '<div class="navigation">' +
								'  <search></search>' +
								'  <fileview></fileview>' +
								'  <iconmenu></iconmenu>' +
								'</div>'
		};
	})

	.directive('search', function () {
		return {
			restrict: 'E',
			replace: true,
			template: '<div class="search">' +
								'  <input type="text" placeholder="search..." />' +
								'  <button type="button" class="btn btn-primary" ng-click="search()" />' +
								'</div>'
		};
	})

	.directive('fileview', function () {
		return {
			restrict: 'E',
			replace: true,
			template: '<div class="file-view"></div>'
		};
	})

	.directive('iconmenu', function () {
		return {
			restrict: 'E',
			replace: true,
			template: '<ul class="icon-menu">' +
								'  <li><a class="icon-item" href="#">John Doe</a></li>' +
								'  <li class="separator"></li>' +
								'  <li><a class="icon-item" href="#">John Doe</a></li>' +
								'</ul>'
		};
	});