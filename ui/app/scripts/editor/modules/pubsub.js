/* global define */
'use strict';

define(['pubsub'], function (PubSub) {

	var frameId = window.frameElement.id,
			AngularPubSub = parent.angular.element('#' + frameId).injector().get('editorService');

	function publish(evt, data) {
		// Publish the event within the editor
		PubSub.publish(evt, data);

		// Also publish the event in the angular app
		if (AngularPubSub) {
			AngularPubSub.publish(evt, data, true);
		}
	}

  return {
		publish: publish,
		subscribe : PubSub.subscribe
  };
});