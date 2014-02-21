path = require('path');

module.exports = {

    // Root directory where temporary app files are placed
    tmpRoot: path.resolve(__dirname, '../../dev'),

    // Root directory for the client app
    clientRoot: path.resolve(__dirname, '../../client'),

    app: {
        // Folder that contains the json files with the mock responses
        // for the services not specific to a particular site, but to
        // the whole app -relative to this file
        mockFolder: path.resolve(__dirname, '../app/mocks'),
        modulesFolder: path.resolve(__dirname, '../app/modules'),
        pluginsFolder: path.resolve(__dirname, '../app/plugins'),
    }

};
