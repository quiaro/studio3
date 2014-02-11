'use strict';

angular.module('crafter.studio-ui.Utils', [])

    .constant('require_css_path', 'studio-ui/lib/require-css/js/css')
    /*
     * Miscellaneous functions used all over
     */
    .service('Utils', ['require_css_path', '$log',
        function(require_css_path, $log) {

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
             * @param type : type of dependency (js, css or less)
             * @param filenameList: list of css files or list of javascript files
             * @return Deferred object, fulfilled when all files have been fetched
             */

            this.loadFiles = function loadFiles(type, baseURL, filenameList) {
                var deferred = $.Deferred(),
                    fileList = (filenameList && Array.isArray(filenameList)) ?
                                    filenameList.map(appendAssetPrefix) :
                                    [];

                function appendAssetPrefix(assetName) {
                    return (type == 'css') ? (require_css_path + '!' + baseURL + assetName) : (baseURL + assetName);
                }

                $log.log('Loading file list: ', fileList);
                require(fileList, function() {
                    deferred.resolve();
                });

                return deferred;
            };

            /*
             * Loads an app's module (and all its file dependencies) based on its
             * descriptor information
             */

            this.loadModule = function loadModule(name, baseURL, jsDpcyList, cssDpcyList) {
                var cssFilesPrm, jsFilesPrm,
                    deferred = $.Deferred();

                // Default path will be relative to this source file
                var baseURL = baseURL || '/';

                // Load module dependencies
                jsFilesPrm = this.loadFiles('js', baseURL, jsDpcyList);
                cssFilesPrm = this.loadFiles('css', baseURL, cssDpcyList);

                $.when(jsFilesPrm, cssFilesPrm).then( function(){
                    $log.log("All dependencies loaded for module: " + name);
                    deferred.resolve();
                }, function() {
                    deferred.reject();
                });

                return deferred;
            };
        }
    ]);
