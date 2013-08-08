'use strict';

angular.module('history.dialog', ['ui.bootstrap.dialog'])
	.factory('historyDialog', function($dialog) {

	var opts = {
		backdrop: true,
		keyboard: true,
		backdropClick: true,
		backdropFade: true,
		templateUrl: 'scripts/dialogs/history/history.tpl.html',
		controller: 'DialogsCtrl'
	};

	return $dialog.dialog(opts);

});