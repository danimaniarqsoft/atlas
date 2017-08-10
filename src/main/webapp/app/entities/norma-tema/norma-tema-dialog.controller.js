(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaTemaDialogController', NormaTemaDialogController);

    NormaTemaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NormaTema'];

    function NormaTemaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NormaTema) {
        var vm = this;

        vm.normaTema = entity;
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
            if (vm.normaTema.id !== null) {
                NormaTema.update(vm.normaTema, onSaveSuccess, onSaveError);
            } else {
                NormaTema.save(vm.normaTema, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:normaTemaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
