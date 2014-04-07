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
        url: '/api/1/content/asset/tree/:site',
        mock: [{
            arguments: {},
            path: '/repo/tree/assets'
        }]
    }, {
        method: 'get',
        url: '/api/1/descriptor/tree/:site',
        mock: [{
            arguments: {},
            path: '/repo/tree/descriptors'
        }]
    }, {
        method: 'get',
        url: '/api/1/template/tree/:site',
        mock: [{
            arguments: {},
            path: '/repo/tree/templates'
        }]
    }, {
        method: 'get',
        url: '/api/1/config/list/:module',
        mock: [{
            arguments: { module: "crafter.studio-ui"},
            path: '/config/list/app/descriptor'
        }, {
            arguments: { module: "crafter.studio-ui.section.login"},
            path: '/config/list/login/descriptor'
        }, {
            arguments: { module: "crafter.studio-ui.section.dashboard"},
            path: '/config/list/dashboard/descriptor'
        }, {
            arguments: { module: "crafter.studio-ui.section.test-service"},
            path: '/config/list/asset-service/descriptor'
        }, {
            arguments: { module: "crafter.studio-ui.plugins.almond"},
            path: '/config/list/almond/descriptor'
        }, {
            arguments: { module: "crafter.studio-ui.plugins.sdo.activity-table.recent"},
            path: '/config/list/recent/descriptor'
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
