(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('ArchivoDetailController', ArchivoDetailController);

    ArchivoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Archivo', 'IdiomaCat', 'Norma'];

    function ArchivoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Archivo, IdiomaCat, Norma) {
        var vm = this;

        vm.archivo = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('atlasApp:archivoUpdate', function(event, result) {
            vm.archivo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
