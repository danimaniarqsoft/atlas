(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('PaisCatSearch', PaisCatSearch);

    PaisCatSearch.$inject = ['$resource'];

    function PaisCatSearch($resource) {
        var resourceUrl =  'api/_search/pais-cats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
