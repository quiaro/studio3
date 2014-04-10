/* global define, alert, prompt */

define(['require',
        'globals',
        'ace/ace',
        'css!./asset-service'], function( require, globals, ace ) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', 'StudioServices', '$state', '$log',
        function(NgRegistry, StudioServices, $state, $log) {

        console.log('StudioServices: ', StudioServices);

        NgRegistry
            .addState('test', {
                url: '/test-service',
                templateUrl: require.toUrl('./templates/asset-service.tpl.html'),

                requireAuth: false,
                rolesAllowed: ['admin', 'editor']
            })

            .addController('AssetCtrl',
                ['$scope', '$timeout', function($scope, $timeout) {

                    function addToList (list, itemObj) {
                        console.log('File uploaded successfully!');
                        console.log(itemObj);

                        $timeout( function() {
                            $scope.$apply(function () {
                                $scope[list].push({
                                    id: itemObj.id.itemId,
                                    name: itemObj.fileName
                                });
                            });
                        });
                    }

                    var editor = ace.edit('code-editor');

                    editor.setTheme('ace/theme/textmate');
                    editor.getSession().setMode('ace/mode/html');

                    $scope.flags = {
                        action: {
                            insert: true,
                            upload: false
                        },
                        code: {
                            xml: true,
                            ftl: false,
                            asset: false
                        },
                        upload: {
                            xml: false,
                            ftl: false,
                            asset: true
                        }
                    };

                    $scope.codeType = 'xml';

                    $scope.selectedFiles = null;
                    $scope.assetList = [
                        {
                            id: '7e4919d0-34bc-49d5-8369-53ff79f763b5',
                            name: 'chp2.txt'
                        }, {
                            id: '4da8fa24-f5be-4f10-be87-471ae6aac768',
                            name: 'myimg.png'
                        }, {
                            id: '916ee2f3-b3c3-43eb-b178-68fbcd802fab',
                            name: 'chp1.txt'
                        }
                    ];

                    $scope.descList = [
                        {
                            id: '8a6c2ab3-8b66-4a86-8a1b-b222310d8475',
                            name: 'sample-descriptor.xml'
                        }, {
                            id: '1a6d450c-71ef-4daa-b527-e27aec941308',
                            name: 'page-2.xml'
                        }
                    ];

                    $scope.tmplList = [
                        {
                            id: 'c2fb0f22-d7d5-4a8e-a099-1962282373f1',
                            name: 'sample-tmpl.ftl'
                        }, {
                            id: '7e035a2e-1a50-4037-805f-4a647b80cf8f',
                            name: 'service.ftl'
                        }
                    ];

                    $scope.templatePath = require.toUrl('./templates');

                    $scope.reset = function () {
                        $scope.flags.code = {
                            xml: true,
                            ftl: false
                        };
                        editor.setValue('');
                    };

                    $scope.setFlag = function (type, flag) {
                        var flags = $scope.flags;

                        for (var i in flags[type]) {
                            if (flags[type].hasOwnProperty(i)) {
                                flags[type][i] = (i === flag) ? true : false;
                            }
                        }
                    };

                    $scope.insertCode = function (codeFlags) {
                        var code, sampleCode;

                        for (var i in codeFlags) {
                            if (codeFlags[i]) {
                                code = i;
                                break;
                            }
                        }

                        switch(code) {
                            case 'xml':
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
                            case 'ftl':
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

                    $scope.submitCode = function (codeFlags) {
                        var content = editor.getSession().getValue(),
                            fileName = prompt('Please type in a file name');

                        if (content) {
                            if (codeFlags.xml) {
                                StudioServices.Descriptor.create({
                                    content_type_id: 'sampleId',
                                    parent_id: '/test/path',
                                    file_name: fileName,
                                    content: content
                                }).then( function(descriptor) {
                                    addToList('descList', descriptor);
                                });
                            } else {
                                StudioServices.Template.create({
                                    parent_id: '/test/path',
                                    file_name: fileName,
                                    content: content
                                }).then( function(template) {
                                    addToList('tmplList', template);
                                });
                            }
                        } else {
                            if (fileName) {
                                alert('The editor is empty. Add some code and try again!');
                            } else {
                                alert('Please type in a file name and try again!');
                            }
                        }
                    };

                    $scope.uploadAsset = function (asset) {
                        var $file;

                        if ($scope.selectedFiles.length) {
                            $file = $scope.selectedFiles[0];

                            StudioServices.Asset.create({
                                    parent_id: asset.path,
                                    file_name: asset.name,
                                    file: $file,
                                    mime_type: $file.type
                                }).then( function( asset ){
                                    addToList('assetList', asset);
                                }, function() {
                                    console.log('Unable to upload file');
                                });
                        }
                    };

                    $scope.uploadDescriptor = function (descriptor) {
                        var $file;

                        if ($scope.selectedFiles.length) {
                            $file = $scope.selectedFiles[0];

                            StudioServices.Descriptor.create({
                                    content_type_id: descriptor.content_type_id,
                                    parent_id: descriptor.path,
                                    file_name: descriptor.name,
                                    file: $file
                                }).then( function( descriptor ){
                                    addToList('descList', descriptor);
                                }, function() {
                                    console.log('Unable to upload file');
                                });
                        }
                    };

                    $scope.uploadTemplate = function (template) {
                        var $file;

                        if ($scope.selectedFiles.length) {
                            $file = $scope.selectedFiles[0];

                            StudioServices.Template.create({
                                    parent_id: template.path,
                                    file_name: template.name,
                                    file: $file
                                }).then( function( template ){

                                    console.log(template);

                                    addToList('tmplList', template);
                                }, function() {
                                    console.log('Unable to upload file');
                                });
                        }
                    };

                    $scope.readItem = function (type, itemId) {
                        var promise, option;

                        switch(type) {
                            case 'descriptor':
                                promise = StudioServices.Descriptor.readText(itemId);
                                option = 'xml';
                                editor.getSession().setMode('ace/mode/xml');
                                break;
                            case 'template':
                                promise = StudioServices.Template.readText(itemId);
                                option = 'ftl';
                                editor.getSession().setMode('ace/mode/ftl');
                                break;
                            case 'asset':
                                promise = StudioServices.Asset.getContent(itemId);
                                option = 'asset';
                                editor.getSession().setMode('ace/mode/text');
                                break;
                        }

                        promise.done( function(content, status, xhr) {

                            console.log('Item Content: ', content);

                            console.log('Mime Type: ', xhr.getResponseHeader('Content-Type'));

                            $timeout( function() {
                                $scope.$apply(function () {
                                    $scope.setFlag('action', 'insert');
                                    $scope.setFlag('code', option);

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
