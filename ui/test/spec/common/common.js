'use strict';

describe('Module: Common', function () {

  var utilService;

  beforeEach( function () {

    module('common', function ($provide) {
      $provide.constant('REGISTRY', {
        path : '/url/to/registry'
      });
    });

  });

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

    it('should return the registry', inject(function ($httpBackend) {

      var httpBackend = $httpBackend,
          promise;

      httpBackend.expectGET('/url/to/registry').respond(200, { "key": "value" });
      promise = utilService.getRegistry();

      promise.then( function (data) {
        expect(data).toEqual({ "key": "value" });
      });  

      httpBackend.flush();

      httpBackend.verifyNoOutstandingExpectation();
      httpBackend.verifyNoOutstandingRequest();
    }));

  });
  
});
