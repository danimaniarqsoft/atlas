(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaDeleteController',NormaDeleteController);

    NormaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Norma'];

    function NormaDeleteController($uibModalInstance, entity, Norma) {
        var vm = this;

        vm.norma = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Norma.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
