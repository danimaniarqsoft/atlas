(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('NormaIdiomaSearch', NormaIdiomaSearch);

    NormaIdiomaSearch.$inject = ['$resource'];

    function NormaIdiomaSearch($resource) {
        var resourceUrl =  'api/_search/norma-idiomas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
