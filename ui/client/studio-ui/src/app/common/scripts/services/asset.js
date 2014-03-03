'use strict';

angular.module('crafter.studio.common')

    // Based on danialfarid's angular-file-upload
    // https://github.com/danialfarid/angular-file-upload
    .service('AssetService',
        ['$http',
         '$rootScope',
         'UtilsService',
         'CONFIG',
         'Env',
        function($http, $rootScope, UtilsService, CONFIG, Env) {

            this.upload = function(config) {

                var serviceDomain = CONFIG.services.domain || '',
                    servicePath = CONFIG.services.asset.upload,
                    formData = new FormData(),
                    promise;

                config.url = serviceDomain + UtilsService.replacePlaceholders(servicePath, { 'site': Env.siteName });
                config.method = config.method || 'POST';
                config.headers = config.headers || {};
                config.headers['Content-Type'] = undefined;
                config.transformRequest = config.transformRequest || $http.defaults.transformRequest;

                if (config.data) {
                    /*jslint forin:false */
                    for (var key in config.data) {
                        var val = config.data[key];

                        if (typeof config.transformRequest === 'function') {
                            val = config.transformRequest(val);
                        } else {
                            for (var i = 0; i < config.transformRequest.length; i++) {
                                var fn = config.transformRequest[i];
                                val = (typeof fn === 'function') ? fn(val) : val;
                            }
                        }
                        formData.append(key, val);
                    }
                    /*jslint forin:true */
                }
                config.transformRequest = angular.identity;
                formData.append(config.fileFormDataName || 'file', config.file, config.file.name);

                /*jslint camelcase:false */
                formData.__setXHR_ = function(xhr) {
                /*jslint camelcase:true */
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

                config.url = serviceDomain + UtilsService.replacePlaceholders(servicePath, { 'site': Env.siteName });
                config.method = 'GET';
                config.headers = config.headers || {};
                config.headers['Content-Type'] = undefined;

                return $http(config);
            };
        }
    ]);