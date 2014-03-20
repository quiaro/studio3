
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
* Clean all files generated during `$ grunt dev`: `$ grunt clean:dev`
* Clean all files generated during `$ grunt build`: `$ grunt clean:build`
* Clean all files generated during `$ grunt dist`: `$ grunt clean:dist`
* Update the studio-js-services library: `$ grunt services`

Crafter Studio UI
----

Crafter Studio UI (CSUI) is a flexible and extensible client app for Crafter Studio. CSUI is made up of different modules, each one responsible for providing its own user interface (UI), requesting its own plugins and gathering its data through the REST services that Crafter Studio provides. The presence of these modules is determined by the [app's configuration](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/server/app/mocks/config/list/app/descriptor.json).

### Project Overview

Here is a brief overview of the CSUI file & folder structure:

**CSUI: `./client`**

* `./client/studio-ui/lib`: bower packages (i.e. 3rd party libraries)
* `./client/studio-ui/src/app`: CSUI core application
* `./client/studio-ui/src/modules`: CSUI modules (requested by the core application when it bootstraps)
* `./client/studio-ui/src/plugins`: CSUI plugins (requested by any of the CSUI modules)

**Node Modules: `./node_modules`**

Node modules used by grunt or the mock servers during tasks:

* *ejs*: Used by server.js to serve index.html
* *grunt-contrib-copy*: Copies source files to a new directory
* *grunt-contrib-uglify*: Minifies/compresses JS files and generates source maps
* *grunt-contrib-jshint*: Validates files with JSHint
* *grunt-contrib-clean*: Cleans files and folders
* *grunt-contrib-imagemin*: Minifies PNG, JPEG and GIF images
* *grunt-contrib-less*: Compiles LESS to CSS
* *grunt-usemin*: Transforms specific construction blocks (of CSS or JS files) in a file into a single line
* *grunt-replace*: Replaces text patterns with a given replacement (text pre-processor)
* *grunt-karma*: Grunt plugin for the karma test runner
* *grunt-open*: Opens urls and files from a grunt task
* *matchdep*: Filters npm module dependencies by name or a text pattern
* *grunt-bower-task*: Installs only the files needed from bower packages
* *grunt-newer*: Configures grunt tasks to run with newer files only
* *grunt-express-server*: Runs an Express server that works with LiveReload + Watch/Regarde
* *grunt-contrib-watch*: Run predefined tasks whenever watched files are added, changed or deleted
* *express*: Fast minimalist web framework for node

**CSUI Mock Servers and Services: `./server`**

* `./server/app`: Mock data for application services
* `./server/build`: Build server for the packaged application (see `$ grunt build`)
* `./server/dev`: Development server for the application (see `$ grunt dev`)
* `./server/sites`: Mock data for site-specific services
* `./server/config.js`: Configuration for both the build and dev servers. This configuration can be overridden or extended via a config.js file inside the server folder (e.g. see `./server/dev/config.js`)
* `./server/mock.js`: Maps app and site-specific service urls to their corresponding mock data

**CSUI Tests: `./test`**

**Root Files**

* `./.jshintrc`: JS hint lint rules
* `./bower.json`: Bower package file. Lists the project's bower package dependencies
* `./Gruntfile.js`: Configuration file for grunt tasks
* `./install.sh`: Installation script for the application
* `./package.json`: Node package file. Lists the project's node module dependencies
* `./pom.xml`: Configuration file for integrating the application into CS3

### Loading of the App

To achieve its goals of flexibility and extensibility, CSUI is an Angular app that combines RequireJS to load scripts on demand, giving it the ability load itself dynamically in run time based on its configuration. 

When CSUI starts, it kicks off a bootstrap process responsible for the following:

1) Get the [application configuration](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/server/app/mocks/config/list/app/descriptor.json), which includes the modules that should be loaded into the application.

