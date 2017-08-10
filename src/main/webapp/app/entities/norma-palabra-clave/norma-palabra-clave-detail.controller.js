(function() {
    'use strict';

    angular
        .module('atlasApp')
        .controller('NormaPalabraClaveDetailController', NormaPalabraClaveDetailController);

    NormaPalabraClaveDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NormaPalabraClave'];

    function NormaPalabraClaveDetailController($scope, $rootScope, $stateParams, previousState, entity, NormaPalabraClave) {
        var vm = this;

        vm.normaPalabraClave = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('atlasApp:normaPalabraClaveUpdate', function(event, result) {
            vm.normaPalabraClave = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
