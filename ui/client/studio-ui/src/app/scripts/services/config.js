'use strict';

angular.module('crafter.studio-ui.services.ConfigService', [])

    .constant('config_service_URL', 'http://localhost:9000/api/1/config/list')

    .factory('ConfigService', ['config_service_URL', function(config_service_URL) {

            return {
                loadConfiguration: function(moduleName) {
                    return $.getJSON(config_service_URL + '/' + moduleName);
                }
            };
        }
    ]);
