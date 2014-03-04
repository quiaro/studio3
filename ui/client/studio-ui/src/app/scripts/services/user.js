'use strict';

angular.module('crafter.studio-ui.services.UserService', [])

    .factory('UserService', [
        function() {
            var userRoles;

            function getUserRoles() {
                // TODO: replace this with a call to an authentication service in the back-end
                userRoles = ['author', 'editor'];
                return userRoles || [];
            }

            return {
                getUserRoles: getUserRoles
            };
        }
    ]);
