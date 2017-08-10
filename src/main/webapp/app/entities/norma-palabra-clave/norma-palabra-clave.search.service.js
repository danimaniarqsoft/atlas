(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('NormaPalabraClaveSearch', NormaPalabraClaveSearch);

    NormaPalabraClaveSearch.$inject = ['$resource'];

    function NormaPalabraClaveSearch($resource) {
        var resourceUrl =  'api/_search/norma-palabra-claves/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
