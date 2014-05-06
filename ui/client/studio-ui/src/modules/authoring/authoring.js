/* global define */

define(['globals',
        'module',
        'require',
        './editor/scripts/require-config.js',
        'css!./authoring'], function( globals, module, require, RequireConfig ) {

    'use strict';

    var config = module.config().config,
        injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', '$log', function(NgRegistry, $log) {

        NgRegistry
            .addState('studio.authoring', {
                url: '/author/*path',
                templateUrl: require.toUrl('./templates/authoring.tpl.html'),
                resolve: {
                    content: ['Language', function (Language) {
                        return Language.from(require.toUrl(config.lang_folder));
                    }]
                },
                controller: 'AuthoringCtrl',
                requireAuth: false,
                rolesAllowed: ['admin', 'editor']
            })

            .addDirective('editorIframe',
                ['$rootScope', function($rootScope) {

                function setupEditor(iframeEl) {
                    // console.log('requirejs: ', requirejs);
                }

                function setupEventBridge(iframeEl) {

                }

                return {
                    restrict: 'E',
                    replace: true,
                    template: '<iframe>',
                    controller: ['$scope', '$element', '$attrs', '$transclude', '$http', '$stateParams', 'Utils', 'CONFIG',
                        function($scope, $element, $attrs, $transclude, $http, $stateParams, Utils, CONFIG) {

                        var reqConfig = RequireConfig(CONFIG, config.editor);

                        $http({
                            method: 'GET',
                            url: $stateParams.path + '.html',
                            cache: false,
                            transformResponse: function(data) {
                                // Inject the editor into the page
                                var requireMapping = CONFIG.requirejs.module_paths['requirejs'],
                                    editorInjectStr;

                                if (!requireMapping) {
                                    throw new Error('No path mapping found for \'requirejs\' in module_paths');
                                }

                                editorInjectStr =   '<!-- Studio Editor Injection -->\n' +
                                                    '<script ' +
                                                        'src="' + Utils.getUrl(CONFIG.base_url, requireMapping) + '.js' + '">' +
                                                    '</script>\n' + reqConfig + '\n' +
                                                    '<script>requirejs(["editor/editor"]);</script>\n'+
                                                    '<!-- EO: Studio Editor Injection -->';

                                data = data.replace(/<\/body>/gm,
                                                    editorInjectStr + '\n</body>');
                                return data;
                            }
                        }).then( function (response) {

                            // Editor injection per:
                            // http://sparecycles.wordpress.com/2012/03/08/inject-content-into-a-new-iframe/
                            $element[0].contentWindow.contents = response.data;
                            $element.attr('src', 'javascript:window["contents"]');

                            $scope.$on(config.editor.load_event, function (event, args) {
                                // setupEventBridge
                            });
                        })

                    }]
                };
            }])

            .addFactory('eventBridge',
                ['$rootScope', '$log', function($rootScope, $log) {

                // Get all the events from the registry that we want to pass on
                // from angular to the editor
                var iframeCache, requireFuncCache;

                function getIframe() {
                    iframeCache = iframeCache || angular.element('#' + config.editor.frame_id);
                    return iframeCache;
                }

                function getRequireFunc(iframe) {
                    if (!requireFuncCache && iframe.length) {
                        requireFuncCache = iframe[0].contentWindow.require;
                    }
                    return requireFuncCache;
                }

                config.bridged_events.forEach( function(evt) {
                    // For each bridged event, create a listener.
                    // When the event is published in angular, this listener will
                    // publish it on the editor as well (using the PubSub module).
                    $rootScope.$on(evt, function(event, data) {
                        var iframe, requireFunc;

                        // We're going to add a property to the message data "cancelBridge"
                        // to check if we should pass on an event or not and avoid creating
                        // an endless loop
                        if (!data.cancelBridge) {
                            data.cancelBridge = true;

                            iframe = getIframe();
                            requireFunc = getRequireFunc(iframe);

                            if (requireFunc) {
                                requireFunc([config.editor.pubsub_module], function (editorPubSub) {
                                    editorPubSub.publish(evt, data);
                                });
                            } else {
                                $log.warn('The global variable \'require\' is not present in the editor iframe');
                            }
                        }
                    });
                });

            }])

            .addController('AuthoringCtrl',
                ['$scope', '$rootScope', 'Language', 'content',
                    function($scope, $rootScope, Language, content) {

                $scope.content = content;

                $rootScope.$on('$sdoChangeLanguage', function(evt, langId){
                    Language.from(require.toUrl(config.lang_folder)).then( function(content) {
                        $scope.content = content;
                    });
                });

                $scope.authoring = {
                    tools: {
                        state: 'off',
                        height: 0,
                        tabs: [{
                            name: 'content',
                            title: 'Content',
                            contentUrl: require.toUrl('./templates/tabs/content.html')
                        }, {
                            name: 'template',
                            title: 'Template',
                            contentUrl: require.toUrl('./templates/tabs/template.html')
                        }, {
                            name: 'revisions',
                            title: 'Revisions',
                            contentUrl: require.toUrl('./templates/tabs/revisions.html')
                        }, {
                            name: 'info',
                            title: 'Info',
                            contentUrl: require.toUrl('./templates/tabs/info.html')
                        }],
                        activeTab: 'content'
                    },
                    setActiveTab: function setActiveTab (tabName) {
                        this.tools.activeTab = tabName;
                    }
                };

                $scope.selectedElement = 'none';

                $scope.$on('editor/element/edit', function (event, args) {
                    $scope.$apply(function () {
                        $scope.authoring.tools.state = 'on';
                        $scope.authoring.tools.height = 30;
                    });
                });

                $scope.$on('editor/element/select', function (event, args) {
                    $scope.$apply(function () {
                        $scope.selectedElement = args.id;
                    });
                });

                $scope.updateElement = function () {
                    console.log('Calling updateElement ... ');
                    $scope.$broadcast('app/element/update', "Element updated in authoring module");
                };

            }]);

    }]);

});
