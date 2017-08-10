(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('EstatusCatSearch', EstatusCatSearch);

    EstatusCatSearch.$inject = ['$resource'];

    function EstatusCatSearch($resource) {
        var resourceUrl =  'api/_search/estatus-cats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
