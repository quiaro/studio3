'use strict';

describe('Controller: DialogsCtrl', function () {

  var DialogsCtrl,
      params,
      $scope;

  beforeEach(module('dialogs'));

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($injector) {
    var $controller = $injector.get('$controller');

    $scope = $injector.get('$rootScope');
    params = {
      $scope : $scope,
      historyDialog : jasmine.createSpyObj('history.dialog', ['open', 'close']),
      goLiveDialog : jasmine.createSpyObj('go-live.dialog', ['open', 'close'])
    };
    DialogsCtrl = $controller('DialogsCtrl', params);
  }));

  it('should open a history dialogue window', function () {
    $scope.showDialog('history');
    expect(params.historyDialog.open).toHaveBeenCalled();
  });

  it('should close a history dialogue window', function () {
    $scope.closeDialog('history');
    expect(params.historyDialog.close).toHaveBeenCalled();
  });

  it('should open a go-live dialogue window', function () {
    $scope.showDialog('go-live');
    expect(params.goLiveDialog.open).toHaveBeenCalled();
  });

  it('should close a go-live dialogue window', function () {
    $scope.closeDialog('go-live');
    expect(params.goLiveDialog.close).toHaveBeenCalled();
  });

});
