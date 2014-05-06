/* global define */
'use strict';

define(['require'], function(require) {

    // Create a string with all the requirejs config
    // in the context of the editor (different from
    // the context of the app)
    return function (globalCfg, editorCfg) {

        var configObj = {
            baseUrl: globalCfg.base_url,
            paths: globalCfg.requirejs.module_paths,
            shim: {
                ckeditor: {
                    exports: 'ckeditor'
                }
            },
            map: {
                '*': globalCfg.requirejs.map
            }

        };

        // Add path mappings to modules specific to the editor
        configObj.paths['editor'] = require.toUrl('..');
        configObj.paths['jquery-private'] = require.toUrl('./jquery-private');

        // Expose editor configuration to the editor module
        configObj['config'] = {
            'editor/editor': editorCfg
        };

        // Add aliases used by the editor
        configObj.map['*']['jquery'] = 'jquery-private';
        configObj.map['jquery-private'] = { 'jquery': 'jquery' };

        return '<script>requirejs.config(' + JSON.stringify(configObj) + ')</script>';
    };

});
