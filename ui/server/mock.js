path = require('path');

module.exports = {
    services: [{
        method: 'get',
        url: '/api/1/audit/activity/:site',
        mock: [{
            arguments: {},
            path: '/audit/activity/noArguments'
        }, {
            arguments: {
                argumentA: "false"
            },
            path: '/audit/activity/argumentAfalse'
        }]
    }, {
        method: 'get',
        url: '/api/1/content/asset/list/:site',
        mock: [{
            arguments: {},
            path: '/repo/list/assets'
        }, {
            arguments: {
                item_id: "folder-f8ab"
            },
            path: '/repo/list/assets/f8ab'
        }, {
            arguments: {
                item_id: "folder-f5c8"
            },
            path: '/repo/list/assets/f5c8'
        }, {
            arguments: {
                item_id: "folder-4d29"
            },
            path: '/repo/list/assets/4d29'
        }]
    }, {
        method: 'get',
        url: '/api/1/descriptor/list/:site',
        mock: [{
            arguments: {},
            path: '/repo/list/descriptors'
        }]
    }, {
        method: 'get',
        url: '/api/1/template/list/:site',
        mock: [{
            arguments: {},
            path: '/repo/list/templates'
        }]
    }, {
        method: 'get',
        url: '/api/1/config/list/:module',
        mock: [{
            arguments: { module: "studio-ui"},
            path: '/config/list/studio-ui'
        }, {
            arguments: { module: "login"},
            path: '/config/list/login'
        }, {
            arguments: { module: "dashboard"},
            path: '/config/list/dashboard'
        }, {
            arguments: { module: "test-service"},
            path: '/config/list/test-service'
        }, {
            arguments: { module: "almond"},
            path: '/config/list/almond'
        }, {
            arguments: { module: "recent"},
            path: '/config/list/recent'
        }, {
            arguments: { module: "tree-navigation"},
            path: '/config/list/tree-navigation'
        }, {
            arguments: { module: "authoring"},
            path: '/config/list/authoring'
        }]
    }, {
        method: 'get',
        url: '/api/1/config/plugins/:container',
        mock: [{
            arguments: { container: "activity"},
            path: '/config/plugins/activity/plugins'
        }]
    }]
};
