(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaPalabraClaveDialogController', NormaPalabraClaveDialogController);

    NormaPalabraClaveDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NormaPalabraClave'];

    function NormaPalabraClaveDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NormaPalabraClave) {
        var vm = this;

        vm.normaPalabraClave = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.normaPalabraClave.id !== null) {
                NormaPalabraClave.update(vm.normaPalabraClave, onSaveSuccess, onSaveError);
            } else {
                NormaPalabraClave.save(vm.normaPalabraClave, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:normaPalabraClaveUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
