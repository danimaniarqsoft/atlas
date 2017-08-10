(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('TipoNormaCatDetailController', TipoNormaCatDetailController);

    TipoNormaCatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoNormaCat', 'IdiomaCat'];

    function TipoNormaCatDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoNormaCat, IdiomaCat) {
        var vm = this;

        vm.tipoNormaCat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('atlasApp:tipoNormaCatUpdate', function(event, result) {
            vm.tipoNormaCat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
