'use strict';

angular.module('common', ['ui.bootstrap.dialog'])

	.factory('util', ['Env', function (Env) {

		/*
		 * @param api -API Category
		 * @param method -Existing method in the API Category
		 * @param searchStr -url search parameters
		 * @return URL used to communicate with the backend; 
		 *         null, if the URL cannot be calculated correctly with the parameters given
		 */
		function getServiceURL(api, method, searchStr) {

			var siteName, urlBase, apiVersion, ss;

			siteName = getEnvProperty('siteName');
			urlBase = getEnvProperty('urlBase');
			apiVersion = getEnvProperty('apiVersion');

			if (api && typeof api === 'string' &&
					method && typeof method === 'string' &&
					typeof searchStr === 'string') {

				ss = (!searchStr) ? '' : '?' + searchStr;
				return '/' + urlBase + '/' + apiVersion + '/' + api + '/' + method + '/' + siteName + ss;
			} else {
				throw new Error('Unable to resolve service URL');
			}
		}

		function getEnvProperty (property) {

			if (Env[property]) {
				return Env[property];
			} else {
				throw new ReferenceError('Env.' + property + ' has not been set');
			}
		}

		function setEnvProperty (property, value) {
			var hasProperty = false;

			for (var prop in Env) {
				if (Env.hasOwnProperty(prop)) {
					// Check if the property does exist in Env
					if (prop === property) {
						hasProperty = true;
					}
				}
			}
			if (hasProperty) {
				Env[property] = value;
			} else {
				throw new ReferenceError(property + ' is not a valid property of Env');
			}
		}

		// expose the API to the user
		return {
			getServiceURL: getServiceURL,
			getEnvProperty: getEnvProperty,
			setEnvProperty: setEnvProperty
		};
	}])

	.factory('alertDialog', ['$dialog', function($dialog) {

		var opts = {
			backdrop: true,
			keyboard: true,
			backdropClick: true,
			backdropFade: true,
			templateUrl: '/templates/dialogs/alert.tpl.html'
		};
		return $dialog.dialog(opts);
	}])

	.factory('goLiveDialog', ['$dialog', function($dialog) {

		var opts = {
			backdrop: true,
			keyboard: true,
			backdropClick: true,
			backdropFade: true,
			templateUrl: '/templates/dialogs/go-live.tpl.html'
		};
		return $dialog.dialog(opts);
	}])

	.factory('historyDialog', ['$dialog', function($dialog) {

		var opts = {
			backdrop: true,
			keyboard: true,
			backdropClick: true,
			backdropFade: true,
			templateUrl: '/templates/dialogs/history.tpl.html'
		};
		return $dialog.dialog(opts);
	}]);
