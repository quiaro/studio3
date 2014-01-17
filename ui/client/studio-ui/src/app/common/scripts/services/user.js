'use strict';

angular.module('crafter.studio.common')

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
    ])