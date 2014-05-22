'use strict';

angular.module('crafter.studio-ui.Language', [])

    .factory('Language', ['$http', '$q', '$rootScope', '$log', 'Utils', 'Preferences', 'GLOBALS',
        function($http, $q, $rootScope, $log, Utils, Preferences, GLOBALS) {

        // session storage language key
        var lang_key = 'studio_ui_lang';

        if (!Preferences.get(lang_key)) {
            // If no language has been set, set it to the default
            Preferences.set(lang_key, GLOBALS.default_language);
        }

        return {
            // Define where the language files will be read from
            from: function from (baseUrl) {
                var dfd = $q.defer(),
                    fileName = Preferences.get(lang_key) + '.json',
                    langFile = Utils.getUrl(baseUrl, fileName);

                    $log.log('Loading language file: ', langFile);

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

                    fileName = GLOBALS.default_language + '.json';
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

                if (langId !== Preferences.get(lang_key)) {
                    // Save the new language value
                    // and broadcast event to all listeners
                    Preferences.set(lang_key, langId);
                    $rootScope.$broadcast('$sdoChangeLanguage', langId);
                }
            }
        };

    }]);
