'use strict';

angular.module('go-live.dialog', ['ui.bootstrap.dialog'])
	.factory('goLiveDialog', function($dialog) {

	var opts = {
		backdrop: true,
		keyboard: true,
		backdropClick: true,
		backdropFade: true,
		templateUrl: 'scripts/dialogs/go-live/go-live.tpl.html',
		controller: 'DialogsCtrl'
	};

	return $dialog.dialog(opts);

});