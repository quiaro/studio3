
angular.module('common')

	// App configuration values
	.constant('I18N', {
		prefix: 'studio-ui/i18n/locale_',
		suffix: '.json'
	})

    .constant('APP_PATHS', {
        dashboard: '/',
        preview: '/preview'
    })

    // TODO: Remove this constant in favor of CONFIG
	.constant('REGISTRY', {
		path: 'studio-ui/config/registry.json',
		bridgedEventsKey: 'bridgedEvents'
	})

    .constant('CONFIG', {
        dashboard: 'studio-ui/config/dashboard.json',
        registry: 'studio-ui/config/registry.json',
        widgets: {
            tplPlaceholder: 'widget.name',
            asyncMethodName: 'getAsyncData',
            namespace: 'widgets'
        }
    })

	.value('Env', {
		siteName: 'mango',
		urlBase: 'api',
		apiVersion: '1'
	});
