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
            asset = new Asset(utils, customConfig.Asset),
            config = new Config(utils, customConfig.Config),
            descriptor = new Descriptor(utils, customConfig.Descriptor),
            template = new Template(utils, customConfig.Template);

        return Object.freeze({
            Asset: asset,
            Config: config,
            Descriptor: descriptor,
            Template: template,
            Utils: utils
        });
    };

});
