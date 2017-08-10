(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('EstatusCatDeleteController',EstatusCatDeleteController);

    EstatusCatDeleteController.$inject = ['$uibModalInstance', 'entity', 'EstatusCat'];

    function EstatusCatDeleteController($uibModalInstance, entity, EstatusCat) {
        var vm = this;

        vm.estatusCat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EstatusCat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
