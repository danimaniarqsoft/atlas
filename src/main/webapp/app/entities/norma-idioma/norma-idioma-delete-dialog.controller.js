(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaIdiomaDeleteController',NormaIdiomaDeleteController);

    NormaIdiomaDeleteController.$inject = ['$uibModalInstance', 'entity', 'NormaIdioma'];

    function NormaIdiomaDeleteController($uibModalInstance, entity, NormaIdioma) {
        var vm = this;

        vm.normaIdioma = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NormaIdioma.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
