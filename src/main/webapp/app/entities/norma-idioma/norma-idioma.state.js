(function() {
    'use strict';

    angular
        .module('atlasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('norma-idioma', {
            parent: 'entity',
            url: '/norma-idioma?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.normaIdioma.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/norma-idioma/norma-idiomas.html',
                    controller: 'NormaIdiomaController',
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
                    $translatePartialLoader.addPart('normaIdioma');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('norma-idioma-detail', {
            parent: 'norma-idioma',
            url: '/norma-idioma/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'atlasApp.normaIdioma.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/norma-idioma/norma-idioma-detail.html',
                    controller: 'NormaIdiomaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('normaIdioma');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NormaIdioma', function($stateParams, NormaIdioma) {
                    return NormaIdioma.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'norma-idioma',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('norma-idioma-detail.edit', {
            parent: 'norma-idioma-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-idioma/norma-idioma-dialog.html',
                    controller: 'NormaIdiomaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NormaIdioma', function(NormaIdioma) {
                            return NormaIdioma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('norma-idioma.new', {
            parent: 'norma-idioma',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-idioma/norma-idioma-dialog.html',
                    controller: 'NormaIdiomaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                titulo: null,
                                descripcion: null,
                                texto: null,
                                link: null,
                                fechaModificacion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('norma-idioma', null, { reload: 'norma-idioma' });
                }, function() {
                    $state.go('norma-idioma');
                });
            }]
        })
        .state('norma-idioma.edit', {
            parent: 'norma-idioma',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-idioma/norma-idioma-dialog.html',
                    controller: 'NormaIdiomaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NormaIdioma', function(NormaIdioma) {
                            return NormaIdioma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('norma-idioma', null, { reload: 'norma-idioma' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('norma-idioma.delete', {
            parent: 'norma-idioma',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/norma-idioma/norma-idioma-delete-dialog.html',
                    controller: 'NormaIdiomaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NormaIdioma', function(NormaIdioma) {
                            return NormaIdioma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('norma-idioma', null, { reload: 'norma-idioma' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
