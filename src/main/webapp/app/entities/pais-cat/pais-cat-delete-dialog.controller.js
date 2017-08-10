(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('PaisCatDeleteController',PaisCatDeleteController);

    PaisCatDeleteController.$inject = ['$uibModalInstance', 'entity', 'PaisCat'];

    function PaisCatDeleteController($uibModalInstance, entity, PaisCat) {
        var vm = this;

        vm.paisCat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PaisCat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
