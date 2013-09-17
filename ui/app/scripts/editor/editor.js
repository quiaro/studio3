'use strict';

angular.module('editor', [])

	.directive('editorIframe', ['editorService', function (editorService) {
		return {
			restrict: 'E',
			scope: {},
			replace: true,
			template: '<iframe id=\'editor-iframe\'>'
		};
	}])

	.factory('editorService', ['$rootScope', function($rootScope) {

		var iframeWindow, iframeRequire;

		function publish (evt, data, angularEvent) {
			iframeWindow = iframeWindow || angular.element('#editor-iframe');

			// Publish the event within the angular app
			$rootScope.$broadcast(evt, data);
			if (!angularEvent && iframeWindow.length) {

				// Also publish the events in the editor-iframe (if there is one) 
				if(!iframeRequire) {
					// Cache the require function to speed things up a little for future calls
					iframeRequire = iframeWindow[0].contentWindow.require;
					if (iframeRequire) {
						iframeRequire(['pubsub'], function (EditorPubSub) {
							EditorPubSub.publish(evt, data);
						});
					}
				} else {
					iframeRequire(['pubsub'], function (EditorPubSub) {
						EditorPubSub.publish(evt, data);
					});
				}
			}
		}

		return {
			publish : publish
		};
	}]);