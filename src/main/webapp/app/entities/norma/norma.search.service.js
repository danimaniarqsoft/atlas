(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('NormaSearch', NormaSearch);

    NormaSearch.$inject = ['$resource'];

    function NormaSearch($resource) {
        var resourceUrl =  'api/_search/normas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
