/* global define */
'use strict';

define(['jquery'], function($) {

    // URL of the config service used to get the descriptor files of all
    // of the app's modules
    var ConfigServiceURL = 'http://localhost:9000/api/1/config/list';

    return {
        getSubmodules: function(moduleName) {
            return $.getJSON(ConfigServiceURL + '/' + moduleName + '/submodules');
        },
        loadConfiguration: function(moduleName) {
            return $.getJSON(ConfigServiceURL + '/' + moduleName);
        }
    };

});