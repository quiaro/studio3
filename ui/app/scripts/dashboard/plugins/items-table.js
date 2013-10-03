'use strict';

(function () {

    return {
        // real value for data property will be assigned in the getData method
        data: {},

        // number of results to show
        filterLength: 0,

        // type of items to show (defaults to all types)
        filterType: '',

        getData: function getData (filterOpts) {

            // We need to use call to preserve the context (this)
            (function (filterOpts) {
                var that = this;

                angular.injector(['ng', 'common']).invoke(function (repo) {
                    repo.list(filterOpts)
                        .then( function (data) {
                            that.data = data;
                            that.filterLength = data.length;
                        });
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
}) ();
