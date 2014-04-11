/* global define, DEBUG */

define(function(require) {

    'use strict';

    // module dependencies
    var requestAgent = require('request_agent'),
        validation = require('../validation'),
        Item = require('./item');

    var Asset = function (utils, overrideObj) {
        // Call the parent constructor
        Item.apply(this, ['Asset', utils, overrideObj, '/content/asset']);
    };

    Asset.prototype = Object.create(Item.prototype);
    Asset.prototype.constructor = Asset;

    /*
     * @param asset object with all the necessary asset properties
     */
    Asset.prototype.create = function create (asset) {

        var assetProperties = [{
                id: 'parent_id',
                name: 'property: parent_id',
                type: 'string',
                required: true
            }, {
                id: 'file_name',
                name: 'property: file_name',
                type: 'string',
                required: true
            }, {
                id: 'file',
                name: 'property: file',
                type: 'file',
                required: true
            }, {
                id: 'mime_type',
                name: 'property: mime_type',
                type: 'string',
                required: true
            }];

        return this.createFromFile(asset, assetProperties);
    };

    /*
     * @param itemId id of the asset for which contents are being retrieved
     * @return contents of the asset (content type varies)
     */
    Asset.prototype.getContent = function getContent (itemId) {
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
            name: 'itemId',
            value: itemId,
            type: 'string',
            required: true,
            empty: false
        }]);

        serviceUrl = this.baseUrl + '/get_content/' + siteName + '?item_id=' + itemId;
        promise = requestAgent.ajax(serviceUrl);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.getContent',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param asset object with all the necessary asset properties
     */
    Asset.prototype.update = function update (asset) {

        var assetProperties = [{
                id: 'item_id',
                name: 'property: item_id',
                type: 'string',
                required: true
            }, {
                id: 'file',
                name: 'property: file',
                type: 'file',
                required: true
            }];

        return this.updateFromFile(asset, assetProperties);
    };

    return Asset;

});
