(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tema-cat', {
            parent: 'entity',
            url: '/tema-cat?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.temaCat.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tema-cat/tema-cats.html',
                    controller: 'TemaCatController',
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
                    $translatePartialLoader.addPart('temaCat');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tema-cat-detail', {
            parent: 'tema-cat',
            url: '/tema-cat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.temaCat.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tema-cat/tema-cat-detail.html',
                    controller: 'TemaCatDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('temaCat');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TemaCat', function($stateParams, TemaCat) {
                    return TemaCat.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tema-cat',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tema-cat-detail.edit', {
            parent: 'tema-cat-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema-cat/tema-cat-dialog.html',
                    controller: 'TemaCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TemaCat', function(TemaCat) {
                            return TemaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tema-cat.new', {
            parent: 'tema-cat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema-cat/tema-cat-dialog.html',
                    controller: 'TemaCatDialogController',
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
                    $state.go('tema-cat', null, { reload: 'tema-cat' });
                }, function() {
                    $state.go('tema-cat');
                });
            }]
        })
        .state('tema-cat.edit', {
            parent: 'tema-cat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema-cat/tema-cat-dialog.html',
                    controller: 'TemaCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TemaCat', function(TemaCat) {
                            return TemaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tema-cat', null, { reload: 'tema-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tema-cat.delete', {
            parent: 'tema-cat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema-cat/tema-cat-delete-dialog.html',
                    controller: 'TemaCatDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TemaCat', function(TemaCat) {
                            return TemaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tema-cat', null, { reload: 'tema-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
