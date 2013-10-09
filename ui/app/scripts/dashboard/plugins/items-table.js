'use strict';

(function () {

    return {
        // real value for data property will be assigned in the getAsyncData method
        data: {},

        // length filter: number of results to show
        filterLength: {
            show: true,
            value: 0
        },

        // type filter: type of items to show (defaults to all types)
        filterType: {
            show: true,
            value: ''
        },

        // widget's asynchronous method to load model data
        getAsyncData: function getAsyncData ($timeout) {

            // Preserve the context (this)
            var that = this;

            angular.injector(['ng', 'common']).invoke(function (repo) {
                // Filter options to be defined here; they could also be defined as
                // a widget property -which would make them configurable
                var filterOpts = {};

                repo.list(filterOpts)
                    .then( function (data) {

                        $timeout(function () {
                            // Delay the updates to the model until it's safe
                            // to start a new digest cycle in Angular
                            that.data = data;

                            if (data.length) {
                                that.filterLength.value = data.length;
                            }
                        });
                    });
            });
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
}) ();
