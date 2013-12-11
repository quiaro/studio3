/* global toastr */
'use strict';

angular.module('crafter.studio.common')

    .config(function($httpProvider){
        // Avoid problem with CORS
        // http://stackoverflow.com/questions/16661032/http-get-is-not-allowed-by-access-control-allow-origin-but-ajax-is
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
    })

    .factory('audit', ['$http', '$q', 'util', 'alertDialog',
        function($http, $q, util, alertDialog) {

            var api = 'audit';

            function makeServiceCall(url, deferred) {
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
                        return replaceValue
                    } else {
                        throw new Error('Placeholder "' + key + '" does not have a replace value in ' + string);
                    }
                }

                return string.replace(/{.*}/g, function(m) { return replacePlaceholder(m) } );
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

                        if (typeof config.transformRequest == 'function') {
                            val = config.transformRequest(val);
                        } else {
                            for (var i = 0; i < config.transformRequest.length; i++) {
                                var fn = config.transformRequest[i];
                                if (typeof fn == 'function') {
                                    val = fn(val);
                                }
                            }
                        }
                        formData.append(key, val);
                    }
                }
                config.transformRequest = angular.identity;
                formData.append(config.fileFormDataName || 'file', config.file, config.file.name);

                formData['__setXHR_'] = function(xhr) {
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

    .factory('alertDialog', ['$dialog',
        function($dialog) {

            var opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                backdropFade: true,
                templateUrl: '/templates/dialogs/alert.tpl.html'
            };
            return $dialog.dialog(opts);
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
    ]);
