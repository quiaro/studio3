/* global define, DEBUG */

define(function(require){

    'use strict';

    // module dependencies
    var requestAgent = require('request_agent'),
        validation = require('../validation');

    var Audit = function (utils, overrideObj) {
        this.name = 'Audit';
        this.utils = utils;
        this.baseUrl = utils.getBaseUrl(overrideObj) + '/audit';

        if (DEBUG) {
            this.utils.logService({
                name: this.name,
                url: this.baseUrl
            });
        }
    };

    Audit.prototype.getActivity = function getActivity (filterOpts) {
        var siteName = this.utils.getSite(),
            serviceUrl,
            promise;

        validation.validateParams([{
            name: 'site name',
            value: siteName,
            type: 'string',
            required: true,
            empty: false
        }, {
            name: 'filter options',
            value: filterOpts,
            type: 'object'
        }]);

        serviceUrl = this.baseUrl + '/activity/' + siteName + '?' + requestAgent.param(filterOpts);
        promise = requestAgent.getJSON(serviceUrl);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.getActivity',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    return Audit;

});
