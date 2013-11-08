'use strict';

angular.module('crafter.studio.common')
	.factory('audit', ['$http', '$q', 'util', 'alertDialog', function($http, $q, util, alertDialog) {

	var api = 'audit';

	function makeServiceCall (url, deferred) {
		$http.get(url).success(function(data) {
			deferred.resolve(data);
		}).error(function() {
			alertDialog.open();
			deferred.reject(null);
		});
	}

	function activity(filtersObj) {

		var url,
			searchStr = '',
			deferred = $q.defer();

		for (var filter in filtersObj) {
			if (filtersObj.hasOwnProperty(filter)) {
				searchStr += filter + '=' + filtersObj[filter];
			}
		}
		url = util.getServiceURL(api, 'activity', searchStr);

		makeServiceCall(url, deferred);
		return deferred.promise;
	}

	function log(item, version) {

		var searchStr, url,
			deferred = $q.defer();

		searchStr = 'item=' + item + 'version=' + version;
		url = util.getServiceURL(api, 'log', searchStr);

		makeServiceCall(url, deferred);
		return deferred.promise;
	}

	// expose the API to the user
	return {
		activity: activity,
		log: log
	};
}]);
