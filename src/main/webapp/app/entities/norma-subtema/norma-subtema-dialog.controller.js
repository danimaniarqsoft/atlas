(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaSubtemaDialogController', NormaSubtemaDialogController);

    NormaSubtemaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NormaSubtema'];

    function NormaSubtemaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NormaSubtema) {
        var vm = this;

        vm.normaSubtema = entity;
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
            if (vm.normaSubtema.id !== null) {
                NormaSubtema.update(vm.normaSubtema, onSaveSuccess, onSaveError);
            } else {
                NormaSubtema.save(vm.normaSubtema, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:normaSubtemaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
