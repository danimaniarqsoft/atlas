(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('estatus-cat', {
            parent: 'entity',
            url: '/estatus-cat?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.estatusCat.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/estatus-cat/estatus-cats.html',
                    controller: 'EstatusCatController',
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
                    $translatePartialLoader.addPart('estatusCat');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('estatus-cat-detail', {
            parent: 'estatus-cat',
            url: '/estatus-cat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.estatusCat.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/estatus-cat/estatus-cat-detail.html',
                    controller: 'EstatusCatDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('estatusCat');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EstatusCat', function($stateParams, EstatusCat) {
                    return EstatusCat.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'estatus-cat',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('estatus-cat-detail.edit', {
            parent: 'estatus-cat-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/estatus-cat/estatus-cat-dialog.html',
                    controller: 'EstatusCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EstatusCat', function(EstatusCat) {
                            return EstatusCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('estatus-cat.new', {
            parent: 'estatus-cat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/estatus-cat/estatus-cat-dialog.html',
                    controller: 'EstatusCatDialogController',
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
                    $state.go('estatus-cat', null, { reload: 'estatus-cat' });
                }, function() {
                    $state.go('estatus-cat');
                });
            }]
        })
        .state('estatus-cat.edit', {
            parent: 'estatus-cat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/estatus-cat/estatus-cat-dialog.html',
                    controller: 'EstatusCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EstatusCat', function(EstatusCat) {
                            return EstatusCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('estatus-cat', null, { reload: 'estatus-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('estatus-cat.delete', {
            parent: 'estatus-cat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/estatus-cat/estatus-cat-delete-dialog.html',
                    controller: 'EstatusCatDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EstatusCat', function(EstatusCat) {
                            return EstatusCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('estatus-cat', null, { reload: 'estatus-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
