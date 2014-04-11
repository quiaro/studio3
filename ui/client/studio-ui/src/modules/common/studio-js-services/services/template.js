/* global define */

define(function(require) {

    'use strict';

    // module dependencies
    var Item = require('./item');

    var Template = function (utils, overrideObj) {
        // Call the parent constructor
        Item.apply(this, ['Template', utils, overrideObj, '/template']);
    };

    Template.prototype = Object.create(Item.prototype);
    Template.prototype.constructor = Template;

    /*
     * @param template object with all the necessary template properties
     */
    Template.prototype.create = function create (template) {

        var templateProperties = [{
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

        if (template.file) {
            // Create new template from file
            templateProperties.push({
                id: 'file',
                name: 'property: file',
                type: 'file',
                required: true
            });

            return this.createFromFile(template, templateProperties);

        } else {
            // Create new template from inline content
            templateProperties.push({
                id: 'content',
                name: 'property: content',
                type: 'string',
                required: true,
                empty: false
            });

            return this.createFromContent(template, templateProperties);
        }
    };

    /*
     * @param template object with all the necessary template properties
     */
    Template.prototype.update = function update (template) {

        var templateProperties = [{
            id: 'item_id',
            name: 'property: item_id',
            type: 'string',
            required: true,
            empty: false
        }];

        if (template.file) {
            // Update new template from file
            templateProperties.push({
                id: 'file',
                name: 'property: file',
                type: 'file',
                required: true
            });

            return this.updateFromFile(template, templateProperties);

        } else {
            // Update new template from inline content
            templateProperties.push({
                id: 'content',
                name: 'property: content',
                type: 'string',
                required: true,
                empty: false
            });

            return this.updateFromContent(template, templateProperties);
        }
    };

    return Template;

});
