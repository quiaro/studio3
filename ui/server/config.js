path = require('path');

module.exports = {
  server: {
    // Folder that contains the json files with the mock responses -relative to this file
    mockFolder: path.resolve(__dirname, './mocks')
  }
};
