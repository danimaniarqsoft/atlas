(function() {
    'use strict';
    angular
        .module('atlasApp')
        .factory('Norma', Norma);

    Norma.$inject = ['$resource', 'DateUtils'];

    function Norma ($resource, DateUtils) {
        var resourceUrl =  'api/normas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaFirma = DateUtils.convertLocalDateFromServer(data.fechaFirma);
                        data.fechaRatifica = DateUtils.convertLocalDateFromServer(data.fechaRatifica);
                        data.fechaIniVigor = DateUtils.convertLocalDateFromServer(data.fechaIniVigor);
                        data.fechaFinVigor = DateUtils.convertLocalDateFromServer(data.fechaFinVigor);
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
                    copy.fechaFirma = DateUtils.convertLocalDateToServer(copy.fechaFirma);
                    copy.fechaRatifica = DateUtils.convertLocalDateToServer(copy.fechaRatifica);
                    copy.fechaIniVigor = DateUtils.convertLocalDateToServer(copy.fechaIniVigor);
                    copy.fechaFinVigor = DateUtils.convertLocalDateToServer(copy.fechaFinVigor);
                    copy.fechaAlta = DateUtils.convertLocalDateToServer(copy.fechaAlta);
                    copy.fechaModificacion = DateUtils.convertLocalDateToServer(copy.fechaModificacion);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaFirma = DateUtils.convertLocalDateToServer(copy.fechaFirma);
                    copy.fechaRatifica = DateUtils.convertLocalDateToServer(copy.fechaRatifica);
                    copy.fechaIniVigor = DateUtils.convertLocalDateToServer(copy.fechaIniVigor);
                    copy.fechaFinVigor = DateUtils.convertLocalDateToServer(copy.fechaFinVigor);
                    copy.fechaAlta = DateUtils.convertLocalDateToServer(copy.fechaAlta);
                    copy.fechaModificacion = DateUtils.convertLocalDateToServer(copy.fechaModificacion);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
