'use strict';

angular.module('crafter.studio-ui.services.AuditService', [])

    .factory('AuditService', ['$http', '$q', 'AppService', '$modal',
        function($http, $q, AppService, $modal) {

            var api = 'audit';

            function activity(filtersObj) {

                var url,
                    searchStr = '',
                    deferred = $q.defer();

                for (var filter in filtersObj) {
                    if (filtersObj.hasOwnProperty(filter)) {
                        searchStr += filter + '=' + filtersObj[filter];
                    }
                }
                url = AppService.getServiceURL(api, 'activity', searchStr);

                AppService.makeServiceCall(url, deferred);
                return deferred.promise;
            }

            function log(item, version) {

                var searchStr, url,
                    deferred = $q.defer();

                searchStr = 'item=' + item + 'version=' + version;
                url = AppService.getServiceURL(api, 'log', searchStr);

                AppService.makeServiceCall(url, deferred);
                return deferred.promise;
            }

            // expose the API to the user
            return {
                activity: activity,
                log: log
            };
        }
    ]);
