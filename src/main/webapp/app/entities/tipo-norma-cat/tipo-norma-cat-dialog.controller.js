(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('TipoNormaCatDialogController', TipoNormaCatDialogController);

    TipoNormaCatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TipoNormaCat', 'IdiomaCat'];

    function TipoNormaCatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TipoNormaCat, IdiomaCat) {
        var vm = this;

        vm.tipoNormaCat = entity;
        vm.clear = clear;
        vm.save = save;
        vm.idiomacats = IdiomaCat.query({filter: 'tiponormacat-is-null'});
        $q.all([vm.tipoNormaCat.$promise, vm.idiomacats.$promise]).then(function() {
            if (!vm.tipoNormaCat.idiomaCat || !vm.tipoNormaCat.idiomaCat.id) {
                return $q.reject();
            }
            return IdiomaCat.get({id : vm.tipoNormaCat.idiomaCat.id}).$promise;
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
            if (vm.tipoNormaCat.id !== null) {
                TipoNormaCat.update(vm.tipoNormaCat, onSaveSuccess, onSaveError);
            } else {
                TipoNormaCat.save(vm.tipoNormaCat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:tipoNormaCatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
