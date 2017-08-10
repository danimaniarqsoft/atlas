(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subtema-cat', {
            parent: 'entity',
            url: '/subtema-cat?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.subtemaCat.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subtema-cat/subtema-cats.html',
                    controller: 'SubtemaCatController',
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
                    $translatePartialLoader.addPart('subtemaCat');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subtema-cat-detail', {
            parent: 'subtema-cat',
            url: '/subtema-cat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.subtemaCat.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subtema-cat/subtema-cat-detail.html',
                    controller: 'SubtemaCatDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subtemaCat');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SubtemaCat', function($stateParams, SubtemaCat) {
                    return SubtemaCat.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'subtema-cat',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('subtema-cat-detail.edit', {
            parent: 'subtema-cat-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subtema-cat/subtema-cat-dialog.html',
                    controller: 'SubtemaCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SubtemaCat', function(SubtemaCat) {
                            return SubtemaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subtema-cat.new', {
            parent: 'subtema-cat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subtema-cat/subtema-cat-dialog.html',
                    controller: 'SubtemaCatDialogController',
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
                    $state.go('subtema-cat', null, { reload: 'subtema-cat' });
                }, function() {
                    $state.go('subtema-cat');
                });
            }]
        })
        .state('subtema-cat.edit', {
            parent: 'subtema-cat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subtema-cat/subtema-cat-dialog.html',
                    controller: 'SubtemaCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SubtemaCat', function(SubtemaCat) {
                            return SubtemaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subtema-cat', null, { reload: 'subtema-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subtema-cat.delete', {
            parent: 'subtema-cat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subtema-cat/subtema-cat-delete-dialog.html',
                    controller: 'SubtemaCatDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SubtemaCat', function(SubtemaCat) {
                            return SubtemaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subtema-cat', null, { reload: 'subtema-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
