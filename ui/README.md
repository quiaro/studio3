
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

3.    Run the app : `$ grunt dev`
       
       Test the look and feel of the app (with live-reload) against a mock server to guarantee a positive user experience.

4.    Build the app: `$ grunt build`
       
       Build the application for production and run it against a mock server.

5.    Build the app: `$ grunt dist`
       
       Build the application for production so that it is ready to be integrated into a .war or .jar file.

### Support Tasks:

* Install any bower packages from the component folder into the lib folder: `$ grunt bower:install`

Node Modules
----

* ejs: Used by server.js to serve index.html
* grunt-contrib-copy: Copies source files to a temp directory
* grunt-contrib-uglify: Minifies/compresses JS files and generates source maps
* grunt-contrib-jshint: Validates files with JSHint
* grunt-contrib-clean: Cleans files and folders
* grunt-contrib-imagemin: Minifies PNG, JPEG and GIF images
* grunt-recess: Compiles LESS to CSS
* grunt-usemin: Transforms specific construction blocks (of CSS or JS files) in a file into a single line
* grunt-replace: Replaces text patterns with a given replacement (text pre-processor)
* grunt-karma: Grunt plugin for the karma test runner
* grunt-open: Opens urls and files from a grunt task
* matchdep: Filters npm module dependencies by name or a text pattern
* grunt-ngmin: AngularJS pre-minifier that inserts inline annotations for dependency injections
* grunt-bower-task: Installs only the files needed from bower packages
* grunt-newer: Configures grunt tasks to run with newer files only
* grunt-express-server: Runs an Express server that works with LiveReload + Watch/Regarde
* grunt-contrib-watch: Run predefined tasks whenever watched files are added, changed or deleted
* express: Fast minimalist web framework for node

Crafter Studio 3 UI
----

Crafter Studio 3 UI (CS3UI) is a flexible and extensible client app for Crafter Studio 3. CS3UI is made up of different modules, each one responsible for providing its own user interface (UI) and gathering its data through the REST services that Crafter Studio 3 provides. The presence of these modules is determined by the [app's configuration](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/server/app/mocks/config/list/app/descriptor.json).

The project's repository currently includes 4 root folders:

* client: CS3UI bootstrap code, 3rd-party library code and images
* node_modules
* server
* test


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

### App Configuration

CS3UI and its modules are configured by means of configuration files, also known as descriptors. There is a [descriptor for the app](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/server/app/mocks/config/list/app/descriptor.json) and one for each module of the application.

The app descriptor sets app-wide settings, including settings shared by all modules of the application. Below is a sample app descriptor with comments:

    {
        // Namespace of the app (currently not used)
        "name": "crafter.studio-ui",

        // Version of the app
        "version": "0.1.0",

        // Default base URL for all modules of the app
        "base_url": "http://localhost:9000/studio-ui/modules",

        // Settings/values shared by all modules
        "module_globals": {
            "dom_root": "#studio-ui",
            "default_state": "login",
            "default_url": "/login",
            "unauthorized_state": "unauthorized",
            "unauthorized_url": "/unauthorized",
            "templates_url": "studio-ui/modules/common/templates"
        },

        // Path mappings for internal modules (i.e. modules that may be used as dependencies
        // by the modules of application). The path settings are assumed to be relative to 
        // "base_url", unless the paths setting starts with a "/" or has a URL protocol 
        // in it ("like http:")
        "module_paths": {
            "globals": "studio-ui/modules/common/globals",
            "common": "studio-ui/modules/common/common"
        },

        // Modules to load for the app
        "modules": [
            "crafter.studio-ui.section.login",
            "crafter.studio-ui.section.dashboard"
        ]
    }

#### Globals Module

When the app bootstraps, all settings found under "module_globals" are put in a module called "globals". All app modules that declare a dependency on this "globals" module can then have access to these settings. For example:

    // This module declares 2 dependencies: one to the globals module and another to a
    // stylesheet, 'mycss.css'
    define(['globals',
        'css!./mycss'], function( globals ) {

        'use strict';

        console.log("The application's DOM root element is: " + globals.dom_root);

        console.log("The application's default state is: " + globals.default_state);
    });

#### Module-Specific Settings

Modules can also declare their own specific configuration values. This can be done by a adding a "config" property in the module descriptor and also adding a special dependency on 'module'. Calling module.config() inside the module will retrieve the module's configuration object, where the config property stores all specific configuration values. For example:

    /* Module descriptor */
    {
        "name": "crafter.studio-ui.module.fictitious",
        "version": "0.1.0",
        "base_url": "http://domain.net/module-repo/",
        "main": "fictitious.js",

        // module-specific configuration 
        "config": {
            "foo": true,
            "bar": "tin can"
        }
    }

    /* Module definition */
    define(['globals',
            'module'], function( globals, module ) {

        'use strict';

        var settings = module.config(),
            injector = angular.element(globals.dom_root).injector();

        injector.invoke(['$log', function($log) {

              $log.log("Foo value: ", settings.config.foo);  // true

              $log.log("Bar value: ", settings.config.bar);  // "tin can"
          }
        ]);
    });
