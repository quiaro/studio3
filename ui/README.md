
Requirements
------------

To work on or build this application you will first need to install [Node](http://nodejs.org/).

Node can be installed in the following ways:

1.   Using the [installer](http://nodejs.org/download/)
     *The Mac installer will install node under /usr/local/bin so you will need to have admin rights to complete the install.

2.   [Building the source code](https://github.com/joyent/node/wiki/Installation)
     This way you can choose to install Node in a custom folder instead of a global directory by using the --prefix config option, thus you are not required to have admin rights to complete the install.

3.   [Via a package manager](https://github.com/joyent/node/wiki/Installing-Node.js-via-package-manager)

Installing
----------

### Manual Install

*Any lines starting with $ are commands to type in a terminal window (the "$" is not meant to be included)*

To install this application (assuming that the whole repository -studio3- has already been cloned):

1.   Go to the home directory of the app (/studio3/ui):

    `$ cd ui`

2.   Install the Grunt-CLI and Bower plugins globally:

    `$ npm install -g grunt-cli bower`


### Automatic Install for Mac OS X and *nix systems

1.  Assuming that the whole repository -studio3- has already been cloned, run the install script found in the "ui" folder :

    `$ sudo ./install.sh`

    *If you installed node using the installer (**Requirements, step 1**), then you will need admin rights to run the install script since it will install the Grunt-CLI and Bower plugins globally. Otherwise, if you installed Node in a custom folder to which you have write access, then sudo is unnecessary to run the script: *

    `$ ./install.sh`

Tasks
-----

A established workflow using grunt tasks can be outlined as follows:

1.    Write units tests, then write code: `$ grunt test`
       
       Start out by writing unit tests for the features or bugs you wish to fix, then write the code that implements the features or bug fixes making sure that all unit tests pass.

2.    Lint your code: `$ grunt lint`
       
       Make sure your code adheres to specific code guidelines.

3.    Run the app : `$ grunt server`
       
       Test the look and feel of the app to guarantee a positive user experience.

4.    Build the app: `$ grunt`
       
       Build the application for deployment and run it on any HTTP server.

About Crafter Studio 3 UI
---------------------

Crafter Studio 3 UI (CS3UI) is a flexible and extensible client app for Crafter Studio 3. CS3UI is made up of different modules, each one responsible for providing its own user interface (UI) and gathering its data through the REST services that Crafter Studio 3 provides. The presence of these modules is determined by the app's configuration.

### Loading of the App

To achieve its goals of flexibility and extensibility, CS3UI is an Angular app that combines RequireJS to load scripts on demand, giving it the ability load itself dynamically in run time based on its configuration. 

When CS3UI starts, it kicks off a bootstrap process responsible for the following:

1) Get the [application configuration](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/server/app/mocks/config/list/app/descriptor.json), which includes the modules that should be loaded into the application.

2) For each module, load their descriptor (e.g. [login descriptor](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/server/app/mocks/config/list/login/descriptor.json) & [dashboard descriptor](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/server/app/mocks/config/list/dashboard/descriptor.json) and then, proceed with the loading of the module. RequireJS handles all dependency calculation and fetches each module's js and css dependencies. In the case of css, import statements may also be used alongside RequireJS.

**Note**: RequireJS is not used to load templates because these are loaded on demand by angular and use angular's own caching system.

#### Loading code on demand with Angular

Since Angular does not natively provide the ability to include new elements (i.e. controllers, directives, services, etc) into the app after Angular's bootstrap process has completed, a service called [NgRegistry](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/client/studio-ui/src/app/scripts/ng_registry.js) exists to work around this limitation. 

NgRegistry follows an approach similar to that described in the following articles to register new elements after Angular bootstraps:

* [Lazy Loading in AngularJS](http://ify.io/lazy-loading-in-angularjs/)
* [Dynamically Loading Controllers and Views with AngularJS and RequireJS](http://weblogs.asp.net/dwahlin/archive/2013/05/22/dynamically-loading-controllers-and-views-with-angularjs-and-requirejs.aspx)

It's important to remember that all CS3UI modules are loaded on demand by RequireJS (and are therefore structured as [AMD modules](http://requirejs.org/docs/whyamd.html)) after Angular bootstraps; consequently, NgRegistry is key in incorporating their code into the app. Since NgRegistry is a service visible only within the app (that exists within the Angular framework), it is necessary to retrieve the app's injector which grants access to all of the app's object instances (including NgRegistry) to javascript code outside the Angular framework. As a result of this, most modules will likely follow this pattern:

    define(['globals',
        'css!./mycss'], function( globals ) {

        'use strict';

        // Get the app's injector
        var injector = angular.element(globals.dom_root).injector();

        // Run a function and make available some Angular object instances (NgRegistry & $log)
        injector.invoke(['NgRegistry', '$log',
            function(NgRegistry, $log) {

                // Use NgRegistry to register a new controller within the app
                NgRegistry
                    .addController('NewCtrl', ['$scope', function ($scope) {
                        $scope.newMethod = function (myVar) {
                            $log.log("newMethod called with param: ", myVar);
                        };
                    }]);

            }
        ]);
    });

