(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaTemaDeleteController',NormaTemaDeleteController);

    NormaTemaDeleteController.$inject = ['$uibModalInstance', 'entity', 'NormaTema'];

    function NormaTemaDeleteController($uibModalInstance, entity, NormaTema) {
        var vm = this;

        vm.normaTema = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NormaTema.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
