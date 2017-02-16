/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var app = angular.module("rhApp");
app.directive("search", function (crudService) {
    return {
        restrict: 'EA',
        templateUrl: 'searchTemplate',
        scope: '=',
        link: function ($scope, element, attrs) {
            var habilitado = attrs.habilitado;
            $scope.habilitado = habilitado;
            var tamanhoPagina = 5;
            $scope.tamanhoPagina = tamanhoPagina;
            var atualPagina = 1;
            var pageAssignedSize2 = 5;

            $scope.filtro = "";
            var firstTimeSearch2 = false;

            searchForPagination(atualPagina, tamanhoPagina, $scope.filtro, habilitado);

            $scope.pageChanged = function (novaPagina, tamanhoPagina, buscaFiltro) {

                $scope.filtro = buscaFiltro;
                atualPagina = atualPagina++;
                this.tamanhoPagina = tamanhoPagina;
                var filtro = buscaFiltro;
                console.log(filtro);
                if (!firstTimeSearch2) {
                    $scope.employees = searchForPagination(novaPagina, this.tamanhoPagina, buscaFiltro, habilitado);
                } else {
                    firstTimeSearch2 = false;
                }
            };
            $scope.typeEmployee = function (type) {
                habilitado = type;
                $scope.habilitado = type;
                $scope.employees = searchForPagination(atualPagina, tamanhoPagina, $scope.filtro, habilitado);
            };
            $scope.$watch('q2', function (novoFiltro) {
                if ($scope.q2.length > 2 || $scope.q2.length === 0) {
                    $scope.filtro = novoFiltro;
                    searchForPagination(atualPagina, tamanhoPagina, novoFiltro, habilitado);
                }
            });
            
            var date="";
            function successOnSearch(data) {
                $("#loader").hide();
                console.log(data.objects);
                angular.forEach(data.objects, function (value, index) {
                    if (angular.isUndefined(value.treinamentos) || value.treinamentos === null) {

                    } else {
                        for (var i = 0; i < value.treinamentos.length; i++) {
                            if (angular.isUndefined(value.treinamentos[i].dtInicio) || value.treinamentos[i].dtInicio === null) {

                            } else {
                                var index = 0;
                                console.log(typeof value.treinamentos[i].dtInicio);
                                date = value.treinamentos[i].dtInicio;
                                value.treinamentos[i].dtInicio = date.slice(3, 10);
                            }

                            if (angular.isUndefined(value.treinamentos[i].dtTermino) || value.treinamentos[i].dtTermino === null) {

                            } else {

                                date = value.treinamentos[i].dtTermino;
                                value.treinamentos[i].dtTermino = date.slice(3, 10);
                            }
                            console.log(value.treinamentos[i].dtInicio);
                            console.log(value.treinamentos[i].dtTermino);
                        }
                    }
                });
                $scope.employees = data.objects;
                $scope.total2 = data.totalEntities;
            }
            function errorOnSearch(error, status) {
                $("#loader").hide();
                console.log("ajax error:" + error);
                console.log("ajax status:" + status);
            }
            function searchForPagination(newPage2, tamanhoPagina, buscaFiltro, habilitado) {
                $("#loader").show();
                if (tamanhoPagina === null) {
                    tamanhoPagina = 1;
                }
                if (buscaFiltro === undefined) {
                    buscaFiltro = "";
                }
                crudService.searchFast(newPage2, tamanhoPagina, buscaFiltro, habilitado, successOnSearch, errorOnSearch);
            }
        }

    };
});