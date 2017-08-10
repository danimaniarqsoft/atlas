(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaPalabraClaveDeleteController',NormaPalabraClaveDeleteController);

    NormaPalabraClaveDeleteController.$inject = ['$uibModalInstance', 'entity', 'NormaPalabraClave'];

    function NormaPalabraClaveDeleteController($uibModalInstance, entity, NormaPalabraClave) {
        var vm = this;

        vm.normaPalabraClave = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NormaPalabraClave.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
