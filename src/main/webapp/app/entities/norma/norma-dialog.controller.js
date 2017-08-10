(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaDialogController', NormaDialogController);

    NormaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Norma', 'EstatusCat', 'PaisCat', 'TipoNormaCat'];

    function NormaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Norma, EstatusCat, PaisCat, TipoNormaCat) {
        var vm = this;

        vm.norma = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.estatuscats = EstatusCat.query({filter: 'norma-is-null'});
        $q.all([vm.norma.$promise, vm.estatuscats.$promise]).then(function() {
            if (!vm.norma.estatusCat || !vm.norma.estatusCat.id) {
                return $q.reject();
            }
            return EstatusCat.get({id : vm.norma.estatusCat.id}).$promise;
        }).then(function(estatusCat) {
            vm.estatuscats.push(estatusCat);
        });
        vm.paiscats = PaisCat.query({filter: 'norma-is-null'});
        $q.all([vm.norma.$promise, vm.paiscats.$promise]).then(function() {
            if (!vm.norma.paisCat || !vm.norma.paisCat.id) {
                return $q.reject();
            }
            return PaisCat.get({id : vm.norma.paisCat.id}).$promise;
        }).then(function(paisCat) {
            vm.paiscats.push(paisCat);
        });
        vm.tiponormacats = TipoNormaCat.query({filter: 'norma-is-null'});
        $q.all([vm.norma.$promise, vm.tiponormacats.$promise]).then(function() {
            if (!vm.norma.tipoNormaCat || !vm.norma.tipoNormaCat.id) {
                return $q.reject();
            }
            return TipoNormaCat.get({id : vm.norma.tipoNormaCat.id}).$promise;
        }).then(function(tipoNormaCat) {
            vm.tiponormacats.push(tipoNormaCat);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.norma.id !== null) {
                Norma.update(vm.norma, onSaveSuccess, onSaveError);
            } else {
                Norma.save(vm.norma, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:normaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaFirma = false;
        vm.datePickerOpenStatus.fechaRatifica = false;
        vm.datePickerOpenStatus.fechaIniVigor = false;
        vm.datePickerOpenStatus.fechaFinVigor = false;
        vm.datePickerOpenStatus.fechaAlta = false;
        vm.datePickerOpenStatus.fechaModificacion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
