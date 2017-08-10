(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('TemaCatDetailController', TemaCatDetailController);

    TemaCatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TemaCat', 'IdiomaCat'];

    function TemaCatDetailController($scope, $rootScope, $stateParams, previousState, entity, TemaCat, IdiomaCat) {
        var vm = this;

        vm.temaCat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('atlasApp:temaCatUpdate', function(event, result) {
            vm.temaCat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
