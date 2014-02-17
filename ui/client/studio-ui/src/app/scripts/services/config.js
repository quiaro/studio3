'use strict';

angular.module('crafter.studio-ui.services.ConfigService', [])

    .constant('module_config_URL', 'http://localhost:9000/api/1/config/list')

    .constant('plugin_config_URL', 'http://localhost:9000/api/1/config/plugins')

    .factory('ConfigService', ['$http', 'module_config_URL', 'plugin_config_URL',
        function($http, module_config_URL, plugin_config_URL) {

            return {
                loadConfiguration: function(moduleName) {
                    return $http({ method: 'GET', url: module_config_URL + '/' + moduleName });
                },
                getPlugins: function(containerName) {
                    return $http({ method: 'GET', url: plugin_config_URL + '/' + containerName });
                }
            };
        }
    ]);
