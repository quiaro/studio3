/* global define */

define(['require',
        'globals',
        'css!./asset-service'], function( require, globals ) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', 'TestStudioServices', '$state', '$log',
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

                    $scope.selectedFiles = null;
                    $scope.assets = {};
                    $scope.assetList = [
                        {   id: '7e4919d0-34bc-49d5-8369-53ff79f763b5',
                            name: 'chp2.txt'
                        }, {
                            id: '4da8fa24-f5be-4f10-be87-471ae6aac768',
                            name: 'myimg.png'
                        }
                    ];

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
                                    console.log('File uploaded successfully!');
                                    console.log(asset);

                                    $timeout( function() {
                                        $scope.$apply(function () {
                                            $scope.assetList.push({
                                                id: asset.id.itemId,
                                                name: asset.fileName
                                            });
                                        });
                                    });

                                }, function() {
                                    console.log('Unable to upload file');
                                });
                        }
                    };

                    $scope.readAsset = function (assetId) {

                        console.log("Reading asset!");

                        StudioServices.Asset.getContent(assetId)
                            .then(function(content) {

                                console.log("Asset Content: ", content);

                                $timeout( function() {
                                    $scope.$apply(function () {
                                        $scope.assetContent = content;
                                    });
                                });

                            }, function() {
                                console.log('Unable to read data from post with id: ' + assetId);
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
