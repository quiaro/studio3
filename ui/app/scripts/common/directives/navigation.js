'use strict';

angular.module('directives.navigation', [])

	.directive('navigation', function () {
		return {
			restrict: 'E',
			replace: true,
			template: '<div class="navigation">' +
								'  <div class="logo"></div>' +
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
	})

	.directive('iconmenu', function () {
		return {
			restrict: 'E',
			replace: true,
			template: '<ul class="icon-menu list-inline pull-left">' +
								'  <li><a href="#">' +
								'     <span class="glyphicon glyphicon-user"></span>' +
								'			<span class="description">Scott Weiland</span>' +
								'  </a></li>' +
								'  <li class="separator">|</li>' +
								'  <li><a href="#">' +
								'     <span class="glyphicon glyphicon-log-out"></span>' +
								'			<span class="description">Sign Out</span>' +
								'  </a></li>' +
								'</ul>'
		};
	});