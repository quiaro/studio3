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
    }])

    .factory('util',
        ['$http', '$q', 'Env', 'REGISTRY', function($http, $q, Env, REGISTRY) {

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

        /*
         * Get the app registry (and cache it if it doesn't exist yet)
         * @return promise : on success it will pass the registry as a JSON object
         */
        function getRegistry () {
            var deferred = $q.defer();

            $http.get(REGISTRY.path)
                .success(function(data) {
                    deferred.resolve(data);
                }).error(function() {
                    // alertDialog.open();
                    deferred.reject(null);
                });
            return deferred.promise;
        }

        // expose the API to the user
        return {
            getServiceURL: getServiceURL,
            getEnvProperty: getEnvProperty,
            setEnvProperty: setEnvProperty,
            getRegistry: getRegistry
        };
    }])

    .factory('AuthenticationService', [ function() {
        var loggedIn = true;

        function logIn () {
            // TODO: replace this with a call to an authentication service in the back-end
            loggedIn = true;
        }

        function isLoggedIn () {
            // TODO: replace this with a call to an authentication service in the back-end
            return loggedIn;
        }

        return {
            logIn: logIn,
            isLoggedIn: isLoggedIn
        };
    }])

    .factory('UserService', [ function() {
        var userRoles;

        function getUserRoles () {
            // TODO: replace this with a call to an authentication service in the back-end
            userRoles = ['author'];
            return userRoles || [];
        }

        return {
            getUserRoles: getUserRoles
        }
    }])

    .factory('UtilsService', [ function() {
        function arrayIntersection (array1, array2) {
            return array1.filter( function (el) {
                return array2.indexOf(el) != -1;
            })
        }

        return {
            arrayIntersection: arrayIntersection
        }
    }])

    .factory('NotificationService', ['toastr', function (toastr) {
        var queue = [];

        return {
          set: function(message) {
            var msg = message;
            queue.push(msg);

          },
          pop: function(message) {
            if (message.type in toastr && typeof toastr[message.type] === 'function') {
              toastr[message.type](message.body, message.title);
            }
          }
        };
    }])

    .factory('toastr', [ function() {
        // Factory that lets you change toastr's plugin default settings
        toastr.options.timeOut = 3500;
        return toastr;
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
