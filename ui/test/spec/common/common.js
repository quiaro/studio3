'use strict';

describe('Module: Common', function () {

  var utilService;

  beforeEach(module('common'));

  describe('Service: Util', function () {

    beforeEach(inject(function (util) {
      utilService = util;
      utilService.setEnvProperty('siteName', 'pebbles');
    }));

    it('should correctly calculate the service URL', function () {

      var siteName, urlBase, apiVersion, res, url;

      siteName = utilService.getEnvProperty('siteName');
      urlBase = utilService.getEnvProperty('urlBase');
      apiVersion = utilService.getEnvProperty('apiVersion');

      url = utilService.getServiceURL('category','method','');
      res = '/' + urlBase + '/' + apiVersion + '/category/method/' + siteName;
      expect(url).toBe(res);

      url = utilService.getServiceURL('category','method','stringWithSearchParams');
      res = '/' + urlBase + '/' + apiVersion + '/category/method/' + siteName + '?stringWithSearchParams';
      expect(url).toBe(res);  
    });

  });
  
});
