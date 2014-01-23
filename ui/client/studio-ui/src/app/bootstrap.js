/* global define */
'use strict';

define(['ConfigService', 'jquery'],
    function(ConfigService, $) {

    /*
     * @param type : type of dependency (js, css or less)
     * @param dependencyArray: list of dependency names (e.g. list of css files
     *          or list of javascript files)
     * @return Deferred object, fulfilled when all dependencies have been fetched
     */

    function loadDependencies(type, baseURL, dependencyArray) {
        var deferred = $.Deferred(),
            dependencies = dependencyArray.map(appendAssetPrefix);

        function appendAssetPrefix(assetName) {
            return (type == 'css') ? ('css!' + baseURL + assetName) : (baseURL + assetName);
        }

        console.log('Loading dependencies: ', dependencies);

        require(dependencies, function() {
            deferred.resolve();
        });

        return deferred;
    }

    /*
     * Loads an app's module (and all its dependencies) based on its
     * descriptor information
     */

    function loadModule(configObj) {
        var cssFilesPromise, jsFilesPromise,
            deferred = $.Deferred();

        // Default path will be relative to this source file
        var baseURL = configObj.baseURL || '/';

        // Load module dependencies
        jsFilesPromise = loadDependencies('js', baseURL, configObj.dependencies.js);
        cssFilesPromise = loadDependencies('css', baseURL, configObj.dependencies.css);

        $.when(jsFilesPromise, cssFilesPromise).then( function(){

            console.log("All dependencies loaded for module: " + configObj.name);

            deferred.resolve();
        }, function() {
            deferred.reject();
        });

        return deferred;
    }

    return {
        initApp: function(appName) {
            var promiseList = [],
                deferred = $.Deferred();

            ConfigService.getSubmodules(appName).then( function (res){

                // We need the configuration for the main module as well so we'll
                // append it into the array of submodules
                var modules = res.submodules.concat(appName);

                modules.forEach( function(moduleName) {
                    promiseList.push(ConfigService.loadConfiguration(moduleName)
                        .then( function(configObj) {
                            loadModule(configObj);
                    }, function () {
                        throw new Error('Unable to load configuration for ' + moduleName);
                    }));
                });

                $.when.apply(window, promiseList).then( function() {

                    console.log('The application ' + appName + ' is now loaded!');

                    deferred.resolve();
                }, function () {
                    throw new Error('Failed to initialize application');
                });

            }, function () {
                throw new Error('Unable to load submodule information');
            });

            return deferred;
        }
    };

});
