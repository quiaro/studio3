/* global define, DEBUG */

define(function(require){

    'use strict';

    // module dependencies
    var requestAgent = require('request_agent'),
        validation = require('../validation');

    var module = function (utils) {
        this.name = 'Config';
        this.utils = utils;
        this.baseUrl = utils.getBaseUrl() + '/config';

        if (DEBUG) {
            this.utils.logService({
                name: this.name,
                url: this.baseUrl
            });
        }
    };

    module.prototype.getDescriptor = function getDescriptor (moduleName) {
        var serviceUrl, promise;

        validation.validateParams([{
            name: 'module name',
            value: moduleName,
            type: 'string',
            required: true,
            empty: false
        }]);

        serviceUrl = this.baseUrl + '/list/' + moduleName;
        promise = requestAgent.getJSON(serviceUrl);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.getDescriptor',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    module.prototype.getPlugins = function getPlugins (containerName) {
        var serviceUrl, promise;

        validation.validateParams([{
            name: 'container name',
            value: containerName,
            type: 'string',
            required: true,
            empty: false
        }]);

        serviceUrl = this.baseUrl + '/plugins/' + containerName;
        promise = requestAgent.getJSON(serviceUrl);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.getPlugins',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    return module;

});
