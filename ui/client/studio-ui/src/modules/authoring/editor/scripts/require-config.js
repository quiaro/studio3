/* global define */
'use strict';

define(['require'], function(require) {

    // Create a string with all the requirejs config
    // in the context of the editor (different from
    // the context of the app)
    return function (baseUrl, dependencies, config) {

        var configStr = '<script>',
            module_paths = config.module_paths,
            map = config.map,
            bridged_events = config.bridged_events,
            depPath, i, key;

        configStr += 'require.config({';
        configStr += '  baseUrl: "' + baseUrl + '",';
        configStr += '  paths: {';

        for (i in dependencies) {
            depPath = module_paths[dependencies[i]];
            if (depPath) {
                configStr += '"' + dependencies[i] + '": "' + depPath + '",';
            } else {
                throw new Error('No path mapping found for editor dependency: ', dependencies[i]);
            }
        }

        configStr += '  "editor": "' + require.toUrl('..') + '",';
        configStr += '  "jquery-private": "' + require.toUrl('./jquery-private') + '"';
        configStr += '  },';

        configStr += '  config: {';
        configStr += '    "editor/scripts/event-bridge": { "bridged_events": [';

        for (i in bridged_events) {
            if (i !== "0") {
                configStr += ',';
            }
            configStr += '"' + bridged_events[i] + '"';
        }

        configStr += '    ]}';
        configStr += '  },';

        configStr += '  shim: { "ckeditor" : { exports: "ckeditor" } },';
        configStr += '  map: {';
        configStr += '    "*": {';

        for (key in map) {
            configStr += '"' + key + '": "' + map[key] + '",';
        }

        configStr += '  "jquery": "jquery-private"'
        configStr += '  },';

        // 'jquery-private' wants the real jQuery module.
        // If this line was not here, there would
        // be an unresolvable cyclic dependency.
        configStr += '    "jquery-private": { "jquery": "jquery" }';
        configStr += '  }';
        configStr += '});';
        configStr += '</script>';

        return configStr;

    };

});
