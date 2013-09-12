'use strict';

angular.module('common')
	.factory('historyDialog', function($dialog) {

	var opts = {
		backdrop: true,
		keyboard: true,
		backdropClick: true,
		backdropFade: true,
		templateUrl: 'scripts/common/resources/dialogs/history/history.tpl.html'
	};

	return $dialog.dialog(opts);

});