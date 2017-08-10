(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('NormaTemaSearch', NormaTemaSearch);

    NormaTemaSearch.$inject = ['$resource'];

    function NormaTemaSearch($resource) {
        var resourceUrl =  'api/_search/norma-temas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
