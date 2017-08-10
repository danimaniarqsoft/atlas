(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaTemaDetailController', NormaTemaDetailController);

    NormaTemaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NormaTema'];

    function NormaTemaDetailController($scope, $rootScope, $stateParams, previousState, entity, NormaTema) {
        var vm = this;

        vm.normaTema = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('atlasApp:normaTemaUpdate', function(event, result) {
            vm.normaTema = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
