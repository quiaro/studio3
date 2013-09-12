'use strict';

angular.module('common')
	.factory('goLiveDialog', function($dialog) {

	var opts = {
		backdrop: true,
		keyboard: true,
		backdropClick: true,
		backdropFade: true,
		templateUrl: 'scripts/common/resources/dialogs/go-live/go-live.tpl.html',
	};

	return $dialog.dialog(opts);

});