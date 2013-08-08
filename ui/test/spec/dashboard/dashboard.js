'use strict';

describe('Controller: DashboardCtrl', function () {

  var DashboardCtrl, controller, scope, httpBackend, params;

  // load the controller's module
  beforeEach(module('dashboard'));

  beforeEach(inject(function($controller, $httpBackend, $http, $rootScope) {

    controller = $controller;
    scope = $rootScope.$new();
    httpBackend = $httpBackend;
    params = {
      $scope: scope,
      $window: jasmine.createSpyObj('$window', ['alert']), // overrides $window with a mock version so window.alert() will not block the test runner with a real alert box
      repo: {
        list: function list() {
          return $http.get('/url/to/activity/items');
        }
      }
    };
    DashboardCtrl = controller('DashboardCtrl', params);
  }));

  afterEach(function() {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  it('should attach the list of recent activity items to the scope when the call to repo.list() is successful', function () {
    httpBackend.expectGET('/url/to/activity/items').respond(200, ['a']);
    scope.getRecentActivity();
    httpBackend.flush();    
    expect(scope.recentActivity).toEqual(['a']);
  });

  it('should display an error message when the call to repo.list() fails', function () {
    httpBackend.expectGET('/url/to/activity/items').respond(500);
    scope.getRecentActivity();
    httpBackend.flush();
    expect(params.$window.alert).toHaveBeenCalled();
    expect(scope.recentActivity).toBeUndefined();
  });

});
