(function() {
    'use strict';

    angular
        .module('atlasApp')
        .factory('IdiomaCatSearch', IdiomaCatSearch);

    IdiomaCatSearch.$inject = ['$resource'];

    function IdiomaCatSearch($resource) {
        var resourceUrl =  'api/_search/idioma-cats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
