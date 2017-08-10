(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('SubtemaCatDetailController', SubtemaCatDetailController);

    SubtemaCatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SubtemaCat', 'IdiomaCat', 'TemaCat'];

    function SubtemaCatDetailController($scope, $rootScope, $stateParams, previousState, entity, SubtemaCat, IdiomaCat, TemaCat) {
        var vm = this;

        vm.subtemaCat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('atlasApp:subtemaCatUpdate', function(event, result) {
            vm.subtemaCat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
