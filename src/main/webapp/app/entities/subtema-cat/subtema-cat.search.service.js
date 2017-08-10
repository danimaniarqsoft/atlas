(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('SubtemaCatSearch', SubtemaCatSearch);

    SubtemaCatSearch.$inject = ['$resource'];

    function SubtemaCatSearch($resource) {
        var resourceUrl =  'api/_search/subtema-cats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
