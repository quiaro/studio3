'use strict';

angular.module('crafter.studio.test-service', ['crafter.studio.common', 'ui.router'])

    .constant('TESTSERVICE', {
        baseUrl: '/studio-ui/src/app/test-service/'
    })

    .config(['$stateProvider',
        '$urlRouterProvider',
        'TESTSERVICE', function ($stateProvider, $urlRouterProvider, TESTSERVICE) {

        $stateProvider
            .state('test', {
                url: '/test-service',
                templateUrl: TESTSERVICE.baseUrl + 'templates/test-service.tpl.html'
            });
    }])

    .controller('AssetUploadCtrl',
        ['$scope', 'assetService', function($scope, assetService) {

            $scope.selectedFiles = null;

            $scope.uploadAsset = function (asset) {
                var $file;

                if ($scope.selectedFiles.length) {
                    $file = $scope.selectedFiles[0];

                    console.log("Uploading file: ", $file);

                    assetService.upload({
                        data: {
                            destination_path: asset.path,
                            file_name: asset.name,
                            mime_type: $file.type
                        },
                        file: $file
                    }).success(function(data, status, headers, config) {
                        // file is uploaded successfully
                        console.log("File uploaded successfully!");
                        console.log(data);
                    }).error( function () {
                        console.log('Unable to upload file');
                    });
                }
            }

            $scope.onFileSelect = function($files) {
                // $files: an array of files selected, each file has name, size, and type.
                $scope.selectedFiles = $files;
            };
    }])

    .controller('AssetReadCtrl',
        ['$scope', '$timeout', 'assetService', function($scope, $timeout, assetService) {

            $scope.readAsset = function (asset) {

                assetService.read({
                    params: {
                        item_id: asset.id
                    }
                }).success(function(data, status, headers, config) {
                    $timeout( function() {
                        $scope.$apply(function () {
                            $scope.asset.content = data;
                        })
                    });
                }).error(function () {
                    console.log('Unable to read data from post with id: ' + asset.id);
                })
            }
    }]);

