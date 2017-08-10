(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('TemaCat', TemaCat);

    TemaCat.$inject = ['$resource'];

    function TemaCat ($resource) {
        var resourceUrl =  'api/tema-cats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
