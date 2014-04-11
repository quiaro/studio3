'use strict';

angular.module('crafter.studio-ui.Utils', [])

    /*
     * Miscellaneous functions used all over
     */
    .service('Utils',
        ['$log', '$q', '$timeout', '$rootScope', 'ServiceProviders', 'DefaultServiceProvider',
        function($log, $q, $timeout, $rootScope, ServiceProviders, DefaultServiceProvider) {

            // Takes a string of the form: "the {tree} is behind the {building}" and uses a
            // replace object { 'tree': 'cedar', 'building': 'National Museum'} to replace the
            // placeholders.
            // Throws an error if there are placeholders that don't have a replace value
            this.replacePlaceholders = function replacePlaceholders (string, replaceObj) {

                function replacePlaceholder(match) {
                    var key = match.substring(1, match.length - 1), // remove '{' and '}' from the match string
                        replaceValue = replaceObj[key];
                    if (replaceValue) {
                        return replaceValue;
                    } else {
                        throw new Error('Placeholder "' + key + '" does not have a replace value in ' + string);
                    }
                }

                return string.replace(/{.*}/g, function(m) { return replacePlaceholder(m); } );
            };

            this.arrayIntersection = function arrayIntersection(array1, array2) {
                return array1.filter(function(el) {
                    return array2.indexOf(el) !== -1;
                });
            };

            /*
             * @param rootObj: an object
             * @param dotNotationStr: A string in dot notation to a function from the previous object.
             *             For example: "level1.level2.service"
             * @return function context reference: reference to the function context
             *             For example: "level1.level2.service" will return a reference to "level2"
             */
            this.getContext = function getContext(rootObj, dotNotationStr) {
                var fragments = dotNotationStr.split(".");
                return fragments.splice(0, fragments.length-1).reduce(function(o, p) { return o[p] }, rootObj);
            };

            /*
             * @param rootObj: an object
             * @param dotNotationStr: A string in dot notation to a function from the previous object.
             *             For example: "level1.level2.service"
             * @return function reference: reference to the object's function
             *             For example: "level1.level2.method" will return a reference to "method"
             */
            this.getMethod = function getMethod(rootObj, dotNotationStr) {
                return dotNotationStr.split(".").reduce(function(o, p) { return o[p] }, rootObj);
            };

            /*
             * @param url: base url value
             * @param path: path or url value
             * @return url value: the value returned will be that of path if it includes a protocol;
             *                    otherwise, the value of path will be appended to that of url
             *                    (a forward slash will be added between them, if necessary)
             */
            this.getUrl = function getUrl(url, path) {
                return (path.indexOf('://') !== -1) ?
                            path :
                            (path.indexOf('/') === 0) ?
                                url + path :
                                url + '/' + path;
            };

            /*
             * Loads an array of modules (and all their file dependencies) based on their names.
             * Modules must be of the same type (i.e. system modules or plugins) since the same
             * base_url value will be used to resolve their file locations.
             *
             * @param moduleNamesArray: an array of module names. A configuration file will be
             *                          returned for each one of these module names.
             * @param base_url: the base url that will be used to resolve the paths for the module's
             *                  main js files.
             * @return promiseList: a list of deferred objects for each one of the modules. These
             *                      deferred objects will be resolved as modules finish downloading.
             */

            this.loadModules = function loadModules(moduleNamesArray, base_url) {
                var promiseList = [],
                    me = this;

                if (moduleNamesArray && Array.isArray(moduleNamesArray)) {

                    moduleNamesArray.forEach( function(moduleName) {
                        var dfd = $q.defer();
                        promiseList.push(dfd.promise);

                        ServiceProviders[DefaultServiceProvider].Config.getDescriptor(moduleName)
                            .then( function(descriptor) {

                                var file = me.getUrl(base_url, descriptor.base_url) + descriptor.main,
                                    modConfig = { config: {} };

                                // Set configuration specific to the module
                                modConfig.config[file] = {
                                    name: descriptor.name,
                                    main: file,
                                    config: descriptor.config
                                };

                                $log.info('Config info for ' + descriptor.name + ':', modConfig.config[file]);

                                // Make module-specific configuration available
                                require.config(modConfig);

                                me.loadModule(file)
                                    .then ( function(moduleValue) {
                                        $log.log('Module ' + moduleName + ' was loaded successfully!');
                                        dfd.resolve(moduleValue);
                                    }, function () {
                                        throw new Error('Unable to load module: ' + moduleName);
                                    });
                            }, function () {
                                throw new Error('Unable to load configuration for ' + moduleName);
                            });
                        });
                } else {
                    $log.warn('moduleNamesArray is not an array of module names: ', moduleNamesArray);
                }

                return promiseList;
            };

            /*
             * Loads an app's module (and all its file dependencies)
             * @param mainFile: url/name of the module's main js file to load
             * @return Deferred object, fulfilled when the file and all its dependencies
             *         have been fetched (via require.js)
             */

            this.loadModule = function loadModule(mainFile) {
                var dfd = $q.defer();

                if (!mainFile || typeof mainFile !== 'string') {
                    throw new Error('Incorrect mainFile value');
                }

                $log.log('Loading file: ' + mainFile);

                require([ mainFile ], function(moduleValue) {
                    $timeout( function() {
                        $rootScope.$apply( function() {
                            dfd.resolve(moduleValue);
                        });
                    });
                });

                return dfd.promise;
            };
        }
    ]);
