(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('norma-tema', {
            parent: 'entity',
            url: '/norma-tema?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.normaTema.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/norma-tema/norma-temas.html',
                    controller: 'NormaTemaController',
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
                    $translatePartialLoader.addPart('normaTema');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('norma-tema-detail', {
            parent: 'norma-tema',
            url: '/norma-tema/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.normaTema.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/norma-tema/norma-tema-detail.html',
                    controller: 'NormaTemaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('normaTema');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NormaTema', function($stateParams, NormaTema) {
                    return NormaTema.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'norma-tema',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('norma-tema-detail.edit', {
            parent: 'norma-tema-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-tema/norma-tema-dialog.html',
                    controller: 'NormaTemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NormaTema', function(NormaTema) {
                            return NormaTema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('norma-tema.new', {
            parent: 'norma-tema',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-tema/norma-tema-dialog.html',
                    controller: 'NormaTemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                temaCatId: null,
                                idiomaCatId: null,
                                normaIdiomaId: null,
                                normaIdiomaNormaId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('norma-tema', null, { reload: 'norma-tema' });
                }, function() {
                    $state.go('norma-tema');
                });
            }]
        })
        .state('norma-tema.edit', {
            parent: 'norma-tema',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-tema/norma-tema-dialog.html',
                    controller: 'NormaTemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NormaTema', function(NormaTema) {
                            return NormaTema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('norma-tema', null, { reload: 'norma-tema' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('norma-tema.delete', {
            parent: 'norma-tema',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-tema/norma-tema-delete-dialog.html',
                    controller: 'NormaTemaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NormaTema', function(NormaTema) {
                            return NormaTema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('norma-tema', null, { reload: 'norma-tema' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
