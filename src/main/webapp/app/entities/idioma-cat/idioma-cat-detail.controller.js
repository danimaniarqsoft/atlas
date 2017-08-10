(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('IdiomaCatDetailController', IdiomaCatDetailController);

    IdiomaCatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'IdiomaCat'];

    function IdiomaCatDetailController($scope, $rootScope, $stateParams, previousState, entity, IdiomaCat) {
        var vm = this;

        vm.idiomaCat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('atlasApp:idiomaCatUpdate', function(event, result) {
            vm.idiomaCat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
