
angular.module('common')

	// App configuration values
	.constant('I18N', {
		prefix: 'i18n/locale_',
		suffix: '.json'
	})

	.value('Env', {
		siteName: '',
		urlBase: 'api',
		apiVersion: '0.1'
	});
