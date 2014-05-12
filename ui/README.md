To learn more about this project, please check out the documentation in our wiki:

http://wiki.craftercms.org/display/CRAFTER/Studio+UI

<!-- 
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

1.    Check unit tests: `$ grunt test`
       
       Write unit tests for the features or bugs you wish to fix, then write the code that implements the features or bug fixes making sure that all unit tests pass.

2.    Lint your code: `$ grunt lint`
       
       Make sure your code adheres to specific code guidelines.

3.    Run the app : `$ grunt dev`
       
       Test the look and feel of the app (with live-reload) against a mock server to guarantee a positive user experience.

4.    Build the app: `$ grunt build`
       
       Build the application for production and run it against a mock server.

5.    Build the app: `$ grunt dist`
       
       Build the application for production so that it is ready to be integrated into a .war or .jar file.

### Support Tasks:

* Copy only the necessary files from bower packages in the component folder (not checked into the repo) into the lib folder: `$ grunt bower:install`
* Clean all folders/files generated during `$ grunt dev`: `$ grunt clean:dev`
* Clean all folders/files generated during `$ grunt build`: `$ grunt clean:build`
* Clean all folders/files generated during `$ grunt dist`: `$ grunt clean:dist`
* Remove all folders/files generated during `$ grunt dev`, `$ grunt build` and/or `$ grunt dist`: `$ grunt cl`
* Update the studio-js-services library: `$ grunt services`

Crafter Studio UI
----

