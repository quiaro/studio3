
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

    // TODO: Remove this constant in favor of CONFIG
	.constant('REGISTRY', {
		path: '/config/registry.json',
		bridgedEventsKey: 'bridgedEvents'
	})

    .constant('CONFIG', {
        dashboard: '/config/dashboard.json',
        registry: '/config/registry.json'
    })

	.value('Env', {
		siteName: '',
		urlBase: 'api',
		apiVersion: '0.1'
	});
