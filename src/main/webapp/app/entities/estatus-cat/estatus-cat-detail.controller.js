(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('EstatusCatDetailController', EstatusCatDetailController);

    EstatusCatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EstatusCat', 'IdiomaCat'];

    function EstatusCatDetailController($scope, $rootScope, $stateParams, previousState, entity, EstatusCat, IdiomaCat) {
        var vm = this;

        vm.estatusCat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('atlasApp:estatusCatUpdate', function(event, result) {
            vm.estatusCat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
