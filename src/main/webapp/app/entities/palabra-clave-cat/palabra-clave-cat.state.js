(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('palabra-clave-cat', {
            parent: 'entity',
            url: '/palabra-clave-cat?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.palabraClaveCat.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/palabra-clave-cat/palabra-clave-cats.html',
                    controller: 'PalabraClaveCatController',
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
                    $translatePartialLoader.addPart('palabraClaveCat');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('palabra-clave-cat-detail', {
            parent: 'palabra-clave-cat',
            url: '/palabra-clave-cat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.palabraClaveCat.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/palabra-clave-cat/palabra-clave-cat-detail.html',
                    controller: 'PalabraClaveCatDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('palabraClaveCat');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PalabraClaveCat', function($stateParams, PalabraClaveCat) {
                    return PalabraClaveCat.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'palabra-clave-cat',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('palabra-clave-cat-detail.edit', {
            parent: 'palabra-clave-cat-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/palabra-clave-cat/palabra-clave-cat-dialog.html',
                    controller: 'PalabraClaveCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PalabraClaveCat', function(PalabraClaveCat) {
                            return PalabraClaveCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('palabra-clave-cat.new', {
            parent: 'palabra-clave-cat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/palabra-clave-cat/palabra-clave-cat-dialog.html',
                    controller: 'PalabraClaveCatDialogController',
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
                    $state.go('palabra-clave-cat', null, { reload: 'palabra-clave-cat' });
                }, function() {
                    $state.go('palabra-clave-cat');
                });
            }]
        })
        .state('palabra-clave-cat.edit', {
            parent: 'palabra-clave-cat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/palabra-clave-cat/palabra-clave-cat-dialog.html',
                    controller: 'PalabraClaveCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PalabraClaveCat', function(PalabraClaveCat) {
                            return PalabraClaveCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('palabra-clave-cat', null, { reload: 'palabra-clave-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('palabra-clave-cat.delete', {
            parent: 'palabra-clave-cat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/palabra-clave-cat/palabra-clave-cat-delete-dialog.html',
                    controller: 'PalabraClaveCatDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PalabraClaveCat', function(PalabraClaveCat) {
                            return PalabraClaveCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('palabra-clave-cat', null, { reload: 'palabra-clave-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
