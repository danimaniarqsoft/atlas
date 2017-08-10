(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('Archivo', Archivo);

    Archivo.$inject = ['$resource', 'DateUtils'];

    function Archivo ($resource, DateUtils) {
        var resourceUrl =  'api/archivos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaAlta = DateUtils.convertLocalDateFromServer(data.fechaAlta);
                        data.fechaModificacion = DateUtils.convertLocalDateFromServer(data.fechaModificacion);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaAlta = DateUtils.convertLocalDateToServer(copy.fechaAlta);
                    copy.fechaModificacion = DateUtils.convertLocalDateToServer(copy.fechaModificacion);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaAlta = DateUtils.convertLocalDateToServer(copy.fechaAlta);
                    copy.fechaModificacion = DateUtils.convertLocalDateToServer(copy.fechaModificacion);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
