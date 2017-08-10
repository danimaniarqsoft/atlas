(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('NormaSubtemaSearch', NormaSubtemaSearch);

    NormaSubtemaSearch.$inject = ['$resource'];

    function NormaSubtemaSearch($resource) {
        var resourceUrl =  'api/_search/norma-subtemas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
