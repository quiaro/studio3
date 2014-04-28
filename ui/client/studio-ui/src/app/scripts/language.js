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

            // On language change event, save value in session storage
            // and reload the page
            $rootScope.$on('$sdoLanguageChange', function (evt, langId){
                console.log('Event handler for language change with param: ', langId);
                sessionStorage[lang_key] = langId;
                $state.forceReload();
                // window.location.reload();
            });

            return {
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
                }
            };

        } else {
            throw new Error("Browser does not support Session Storage");
        }

    }]);
