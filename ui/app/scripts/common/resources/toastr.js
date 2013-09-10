/* global toastr */
'use strict';

angular.module('resources.toastr', [])
  .factory('toastr', function() {

    toastr.options.timeOut = 3500;

    return toastr;
  });