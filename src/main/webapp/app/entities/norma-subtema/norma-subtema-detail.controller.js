(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaSubtemaDetailController', NormaSubtemaDetailController);

    NormaSubtemaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NormaSubtema'];

    function NormaSubtemaDetailController($scope, $rootScope, $stateParams, previousState, entity, NormaSubtema) {
        var vm = this;

        vm.normaSubtema = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('atlasApp:normaSubtemaUpdate', function(event, result) {
            vm.normaSubtema = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
