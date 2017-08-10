(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaDetailController', NormaDetailController);

    NormaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Norma', 'EstatusCat', 'PaisCat', 'TipoNormaCat'];

    function NormaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Norma, EstatusCat, PaisCat, TipoNormaCat) {
        var vm = this;

        vm.norma = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('atlasApp:normaUpdate', function(event, result) {
            vm.norma = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
