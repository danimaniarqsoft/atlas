'use strict';

describe('Controller Tests', function() {

    describe('EstatusCat Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEstatusCat, MockIdiomaCat;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEstatusCat = jasmine.createSpy('MockEstatusCat');
            MockIdiomaCat = jasmine.createSpy('MockIdiomaCat');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EstatusCat': MockEstatusCat,
                'IdiomaCat': MockIdiomaCat
            };
            createController = function() {
                $injector.get('$controller')("EstatusCatDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'atlasApp:estatusCatUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
