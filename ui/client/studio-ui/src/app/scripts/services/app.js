'use strict';

angular.module('crafter.studio-ui.services.AppService', [])

    .service('AppService', ['$http', '$q', 'Env', '$modal',
        function($http, $q, Env, $modal) {

            /*
             * @param api -API Category
             * @param method -Existing method in the API Category
             * @param searchStr -url search parameters
             * @return URL used to communicate with the backend;
             *         null, if the URL cannot be calculated correctly with the parameters given
             */

            this.getServiceURL = function getServiceURL(api, method, searchStr) {

                var siteName, urlBase, apiVersion, ss;

                siteName = this.getEnvProperty('siteName');
                urlBase = this.getEnvProperty('urlBase');
                apiVersion = this.getEnvProperty('apiVersion');

                if (api && typeof api === 'string' &&
                    method && typeof method === 'string' &&
                    typeof searchStr === 'string') {

                    ss = (!searchStr) ? '' : '?' + searchStr;
                    return '/' + urlBase + '/' + apiVersion + '/' + api + '/' + method + '/' + siteName + ss;
                } else {
                    throw new Error('Unable to resolve service URL');
                }
            };

            this.getEnvProperty = function getEnvProperty(property) {

                if (Env[property]) {
                    return Env[property];
                } else {
                    throw new ReferenceError('Env.' + property + ' has not been set');
                }
            };

            this.setEnvProperty = function setEnvProperty(property, value) {
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
            };

            this.makeServiceCall = function makeServiceCall(url, deferred) {
                $http.get(url).success(function(data) {
                    deferred.resolve(data);
                }).error(function() {
                    $modal.open({
                        templateUrl: '/templates/dialogs/alert.tpl.html'
                    });
                    deferred.reject(null);
                });
            };
        }
    ]);
