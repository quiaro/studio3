'use strict';

(function () {

    return {
        // real value for data property will be assigned in the getAsyncData method
        data: {},

        // number of results to show
        filterLength: 0,

        // type of items to show (defaults to all types)
        filterType: '',

        // widget's asynchronous method to load model data
        getAsyncData: function getAsyncData ($timeout) {

            // We need to use call to preserve the context (this)
            (function () {
                var that = this;

                angular.injector(['ng', 'common']).invoke(function (repo) {
                    // Filter options to be defined here
                    var filterOpts = {};

                    repo.list(filterOpts)
                        .then( function (data) {

                            $timeout(function () {
                                // Delay the updates to the model until it's safe
                                // to start a new digest cycle
                                that.data = data;

                                if (data.length) {
                                    that.filterLength = data.length;
                                }
                            });
                        });
                });

            }).call(this);
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
