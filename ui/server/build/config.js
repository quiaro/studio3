path = require('path');

module.exports = {

    // Root directory where temporary app files are placed
    tmpRoot: path.resolve(__dirname, '../../target/META-INF/resources'),

    // Root directory for the client app
    clientRoot: path.resolve(__dirname, '../../target/META-INF/resources'),

    app: {
        // Folder that contains the json files with the mock responses
        // for the services not specific to a particular site, but to
        // the whole app -relative to this file
        mockFolder: path.resolve(__dirname, '../app/mocks'),
        modulesFolder: path.resolve(__dirname, '../../target/META-INF/resources/studio-ui/src/modules'),
        pluginsFolder: path.resolve(__dirname, '../../target/META-INF/resources/studio-ui/src/plugins'),
    }

};
