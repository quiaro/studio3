/* global define, sessionStorage */
'use strict';

angular.module('crafter.studio-ui.Language', [])

    .factory('Language', ['$http', '$q', '$rootScope', '$state', '$log', 'Utils', 'GLOBALS',
        function($http, $q, $rootScope, $state, $log, Utils, GLOBALS) {

        // session storage language key
        var lang_key = 'studio_ui_lang';

        if (sessionStorage) {

            if (!sessionStorage[lang_key]) {
                // If no language has been set, set it to the default
                sessionStorage[lang_key] = GLOBALS.default_language;
            }

            return {
                // Define where the language files will be read from
                from: function from (baseUrl) {
                    var dfd = $q.defer(),
                        fileName = sessionStorage[lang_key] + ".js",
                        langFile = Utils.getUrl(baseUrl, fileName);

                        console.log('Lang File: ', langFile);

                    $http({
                        method: 'GET',
                        url: langFile,
                        cache: true,
                        responseType: 'json'
                    }).success(function(data) {
                        dfd.resolve(data);
                    }).error(function() {
                        $log.warn('Language file not found in: ' + langFile);
                        $log.warn('Fallback to default language: ' + GLOBALS.default_language);

                        fileName = GLOBALS.default_language + ".js";
                        langFile = Utils.getUrl(baseUrl, fileName);

                        $http({
                            method: 'GET',
                            url: langFile,
                            cache: true,
                            responseType: 'json'
                        }).success(function(data) {
                            dfd.resolve(data);
                        }).error(function() {
                            $log.error('Language file not found in: ' + langFile);
                            dfd.reject('No appropriate language files found for module');
                        });
                    });

                    return dfd.promise;
                },
                changeTo: function changeTo (langId) {
                    // Save the new language value in session storage
                    // and refresh the view
                    sessionStorage[lang_key] = langId;
                    $state.forceReload();
                }
            };

        } else {
            throw new Error("Browser does not support Session Storage");
        }

    }]);
