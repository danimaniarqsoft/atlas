'use strict';

describe('Controller Tests', function() {

    describe('Archivo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockArchivo, MockIdiomaCat, MockNorma;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockArchivo = jasmine.createSpy('MockArchivo');
            MockIdiomaCat = jasmine.createSpy('MockIdiomaCat');
            MockNorma = jasmine.createSpy('MockNorma');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Archivo': MockArchivo,
                'IdiomaCat': MockIdiomaCat,
                'Norma': MockNorma
            };
            createController = function() {
                $injector.get('$controller')("ArchivoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'atlasApp:archivoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
