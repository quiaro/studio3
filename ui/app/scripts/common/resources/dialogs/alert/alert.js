'use strict';

angular.module('alert.dialog', ['ui.bootstrap.dialog'])
	.factory('alertDialog', function($dialog) {

	var opts = {
		backdrop: true,
		keyboard: true,
		backdropClick: true,
		backdropFade: true,
		templateUrl: 'scripts/dialogs/alert/alert.tpl.html'
	};

	return $dialog.dialog(opts);

});