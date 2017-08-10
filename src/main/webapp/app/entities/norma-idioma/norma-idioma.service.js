(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('NormaIdioma', NormaIdioma);

    NormaIdioma.$inject = ['$resource', 'DateUtils'];

    function NormaIdioma ($resource, DateUtils) {
        var resourceUrl =  'api/norma-idiomas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaModificacion = DateUtils.convertLocalDateFromServer(data.fechaModificacion);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaModificacion = DateUtils.convertLocalDateToServer(copy.fechaModificacion);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaModificacion = DateUtils.convertLocalDateToServer(copy.fechaModificacion);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
