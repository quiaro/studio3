/* global define */

define(function (require) {

    'use strict';

    // module dependencies
    var Asset = require('./services/asset'),
        Config = require('./services/config'),
        Descriptor = require('./services/descriptor'),
        Template = require('./services/template'),
        Utils = require('./utils');

    return function(customConfig) {

        var utils = new Utils(customConfig),
            asset = new Asset(utils),
            config = new Config(utils),
            descriptor = new Descriptor(utils),
            template = new Template(utils);

        return Object.freeze({
            Asset: asset,
            Config: config,
            Descriptor: descriptor,
            Template: template,
            Utils: utils
        });
    };

});
