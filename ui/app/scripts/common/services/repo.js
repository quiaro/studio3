'use strict';

angular.module('common')
	.factory('repo', ['$http', '$q', 'util', 'alertDialog', function($http, $q, util, alertDialog) {

	var api = 'repo';

	function makeServiceCall (url, deferred) {
		$http.get(url).success(function(data) {
			deferred.resolve(data);
		}).error(function() {
			alertDialog.open();
			deferred.reject(null);
		});
	}

	function list(filtersObj) {

		var url,
			searchStr = '',
			deferred = $q.defer();

		for (var filter in filtersObj) {
			if (filtersObj.hasOwnProperty(filter)) {
				searchStr += filter + '=' + filtersObj[filter];
			}
		}
		url = util.getServiceURL(api, 'list', searchStr);

		makeServiceCall(url, deferred);
		return deferred.promise;
	}

	function read(item, version) {

		var searchStr, url,
			deferred = $q.defer();

		searchStr = 'item=' + item + 'version=' + version;
		url = util.getServiceURL(api, 'read', searchStr);

		makeServiceCall(url, deferred);
		return deferred.promise;
	}

	// expose the API to the user
	return {
		list: list,
		read: read
	};
}]);