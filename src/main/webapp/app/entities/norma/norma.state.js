(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('norma', {
            parent: 'entity',
            url: '/norma?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.norma.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/norma/normas.html',
                    controller: 'NormaController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('norma');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('norma-detail', {
            parent: 'norma',
            url: '/norma/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.norma.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/norma/norma-detail.html',
                    controller: 'NormaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('norma');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Norma', function($stateParams, Norma) {
                    return Norma.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'norma',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('norma-detail.edit', {
            parent: 'norma-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma/norma-dialog.html',
                    controller: 'NormaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Norma', function(Norma) {
                            return Norma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('norma.new', {
            parent: 'norma',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma/norma-dialog.html',
                    controller: 'NormaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fechaFirma: null,
                                fechaRatifica: null,
                                fechaIniVigor: null,
                                fechaFinVigor: null,
                                firmantes: null,
                                fechaAlta: null,
                                fechaModificacion: null,
                                jhiUserId: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('norma', null, { reload: 'norma' });
                }, function() {
                    $state.go('norma');
                });
            }]
        })
        .state('norma.edit', {
            parent: 'norma',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma/norma-dialog.html',
                    controller: 'NormaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Norma', function(Norma) {
                            return Norma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('norma', null, { reload: 'norma' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('norma.delete', {
            parent: 'norma',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma/norma-delete-dialog.html',
                    controller: 'NormaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Norma', function(Norma) {
                            return Norma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('norma', null, { reload: 'norma' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
