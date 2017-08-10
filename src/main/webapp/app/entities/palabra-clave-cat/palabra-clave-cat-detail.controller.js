(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('PalabraClaveCatDetailController', PalabraClaveCatDetailController);

    PalabraClaveCatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PalabraClaveCat', 'IdiomaCat'];

    function PalabraClaveCatDetailController($scope, $rootScope, $stateParams, previousState, entity, PalabraClaveCat, IdiomaCat) {
        var vm = this;

        vm.palabraClaveCat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('atlasApp:palabraClaveCatUpdate', function(event, result) {
            vm.palabraClaveCat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
