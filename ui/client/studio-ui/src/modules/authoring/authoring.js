/* global define */

define(['globals',
        'module',
        'require',
        'pubsub'], function( globals, module, require, pubsub ) {

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
                ['eventBridge', '$rootScope', function(eventBridge, $rootScope) {
                return {
                    restrict: 'E',
                    replace: true,
                    template: '<iframe id="' + config.editor.frame_id + '">'
                };
            }])

            .addFactory('eventBridge',
                ['$rootScope', '$log', 'CONFIG', function($rootScope, $log, CONFIG) {

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

                CONFIG.bridged_events.forEach( function(evt) {
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
                ['$scope', '$rootScope', '$stateParams', 'Language', 'content',
                    function($scope, $rootScope, $stateParams, Language, content) {

                $scope.content = content;

                $rootScope.$on('$sdoChangeLanguage', function(evt, langId){
                    Language.from(require.toUrl(config.lang_folder)).then( function(content) {
                        $scope.content = content;
                    });
                });

                $scope.authoring = {
                    site: $stateParams.path + '.html',
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

            }]);

    }]);

});


/* TODO : Remove the controllers below. These are here only to test communication from
 * the angular app to the editor
 */
function OneCtrl ($scope, $rootScope) {
    $scope.locked = {};
    $scope.locked.value = true;

    $scope.lockView = function lockView() {
        $rootScope.$broadcast('app/element/update', {
            id: $scope.selectedElement,
            state: 'locked'
        });
    };
}

function TwoCtrl ($scope, $rootScope) {
    $scope.readonly = {};
    $scope.readonly.value = false;

    $scope.readOnlyView = function readOnlyView() {
        $rootScope.$broadcast('app/element/update', {
            id: $scope.selectedElement,
            state: 'read'
        });
    };
}

function ThreeCtrl ($scope, $rootScope) {
    $scope.edit = {};
    $scope.edit.value = true;

    $scope.editView = function editView() {
        $rootScope.$broadcast('app/element/update', {
            id: $scope.selectedElement,
            state: 'edit'
        });
    };
}

OneCtrl.$inject = ['$scope', '$rootScope'];

TwoCtrl.$inject = ['$scope', '$rootScope'];

ThreeCtrl.$inject = ['$scope', '$rootScope'];
