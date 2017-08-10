(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaIdiomaDialogController', NormaIdiomaDialogController);

    NormaIdiomaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'NormaIdioma', 'IdiomaCat', 'Norma'];

    function NormaIdiomaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, NormaIdioma, IdiomaCat, Norma) {
        var vm = this;

        vm.normaIdioma = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.idiomacats = IdiomaCat.query({filter: 'normaidioma-is-null'});
        $q.all([vm.normaIdioma.$promise, vm.idiomacats.$promise]).then(function() {
            if (!vm.normaIdioma.idiomaCat || !vm.normaIdioma.idiomaCat.id) {
                return $q.reject();
            }
            return IdiomaCat.get({id : vm.normaIdioma.idiomaCat.id}).$promise;
        }).then(function(idiomaCat) {
            vm.idiomacats.push(idiomaCat);
        });
        vm.normas = Norma.query({filter: 'normaidioma-is-null'});
        $q.all([vm.normaIdioma.$promise, vm.normas.$promise]).then(function() {
            if (!vm.normaIdioma.norma || !vm.normaIdioma.norma.id) {
                return $q.reject();
            }
            return Norma.get({id : vm.normaIdioma.norma.id}).$promise;
        }).then(function(norma) {
            vm.normas.push(norma);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.normaIdioma.id !== null) {
                NormaIdioma.update(vm.normaIdioma, onSaveSuccess, onSaveError);
            } else {
                NormaIdioma.save(vm.normaIdioma, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:normaIdiomaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaModificacion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
