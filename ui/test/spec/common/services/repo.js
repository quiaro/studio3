'use strict';

describe('Service: Repo', function () {

  var repoService, httpBackend, params;

  beforeEach(module('services.repo'));

  beforeEach(inject(function($httpBackend, $http, $service, repo) {

    httpBackend = $httpBackend;
    params = {
      $http: $http,
      $window: jasmine.createSpyObj('$window', ['alert']), // overrides $window with a mock version so window.alert() will not block the test runner with a real alert box
      util: {
        getServiceURL: function getServiceURL() {
          return '/url/to/backend/service';
        }
      }
    };
    repo.$inject = params;
  }));

  afterEach(function() {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  xit('should return a list of items if successful', function () {
    var val;
    
    httpBackend.expectGET('/url/to/backend/service').respond(200, ['a']);
    val = repoService.getList();
    httpBackend.flush();
    expect(val).toEqual(['a']);
  });

  xit('should return null and display an error message if it fails', function () {
    var val;

    httpBackend.expectGET('/url/to/backend/service').respond(500);
    val = repoService.getList();
    httpBackend.flush();
    expect(params.$window.alert).toHaveBeenCalled();
    expect(scope.recentActivity).toBeUndefined();
  });

});
