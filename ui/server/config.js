path = require('path');

module.exports = {

    // Files that need to be loaded from the .tmp directory
    tmpFiles: ['studio.css', 'editor.css'],

    // Root directory where temporary app files are placed
    tmpRoot: path.resolve(__dirname, '../.tmp'),

    // Root directory for the client app
    clientRoot: path.resolve(__dirname, '../client'),

    // Sample sites
    mango: {
        // Folder that contains the json files with the mock responses
        // for the mango site -relative to this file
        mockFolder: path.resolve(__dirname, './sites/mango/mocks'),
        sitesFolder: path.resolve(__dirname, './sites/mango/pages')
    }
};
