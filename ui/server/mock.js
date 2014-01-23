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
        url: '/api/1/config/list/:module',
        mock: [{
            arguments: { module: "crafter.studio-ui"},
            path: '/config/list/app/descriptor'
        }, {
            arguments: { module: "crafter.studio-ui.login"},
            path: '/config/list/login/descriptor'
        }]
    }, {
        method: 'get',
        url: '/api/1/config/list/:module/dependencies',
        mock: [{
            arguments: { module: "crafter.studio-ui"},
            path: '/config/list/app/dependencies'
        }, {
            arguments: { module: "crafter.studio-ui.login"},
            path: '/config/list/login/dependencies'
        }]
    }]
};
