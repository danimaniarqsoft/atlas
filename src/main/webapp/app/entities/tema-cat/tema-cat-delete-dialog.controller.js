(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('TemaCatDeleteController',TemaCatDeleteController);

    TemaCatDeleteController.$inject = ['$uibModalInstance', 'entity', 'TemaCat'];

    function TemaCatDeleteController($uibModalInstance, entity, TemaCat) {
        var vm = this;

        vm.temaCat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TemaCat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
