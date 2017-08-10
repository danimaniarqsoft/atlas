(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('PalabraClaveCatDeleteController',PalabraClaveCatDeleteController);

    PalabraClaveCatDeleteController.$inject = ['$uibModalInstance', 'entity', 'PalabraClaveCat'];

    function PalabraClaveCatDeleteController($uibModalInstance, entity, PalabraClaveCat) {
        var vm = this;

        vm.palabraClaveCat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PalabraClaveCat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
