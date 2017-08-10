'use strict';

describe('Controller Tests', function() {

    describe('PalabraClaveCat Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPalabraClaveCat, MockIdiomaCat;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPalabraClaveCat = jasmine.createSpy('MockPalabraClaveCat');
            MockIdiomaCat = jasmine.createSpy('MockIdiomaCat');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PalabraClaveCat': MockPalabraClaveCat,
                'IdiomaCat': MockIdiomaCat
            };
            createController = function() {
                $injector.get('$controller')("PalabraClaveCatDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'atlasApp:palabraClaveCatUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
