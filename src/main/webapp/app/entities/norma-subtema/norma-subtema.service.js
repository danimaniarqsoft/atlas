(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('NormaSubtema', NormaSubtema);

    NormaSubtema.$inject = ['$resource'];

    function NormaSubtema ($resource) {
        var resourceUrl =  'api/norma-subtemas/:id';

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
