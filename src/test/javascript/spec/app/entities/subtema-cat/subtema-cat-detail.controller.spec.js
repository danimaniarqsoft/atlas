'use strict';

describe('Controller Tests', function() {

    describe('SubtemaCat Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSubtemaCat, MockIdiomaCat, MockTemaCat;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSubtemaCat = jasmine.createSpy('MockSubtemaCat');
            MockIdiomaCat = jasmine.createSpy('MockIdiomaCat');
            MockTemaCat = jasmine.createSpy('MockTemaCat');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SubtemaCat': MockSubtemaCat,
                'IdiomaCat': MockIdiomaCat,
                'TemaCat': MockTemaCat
            };
            createController = function() {
                $injector.get('$controller')("SubtemaCatDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'atlasApp:subtemaCatUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
