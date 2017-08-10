(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaIdiomaDetailController', NormaIdiomaDetailController);

    NormaIdiomaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'NormaIdioma', 'IdiomaCat', 'Norma'];

    function NormaIdiomaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, NormaIdioma, IdiomaCat, Norma) {
        var vm = this;

        vm.normaIdioma = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('atlasApp:normaIdiomaUpdate', function(event, result) {
            vm.normaIdioma = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
