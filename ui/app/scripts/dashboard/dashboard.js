'use strict';

angular.module('dashboard', ['common'])

  .controller('DashboardCtrl',
		['$scope', 'repo', 'notifications', function($scope, repo, notifications) {

		$scope.notifications = notifications;

		$scope.getRecentActivity = function getRecentActivity () {

			var filterObj, promise;

			function buildFilterObj () {
				// TO-DO: Build the config object for the specific functionality we're on
				return {};
			}

			// Initialize value
			$scope.recentActivity = null;

			filterObj = buildFilterObj();
			promise = repo.list(filterObj);

			// Service is handling any failures
			promise.then( function (data) {
        $scope.recentActivity = data;
      });
		};

	}]);

