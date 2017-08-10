(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('PaisCatDetailController', PaisCatDetailController);

    PaisCatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PaisCat', 'IdiomaCat'];

    function PaisCatDetailController($scope, $rootScope, $stateParams, previousState, entity, PaisCat, IdiomaCat) {
        var vm = this;

        vm.paisCat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('atlasApp:paisCatUpdate', function(event, result) {
            vm.paisCat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
