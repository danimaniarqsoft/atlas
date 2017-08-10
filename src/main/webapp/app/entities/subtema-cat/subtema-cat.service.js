(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('SubtemaCat', SubtemaCat);

    SubtemaCat.$inject = ['$resource'];

    function SubtemaCat ($resource) {
        var resourceUrl =  'api/subtema-cats/:id';

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
