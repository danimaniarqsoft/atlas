(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('SubtemaCatDialogController', SubtemaCatDialogController);

    SubtemaCatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SubtemaCat', 'IdiomaCat', 'TemaCat'];

    function SubtemaCatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, SubtemaCat, IdiomaCat, TemaCat) {
        var vm = this;

        vm.subtemaCat = entity;
        vm.clear = clear;
        vm.save = save;
        vm.idiomas = IdiomaCat.query({filter: 'subtemacat-is-null'});
        $q.all([vm.subtemaCat.$promise, vm.idiomas.$promise]).then(function() {
            if (!vm.subtemaCat.idioma || !vm.subtemaCat.idioma.id) {
                return $q.reject();
            }
            return IdiomaCat.get({id : vm.subtemaCat.idioma.id}).$promise;
        }).then(function(idioma) {
            vm.idiomas.push(idioma);
        });
        vm.temacats = TemaCat.query({filter: 'subtemacat-is-null'});
        $q.all([vm.subtemaCat.$promise, vm.temacats.$promise]).then(function() {
            if (!vm.subtemaCat.temaCat || !vm.subtemaCat.temaCat.id) {
                return $q.reject();
            }
            return TemaCat.get({id : vm.subtemaCat.temaCat.id}).$promise;
        }).then(function(temaCat) {
            vm.temacats.push(temaCat);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subtemaCat.id !== null) {
                SubtemaCat.update(vm.subtemaCat, onSaveSuccess, onSaveError);
            } else {
                SubtemaCat.save(vm.subtemaCat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:subtemaCatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
