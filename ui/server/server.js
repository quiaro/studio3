// Module dependencies
var express = require('express'),
    http = require('http'),
    path = require('path'),
    config = require('./config.js'),
    mock = require('./mock.js');

var app = express();

// all environments
app.set('port', process.env.PORT || 3000);

app.set('views', path.join(__dirname, '../.tmp'));
app.engine('.html', require('ejs').renderFile);

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
    app.use(express.static(path.join(__dirname, '../client')));
    app.use(express.errorHandler());
}

// Any requests will be sent to index.html where they'll be redirected accordingly
// app.get( "/dashboard", function( req, res ) {
//     res.redirect( '/#/dashboard' );
// });

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
        // then default to the app scope
        var siteName = req.params.site || 'app';

        // If there's a module variable in the path, we'll use it to get its descriptor
        // If there's a container variable in the path, we'll use it to get its plugins
        var queryObj = (req.params.module) ?
                            { "module": req.params.module } :
                        (req.params.container) ?
                            { "container": req.params.container } : req.query;

        var mockPath = getMockPath(serviceObj.mock, queryObj);

        res.json( require(config[siteName].mockFolder + mockPath) );
    });
});

// Load assets related to the app
app.get( '/studio-ui/modules/*', function( req, res ) {
    // The string value of the wildcard (*) will be stored in req.params[0]
    res.sendfile( config['app'].modulesFolder + '/' + req.params[0]);
});

// Load plugins for the app
app.get( '/studio-ui/plugins/*', function( req, res ) {
    // The string value of the wildcard (*) will be stored in req.params[0]
    res.sendfile( config['app'].pluginsFolder + '/' + req.params[0]);
});

// Load assets related to a specific site
app.get( '/site/:site/*', function( req, res ) {
    // The string value of the wildcard (*) will be stored in req.params[0]
    res.sendfile( config[req.params.site].assetsFolder + '/' + req.params[0]);
});

app.get( '*', function( req, res ) {

    // Assets will be loaded from the client root (except for the files listed in tmpFiles)
    var assetUrlRe = /[\w\/\-:]*\.[\w]+/,
        fileName = req.url.substr(req.url.lastIndexOf("/") + 1);

    if (config.tmpFiles.indexOf(fileName) >= 0) {
        res.sendfile( config.tmpRoot + req.url);

    } else if (!assetUrlRe.test(req.url)) {
        res.render('index.html');

    } else {
        res.sendfile( config.clientRoot + req.url);
    }
});

http.createServer(app).listen(app.get('port'), function () {
    console.log("Express server listening on port %d in %s mode", app.get('port'), app.get('env'));
});
