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

        var cfg = customConfig || {},
            utils = new Utils(cfg),
            asset = new Asset(utils, cfg.Asset),
            config = new Config(utils, cfg.Config),
            descriptor = new Descriptor(utils, cfg.Descriptor),
            template = new Template(utils, cfg.Template);

        return Object.freeze({
            Asset: asset,
            Config: config,
            Descriptor: descriptor,
            Template: template,
            Utils: utils
        });
    };

});
