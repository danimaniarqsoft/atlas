(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('ArchivoSearch', ArchivoSearch);

    ArchivoSearch.$inject = ['$resource'];

    function ArchivoSearch($resource) {
        var resourceUrl =  'api/_search/archivos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
