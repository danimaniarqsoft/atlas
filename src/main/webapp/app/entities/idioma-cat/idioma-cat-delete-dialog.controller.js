(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('IdiomaCatDeleteController',IdiomaCatDeleteController);

    IdiomaCatDeleteController.$inject = ['$uibModalInstance', 'entity', 'IdiomaCat'];

    function IdiomaCatDeleteController($uibModalInstance, entity, IdiomaCat) {
        var vm = this;

        vm.idiomaCat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            IdiomaCat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
