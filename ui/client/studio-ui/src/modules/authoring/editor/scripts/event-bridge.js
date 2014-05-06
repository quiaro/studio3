/* global define */
'use strict';

define(['module', 'jquery', 'pubsub'], function (module, $, PubSub) {

    // Get all the events from the registry that we want to pass on
    // from the editor to angular
    var bridgedEvents = module.config().bridged_events,
            frameId = window.frameElement.id,
            AngularRootScope = parent.angular.element('#' + frameId)
                                                .injector().get('$rootScope');

    if(!AngularRootScope) {
        throw new Error ('Angular root scope cannot be reached from the editor');
    }

    bridgedEvents.forEach( function(evt) {
        // For each event registered in the registry, create a listener.
        // When the event is published in the editor, this listener will
        // publish it on angular as well.
        PubSub.subscribe(evt, function(msg, data) {

            // How do we know if the event wasn't coming from Angular? If it was,
            // then we should not broadcast it in Angular, otherwise we'll create
            // an endless loop. We're going to add a property to the message data
            // "cancelBridge" to check if we should pass on an event or not.
            if (!data.cancelBridge) {
                data.cancelBridge = true;
                AngularRootScope.$broadcast(evt, data);
            }
        });
    });

});
