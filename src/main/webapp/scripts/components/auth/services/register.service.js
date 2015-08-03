'use strict';

angular.module('jtlserverApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


