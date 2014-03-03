'use strict';

angular.module('crafter.studio.common')

    .factory('AuthService', [
        function() {
            var loggedIn = false;

            function logIn() {
                // TODO: replace this with a call to an authentication service in the back-end
                loggedIn = true;
            }

            function isLoggedIn() {
                // TODO: replace this with a call to an authentication service in the back-end
                return loggedIn;
            }

            return {
                logIn: logIn,
                isLoggedIn: isLoggedIn
            };
        }
    ]);