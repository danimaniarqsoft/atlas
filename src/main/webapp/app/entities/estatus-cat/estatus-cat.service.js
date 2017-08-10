(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('EstatusCat', EstatusCat);

    EstatusCat.$inject = ['$resource'];

    function EstatusCat ($resource) {
        var resourceUrl =  'api/estatus-cats/:id';

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
