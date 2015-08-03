'use strict';

angular.module('jtlserverApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
