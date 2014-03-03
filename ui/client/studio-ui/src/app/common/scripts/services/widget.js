'use strict';

angular.module('crafter.studio.common')

    .service('WidgetService',
        ['$templateCache', '$http', '$q', 'CONFIG',
        function ($templateCache, $http, $q, CONFIG) {

        this.create = function create (prototypeObj) {
            var newWidget = Object.create(prototypeObj);

            // Because we want to keep widgets independent of each other, we'll copy
            // any properties that are not functions (i.e. data properties) from the prototype object
            // into the widget's own model; otherwise, the widgets will rely on the prototype's
            // model and share data (... and widgets will start acting strange!)
            Object.keys(prototypeObj).forEach( function(modelKey) {
                if (typeof prototypeObj[modelKey] !== 'function') {
                    newWidget[modelKey] = angular.copy(prototypeObj[modelKey]);
                }
            });
            return newWidget;
        };

        this.getWidgets = function getWidgets(section) {
            var deferred = $q.defer();

            $http.get(CONFIG[section])
                .success(function(data) {
                    var widgets = data &&
                                    data.dashboard &&
                                        data.dashboard.widgets;

                    if (widgets && angular.isArray(widgets) && widgets.length) {
                        deferred.resolve(widgets);
                    } else {
                        deferred.resolve([]);
                    }
                }).error(function() {
                    deferred.reject(null);
                });
            return deferred.promise;
        };

        /*
         * @param section app section for which widgets are being loaded
         * @param widgetProperty widget property pointing to an asset to be loaded asynchronously
         * @param fetchDuplicateAssets whether we should make another request for an asset we have already
         *        requested. Depending on how we want to process the asset after we retrieve it
         *        (via the assetCallback), it may not be necessary to fetch it twice. For example, if we
         *        fetch a template and want to change it before we resolve the promise for it, then
         *        we should make a new request for it each time.
         * @param assetCallback Function called to modify and/or process each individual asset fetched before
         *        its promise is resolved
         */
        this.getPropertyAssets = function getPropertyAssets(section, widgetProperty, fetchDuplicateAssets, assetCallback) {
            var deferred = $q.defer(),
                fetchedAssets = {};

            this.getWidgets(section).then( function (widgets) {

                var allPromises = [];

                widgets.forEach( function (widget) {
                    var assetPromise;

                    if (widget[widgetProperty]) {
                        if (fetchDuplicateAssets ||
                            (!fetchDuplicateAssets && typeof fetchedAssets[widget[widgetProperty]] === 'undefined')) {

                            // Set temporary value. Since the call is asynchronous, make known
                            // right away that an asset is being fetched
                            fetchedAssets[widget[widgetProperty]] = true;
                            assetPromise = $q.defer();

                            // TODO: add an interceptor for error handling and recovery
                            $http.get(widget[widgetProperty], { cache: $templateCache })
                                .success( function (asset) {
                                    var resolveValue = asset;

                                    if (typeof assetCallback === 'function') {
                                        // process/change the asset
                                        resolveValue = assetCallback(asset, widget);
                                    }
                                    // Replace temporary value with the real asset data
                                    fetchedAssets[widget[widgetProperty]] = resolveValue;
                                    assetPromise.resolve(resolveValue);
                                })
                                .error( function () {
                                    assetPromise.reject(null);
                                });

                            allPromises.push(assetPromise.promise);
                        }
                    } else {
                        throw new Error ('Widget ' + widget.name + ' is missing property: ' + widgetProperty);
                    }
                });

                // All the promises have been resolved
                if (allPromises.length) {
                    $q.all(allPromises)
                        .then( function (allPromises) {
                            var resolveValue = fetchDuplicateAssets ? allPromises : fetchedAssets;
                            deferred.resolve(resolveValue);
                        });
                } else {
                    deferred.resolve(null);
                }
            });
            return deferred.promise;
        };

        this.processPrototype = function processPrototype (prototypeStr, widget) {
            /*jshint evil:true */
            return eval(prototypeStr);
            /*jshint evil:false */
        };

        /* Substitute references to the template's generic placeholder to make them
         * specific to the widgets' model
         */
        this.processTemplate = function processTemplate (templateStr, widget) {
            var widgetModelStr = (widget.name) ? CONFIG.widgets.namespace + '.' + widget.name :
                                                    CONFIG.widgets.tplPlaceholder,
                reWidgetPlaceholder = new RegExp(CONFIG.widgets.tplPlaceholder, 'g');

            return templateStr.replace(reWidgetPlaceholder, widgetModelStr);
        };

        // Method known to be asynchronous by widgets
        this.getAsyncMethodName = function getAsyncMethodName () {
            return CONFIG.widgets.asyncMethodName;
        };

        this.getNamespace = function getNamespace() {
            return CONFIG.widgets.namespace;
        };

    }]);
