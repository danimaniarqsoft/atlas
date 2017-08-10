(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('idioma-cat', {
            parent: 'entity',
            url: '/idioma-cat?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.idiomaCat.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/idioma-cat/idioma-cats.html',
                    controller: 'IdiomaCatController',
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
                    $translatePartialLoader.addPart('idiomaCat');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('idioma-cat-detail', {
            parent: 'idioma-cat',
            url: '/idioma-cat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.idiomaCat.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/idioma-cat/idioma-cat-detail.html',
                    controller: 'IdiomaCatDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('idiomaCat');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'IdiomaCat', function($stateParams, IdiomaCat) {
                    return IdiomaCat.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'idioma-cat',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('idioma-cat-detail.edit', {
            parent: 'idioma-cat-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/idioma-cat/idioma-cat-dialog.html',
                    controller: 'IdiomaCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IdiomaCat', function(IdiomaCat) {
                            return IdiomaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('idioma-cat.new', {
            parent: 'idioma-cat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/idioma-cat/idioma-cat-dialog.html',
                    controller: 'IdiomaCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idioma1: null,
                                idioma2: null,
                                idioma3: null,
                                idioma4: null,
                                idioma5: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('idioma-cat', null, { reload: 'idioma-cat' });
                }, function() {
                    $state.go('idioma-cat');
                });
            }]
        })
        .state('idioma-cat.edit', {
            parent: 'idioma-cat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/idioma-cat/idioma-cat-dialog.html',
                    controller: 'IdiomaCatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IdiomaCat', function(IdiomaCat) {
                            return IdiomaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('idioma-cat', null, { reload: 'idioma-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('idioma-cat.delete', {
            parent: 'idioma-cat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/idioma-cat/idioma-cat-delete-dialog.html',
                    controller: 'IdiomaCatDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['IdiomaCat', function(IdiomaCat) {
                            return IdiomaCat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('idioma-cat', null, { reload: 'idioma-cat' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
