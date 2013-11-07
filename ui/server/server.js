// Module dependencies
var express = require('express'),
    http = require('http'),
    path = require('path'),
    config = require('./config.js'),
    mock = require('./mock.js');

var app = express();

// all environments
app.set('port', process.env.PORT || 3000);

app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);

// development only
if ('production' === app.get('env')) {
    app.use(express.static(path.join(__dirname, 'target/META-INF/resources')));
} else {
    // Paths are relative to this file
    app.use(express.static(path.join(__dirname, '../.tmp')));
    app.use(express.static(path.join(__dirname, '../app')));
    app.use(express.errorHandler());
}

// Any requests to preview will be sent to index.html where they'll be redirected accordingly
app.get( "/preview", function( req, res ) {
    res.redirect( '/#/preview' );
});

// set responses for all the services defined
mock.services.forEach( function(serviceObj) {

    function getMockPath (mockArray, queryObj) {
        queryObj = queryObj || {};

        for (var i = mockArray.length - 1; i >= 0; i--) {
            // Cheap and easy way to check for object equality
            // All we want to know is if the query string matches the mock's arguments
            if (JSON.stringify(mockArray[i].arguments) === JSON.stringify(queryObj))
                return mockArray[i].path;
        }
        return '';
    }

    app[serviceObj.method]( serviceObj.url, function( req, res ) {

        // Get the site name from the url (:site). If there's no :site parameter in the url
        // then default to one of projects
        var siteName = req.params.site || 'mango';
        var mockPath = getMockPath(serviceObj.mock, req.query);

        res.json( require(config[siteName].mockFolder + mockPath) );
    });
});

app.get( "/site/:site/*", function( req, res ) {
    // The string value of the wildcard (*) will be stored in req.params[0]
    res.sendfile( config[req.params.site].sitesFolder + '/' + req.params[0]);
});

http.createServer(app).listen(app.get('port'), function () {
    console.log("Express server listening on port %d in %s mode", app.get('port'), app.get('env'));
});
