'use strict';

angular.module('services.repo', ['resources.util'])
	.service('repo', function($http, util) {

	var api = 'repo';

	function list(filtersObj) {

		var url,
			searchStr = '';

		for (var filter in filtersObj) {
			if (filtersObj.hasOwnProperty(filter)) {
				searchStr += filter + '=' + filtersObj[filter];
			}
		}
		url = util.getServiceURL(api, 'list', searchStr);

		return $http.get(url);
	}

	function read(item, version) {

		var searchStr, url;

		searchStr = 'item=' + item + 'version=' + version;
		url = util.getServiceURL(api, 'read', searchStr);

		return $http.get(url);
	}

	// expose the API to the user
	return {
		list: list,
		read: read
	};
});