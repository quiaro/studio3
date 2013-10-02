
angular.module('common')

	// App configuration values
	.constant('I18N', {
		prefix: 'i18n/locale_',
		suffix: '.json'
	})

    .constant('APP_PATHS', {
        dashboard: '/',
        preview: '/preview'
    })

	.constant('REGISTRY', {
		path: '/config/registry.json',
		bridgedEventsKey: 'bridgedEvents'
	})

	.value('Env', {
		siteName: '',
		urlBase: 'api',
		apiVersion: '0.1'
	});
