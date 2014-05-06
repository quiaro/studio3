/* global define */
'use strict';

define(['pubsub'], function (pubsub) {

    return function (eventList) {
        // Get all the events from the registry that we want to pass on
        // from the editor to angular
        var ngAuthoringScope = parent.angular.element('#' + window.frameElement.id).scope();

        if(!ngAuthoringScope) {
            throw new Error ('Angular root scope cannot be reached from the editor');
        }

        eventList.forEach( function(evt) {
            // For each registered event, create a listener.
            // When the event is published in the editor, this listener will
            // publish it on angular as well.
            pubsub.subscribe(evt, function(msg, data) {

                // How do we know if the event wasn't coming from Angular? If it was,
                // then we should not broadcast it in Angular, otherwise we'll create
                // an endless loop. We're going to add a property to the message data
                // "cancelBridge" to check if we should pass on an event or not.
                if (!data.cancelBridge) {
                    data.cancelBridge = true;
                    ngAuthoringScope.$broadcast(evt, data);
                }
            });
        });
    };

});
