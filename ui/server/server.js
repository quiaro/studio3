// Module dependencies
var express = require('express'),
    http = require('http'),
    path = require('path'),
    config = require('./config.js');

// Mock data
var items = require(config.server.mockFolder + '/items');

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

app.get( "/api/0.1/repo/list/pebbles", function( req, res ) {
    res.json( items );
});

http.createServer(app).listen(app.get('port'), function () {
    console.log("Express server listening on port %d in %s mode", app.get('port'), app.get('env'));
});
