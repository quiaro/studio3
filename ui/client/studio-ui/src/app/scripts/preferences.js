/* global localStorage */
'use strict';

angular.module('crafter.studio-ui.Preferences', [])

    .factory('Preferences', [function() {

        if (localStorage) {
            return {
                set: function set (id, value) {
                    localStorage[id] = value;
                },
                get: function get (id) {
                    return localStorage[id];
                }
            };
        } else {
            throw new Error('Browser does not support Session Storage. Unable to save user preferences');
        }
    }]);
