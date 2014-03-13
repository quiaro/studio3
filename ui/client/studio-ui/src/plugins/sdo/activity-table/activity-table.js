/* global define */

/*jshint -W040 */
define(['require', 'globals'],
    function( require, globals ) {

    'use strict';

    var injector = angular.element(globals.dom_root).injector();

    function setSortClass(column) {
        var sortOrder;

        sortOrder = (this.sort.descending) ? 'descending' : 'ascending';
        return column === this.sort.column && 'sort-' + sortOrder;
    }

    function changeSorting(column) {
        var sort = this.sort;
        if (sort.column === column) {
            sort.descending = !sort.descending;
        } else {
            sort.column = column;
            sort.descending = false;
        }
    }

    injector.invoke(['NgRegistry', function(NgRegistry) {

            NgRegistry
                .addFilter('sdoActivityTableTypeFilter', function() {
                    return function filterByType (items, type) {
                        if (!type || '' === type) {
                            return items;
                        }

                        return items.filter(function(element, index, array) {
                            return element.type === type;
                        });
                    };
                })

                .addDirective('sdoActivityTableFilterType', function() {
                    return {
                        restrict: 'E',
                        replace: true,
                        templateUrl: require.toUrl('./templates/filter-type.tpl.html')
                    };
                })

                .addDirective('sdoActivityTableFilterLength', function() {
                    return {
                        restrict: 'E',
                        replace: true,
                        templateUrl: require.toUrl('./templates/filter-length.tpl.html')
                    };
                });
        }
    ]);

    return {
        table: {
            sort: {
                column: 'name',
                descending: false
            },
            setSortClass: setSortClass,
            changeSorting: changeSorting,
            data: {}
        },
        filterType: {
            header: 'Type',
            tip: 'Filter by item type',
            selected: '',
            options: [{
                value: '',
                text: 'Any'
            }, {
                value: 'page',
                text: 'Page'
            }, {
                value: 'component',
                text: 'Component'
            }, {
                value: 'asset',
                text: 'Asset'
            }]
        },
        filterLength: {
            header: 'Items',
            tip: 'Filter by number of results',
            value: 0
        }
    };

});
/*jshint -W040 */
