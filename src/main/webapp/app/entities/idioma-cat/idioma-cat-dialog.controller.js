(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('IdiomaCatDialogController', IdiomaCatDialogController);

    IdiomaCatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'IdiomaCat'];

    function IdiomaCatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, IdiomaCat) {
        var vm = this;

        vm.idiomaCat = entity;
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
            if (vm.idiomaCat.id !== null) {
                IdiomaCat.update(vm.idiomaCat, onSaveSuccess, onSaveError);
            } else {
                IdiomaCat.save(vm.idiomaCat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:idiomaCatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
