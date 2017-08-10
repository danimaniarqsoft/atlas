(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('norma-palabra-clave', {
            parent: 'entity',
            url: '/norma-palabra-clave?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.normaPalabraClave.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/norma-palabra-clave/norma-palabra-claves.html',
                    controller: 'NormaPalabraClaveController',
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
                    $translatePartialLoader.addPart('normaPalabraClave');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('norma-palabra-clave-detail', {
            parent: 'norma-palabra-clave',
            url: '/norma-palabra-clave/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.normaPalabraClave.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/norma-palabra-clave/norma-palabra-clave-detail.html',
                    controller: 'NormaPalabraClaveDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('normaPalabraClave');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NormaPalabraClave', function($stateParams, NormaPalabraClave) {
                    return NormaPalabraClave.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'norma-palabra-clave',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('norma-palabra-clave-detail.edit', {
            parent: 'norma-palabra-clave-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-palabra-clave/norma-palabra-clave-dialog.html',
                    controller: 'NormaPalabraClaveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NormaPalabraClave', function(NormaPalabraClave) {
                            return NormaPalabraClave.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('norma-palabra-clave.new', {
            parent: 'norma-palabra-clave',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-palabra-clave/norma-palabra-clave-dialog.html',
                    controller: 'NormaPalabraClaveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                palabraClaveId: null,
                                idiomaCatId: null,
                                normaIdiomaId: null,
                                normaIdiomaNormaId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('norma-palabra-clave', null, { reload: 'norma-palabra-clave' });
                }, function() {
                    $state.go('norma-palabra-clave');
                });
            }]
        })
        .state('norma-palabra-clave.edit', {
            parent: 'norma-palabra-clave',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-palabra-clave/norma-palabra-clave-dialog.html',
                    controller: 'NormaPalabraClaveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NormaPalabraClave', function(NormaPalabraClave) {
                            return NormaPalabraClave.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('norma-palabra-clave', null, { reload: 'norma-palabra-clave' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('norma-palabra-clave.delete', {
            parent: 'norma-palabra-clave',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-palabra-clave/norma-palabra-clave-delete-dialog.html',
                    controller: 'NormaPalabraClaveDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NormaPalabraClave', function(NormaPalabraClave) {
                            return NormaPalabraClave.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('norma-palabra-clave', null, { reload: 'norma-palabra-clave' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
