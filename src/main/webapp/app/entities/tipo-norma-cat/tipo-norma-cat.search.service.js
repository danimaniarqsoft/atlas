(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('TipoNormaCatSearch', TipoNormaCatSearch);

    TipoNormaCatSearch.$inject = ['$resource'];

    function TipoNormaCatSearch($resource) {
        var resourceUrl =  'api/_search/tipo-norma-cats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
