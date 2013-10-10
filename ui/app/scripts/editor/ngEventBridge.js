'use strict';

angular.module('ngEventBridge', ['common'])

	.value('editorId', 'editor-iframe')

	// The name of the PubSub module comes from the require config in editor/main.js
	.value('PubSubModule', 'pubsub')

	.directive('editorIframe',
		['eventBridge', '$rootScope', 'editorId', function(eventBridge, $rootScope, editorId) {
		return {
			restrict: 'E',
			replace: true,
			template: '<iframe id="' + editorId + '">'
		};
	}])

	.factory('eventBridge',
		['$rootScope', 'util', 'editorId', 'PubSubModule', 'REGISTRY',
			function($rootScope, util, editorId, PubSubModule, REGISTRY) {

		var registryPromise = util.getRegistry();

		registryPromise.then(function (registryObj) {

			// Get all the events from the registry that we want to pass on
			// from angular to the editor
			var bridgedEvents = registryObj[REGISTRY.bridgedEventsKey],
					iframeCache, requireFuncCache;

			function getIframe() {
				iframeCache = iframeCache || angular.element('#' + editorId);
				return iframeCache;
			}

			function getRequireFunc(iframe) {
				if (!requireFuncCache && iframe.length) {
					requireFuncCache = iframe[0].contentWindow.require;
				}
				return requireFuncCache;
			}

			bridgedEvents.forEach( function(evt) {
				// For each event registered in the registry, create a listener.
				// When the event is published in angular, this listener will
				// publish it on the editor as well (using the PubSub module).
				$rootScope.$on(evt, function(event, data) {
					var iframe, requireFunc;

					// We're going to add a property to the message data "cancelBridge"
					// to check if we should pass on an event or not and avoid creating
					// an endless loop
					if (!data.cancelBridge) {
						data.cancelBridge = true;

						iframe = getIframe();
						requireFunc = getRequireFunc(iframe);

						requireFunc([PubSubModule], function (EditorPubSub) {
							EditorPubSub.publish(evt, data);
						});
					}
				});
			});
		});

	}]);
