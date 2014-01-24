console.log("Running inside Login section!");

'use strict';

angular.injector(['ng', 'crafter.studio-ui.NgRegistry'])
    .invoke(function(NgRegistry, $controller) {

        NgRegistry.addValue('Name', 'Michael Jordan');

        NgRegistry.addController('SomeController', function (Name) {
            var name = Name || 'Harold';
            console.log(name + ' got skills!');
        });

        var func = $controller('SomeController');
        console.log(func);
});
