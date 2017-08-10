(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('PaisCat', PaisCat);

    PaisCat.$inject = ['$resource'];

    function PaisCat ($resource) {
        var resourceUrl =  'api/pais-cats/:id';

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
