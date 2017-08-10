(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('TemaCatDialogController', TemaCatDialogController);

    TemaCatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TemaCat', 'IdiomaCat'];

    function TemaCatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TemaCat, IdiomaCat) {
        var vm = this;

        vm.temaCat = entity;
        vm.clear = clear;
        vm.save = save;
        vm.idiomacats = IdiomaCat.query({filter: 'temacat-is-null'});
        $q.all([vm.temaCat.$promise, vm.idiomacats.$promise]).then(function() {
            if (!vm.temaCat.idiomaCat || !vm.temaCat.idiomaCat.id) {
                return $q.reject();
            }
            return IdiomaCat.get({id : vm.temaCat.idiomaCat.id}).$promise;
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
            if (vm.temaCat.id !== null) {
                TemaCat.update(vm.temaCat, onSaveSuccess, onSaveError);
            } else {
                TemaCat.save(vm.temaCat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('atlasApp:temaCatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
