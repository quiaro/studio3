/* global define, alert, prompt */

define(['require',
        'globals',
        'ace/ace',
        'module',
        'directives',
        'css!./asset-service'], function( require, globals, ace, module ) {

    'use strict';

    var config = module.config().config,
        injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', 'ServiceProviders', 'DefaultServiceProvider', '$state', '$log',
        function(NgRegistry, ServiceProviders, DefaultServiceProvider, $state, $log) {

        var serviceProvider = (config && config.serviceProvider) ?
                                ServiceProviders[config.serviceProvider] :
                                ServiceProviders[DefaultServiceProvider];

        NgRegistry
            .addState('test', {
                url: '/test-service',
                templateUrl: require.toUrl('./templates/asset-service.tpl.html'),

                requireAuth: false,
                rolesAllowed: ['admin', 'editor']
            })

            .addController('AssetCtrl',
                ['$scope', '$timeout', function($scope, $timeout) {

                    var treeNav, treeNavClearWatch,
                        editor = ace.edit('code-editor');

                    // Initialize scope values
                    treeNav = $scope.treeNav = {};
                    $scope.selectedFiles = null;
                    $scope.nodeSelected = null;
                    $scope.action = 'upload';
                    $scope.templatePath = require.toUrl('./templates');

                    // Set editor settings
                    editor.setTheme('ace/theme/textmate');
                    editor.getSession().setMode('ace/mode/html');

                    // Set up watches
                    treeNavClearWatch = $scope.$watchCollection('treeNav', function(newValue, oldValue) {

                        if (newValue === oldValue) { return; }

                        $scope.nodeSelected = treeNav.select_first_branch();
                        $scope.fileType = $scope.nodeSelected.contentType;
                        $scope.isFolder = $scope.nodeSelected.folder;

                        if ($scope.isFolder) {
                            $scope.action = 'create';
                        } else {
                            $scope.action = 'edit';
                        }
                        treeNavClearWatch();
                    });

                    $scope.updateSelected = function updateSelected(branch) {
                        $scope.nodeSelected = branch;
                        $scope.fileType = branch.contentType;
                        $scope.isFolder = branch.folder;

                        editor.setValue('');

                        // If the action currently selected is upload, then stay as is
                        if ($scope.fileType === 'asset') {
                            $scope.action = 'upload';
                            if (!$scope.isFolder) {
                                $scope.readItem(branch.contentType, branch.id.itemId);
                            }
                        } else {
                            if ($scope.action !== 'upload') {
                                if ($scope.isFolder) {
                                    $scope.action = 'create';
                                } else {
                                    $scope.action = 'edit';
                                    $scope.readItem(branch.contentType, branch.id.itemId);
                                }
                            }
                        }
                    };

                    $scope.reset = function () {
                        editor.setValue('');
                    };

                    $scope.insertCode = function (fileType) {
                        var sampleCode;

                        switch(fileType) {
                            case 'descriptor':
                                editor.getSession().setMode('ace/mode/xml');
                                sampleCode = '<?xml version="1.0" encoding="UTF-8"?>\n' +
                                             '<page>\n' +
                                             '  <content-type>/page/page</content-type>\n' +
                                             '  <merge-strategy>inherit-levels</merge-strategy>\n' +
                                             '  <file-name>site-dashboard.xml</file-name>\n' +
                                             '  <folder-name>site-dashboard</folder-name>\n' +
                                             '  <internal-name>Site Dashboard</internal-name>\n' +
                                             '  <title>PAGE-TITLE-SITE-DASHBOARD</title>\n' +
                                             '  <url>site-dashboard</url>\n' +
                                             '  <template>site-dashboard</template>\n' +
                                             '  <createdDate>1/3/2014 14:57:31</createdDate>\n' +
                                             '  <lastModifiedDate>1/3/2014 14:57:31</lastModifiedDate>\n' +
                                             '</page>\n';
                                break;
                            case 'template':
                                editor.getSession().setMode('ace/mode/ftl');
                                sampleCode = '<html>\n' +
                                             '<head>\n' +
                                             '  <title>Welcome!</title>\n' +
                                             '</head>\n' +
                                             '<body>\n' +
                                             '  <h1>\n' +
                                             '    Welcome ${user}<#if user == "Big Joe">, our beloved leader</#if>!\n' +
                                             '  </h1>\n' +
                                             '  <p>Our latest product:\n' +
                                             '  <a href="${latestProduct.url}">${latestProduct.name}</a>!\n' +
                                             '</body>\n' +
                                             '</html>\n';
                                break;
                        }
                        editor.getSession().setValue(sampleCode);
                    };

                    $scope.submitCode = function (action, fileType, nodeSelected) {
                        var content = editor.getSession().getValue(),
                            itemId = (nodeSelected && nodeSelected.id && nodeSelected.id.itemId) || '/',
                            fileName;

                        if (content) {

                            if (action === 'create') {

                                fileName = prompt('Please type in a file name');

                                if (!fileName) {
                                    alert('Please type in a file name and try again!');
                                    return;
                                }

                                if (fileType === 'descriptor') {
                                    serviceProvider.Descriptor.create({
                                        content_type_id: 'sampleId',
                                        parent_id: itemId,
                                        file_name: fileName,
                                        content: content
                                    }).then( function(descriptor) {
                                        descriptor.contentType = nodeSelected.contentType;
                                        $timeout( function() {
                                            treeNav.add_branch(nodeSelected, descriptor);
                                        });
                                    });
                                } else if (fileType === 'template') {
                                    serviceProvider.Template.create({
                                        parent_id: itemId,
                                        file_name: fileName,
                                        content: content
                                    }).then( function(template) {
                                        template.contentType = nodeSelected.contentType;
                                        $timeout( function() {
                                            treeNav.add_branch(nodeSelected, template);
                                        });
                                    });
                                }
                            } else {

                                // update existing content
                                if (fileType === 'descriptor') {
                                    serviceProvider.Descriptor.update({
                                        item_id: itemId,
                                        content: content
                                    }).then( function(descriptor) {
                                        // console.log('Descriptor updated: ', descriptor);
                                    });
                                } else if (fileType === 'template') {
                                    serviceProvider.Template.update({
                                        item_id: itemId,
                                        content: content
                                    }).then( function(template) {
                                        // console.log('Template updated: ', template);
                                    });
                                }
                            }
                        } else {
                            alert('The editor is empty. Add some code and try again!');
                        }
                    };

                    $scope.uploadAsset = function (isFolder, nodeSelected, asset) {
                        var $file, itemId;

                        if ($scope.selectedFiles.length) {
                            $file = $scope.selectedFiles[0];
                            itemId = (nodeSelected && nodeSelected.id && nodeSelected.id.itemId) || '/';

                            if (isFolder) {

                                serviceProvider.Asset.create({
                                    parent_id: itemId,
                                    file_name: asset.name,
                                    file: $file,
                                    mime_type: $file.type
                                }).then( function( asset ){
                                    asset.contentType = nodeSelected.contentType;
                                    $timeout( function() {
                                        treeNav.add_branch(nodeSelected, asset);
                                    });
                                }, function() {
                                    console.log('Unable to upload file');
                                });
                            } else {

                                serviceProvider.Asset.update({
                                    item_id: itemId,
                                    file: $file
                                }).then( function( asset ){
                                    $scope.readItem(nodeSelected.contentType, itemId);
                                }, function() {
                                    console.log('Unable to upload file');
                                });
                            }
                        }
                    };

                    $scope.uploadDescriptor = function (isFolder, nodeSelected, descriptor) {
                        var $file, itemId;

                        if ($scope.selectedFiles.length) {
                            $file = $scope.selectedFiles[0];
                            itemId = (nodeSelected && nodeSelected.id && nodeSelected.id.itemId) || '/';

                            if (isFolder) {

                                serviceProvider.Descriptor.create({
                                    content_type_id: descriptor.content_type_id,
                                    parent_id: itemId,
                                    file_name: descriptor.name,
                                    file: $file
                                }).then( function( descriptor ){
                                    descriptor.contentType = nodeSelected.contentType;
                                    $timeout( function() {
                                        treeNav.add_branch(nodeSelected, descriptor);
                                    });
                                }, function() {
                                    console.log('Unable to upload file');
                                });
                            } else {

                                serviceProvider.Descriptor.update({
                                    item_id: itemId,
                                    file: $file
                                }).then( function( descriptor ){
                                    console.log('Descriptor updated: ', descriptor);
                                }, function() {
                                    console.log('Unable to upload file');
                                });
                            }
                        }
                    };

                    $scope.uploadTemplate = function (isFolder, nodeSelected, template) {
                        var $file, itemId;

                        if ($scope.selectedFiles.length) {
                            $file = $scope.selectedFiles[0];
                            itemId = (nodeSelected && nodeSelected.id && nodeSelected.id.itemId) || '/';

                            if (isFolder) {

                                serviceProvider.Template.create({
                                    parent_id: itemId,
                                    file_name: template.name,
                                    file: $file
                                }).then( function( template ){
                                    template.contentType = nodeSelected.contentType;
                                    $timeout( function() {
                                        treeNav.add_branch(nodeSelected, template);
                                    });
                                }, function() {
                                    console.log('Unable to upload file');
                                });
                            } else {

                                serviceProvider.Template.update({
                                    item_id: itemId,
                                    file: $file
                                }).then( function( template ){
                                    console.log('Template updated: ', template);
                                }, function() {
                                    console.log('Unable to upload file');
                                });
                            }
                        }
                    };

                    $scope.readItem = function (type, itemId) {
                        var promise;

                        switch(type) {
                            case 'descriptor':
                                promise = serviceProvider.Descriptor.readText(itemId);
                                editor.getSession().setMode('ace/mode/xml');
                                break;
                            case 'template':
                                promise = serviceProvider.Template.readText(itemId);
                                editor.getSession().setMode('ace/mode/ftl');
                                break;
                            case 'asset':
                                promise = serviceProvider.Asset.getContent(itemId);
                                editor.getSession().setMode('ace/mode/text');
                                break;
                        }

                        promise.done( function(content, status, xhr) {
                            $timeout( function() {
                                $scope.$apply(function () {
                                    editor.getSession().setValue(content);
                                });
                            });

                        });

                        promise.fail( function() {
                            console.log('Unable to read data from post with id: ' + itemId);
                        });
                    };

                    $scope.onFileSelect = function($files) {
                        // $files: an array of files selected, each file has name, size, and type.
                        $scope.selectedFiles = $files;
                    };

            }])

            .addDirective('sdoFileSelect', ['$parse', '$http',
                function($parse, $http) {
                    return function(scope, elem, attr) {
                        var fn = $parse(attr.sdoFileSelect);
                        elem.bind('change', function(evt) {
                            var files = [],
                                fileList, i;
                            fileList = evt.target.files;
                            if (fileList !== null) {
                                for (i = 0; i < fileList.length; i++) {
                                    files.push(fileList.item(i));
                                }
                            }
                            scope.$apply(function() {
                                fn(scope, {
                                    $files: files,
                                    $event: evt
                                });
                            });
                        });
                        elem.bind('click', function() {
                            this.value = null;
                        });
                    };
                }
            ]);

    }]);

});
