path = require('path');

module.exports = {

    // Files that need to be loaded from the tmp directory (i.e. dev or build)
    tmpFiles: ['studio.css'],

    // Paths from the root (i.e. http://localhost:9000/)
    path: {
        modules: '/studio-ui/modules/*',
        plugins: '/studio-ui/plugins/*',
        sites: '/site/:site/*'
    },

    // Sample sites
    mango: {
        // Folder that contains the json files with the mock responses
        // for the mango site -relative to this file
        mockFolder: path.resolve(__dirname, './sites/mango/mocks'),
        assetsFolder: path.resolve(__dirname, './sites/mango/pages')
    }
};
