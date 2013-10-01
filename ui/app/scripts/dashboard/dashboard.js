'use strict';

angular.module('dashboard', ['common'])

    // Filter items depending on their category: pages, components, assets or any
    .filter('typeFilter', function() {
        return function (items, category) {
            if (!category || '' === category) {
                return items;
            }

            return items.filter(function(element, index, array) {
                return element.type === category;
            });
        }
    })

    .controller('DashboardCtrl',
		['$scope', 'repo', 'notifications', function($scope, repo, notifications) {

        $scope.notifications = notifications;

        var table = {
            // real value for data property will be assigned in the getData method
            data: {},

            // number of results to show
            filterLength: 0,

            getData: function getData (filterOpts) {

                // We need to use call to preserve the context (this)
                (function (filterOpts) {
                    var that = this;

                    repo.list(filterOpts)
                        .then( function (data) {
                            that.data = data;
                            that.filterLength = data.length;
                        });
                }).call(this, filterOpts);
            },

            setSortClass: function setSortClass (column) {
                var sortOrder;

                sortOrder = (this.sort.descending) ? 'descending' : 'ascending';
                return column === this.sort.column && 'sort-' + sortOrder;

            },

            changeSorting: function changeSorting (column) {
                var sort = this.sort;
                if (sort.column === column) {
                    sort.descending = !sort.descending;
                } else {
                    sort.column = column;
                    sort.descending = false;
                }
            }
        };

        // Dashboard tables : Make all dashboard tables inherit from the table object
        $scope.recentActivity = Object.create(table);

        $scope.recentActivity.sort = {
            column : 'lastAuthor',
            descending : false
        };
        $scope.recentActivity.filterType = '';

	}]);

