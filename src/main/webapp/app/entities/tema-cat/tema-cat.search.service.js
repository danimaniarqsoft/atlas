(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('TemaCatSearch', TemaCatSearch);

    TemaCatSearch.$inject = ['$resource'];

    function TemaCatSearch($resource) {
        var resourceUrl =  'api/_search/tema-cats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
