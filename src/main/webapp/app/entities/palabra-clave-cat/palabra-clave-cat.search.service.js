(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('PalabraClaveCatSearch', PalabraClaveCatSearch);

    PalabraClaveCatSearch.$inject = ['$resource'];

    function PalabraClaveCatSearch($resource) {
        var resourceUrl =  'api/_search/palabra-clave-cats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
