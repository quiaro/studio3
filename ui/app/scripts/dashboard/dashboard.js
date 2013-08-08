'use strict';

angular.module('dashboard', ['dialogs', 'services.repo'])

  .controller('DashboardCtrl', ['$scope', '$window', 'repo', function($scope, $window, repo) {

    // Get Recent Activity data
		$scope.getRecentActivity = function getRecentActivity () {
			repo.list().success(function(data) {
				$scope.recentActivity = data;
			}).error(function() {
				$window.alert('Unable to retrieve data for recent activity. Please try reloading or contact your system administrator.');
			});
		};

	}]);
