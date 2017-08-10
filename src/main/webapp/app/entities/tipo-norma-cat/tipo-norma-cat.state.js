(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-norma-cat', {
            parent: 'entity',
            url: '/tipo-norma-cat?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.tipoNormaCat.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-norma-cat/tipo-norma-cats.html',
                    controller: 'TipoNormaCatController',
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
                    $translatePartialLoader.addPart('tipoNormaCat');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-norma-cat-detail', {
            parent: 'tipo-norma-cat',
            url: '/tipo-norma-cat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.tipoNormaCat.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-norma-cat/tipo-norma-cat-detail.html',
                    controller: 'TipoNormaCatDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoNormaCat');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoNormaCat', function($stateParams, TipoNormaCat) {
                    return TipoNormaCat.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-norma-cat',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-norma-cat-detail.edit', {
            parent: 'tipo-norma-cat-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-norma-cat/tipo-norma-cat-dialog.html',
                    controller: 'TipoNormaCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoNormaCat', function(TipoNormaCat) {
                            return TipoNormaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-norma-cat.new', {
            parent: 'tipo-norma-cat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-norma-cat/tipo-norma-cat-dialog.html',
                    controller: 'TipoNormaCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-norma-cat', null, { reload: 'tipo-norma-cat' });
                }, function() {
                    $state.go('tipo-norma-cat');
                });
            }]
        })
        .state('tipo-norma-cat.edit', {
            parent: 'tipo-norma-cat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-norma-cat/tipo-norma-cat-dialog.html',
                    controller: 'TipoNormaCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoNormaCat', function(TipoNormaCat) {
                            return TipoNormaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-norma-cat', null, { reload: 'tipo-norma-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-norma-cat.delete', {
            parent: 'tipo-norma-cat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-norma-cat/tipo-norma-cat-delete-dialog.html',
                    controller: 'TipoNormaCatDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoNormaCat', function(TipoNormaCat) {
                            return TipoNormaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-norma-cat', null, { reload: 'tipo-norma-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
