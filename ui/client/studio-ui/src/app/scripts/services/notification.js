/* global toastr */
'use strict';

angular.module('crafter.studio-ui.common')

    .factory('NotificationService', ['toastr',
        function(toastr) {
            var queue = [];

            return {
                set: function(message) {
                    var msg = message;
                    queue.push(msg);

                },
                pop: function(message) {
                    if (message.type in toastr && typeof toastr[message.type] === 'function') {
                        toastr[message.type](message.body, message.title);
                    }
                }
            };
        }
    ])

    .factory('toastr', [
        function() {
            // Factory that lets you change toastr's plugin default settings
            toastr.options.timeOut = 3500;
            return toastr;
        }
    ]);
