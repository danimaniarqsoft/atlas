(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('PaisCatDialogController', PaisCatDialogController);

    PaisCatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PaisCat', 'IdiomaCat'];

    function PaisCatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, PaisCat, IdiomaCat) {
        var vm = this;

        vm.paisCat = entity;
        vm.clear = clear;
        vm.save = save;
        vm.idiomacats = IdiomaCat.query({filter: 'paiscat-is-null'});
        $q.all([vm.paisCat.$promise, vm.idiomacats.$promise]).then(function() {
            if (!vm.paisCat.idiomaCat || !vm.paisCat.idiomaCat.id) {
                return $q.reject();
            }
            return IdiomaCat.get({id : vm.paisCat.idiomaCat.id}).$promise;
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
            if (vm.paisCat.id !== null) {
                PaisCat.update(vm.paisCat, onSaveSuccess, onSaveError);
            } else {
                PaisCat.save(vm.paisCat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:paisCatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
