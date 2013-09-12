
angular.module('resources.vars', [])

	// App configuration values
	.constant('Env', {
		siteName: '',
		urlBase: 'api',
		apiVersion: '0.1'
	})

	.constant('I18N', {
		prefix: 'i18n/locale_',
		suffix: '.json'
	});

