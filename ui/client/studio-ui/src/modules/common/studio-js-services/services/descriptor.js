/* global define */

define(function(require) {

    'use strict';

    // module dependencies
    var Item = require('./item');

    var Descriptor = function (utils) {
        // Call the parent constructor
        Item.apply(this, ['Descriptor', utils, '/descriptor']);
    };

    Descriptor.prototype = Object.create(Item.prototype);
    Descriptor.prototype.constructor = Descriptor;

    /*
     * @param descriptor object with all the necessary descriptor properties
     */
    Descriptor.prototype.create = function create (descriptor) {

        var descriptorProperties = [{
            id: 'content_type_id',
            name: 'property: content_type_id',
            type: 'string',
            required: true
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

        if (descriptor.file) {
            // Create new descriptor from file
            descriptorProperties.push({
                id: 'file',
                name: 'property: file',
                type: 'file',
                required: true
            });

            return this.createFromFile(descriptor, descriptorProperties);

        } else {
            // Create new descriptor from inline content
            descriptorProperties.push({
                id: 'content',
                name: 'property: content',
                type: 'string',
                required: true,
                empty: false
            });

            return this.createFromContent(descriptor, descriptorProperties);
        }
    };

    /*
     * @param descriptor object with all the necessary descriptor properties
     */
    Descriptor.prototype.update = function update (descriptor) {

        var descriptorProperties = [{
            id: 'item_id',
            name: 'property: item_id',
            type: 'string',
            required: true,
            empty: false
        }];

        if (descriptor.file) {
            // Update new descriptor from file
            descriptorProperties.push({
                id: 'file',
                name: 'property: file',
                type: 'file',
                required: true
            });

            return this.updateFromFile(descriptor, descriptorProperties);

        } else {
            // Update new descriptor from inline content
            descriptorProperties.push({
                id: 'content',
                name: 'property: content',
                type: 'string',
                required: true,
                empty: false
            });

            return this.updateFromContent(descriptor, descriptorProperties);
        }
    };

    return Descriptor;

});
