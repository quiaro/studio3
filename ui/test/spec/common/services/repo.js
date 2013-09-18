'use strict';

describe('Module: Common', function () {

  var repoService, httpBackend,
      alertDialog = jasmine.createSpyObj('alertDialog', ['open']);

  beforeEach( function () {

    module('common', function ($provide) {
      $provide.value('util', {
          getServiceURL : function () {
            return '/url/to/backend/service';  
          }
      });
      $provide.value('alertDialog', alertDialog);
    });

  });

  describe('Service: Repo', function () {

    beforeEach(inject(function($httpBackend, repo) {
      httpBackend = $httpBackend;
      repoService = repo;
    }));

    afterEach(function() {
      httpBackend.verifyNoOutstandingExpectation();
      httpBackend.verifyNoOutstandingRequest();
    });

    it('should return a list of items', function () {
      var promise;
      
      httpBackend.expectGET('/url/to/backend/service').respond(200, ['a', 'b']);
      promise = repoService.list();

      promise.then( function (data) {
        expect(data).toEqual(['a', 'b']);
      });  

      httpBackend.flush();
      
    });

    it('should return null and display an error message when it fails', function () {
      var promise, promiseArr = [];

      // All methods in the repo service should behave the same
      for (var method in repoService) {
        httpBackend.expectGET('/url/to/backend/service').respond(500);
        promise = repoService[method]();

        promise.then( function (data) {
          // should not be called because the service call is returning a 500 -just in case, we'll make it fail
          expect(data).toBe(null);
        }, function (data) {
          expect(alertDialog.open).toHaveBeenCalled();
          expect(data).toBe(null);
        });

        // Store away the promise in an array
        promiseArr.push(promise);
        httpBackend.flush();
      }
    });

  });
    
});
