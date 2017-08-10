(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('NormaTema', NormaTema);

    NormaTema.$inject = ['$resource'];

    function NormaTema ($resource) {
        var resourceUrl =  'api/norma-temas/:id';

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
