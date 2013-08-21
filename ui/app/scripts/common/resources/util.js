'use strict';

angular.module('resources.util', ['resources.vars'])
	.factory('util', ['S2dio', function (S2dio) {

	/*
	 * @param api -API Category
	 * @param method -Existing method in the API Category
	 * @param searchStr -url search parameters
	 * @return URL used to communicate with the backend; null, if the URL cannot be calculated correctly with the parameters given
	 */
	function getServiceURL(api, method, searchStr) {

		var siteName, urlBase, apiVersion;

		siteName = getS2dioProperty('siteName');
		urlBase = getS2dioProperty('urlBase');
		apiVersion = getS2dioProperty('apiVersion');

		if (api && typeof api === 'string' &&
				method && typeof method === 'string' &&
				typeof searchStr === 'string') {
			return '/' + urlBase + '/' + apiVersion + '/' + api + '/' + method + '/' + siteName + ((!searchStr) ? '' : '?' + searchStr);
		} else {
			throw new Error('Unable to resolve service URL');
		}
	}

	function getS2dioProperty (property) {

		if (S2dio[property]) {
			return S2dio[property];
		} else {
			throw new ReferenceError('S2dio.' + property + ' has not been set');
		}
	}

	function setS2dioProperty (property, value) {
		var hasProperty = false;

		for (var prop in S2dio) {
			if (S2dio.hasOwnProperty(prop)) {
				// Check if the property does exist in S2dio
				if (prop === property) {
					hasProperty = true;
				}
			}
		}
		if (hasProperty) {
			S2dio[property] = value;
		} else {
			throw new ReferenceError(property + ' is not a valid property of S2dio');
		}
	}

	// expose the API to the user
	return {
		getServiceURL: getServiceURL,
		getS2dioProperty: getS2dioProperty,
		setS2dioProperty: setS2dioProperty
	};
}]);