Crafter Studio UI (CSUI) is a flexible and extensible client app for Crafter Studio. CSUI is made up of different modules, each one responsible for providing its own user interface (UI), requesting its own plugins and gathering its data through the REST services that Crafter Studio provides. The presence of these modules is determined by the [app's configuration](https://github.com/quiaro/studio3/blob/test-asset-service/ui/server/app/mocks/config/list/studio-ui.json).

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

1) Get the [application configuration](https://github.com/quiaro/studio3/blob/test-asset-service/ui/server/app/mocks/config/list/studio-ui.json), which includes the modules that should be loaded into the application.

2) For each module, load their descriptor (e.g. [login descriptor](https://github.com/quiaro/studio3/blob/test-asset-service/ui/server/app/mocks/config/list/login.json) & [dashboard descriptor](https://github.com/quiaro/studio3/blob/test-asset-service/ui/server/app/mocks/config/list/dashboard.json) and then, proceed with the loading of the module. RequireJS handles all dependency calculation and fetches each module's js and css dependencies. In the case of css, import statements may also be used alongside RequireJS.

#### Loading Static Dependecies

In CSUI, static dependencies (i.e. JavaScript modules, CSS & LESS) are generally declared within each module definition and are resolved by RequireJS. However, there are a few CSS and Javascript exceptions declared within &lt;link&gt; and &lt;script&gt; tags in the app's index.html file.

##### Static Dependencies in `index.html`

JavaScript files and stylesheets included in `index.html` fall under one of the following categories:

* **CSUI Core**: `./client/studio-ui/src/app/app.js` makes up the core of the application and is responsible for loading all Angular modules that the application depends on. Similarly, there's only one core stylesheet: `studio-ui/studio.css`, which is the result of pre-compiling `./client/studio-ui/src/app/styles/app.less`.

* **Core Libraries**: These are 3rd-party files necessary for CSUI Core to work. In CSS, the only core library is Bootstrap and in JavaScript, the only two core libraries are: RequireJS and Angular. 

* **Angular Modules**: These are project or 3rd-party Angular modules and their corresponding stylesheets. **Project Angular modules are required by CSUI Core** (and may be used by CSUI modules and plugins), whereas **3rd-party Angular modules are required by one or more CSUI modules and plugins**. For example, the project's tree navigation plugin is an Angular directive that extends from [abn-tree](https://github.com/nickperkinslondon/angular-bootstrap-nav-tree), a 3rd-party directive. Note that these Angular modules are different from CSUI modules (found in `./client/studio-ui/src/modules`) in that they don't require any external configuration and rely exclusively on Angular's loading mechanism.

Additionally, `index.html` also includes the path (i.e. RequireJS shortcut) to the services library that CSUI core depends on to load the [app's configuration](https://github.com/quiaro/studio3/blob/test-asset-service/ui/server/app/mocks/config/list/studio-ui.json). This way, the folder to the services library can be referenced by the alias `studioServices`. The other alias (request_agent) references a dependency of this services library.

##### Static Dependencies in Modules

Module dependencies on other modules, JavaScript files and stylesheets (CSS & LESS) must be declared in the module definition. 

###### Shared Modules

Modules are supposed to be autonomous, therefore they should not declare dependencies on other modules. However, there are modules shared through out the application (typically found under `./client/studio-ui/src/modules/common`). These common modules should be exposed via a path mapping in the [app's configuration](https://github.com/quiaro/studio3/blob/test-asset-service/ui/server/app/mocks/config/list/studio-ui.json) under the `module_paths` property. These modules can then be referenced by their path mapping.

Consider the following example:

    my-module.js  >> depends on: globals (common module) & directives (common module)

The AMD module definition for my-module.js would then be:

    define(['globals', 'directives'],
      function( globals, directives ) { ... });

###### JavaScript Files

When a module is loaded, a URL prefix is determined based on the `base_url` values of the app configuration file and its own descriptor file. This URL prefix is combined with `main` property in the descriptor file to calculate the URL of the module's main file. A module may declare dependencies on other JavaScript files that are part of the module. To load these JavaScript files, a path relative to the module's main file may be used. RequireJS uses the same URL prefix as for the main file to calculate the URLs of these JavaScript dependencies and, like the main file, **these dependencies must include their .js extension**.

Consider the following example:

    my-module.js  >> depends on: 
                     globals (common module)
                     my-dependency.js (located in the `scripts` folder, i.e. ./scripts/my-dependency.js)

The AMD module definition for my-module.js would then be:

    define(['globals', './scripts/my-dependency.js'],
      function( globals, MyDependency ) { ... });


###### Stylesheets (CSS & LESS)

RequireJS loader plugins for [LESS](https://github.com/guybedford/require-less) & [CSS](https://github.com/guybedford/require-css) allow modules to declare dependencies on LESS and/or CSS files respectively.

For example:

    my-plugin.js  >> depends on: 
                     my-dependency.js
                     my-css-file.css
                     my-less-file.less

The AMD module definition for my-plugin.js would then be:

    define(['./my-dependency.js', 'css!./my-css-file', 'less!./my-less-file'],
      function( MyDependency ) { ... });

Notice that the CSS plugin is invoked by the `css!` prefix while the LESS plugin is invoked by the `less!` prefix. These prefixes correspond to the key values under the `map` property, inside the `requirejs` property of the [app's configuration file](https://github.com/quiaro/studio3/blob/test-asset-service/ui/server/app/mocks/config/list/studio-ui.json). Notice also that **the paths to both CSS and LESS files are relative to the module's main file and should not include the file's extension**.

###### LESS Support

CSUI core, its modules and its plugins, all have support for [LESS](http://lesscss.org/). However, LESS stylesheets for the app and its modules are pre-processed and turned into CSS as part of the development workflow, meaning that the app and its modules will actually consume these stylesheets as CSS. Therefore, CSUI and all its modules should reference their stylesheets as CSS and not as LESS.

Consider the following example:

    my-module.js  >> depends on: require (module), globals (module), my-stylesheet.less

This would ordinarily correspond to the following AMD module definition:

    define(['require', 'globals', 'less!./my-stylesheet'],
      function( require, globals ) { ... });

However, since my-stylesheet.less will be pre-processed as part of the development workflow, the correct way to define my-module.js is:

    define(['require', 'globals', 'css!./my-stylesheet'],
      function( require, globals ) { ... });
                                   
Since plugins are expected to load and change during runtime, their stylesheets are not pre-processed during the development workflow like those of the modules. Instead, they are pre-processed on runtime (by means of the [LESS loader plugin](https://github.com/guybedford/require-less)) and as a result of this, any dependencies on LESS stylesheets need to be declared as such. For example,

    my-plugin.js  >> depends on: globals (module), my-stylesheet.less
  
This plugin would use the following AMD module definition:

    define(['globals', 'less!./my-stylesheet'],
      function( globals ) { ... });

#### Loading Code on Demand with Angular

Since Angular does not natively provide the ability to include new elements (i.e. controllers, directives, services, etc) into the app after Angular's bootstrap process has completed, a service called [NgRegistry](https://github.com/quiaro/studio3/blob/test-asset-service/ui/client/studio-ui/src/app/scripts/ng_registry.js) exists to work around this limitation. 

NgRegistry follows an approach similar to those described in the following articles to register new elements after Angular bootstraps:

* [Lazy Loading in AngularJS](http://ify.io/lazy-loading-in-angularjs/)
* [Dynamically Loading Controllers and Views with AngularJS and RequireJS](http://weblogs.asp.net/dwahlin/archive/2013/05/22/dynamically-loading-controllers-and-views-with-angularjs-and-requirejs.aspx)

It's important to remember that all CSUI modules are loaded on demand by RequireJS (and are therefore structured as [AMD modules](http://requirejs.org/docs/whyamd.html#amd)) after Angular bootstraps; consequently, NgRegistry is key in incorporating their code into the app. Since NgRegistry is a service visible only within the app (that exists within the Angular framework), it is necessary to retrieve the app's injector which grants access to all of the app's object instances (including NgRegistry) to javascript code outside the Angular framework. As a result of this, most modules will likely follow this pattern:

    define(['globals',
        'css!./mycss'], function( globals ) {

        'use strict';

        // Get the app's injector
        var injector = angular.element(globals.dom_root).injector();

        // Use the injector to run a function that uses some Angular 
        // object instances (NgRegistry & $log)
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

Like modules, plugins are also loaded on demand by RequireJS after Angular bootstraps. Plugins are actually a special kind of module that **must return their directive** as their module value. Plugins are loaded when a view that contains them is rendered (see Loading Templates with Modules). Plugins can be loaded individually or as a group belonging to a specific container.

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

When this plugin is rendered, it will show: "Hello World! My name is Gustavo".

###### Loading Individual Plugins

Because plugins are loaded asynchronously, if a user tried using the previous sample plugin by writing into a template file:

    <sdo-plugin-almond></sdo-plugin-almond>

Nothing would happen because the app doesn't have any information about this directive. To load the plugin information first, the user must use the directive in combination with the sdoPluginSrc attribute (included in the directives module) as follows:

    <sdo-plugin-almond sdo-plugin-src="crafter.studio-ui.plugin.almond"></sdo-plugin-almond>

Where the value of the sdoPluginSrc attribute (e.g. crafter.studio-ui.plugin.almond) is the plugin's registered name (as it appears in its descriptor file). The sdoPluginSrc directive then proceeds to load all of the plugin's files, and finally calls the $compile function on the element (e.g. &lt;sdo-plugin-almond&gt;). **Remember to declare a dependency on the `directives` module from the module that makes use of the plugin.** 

Loading a plugin individually makes it possible to set specific plugin behavior/configuration via attributes. Imagine wanting to add some custom behavior like controlling the length of the message by adding another attribute, for example:

    <sdo-plugin-almond message-length="10" 
                       sdo-plugin-src="crafter.studio-ui.plugin.almond"></sdo-plugin-almond>
 
In this case, you are able to set the length of the message using the message-length attribute and create different instances in the page of the same plugin:

    <sdo-plugin-almond message-length="6" 
                       sdo-plugin-src="crafter.studio-ui.plugin.almond"></sdo-plugin-almond>
    <sdo-plugin-almond message-length="12" 
                       sdo-plugin-src="crafter.studio-ui.plugin.almond"></sdo-plugin-almond>

###### Loading All Plugins for a Container

Plugins can also be loaded in bulk using the sdoPlugins directive, which loads all plugins for a specific container. The following example shows how to load all plugins for a container named "activity":

    <sdo-plugins plugin-container="activity"></sdo-plugins>

Unlike loading plugins individually, this method of loading plugins does not allow the user to control the settings/behavior of each one of the plugins loaded. This means that all plugins will be loaded with their default settings (i.e. whatever their returned module value is). 

#### Loading Templates

The CSUI core is an engine responsible for loading the application's modules, and it does not have templates or views in and of itself. Only the modules and plugins have templates linked to them, and they both have a slightly different way of loading them.

##### Loading Templates with Modules

Each module adds one or more states to the application (using [ui-router](https://github.com/angular-ui/ui-router) behind the scenes) and it can assign a specific template to each one of them via the `templateUrl` property. 

For example, the following is a bare bones module defining a new state (moduleNamespace.sampleState) in the application with a template assigned to it (`my-template.tpl.html`):

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

Notice that the location of both the stylesheet (mycss.css) and the template is relative to the module's JS file. Also, notice that RequireJS is not used to load templates for modules because these are loaded on demand by Angular when the user navigates to their corresponding url. In this example, the template will not be loaded until the user navigates to `http://sample-domain.net/sample-state`.

##### Loading Templates with Plugins

When a module template loads, it may load specific plugins referenced by a custom directive and/or it may load all plugins for a specific container type by using the `sdoPlugins` directive. In both cases, as long as the plugin is declared as an Angular directive, it is possible to load its template via the directive's `template` or `templateUrl` properties.

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

Notice that the location of the stylesheet (almond.less) and the template (almond.tpl.html) are relative to the module's JS file. Also, notice that, similar to loading templates with modules, RequireJS is not used to load templates; instead, these are loaded on demand by Angular.

### <a name="app_configuration"></a>App Configuration

CSUI and its modules are configured by means of configuration files, also known as descriptors. There is a [descriptor for the app](https://github.com/quiaro/studio3/blob/test-asset-service/ui/server/app/mocks/config/list/studio-ui.json) and one for each module of the application.

The app descriptor sets app-wide settings, including settings shared by all modules of the application. Below is a sample app descriptor with comments:

    {
        // Namespace of the app (currently not used)
        "name": "crafter.studio-ui",

        // Version of the app
        "version": "0.1.0",

        // Default base URL for all modules of the app
        "base_url": "http://localhost:9000/studio-ui",

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

            // Angular DOM root element
            "dom_root": "#studio-ui",

            // State to where the user will be redirected if she tries
            // to access a state that requires the user to be signed in
            "default_state": "test",

            // URL of default_state
            "default_url": "/test-service",

            // Name of the application's default service provider. If no other service
            // providers are defined or if they are defined, but are named differently 
            // than the default_service_provider then this name will serve as reference 
            // to the service provider used to boostrap the application. If one of the 
            // service providers in the 'service_providers' property has the same name 
            // as the default_service_provider then the service provider used to bootstrap
            // the application is not accessible to any modules or plugins (in other
            // words, the services used to bootstrap the application will be hidden to 
            // everyone except to the CSUI core)
            "default_service_provider": "StudioServices",

            // Default language for modules/plugins that have multi-language support
            "default_language": "en",

            // State to where the user will be redirected if she's logged in
            // but doesn't have permissions to access the requested state
            "unauthorized_state": "unauthorized",

            // URL of unauthorized_state
            "unauthorized_url": "/unauthorized",

            // Path for common templates used within the app. This value will be
            // relative to base_url unless it includes a protocol (e.g. http://) 
            "templates_url": "modules/common/templates",

            // Path for app plugins. This value will be relative to base_url 
            // unless it includes a protocol (e.g. http://). This path can also 
            // be overriden within a plugin descriptor via the base_url -the
            // value of base_url will be appended to plugins_url unless it
            // includes a protocol, in which case only the plugin descriptor's
            // base_url value is used
            "plugins_url": "plugins"
        },

        // Modules to load for the app
        "modules": [
            "crafter.studio-ui.section.login",
            "crafter.studio-ui.section.dashboard"
        ]

        // Configuration for additional providers or sources of services
        // The example below, lets the application have access to the same 
        // services library running on different locations (one locally,
        // on port 9000, and another remotely -on studio3.craftercms.org)
        "service_providers": [{
            "name": "LocalStudioServices",

            // Location of the constructor
            "main": "studioServices/studioServices",
            "config": {
                "server": {
                    "port": 9000
                },
                "site": "mango"
            }
        }, {
            "name": "RemoteStudioServices",

            // Location of the constructor
            "main": "studioServices/studioServices",
            "config": {
                "server": {
                    "domain": "studio3.craftercms.org",
                    "port": ""
                },
                "site": "coconut"
            }
        }]
    }

#### Globals Module

When the app bootstraps, all settings found under `module_globals` are put in a module called `globals`. All app modules that declare a dependency on this `globals` module can then have access to these settings. For example:

    define(['globals'], function( globals ) {

        'use strict';

        console.log("The application's DOM root element is: " + globals.dom_root);

        console.log("The application's default state is: " + globals.default_state);
    });

#### Service Providers

CSUI core calls an initial service to fetch the [app's configuration file](https://github.com/quiaro/studio3/blob/test-asset-service/ui/server/app/mocks/config/list/studio-ui.json). After the app bootstraps, the user is able to interact with it and as she does, the app and its modules will call services to get or save the information input by the user. All of these services do not have to be offered by the same service provider. In other words, CSUI can be configured so that the CSUI core, modules and plugins can use different service providers. This makes it possible to work with 3rd-party services within the application, if necessary.

Another benefit of this feature is giving developers the ability to add new functional modules that can use mocked versions of new services locally while the rest of the application can continue working with stable versions of the services (e.g. off of a test server). 

Service providers can be configured in the app's configuration file via the `service_providers` property. The [App Configuration section](#app_configuration) provides an example on how to set different service providers. These service providers are instantiated during the bootstrap process and are put in an array. The Angular value `ServiceProviders` keeps a reference to this array of service providers, which can then be injected into any module or plugin.

The property `default_service_provider` in the app's configuration file allows a specific service provider to be set as the default for the application. The Angular value `DefaultServiceProvider` saves this value, thus allowing any module or plugin access to the default service provider by combining this name and the `ServiceProviders` array, as `ServiceProviders[DefaultServiceProvider]`. For example:

    define(['globals'], function( globals ) {

        'use strict';

        var injector = angular.element(globals.dom_root).injector();

        injector.invoke(['ServiceProviders', 'DefaultServiceProvider',
            function(ServiceProviders, DefaultServiceProvider) {

                // Get reference to the default service provider
                var serviceProvider = ServiceProviders[DefaultServiceProvider];

                // Use the service provider's API. Invoke the get method belonging 
                // to a fictitious Resources module of the service provider
                serviceProvider.Resources.get('resource-id-123').then( function(resource) {
                        console.log('Resource: ', resource);
                    });
            }
        ]);
    });

### Module Configuration

Modules and plugins can declare their own specific configuration via their descriptor files. This can be done by a adding a `config` property in the descriptor and also adding a dependency on the special RequireJS module `module`. Calling `module.config()` inside the module will retrieve the module's configuration object, where the config property stores all specific configuration values. For example:

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

        injector.invoke(['$log', 
            function($log) {

                $log.log("Foo value: ", settings.config.foo);  // true

                $log.log("Bar value: ", settings.config.bar);  // "tin can"
            }
        ]);
    });

#### Using a Specific Service Provider

Via its own descriptor file, a module can be configured to request specific access to one (or more) of the service providers available to the application. For example, assuming that 2 different service providers have been configured for the app: `StudioServices` (default) and `LocalStudioServices`, the module can be configured to work with `LocalStudioServices` as shown below: 

    /* Module descriptor */
    {
        "name": "crafter.studio-ui.module.fictitious",
        "version": "0.1.0",
        "base_url": "http://domain.net/module-repo/",
        "main": "fictitious.js",

        // module-specific configuration
        // The service_provider value corresponds to one of the names 
        // of service providers in the app's configuration file 
        "config": {
            "service_provider": "LocalStudioServices"
        }
    }

    /* Module definition */
    define(['globals', 'module'], function( globals, module ) {

        'use strict';

        var config = module.config().config,
            injector = angular.element(globals.dom_root).injector();

        injector.invoke(['ServiceProviders', 'DefaultServiceProvider',
            function(ServiceProviders, DefaultServiceProvider) {

                // Get reference to the service provider specified in the
                // module descriptor file. If not, use the default service
                // provider as fallback 
                var serviceProvider = (config && config.service_provider) ?
                                ServiceProviders[config.service_provider] :
                                ServiceProviders[DefaultServiceProvider];

                // Use the service provider's API. Invoke the get method belonging 
                // to a fictitious Resources module of the service provider
                serviceProvider.Resources.get('resource-id-123').then( function(resource) {
                        console.log('Resource: ', resource);
                    });
            }
        ]);
    });


#### Multi-Language Support

Modules and plugins have the option to provide multi-language support through the Language service. The Language service stores the user language preference in [localStorage](https://developer.mozilla.org/en-US/docs/Web/Guide/API/DOM/Storage#localStorage) and broadcasts an `$sdoChangeLanguage` event when this value is changed. Note that when a language preference changes, the view is not refreshed; instead, the modules and plugins subscribed to the `$sdoChangeLanguage` event are responsible for updating their scope.

The Language service provides two methods: 

* `from`(*languageFolder*): Get the language file matching the user's current language preference. The *languageFolder* will be a path relative to the module's main file pointing to a folder with all the language files available for the module.
  
* `changeTo`(*languageId*): Sets the user's language preference to *languageId*.

 -->
