(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('EstatusCatDialogController', EstatusCatDialogController);

    EstatusCatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'EstatusCat', 'IdiomaCat'];

    function EstatusCatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, EstatusCat, IdiomaCat) {
        var vm = this;

        vm.estatusCat = entity;
        vm.clear = clear;
        vm.save = save;
        vm.idiomacats = IdiomaCat.query({filter: 'estatuscat-is-null'});
        $q.all([vm.estatusCat.$promise, vm.idiomacats.$promise]).then(function() {
            if (!vm.estatusCat.idiomaCat || !vm.estatusCat.idiomaCat.id) {
                return $q.reject();
            }
            return IdiomaCat.get({id : vm.estatusCat.idiomaCat.id}).$promise;
        }).then(function(idiomaCat) {
            vm.idiomacats.push(idiomaCat);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.estatusCat.id !== null) {
                EstatusCat.update(vm.estatusCat, onSaveSuccess, onSaveError);
            } else {
                EstatusCat.save(vm.estatusCat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:estatusCatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
