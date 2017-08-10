'use strict';

describe('Controller Tests', function() {

    describe('NormaIdioma Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockNormaIdioma, MockIdiomaCat, MockNorma;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockNormaIdioma = jasmine.createSpy('MockNormaIdioma');
            MockIdiomaCat = jasmine.createSpy('MockIdiomaCat');
            MockNorma = jasmine.createSpy('MockNorma');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'NormaIdioma': MockNormaIdioma,
                'IdiomaCat': MockIdiomaCat,
                'Norma': MockNorma
            };
            createController = function() {
                $injector.get('$controller')("NormaIdiomaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'atlasApp:normaIdiomaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
