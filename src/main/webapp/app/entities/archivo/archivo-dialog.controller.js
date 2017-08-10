(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('ArchivoDialogController', ArchivoDialogController);

    ArchivoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Archivo', 'IdiomaCat', 'Norma'];

    function ArchivoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Archivo, IdiomaCat, Norma) {
        var vm = this;

        vm.archivo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.idiomacats = IdiomaCat.query({filter: 'archivo-is-null'});
        $q.all([vm.archivo.$promise, vm.idiomacats.$promise]).then(function() {
            if (!vm.archivo.idiomaCat || !vm.archivo.idiomaCat.id) {
                return $q.reject();
            }
            return IdiomaCat.get({id : vm.archivo.idiomaCat.id}).$promise;
        }).then(function(idiomaCat) {
            vm.idiomacats.push(idiomaCat);
        });
        vm.normas = Norma.query({filter: 'archivo-is-null'});
        $q.all([vm.archivo.$promise, vm.normas.$promise]).then(function() {
            if (!vm.archivo.norma || !vm.archivo.norma.id) {
                return $q.reject();
            }
            return Norma.get({id : vm.archivo.norma.id}).$promise;
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
            if (vm.archivo.id !== null) {
                Archivo.update(vm.archivo, onSaveSuccess, onSaveError);
            } else {
                Archivo.save(vm.archivo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:archivoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaAlta = false;
        vm.datePickerOpenStatus.fechaModificacion = false;

        vm.setFile = function ($file, archivo) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        archivo.file = base64Data;
                        archivo.fileContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