2) For each module, load their descriptor (e.g. [login descriptor](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/server/app/mocks/config/list/login/descriptor.json) & [dashboard descriptor](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/server/app/mocks/config/list/dashboard/descriptor.json) and then, proceed with the loading of the module. RequireJS handles all dependency calculation and fetches each module's js and css dependencies. In the case of css, import statements may also be used alongside RequireJS.

#### Loading Code on Demand with Angular

Since Angular does not natively provide the ability to include new elements (i.e. controllers, directives, services, etc) into the app after Angular's bootstrap process has completed, a service called [NgRegistry](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/client/studio-ui/src/app/scripts/ng_registry.js) exists to work around this limitation. 

NgRegistry follows an approach similar to that described in the following articles to register new elements after Angular bootstraps:

* [Lazy Loading in AngularJS](http://ify.io/lazy-loading-in-angularjs/)
* [Dynamically Loading Controllers and Views with AngularJS and RequireJS](http://weblogs.asp.net/dwahlin/archive/2013/05/22/dynamically-loading-controllers-and-views-with-angularjs-and-requirejs.aspx)

It's important to remember that all CSUI modules are loaded on demand by RequireJS (and are therefore structured as [AMD modules](http://requirejs.org/docs/whyamd.html)) after Angular bootstraps; consequently, NgRegistry is key in incorporating their code into the app. Since NgRegistry is a service visible only within the app (that exists within the Angular framework), it is necessary to retrieve the app's injector which grants access to all of the app's object instances (including NgRegistry) to javascript code outside the Angular framework. As a result of this, most modules will likely follow this pattern:

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

##### Loading Plugins

Plugins are modules that **must return their directive** as their module value. This means that plugins are also loaded on demand by RequireJS after Angular bootstraps. More specifically, plugins are loaded when the view corresponding to a state of the application renders (see Loading Templates with Modules). Plugins can be loaded individually or as a group for a specific container.

Below is a sample plugin referenced by the tag `<sdo-plugin-almond></sdo-plugin-almond>`:

    define(['require', 'globals', 'less!./almond'],
        function( require, globals ) {

        'use strict';

        var injector = angular.element(globals.dom_root).injector();

        injector.invoke(['NgRegistry', function(NgRegistry) {

                NgRegistry
                    .addController('AlmondCtrl',
                        ['$scope', '$timeout', function ($scope, $timeout) {

                        $timeout( function() {
                            $scope.$apply( function() {
                                // Make sure the templates are updated with the values in the scope
                                $scope.name = 'Gustavo';
                            });
                        });
                    }])

                    .addDirective('sdoPluginAlmond', [function() {

                        return {
                            restrict: 'E',
                            controller: 'AlmondCtrl',
                            replace: true,
                            scope: {},
                            template: '<div>Hello World! My name is {{name}}</div>'
                        };
                    }]);
            }
        ]);

        return '<sdo-plugin-almond></sdo-plugin-almond>';
    });

When this plugin is rendered, all it will do is print out: "Hello World! My name is Gustavo". 

###### Loading Individual Plugins

Based on the previous example, to load the plugin on a page the user would write the plugin's directive together with the sdoPluginSrc directive, similar to the following:

    <sdo-plugin-almond sdo-plugin-src="crafter.studio-ui.plugin.almond"></sdo-plugin-almond>

... where "crafter.studio-ui.plugin.almond" is the plugin's name as it appears in its descriptor file. Loading a plugin individually makes it possible to set specific plugin behavior/configuration via attributes. Imagine wanting to add some custom behavior like controlling the length of the message by adding another directive, for example:

    <sdo-plugin-almond sdo-almond-length="10" sdo-plugin-src="crafter.studio-ui.plugin.almond"></sdo-plugin-almond>
 
In this case, you are able to set the length of the message using the sdoAlmondLength directive and create different instances in the page of the same plugin:

    <sdo-plugin-almond sdo-almond-length="6" sdo-plugin-src="crafter.studio-ui.plugin.almond"></sdo-plugin-almond>
    <sdo-plugin-almond sdo-almond-length="12" sdo-plugin-src="crafter.studio-ui.plugin.almond"></sdo-plugin-almond>

###### Loading All Plugins for a Container

Plugins can also be loaded in bulk using the sdoPlugins directive, which loads all plugins for a specific container. The following example shows how to load all plugins for a container named "activity":

    <sdo-plugins plugin-container="activity"></sdo-plugins>

Unlike loading plugins individually, this method of loading plugins does not allow the user to control the settings/behavior of each one of the plugins loaded. This means that all plugins will be loaded with their default settings. 

#### Loading Templates

The CSUI core is responsible for loading the application's modules, but it does not have a template (i.e. graphic interface) associated with it. Only the modules and plugins can have templates linked to them, and they both have a slightly different way of loading them.

##### Loading Templates with Modules

Each module adds one more states to the application (using [ui-router](https://github.com/angular-ui/ui-router) behind the scenes) and it can assign a specific template to each one of them. 

For example, the following is a bare bones module defining a new state (moduleNamespace.sampleState) in the application and a template for it (my-template.tpl.html):

    define(['require',
            'globals',
            'css!./mycss'], function( require, globals ) {

        'use strict';

        var injector = angular.element(globals.dom_root).injector();

        injector.invoke(['NgRegistry',
            function(NgRegistry) {

                NgRegistry
                    .addState('moduleNamespace.sampleState', {
                        url: '/sample-state',
                        templateUrl: require.toUrl('./templates/my-template.tpl.html')
                    });
            }
        ]);

    });

Notice that the location of both the stylesheet (mycss.css) and the template is relative to the module's JS file. Also, notice that RequireJS is not used to load templates for modules because these are loaded on demand by Angular when the user navigates to their corresponding url. In this example, the template will not be loaded until the user navigates to 'http://sample-domain.net/sample-state'.

##### Loading Templates with Plugins

When a module template loads, it may load specific plugins referenced by a custom directive and/or it may load all plugins for a specific container type by using the 'sdoPlugins' directive. In both cases, as long as the plugin is declared as an Angular directive, it is possible to load its template via the directive's *template* or *templateUrl* properties.

The following is a sample plugin encapsulated within the custom directive `<sdo-plugin-almond>`:

    define(['require', 'globals', 'less!./almond'],
        function( require, globals ) {

        'use strict';

        var injector = angular.element(globals.dom_root).injector();

        injector.invoke(['NgRegistry', function(NgRegistry) {

                NgRegistry
                    .addDirective('sdoPluginAlmond', [function() {
                        return {
                            restrict: 'E',
                            replace: true,
                            scope: {},
                            templateUrl: require.toUrl('./templates/almond.tpl.html')
                        };
                    }]);
            }
        ]);

        return '<sdo-plugin-almond></sdo-plugin-almond>';
    });

Notice that the location of the stylesheet (almond.less) and the template (almond.tpl.html) is relative to the module's JS file. Also, notice that, similar to loading templates with modules, RequireJS is not used to load templates; instead, these are loaded on demand by Angular.

### App Configuration

CSUI and its modules are configured by means of configuration files, also known as descriptors. There is a [descriptor for the app](https://github.com/quiaro/studio3/blob/2217857ec16da4c3c69877f50cd4f2b067c2e4ce/ui/server/app/mocks/config/list/app/descriptor.json) and one for each module of the application.

The app descriptor sets app-wide settings, including settings shared by all modules of the application. Below is a sample app descriptor with comments:

    {
        // Namespace of the app (currently not used)
        "name": "crafter.studio-ui",

        // Version of the app
        "version": "0.1.0",

        // Default base URL for all modules of the app
        "base_url": "http://localhost:9000/studio-ui/modules",

        // Configuration for requirejs
        "requirejs": {

            // Map for plugins
            "map": {
                "css": "lib/require-css/js/css",
                "less": "lib/require-less/js/less",
                "text": "lib/requirejs-text/js/text"
            },

            // Path mappings for internal modules (i.e. modules that may be used as dependencies
            // by the modules of the application). The path settings are assumed to be relative to 
            // "base_url", unless the path value starts with a "/" or has a URL protocol in it (e.g. "http:")
            "module_paths": {
                "globals": "modules/common/globals",
                "common": "modules/common/common",
                "directives": "modules/common/directives"
            }
        },

        // Settings/values shared by all modules
        "module_globals": {
            "dom_root": "#studio-ui",
            "default_state": "login",
            "default_url": "/login",
            "unauthorized_state": "unauthorized",
            "unauthorized_url": "/unauthorized",

            // Path for common templates used within the app
            "templates_url": "studio-ui/modules/common/templates",

            // Path for plugins (can be overridden within a plugin descriptor)
            "plugins_url": "plugins"
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

### LESS Support

CSUI, its modules and its plugins, all have support for [LESS](http://lesscss.org/). However, LESS stylesheets for the app and its modules are pre-processed and turned into CSS as part of the development cycle, meaning that the app and its modules will actually consume these stylesheets as CSS. Therefore, CSUI and all its modules should reference their stylesheets as CSS and not as LESS.

Consider the following example:

    my-module.js  >> depends on: require (module), globals (module), my-stylesheet.less

This would ordinarily correspond to the following AMD module definition:

    define(['require', 'globals', 'less!./my-stylesheet'],
      function( require, globals ) { ... });

However, since my-stylesheet.less will be pre-processed as part of the development cycle, the correct way to define my-module.js would be:

    define(['require', 'globals', 'css!./my-stylesheet'],
      function( require, globals ) { ... });
                                   
Since plugins are expected to be loaded and change during runtime, their stylesheets are not pre-processed during the development cycle like those of the modules. Instead, they are pre-processed on runtime (by means of the [LESS loader plugin](https://github.com/guybedford/require-less)) and as a result of this, any dependencies on LESS stylesheets need to be declared as such. For example,

    my-plugin.js  >> depends on: globals (module), my-stylesheet.less
  
This plugin would correspond to the following AMD module definition:

    define(['globals', 'less!./my-stylesheet'],
      function( globals ) { ... });
