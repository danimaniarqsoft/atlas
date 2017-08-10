(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaSubtemaDeleteController',NormaSubtemaDeleteController);

    NormaSubtemaDeleteController.$inject = ['$uibModalInstance', 'entity', 'NormaSubtema'];

    function NormaSubtemaDeleteController($uibModalInstance, entity, NormaSubtema) {
        var vm = this;

        vm.normaSubtema = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NormaSubtema.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
