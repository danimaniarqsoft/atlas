(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('SubtemaCatDeleteController',SubtemaCatDeleteController);

    SubtemaCatDeleteController.$inject = ['$uibModalInstance', 'entity', 'SubtemaCat'];

    function SubtemaCatDeleteController($uibModalInstance, entity, SubtemaCat) {
        var vm = this;

        vm.subtemaCat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SubtemaCat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
