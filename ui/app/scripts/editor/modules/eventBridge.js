/* global define */
'use strict';

define(['config', 'jquery', 'pubsub'], function (cfg, $, PubSub) {

	var registryPromise;

	// getRegistry may eventually be moved to a util module or common library
	function getRegistry() {
		var deferred = $.Deferred();

		$.getJSON(cfg.REGISTRY.path)
			.done(function(data) {
				deferred.resolve(data);
			}).error(function() {
				deferred.reject(null);
			});
		return deferred.promise();
	}

	registryPromise = getRegistry();

	registryPromise.done(function (registryObj) {

		// Get all the events from the registry that we want to pass on
		// from the editor to angular
		var bridgedEvents = registryObj[cfg.REGISTRY.bridgedEventsKey],
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

					// console.log("--- Publishing in Angular ---");
					// console.log("Event: ", evt);
					// console.log("Data: ", data);

					AngularRootScope.$broadcast(evt, data);
				}
			});
		});
	});

});