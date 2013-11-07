'use strict';

describe('Module: Dashboard', function () {

  beforeEach( function () {

    // load dependency
    module('crafter.studio.common', function ($provide) {
      $provide.value('util', {
          getServiceURL : function () {
            return '/url/to/backend/service';
          }
      });
    });

    module('crafter.studio.dashboard');
  });

  describe('Controller: DashboardCtrl', function () {

    var DashboardCtrl, scope, params, httpBackend;

    beforeEach(inject(function($controller, $httpBackend, $rootScope, repo) {

      httpBackend = $httpBackend;

      scope = $rootScope.$new();
      params = {
        $scope: scope,
        repo: repo
      };
      DashboardCtrl = $controller('DashboardCtrl', params);
    }));

    /*
    it('should append a list of recent activity items to the scope', function () {

      runs(function () {
        httpBackend.expectGET('/url/to/backend/service').respond(200, [1, 2, 3]);
        scope.getRecentActivity();
        httpBackend.flush();
      })

      waitsFor(function () {
        return scope.recentActivity !== null;
      }, "scope.recentActivity to be set", 250);

      runs(function () {
        expect(scope.recentActivity).toEqual([1, 2, 3]);
      });
    });
    */

  });

});
