(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('TipoNormaCatDeleteController',TipoNormaCatDeleteController);

    TipoNormaCatDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoNormaCat'];

    function TipoNormaCatDeleteController($uibModalInstance, entity, TipoNormaCat) {
        var vm = this;

        vm.tipoNormaCat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoNormaCat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
