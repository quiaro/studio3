/* global toastr */
'use strict';

angular.module('common', ['ui.bootstrap.dialog'])

    .factory('Widget', ['$templateCache', '$http', '$q', 'CONFIG', function ($templateCache, $http, $q, CONFIG) {

        function getWidgets() {
            var deferred = $q.defer();

            $http.get(CONFIG.dashboard)
                .success(function(data) {
                    var widgets = data &&
                                    data.dashboard &&
                                        data.dashboard.widgets;

                    if (widgets && angular.isArray(widgets) && widgets.length) {
                        deferred.resolve(widgets);
                    } else {
                        deferred.resolve(null);
                    }
                }).error(function() {
                    deferred.reject(null);
                });
            return deferred.promise;
        }

        /*
         * @param widgetProperty widget property pointing to an asset to be loaded asynchronously
         * @param fetchDuplicateAssets whether we should make another request for an asset we have already
         *        requested. Depending on how we want to process the asset after we retrieve it
         *        (via the assetCallback), it may not be necessary to fetch it twice. For example, if we
         *        fetch a template and want to change it before we resolve the promise for it, then
         *        we should make a new request for it each time.
         * @param assetCallback Function called to modify and/or process each individual asset fetched before
         *        its promise is resolved
         * @param resolveCallback Function called after all assets have been resolved
         */
        function getPropertyAssets(widgetProperty, fetchDuplicateAssets, assetCallback, resolveCallback) {
            var deferred = $q.defer(),
                fetchedAssets = {};

            getWidgets().then( function (widgets) {

                var allPromises = [];

                widgets && widgets.forEach( function (widget) {
                    var assetPromise;

                    if (widget[widgetProperty]) {
                        if (fetchDuplicateAssets ||
                            (!fetchDuplicateAssets && typeof fetchedAssets[widget[widgetProperty]] === 'undefined')) {

                            // Set temporary value. Since the call is asynchronous, make known
                            // right away that an asset is being fetched
                            fetchedAssets[widget[widgetProperty]] = true;
                            assetPromise = $q.defer();

                            // TODO: add an interceptor for error handling and recovery
                            $http.get(widget[widgetProperty], { cache: $templateCache })
                                .success( function (asset) {
                                    var resolveValue = asset;

                                    if (typeof assetCallback === 'function') {
                                        // process/change the asset
                                        resolveValue = assetCallback(asset, widget);
                                    }
                                    // Replace temporary value with the real asset data
                                    fetchedAssets[widget[widgetProperty]] = resolveValue;
                                    assetPromise.resolve(resolveValue);
                                })
                                .error( function () {
                                    assetPromise.reject(null);
                                });

                            allPromises.push(assetPromise.promise);
                        }
                    } else {
                        throw new Error ("Widget " + widget.name + " is missing property: " + widgetProperty);
                    }
                });

                // All the promises have been resolved
                if (allPromises.length) {
                    $q.all(allPromises)
                        .then( function (allPromises) {
                            var resolveValue = fetchDuplicateAssets ? allPromises : fetchedAssets;
                            deferred.resolve(resolveValue);
                        });
                } else {
                    deferred.resolve(null);
                }
            });
            return deferred.promise;
        }

        function processPrototype (prototypeStr, widget) {
            return eval(prototypeStr);
        }

        /* Substitute references to the template's generic placeholder to make them
         * specific to the widgets' model
         */
        function processTemplate (templateStr, widget) {
            var widgetSpecificTpl,
                widgetModelStr = (widget.name) ? widget.name : "model";

            return templateStr.replace(/model/g, widgetModelStr);
        }

        return {
            getWidgets: getWidgets,
            getPropertyAssets: getPropertyAssets,
            processPrototype: processPrototype,
            processTemplate: processTemplate
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

    .factory('notifications', ['toastr', function (toastr) {
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
