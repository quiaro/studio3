'use strict';

angular.module('crafter.studio.common', [])

    .constant('COMMON', {
        baseUrl: '/studio-ui/src/app/common/'
    })

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
        },
        services: {
            domain: "http://localhost:9666",
            asset: {
                upload: "/api/1/content/asset/create/{site}",
                read: "/api/1/content/asset/read/{site}"
            }
        }
    })

	.value('Env', {
		siteName: 'mango',
		urlBase: 'api',
		apiVersion: '1'
	});
