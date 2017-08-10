(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('NormaPalabraClave', NormaPalabraClave);

    NormaPalabraClave.$inject = ['$resource'];

    function NormaPalabraClave ($resource) {
        var resourceUrl =  'api/norma-palabra-claves/:id';

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
