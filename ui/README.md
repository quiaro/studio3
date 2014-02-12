
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

Crafter Studio 3 UI (CS3UI) is a client app for Crafter Studio 3. It consists in a user interface that consumes the REST services that Crafter Studio 3 provides.

### Loading of the App

With extensibility in mind, CS3UI loads itself dynamically in run time. When CS3UI starts, it kicks off a bootstrap process responsible for the following:

1) Get the application configuration (descriptor), including all its sections (e.g. login, dashboard, authoring, etc).

2) For each section, load their descriptor to set any configuration variables prior to the loading of the module. Then, proceed with the loading of the module's main js file. All js and css dependencies stemming from the main js file will be fetched using require.js. In the case of css, import statements may also be used alongside requirejs.
