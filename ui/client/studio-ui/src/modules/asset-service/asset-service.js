/* global define */

define(['require',
        'globals',
        'css!./asset-service'], function( require, globals ) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    injector.invoke(['NgRegistry', 'StudioServices', '$state', '$log',
        function(NgRegistry, StudioServices, $state, $log) {

        NgRegistry
            .addState('test', {
                url: '/test-service',
                templateUrl: require.toUrl('./templates/asset-service.tpl.html'),

                requireAuth: false,
                rolesAllowed: ['admin', 'editor']
            })

            .controller('AssetUploadCtrl',
                ['$scope', 'AssetService', function($scope, AssetService) {

                    $scope.selectedFiles = null;

                    $scope.uploadAsset = function (asset) {
                        var $file;

                        if ($scope.selectedFiles.length) {
                            $file = $scope.selectedFiles[0];

                            AssetService.upload({
                                /*jslint camelcase:false */
                                data: {
                                    parent_id: asset.path,
                                    file_name: asset.name,
                                    mime_type: $file.type
                                },
                                /*jslint camelcase:true */
                                file: $file
                            }).success(function(data, status, headers, config) {
                                // file is uploaded successfully
                                console.log('File uploaded successfully!');
                                console.log(data);
                            }).error( function () {
                                console.log('Unable to upload file');
                            });
                        }
                    };

                    $scope.onFileSelect = function($files) {
                        // $files: an array of files selected, each file has name, size, and type.
                        $scope.selectedFiles = $files;
                    };
            }])

            .controller('AssetReadCtrl',
                ['$scope', '$timeout', 'AssetService', function($scope, $timeout, AssetService) {

                    $scope.readAsset = function (asset) {

                        AssetService.read({
                            params: {
                                /*jslint camelcase:false */
                                item_id: asset.id
                                /*jslint camelcase:true */
                            }
                        }).success(function(data, status, headers, config) {
                            $timeout( function() {
                                $scope.$apply(function () {
                                    $scope.asset.content = data;
                                });
                            });
                        }).error(function () {
                            console.log('Unable to read data from post with id: ' + asset.id);
                        });
                    };
            }])

            .directive('sdoFileSelect', ['$parse', '$http',
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
