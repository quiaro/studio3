/* global toastr */
'use strict';

angular.module('common')
  .factory('toastr', function() {

    toastr.options.timeOut = 3500;

    return toastr;
  });