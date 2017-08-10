(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('PalabraClaveCatDialogController', PalabraClaveCatDialogController);

    PalabraClaveCatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PalabraClaveCat', 'IdiomaCat'];

    function PalabraClaveCatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, PalabraClaveCat, IdiomaCat) {
        var vm = this;

        vm.palabraClaveCat = entity;
        vm.clear = clear;
        vm.save = save;
        vm.idiomacats = IdiomaCat.query({filter: 'palabraclavecat-is-null'});
        $q.all([vm.palabraClaveCat.$promise, vm.idiomacats.$promise]).then(function() {
            if (!vm.palabraClaveCat.idiomaCat || !vm.palabraClaveCat.idiomaCat.id) {
                return $q.reject();
            }
            return IdiomaCat.get({id : vm.palabraClaveCat.idiomaCat.id}).$promise;
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
            if (vm.palabraClaveCat.id !== null) {
                PalabraClaveCat.update(vm.palabraClaveCat, onSaveSuccess, onSaveError);
            } else {
                PalabraClaveCat.save(vm.palabraClaveCat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:palabraClaveCatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
