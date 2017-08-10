(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('norma-subtema', {
            parent: 'entity',
            url: '/norma-subtema?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.normaSubtema.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/norma-subtema/norma-subtemas.html',
                    controller: 'NormaSubtemaController',
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
                    $translatePartialLoader.addPart('normaSubtema');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('norma-subtema-detail', {
            parent: 'norma-subtema',
            url: '/norma-subtema/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.normaSubtema.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/norma-subtema/norma-subtema-detail.html',
                    controller: 'NormaSubtemaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('normaSubtema');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NormaSubtema', function($stateParams, NormaSubtema) {
                    return NormaSubtema.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'norma-subtema',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('norma-subtema-detail.edit', {
            parent: 'norma-subtema-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-subtema/norma-subtema-dialog.html',
                    controller: 'NormaSubtemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NormaSubtema', function(NormaSubtema) {
                            return NormaSubtema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('norma-subtema.new', {
            parent: 'norma-subtema',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-subtema/norma-subtema-dialog.html',
                    controller: 'NormaSubtemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                subtemaCatId: null,
                                idiomaCatId: null,
                                normaIdiomaId: null,
                                normaIdiomaIdiomaCatId: null,
                                normaIdiomaNormaId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('norma-subtema', null, { reload: 'norma-subtema' });
                }, function() {
                    $state.go('norma-subtema');
                });
            }]
        })
        .state('norma-subtema.edit', {
            parent: 'norma-subtema',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-subtema/norma-subtema-dialog.html',
                    controller: 'NormaSubtemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NormaSubtema', function(NormaSubtema) {
                            return NormaSubtema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('norma-subtema', null, { reload: 'norma-subtema' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('norma-subtema.delete', {
            parent: 'norma-subtema',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-subtema/norma-subtema-delete-dialog.html',
                    controller: 'NormaSubtemaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NormaSubtema', function(NormaSubtema) {
                            return NormaSubtema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('norma-subtema', null, { reload: 'norma-subtema' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
