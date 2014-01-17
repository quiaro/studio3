'use strict';

angular.module('crafter.studio.common')

    /* 
     * Service that compiles miscellaneous functions used all over 
     */
    .service('UtilsService', 
        function() {

                // Takes a string of the form: "the {tree} is behind the {building}" and uses a
                // replace object { 'tree': 'cedar', 'building': 'National Museum'} to replace the
                // placeholders.
                // Throws an error if there are placeholders that don't have a replace value
                this.replacePlaceholders = function replacePlaceholders (string, replaceObj) {

                    function replacePlaceholder(match) {
                        var key = match.substring(1, match.length - 1), // remove '{' and '}' from the match string
                            replaceValue = replaceObj[key];
                        if (replaceValue) {
                            return replaceValue;
                        } else {
                            throw new Error('Placeholder "' + key + '" does not have a replace value in ' + string);
                        }
                    }

                    return string.replace(/{.*}/g, function(m) { return replacePlaceholder(m); } );
                };

                this.arrayIntersection = function arrayIntersection(array1, array2) {
                    return array1.filter(function(el) {
                        return array2.indexOf(el) !== -1;
                    });
                };
            }
        );