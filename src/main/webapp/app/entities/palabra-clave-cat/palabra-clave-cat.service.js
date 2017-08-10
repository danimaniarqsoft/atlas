(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('PalabraClaveCat', PalabraClaveCat);

    PalabraClaveCat.$inject = ['$resource'];

    function PalabraClaveCat ($resource) {
        var resourceUrl =  'api/palabra-clave-cats/:id';

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
