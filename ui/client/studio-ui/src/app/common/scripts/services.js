/* global toastr */
'use strict';

angular.module('crafter.studio.common')

    .config(function($httpProvider){
        // Avoid problem with CORS
        // http://stackoverflow.com/questions/16661032/http-get-is-not-allowed-by-access-control-allow-origin-but-ajax-is
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
    })

    .factory('audit', ['$http', '$q', 'util', '$modal',
        function($http, $q, util, $modal) {

            var api = 'audit';

            function makeServiceCall(url, deferred) {
                $http.get(url).success(function(data) {
                    deferred.resolve(data);
                }).error(function() {
                    $modal.open({
                        templateUrl: '/templates/dialogs/alert.tpl.html'
                    });
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
        }
    ])

    // TODO: Merge this service into the appService
    .factory('util', ['$http', '$q', 'Env', 'REGISTRY',
        function($http, $q, Env, REGISTRY) {

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

            function getEnvProperty(property) {

                if (Env[property]) {
                    return Env[property];
                } else {
                    throw new ReferenceError('Env.' + property + ' has not been set');
                }
            }

            function setEnvProperty(property, value) {
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

            function getRegistry() {
                var deferred = $q.defer();

                $http.get(REGISTRY.path)
                    .success(function(data) {
                        deferred.resolve(data);
                    }).error(function() {
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
        }
    ])

    .service('utilService',
        function($http, $rootScope, CONFIG) {

            // Takes a string of the form: "the {tree} is behind the {building}" and uses a
            // replace object { 'tree': 'cedar', 'building': 'National Museum'} to replace the
            // placeholders.
            // Throws an error if there are placeholders that don't have a replace value
            this.replacePlaceholders = function(string, replaceObj) {

                function replacePlaceholder(match) {
                    var key = match.substring(1, match.length - 1), // remove '{' and '}' from the match string
                        replaceValue = replaceObj[key];
                    if (replaceValue) {
                        return replaceValue;
                    } else {
                        throw new Error('Placeholder "' + key + '" does not have a replace value in ' + string);
                    }
                }

                return string.replace(/{.*}/g, function(m) { return replacePlaceholder(m); } );
            };
        }
    )

    // Based on danialfarid's angular-file-upload
    // https://github.com/danialfarid/angular-file-upload
    .service('assetService',
        ['$http',
         '$rootScope',
         'utilService',
         'CONFIG',
         'Env',
        function($http, $rootScope, utilService, CONFIG, Env) {

            this.upload = function(config) {

                var serviceDomain = CONFIG.services.domain || '',
                    servicePath = CONFIG.services.asset.upload,
                    formData = new FormData(),
                    promise;

                config.url = serviceDomain + utilService.replacePlaceholders(servicePath, { 'site': Env.siteName });
                config.method = config.method || 'POST';
                config.headers = config.headers || {};
                config.headers['Content-Type'] = undefined;
                config.transformRequest = config.transformRequest || $http.defaults.transformRequest;

                if (config.data) {
                    for (var key in config.data) {
                        var val = config.data[key];

                        if (typeof config.transformRequest === 'function') {
                            val = config.transformRequest(val);
                        } else {
                            for (var i = 0; i < config.transformRequest.length; i++) {
                                var fn = config.transformRequest[i];
                                if (typeof fn === 'function') {
                                    val = fn(val);
                                }
                            }
                        }
                        formData.append(key, val);
                    }
                }
                config.transformRequest = angular.identity;
                formData.append(config.fileFormDataName || 'file', config.file, config.file.name);

                formData.__setXHR_ = function(xhr) {
                    config.__XHR = xhr;
                    xhr.upload.addEventListener('progress', function(e) {
                        if (config.progress) {
                            config.progress(e);
                            if (!$rootScope.$$phase) {
                                $rootScope.$apply();
                            }
                        }
                    }, false);
                    //fix for firefox not firing upload progress end
                    xhr.upload.addEventListener('load', function(e) {
                        if (e.lengthComputable) {
                            config.progress(e);
                            if (!$rootScope.$$phase) {
                                $rootScope.$apply();
                            }
                        }
                    }, false);
                };

                config.data = formData;

                promise = $http(config);

                promise.progress = function(fn) {
                    config.progress = fn;
                    return promise;
                };

                promise.abort = function() {
                    if (config.__XHR) {
                        config.__XHR.abort();
                    }
                    return promise;
                };
                promise.then = (function(promise, origThen) {
                    return function(s, e, p) {
                        config.progress = p || config.progress;
                        origThen.apply(promise, [s, e, p]);
                        return promise;
                    };
                })(promise, promise.then);

                return promise;
            };

            this.read = function (config) {

                var serviceDomain = CONFIG.services.domain || '',
                    servicePath = CONFIG.services.asset.read;

                config.url = serviceDomain + utilService.replacePlaceholders(servicePath, { 'site': Env.siteName });
                config.method = 'GET';
                config.headers = config.headers || {};
                config.headers['Content-Type'] = undefined;

                return $http(config);
            };
        }
    ])

    .factory('AuthenticationService', [
        function() {
            var loggedIn = false;

            function logIn() {
                // TODO: replace this with a call to an authentication service in the back-end
                loggedIn = true;
            }

            function isLoggedIn() {
                // TODO: replace this with a call to an authentication service in the back-end
                return loggedIn;
            }

            return {
                logIn: logIn,
                isLoggedIn: isLoggedIn
            };
        }
    ])

    .factory('UserService', [
        function() {
            var userRoles;

            function getUserRoles() {
                // TODO: replace this with a call to an authentication service in the back-end
                userRoles = ['author', 'editor'];
                return userRoles || [];
            }

            return {
                getUserRoles: getUserRoles
            };
        }
    ])

    .factory('UtilsService', [
        function() {
            function arrayIntersection(array1, array2) {
                return array1.filter(function(el) {
                    return array2.indexOf(el) !== -1;
                });
            }

            return {
                arrayIntersection: arrayIntersection
            };
        }
    ])

    .factory('NotificationService', ['toastr',
        function(toastr) {
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
        }
    ])

    .factory('toastr', [
        function() {
            // Factory that lets you change toastr's plugin default settings
            toastr.options.timeOut = 3500;
            return toastr;
        }
    ])

    .factory('goLiveDialog', ['$dialog',
        function($dialog) {

            var opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                backdropFade: true,
                templateUrl: '/templates/dialogs/go-live.tpl.html'
            };
            return $dialog.dialog(opts);
        }
    ])

    .factory('historyDialog', ['$dialog',
        function($dialog) {

            var opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                backdropFade: true,
                templateUrl: '/templates/dialogs/history.tpl.html'
            };
            return $dialog.dialog(opts);
        }
    ])

    .factory('WidgetService',
        ['$templateCache', '$http', '$q', 'CONFIG',
        function ($templateCache, $http, $q, CONFIG) {

        function create (prototypeObj) {
            var newWidget = Object.create(prototypeObj);

            // Because we want to keep widgets independent of each other, we'll copy
            // any properties that are not functions (i.e. data properties) from the prototype object
            // into the widget's own model; otherwise, the widgets will rely on the prototype's
            // model and share data (... and widgets will start acting strange!)
            Object.keys(prototypeObj).forEach( function(modelKey) {
                if (typeof prototypeObj[modelKey] !== 'function') {
                    newWidget[modelKey] = angular.copy(prototypeObj[modelKey]);
                }
            });
            return newWidget;
        }

        function getWidgets(section) {
            var deferred = $q.defer();

            $http.get(CONFIG[section])
                .success(function(data) {
                    var widgets = data &&
                                    data.dashboard &&
                                        data.dashboard.widgets;

                    if (widgets && angular.isArray(widgets) && widgets.length) {
                        deferred.resolve(widgets);
                    } else {
                        deferred.resolve([]);
                    }
                }).error(function() {
                    deferred.reject(null);
                });
            return deferred.promise;
        }

        /*
         * @param section app section for which widgets are being loaded
         * @param widgetProperty widget property pointing to an asset to be loaded asynchronously
         * @param fetchDuplicateAssets whether we should make another request for an asset we have already
         *        requested. Depending on how we want to process the asset after we retrieve it
         *        (via the assetCallback), it may not be necessary to fetch it twice. For example, if we
         *        fetch a template and want to change it before we resolve the promise for it, then
         *        we should make a new request for it each time.
         * @param assetCallback Function called to modify and/or process each individual asset fetched before
         *        its promise is resolved
         */
        function getPropertyAssets(section, widgetProperty, fetchDuplicateAssets, assetCallback) {
            var deferred = $q.defer(),
                fetchedAssets = {};

            getWidgets(section).then( function (widgets) {

                var allPromises = [];

                widgets.forEach( function (widget) {
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
                        throw new Error ('Widget ' + widget.name + ' is missing property: ' + widgetProperty);
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
            /*jshint evil:true */
            return eval(prototypeStr);
        }

        /* Substitute references to the template's generic placeholder to make them
         * specific to the widgets' model
         */
        function processTemplate (templateStr, widget) {
            var widgetModelStr = (widget.name) ? CONFIG.widgets.namespace + '.' + widget.name :
                                                    CONFIG.widgets.tplPlaceholder,
                reWidgetPlaceholder = new RegExp(CONFIG.widgets.tplPlaceholder, 'g');

            return templateStr.replace(reWidgetPlaceholder, widgetModelStr);
        }

        // Method known to be asynchronous by widgets
        function getAsyncMethodName () {
            return CONFIG.widgets.asyncMethodName;
        }

        function getNamespace() {
            return CONFIG.widgets.namespace;
        }

        return {
            create: create,
            getWidgets: getWidgets,
            getPropertyAssets: getPropertyAssets,
            processPrototype: processPrototype,
            processTemplate: processTemplate,
            getAsyncMethodName : getAsyncMethodName,
            getNamespace: getNamespace
        };
    }]);
