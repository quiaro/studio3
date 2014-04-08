/* global define, DEBUG */

define(function(require) {

    'use strict';

    // module dependencies
    var requestAgent = require('request_agent'),
        validation = require('../validation');

    var Item = function (name, utils, path) {
        this.name = name;
        this.utils = utils;
        this.baseUrl = utils.getBaseUrl() + path;

        if (DEBUG) {
            this.utils.logService({
                name: this.name,
                url: this.baseUrl
            });
        }
    };

    /*
     * @param item object with all the necessary item properties to be created
     * @param itemPropValidationList array with all the property validation definitions
     */
    Item.prototype.createFromContent = function createFromContent ( item, itemPropValidationList ) {
        var siteName = this.utils.getSite(),
            params,
            serviceUrl = this.baseUrl + '/create/' + siteName,
            promise;

        params = [{
            name: 'site name',
            value: siteName,
            type: 'string',
            required: true,
            empty: false
        }, {
            name: 'item',
            value: item,
            type: 'object',
            required: true,
            properties: itemPropValidationList
        }];

        validation.validateParams(params);

        promise = requestAgent.post(serviceUrl, item);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.create',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param item object with all the necessary item properties to be created
     * @param itemPropValidationList array with all the property validation definitions
     */
    Item.prototype.createFromFile = function createFromFile ( item, itemPropValidationList ) {
        var siteName = this.utils.getSite(),
            formData = new FormData(),
            params,
            serviceUrl = this.baseUrl + '/create/' + siteName,
            promise;

        params = [{
            name: 'site name',
            value: siteName,
            type: 'string',
            required: true,
            empty: false
        }, {
            name: 'item',
            value: item,
            type: 'object',
            required: true,
            properties: itemPropValidationList
        }];

        validation.validateParams(params);

        itemPropValidationList.forEach( function(propertyObj) {

            var propId = propertyObj.id;

            if (propertyObj.type === 'file') {
                formData.append(propId, item[propId], item[propId].name);
            } else {
                formData.append(propId, item[propId]);
            }
        });

        promise = requestAgent.ajax({
            contentType: false,
            data: formData,
            processData: false,
            type: 'POST',
            url: serviceUrl
        });

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.create',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param itemId id of the asset to delete
     */
    Item.prototype.delete = function deleteFn (itemId) {
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

        serviceUrl = this.baseUrl + '/delete/' + siteName;
        promise = requestAgent.post(serviceUrl, { item_id: itemId });

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.delete',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param obj object with all the necessary item properties
     */
    Item.prototype.duplicate = function duplicate (obj) {
        var siteName = this.utils.getSite(),
            params,
            objProperties,
            serviceUrl,
            promise;

        objProperties = [{
            id: 'item_id',
            name: 'property: item_id',
            type: 'string',
            required: true,
            empty: false
        }, {
            id: 'parent_id',
            name: 'property: parent_id',
            type: 'string',
            required: true
        }, {
            id: 'file_name',
            name: 'property: file_name',
            type: 'string',
            required: true,
            empty: false
        }];

        params = [{
            name: 'site name',
            value: siteName,
            type: 'string',
            required: true,
            empty: false
        }, {
            name: 'obj',
            value: obj,
            type: 'object',
            required: true,
            properties: objProperties
        }];

        validation.validateParams(params);

        serviceUrl = this.baseUrl + '/duplicate/' + siteName;
        promise = requestAgent.post(serviceUrl, obj);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.duplicate',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param queryObj object with the query information
     * @return TO-DO
     */
    Item.prototype.find = function find (queryObj) {
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
            name: 'query object',
            value: queryObj,
            type: 'object',
            required: true
        }]);

        serviceUrl = this.baseUrl + '/find/' + siteName + '?' + requestAgent.param(queryObj);
        promise = requestAgent.get(serviceUrl);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.find',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param obj object with all the necessary item properties
     */
    Item.prototype.move = function move (obj) {
        var siteName = this.utils.getSite(),
            params,
            objProperties,
            serviceUrl,
            promise;

        objProperties = [{
            id: 'item_id',
            name: 'property: item_id',
            type: 'string',
            required: true,
            empty: false
        }, {
            id: 'parent_id',
            name: 'property: parent_id',
            type: 'string',
            required: true
        }, {
            id: 'file_name',
            name: 'property: file_name',
            type: 'string',
            required: true,
            empty: false
        }];

        params = [{
            name: 'site name',
            value: siteName,
            type: 'string',
            required: true,
            empty: false
        }, {
            name: 'obj',
            value: obj,
            type: 'object',
            required: true,
            properties: objProperties
        }];

        validation.validateParams(params);

        serviceUrl = this.baseUrl + '/move/' + siteName;
        promise = requestAgent.post(serviceUrl, obj);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.move',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param itemId id of the item to read
     * @return itemMetadata metadata of the item
     */
    Item.prototype.read = function read (itemId) {
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

        serviceUrl = this.baseUrl + '/read/' + siteName + '?item_id=' + itemId;
        promise = requestAgent.getJSON(serviceUrl);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.read',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param itemId id of the item to read
     * @return text value of the item
     */
    Item.prototype.readText = function readText (itemId) {
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

        serviceUrl = this.baseUrl + '/read_text/' + siteName + '?item_id=' + itemId;
        promise = requestAgent.get(serviceUrl);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.readText',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param itemId id of the item to read
     * @return text value of the item
     */
    Item.prototype.list = function list (itemId) {
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
            type: 'string'
        }]);

        serviceUrl = this.baseUrl + '/list/' + siteName;
        promise = requestAgent.get(serviceUrl);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.list',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param item object with all the necessary item properties
     * @param itemPropValidationList array with all the property validation definitions
     */
    Item.prototype.updateFromContent = function updateFromContent ( item, itemPropValidationList ) {
        var siteName = this.utils.getSite(),
            params,
            serviceUrl = this.baseUrl + '/update/' + siteName,
            promise;

        params = [{
            name: 'site name',
            value: siteName,
            type: 'string',
            required: true,
            empty: false
        }, {
            name: 'item',
            value: item,
            type: 'object',
            required: true,
            properties: itemPropValidationList
        }];

        validation.validateParams(params);

        promise = requestAgent.post(serviceUrl, item);

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.update',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    /*
     * @param item object with all the necessary item properties
     * @param itemPropValidationList array with all the property validation definitions
     */
    Item.prototype.updateFromFile = function updateFromFile ( item, itemPropValidationList ) {
        var siteName = this.utils.getSite(),
            formData = new FormData(),
            params,
            serviceUrl = this.baseUrl + '/update/' + siteName,
            promise;

        params = [{
            name: 'site name',
            value: siteName,
            type: 'string',
            required: true,
            empty: false
        }, {
            name: 'item',
            value: item,
            type: 'object',
            required: true,
            properties: itemPropValidationList
        }];

        validation.validateParams(params);

        itemPropValidationList.forEach( function(propertyObj) {

            var propId = propertyObj.id;

            if (propertyObj.type === 'file') {
                formData.append(propId, item[propId], item[propId].name);
            } else {
                formData.append(propId, item[propId]);
            }
        });

        promise = requestAgent.ajax({
            contentType: false,
            data: formData,
            processData: false,
            type: 'POST',
            url: serviceUrl
        });

        if (DEBUG) {
            this.utils.logMethod({
                name: this.name + '.update',
                url: serviceUrl,
                promise: promise
            });
        }

        return promise;
    };

    return Item;

});
