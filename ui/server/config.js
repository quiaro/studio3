path = require('path');

module.exports = {
  mango: {
    // Folder that contains the json files with the mock responses
    // for the mango site -relative to this file
    mockFolder: path.resolve(__dirname, './sites/mango/mocks'),
    sitesFolder: path.resolve(__dirname, './sites/mango/pages')
  }
};
