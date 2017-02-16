<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>

    </head>
    <style>
        .typeEmployees{margin-top: 20px;}
    </style>
    <body>
        <div class="row">
            <div class="col-xs-4">
                <label for="search">Buscar:</label>
                <input ng-model="q2" id="search" class="form-control" placeholder="Buscar Nome">
            </div>
            <div class="col-xs-4">
                <label for="search">Itens Por Página:</label>
                <input type="number" min="1" max="20" class="form-control" ng-model="tamanhoPagina">
            </div>
            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">
                <div class="col-xs-4">
                    <div class="btn-group typeEmployees pull-right">
                        <button type="button" class="btn btn-primary" ng-click="typeEmployee('true')">Ativos</button>
                        <button type="button" class="btn btn-primary" ng-click="typeEmployee('false')">Inativos</button>
                    </div>
                </div>
            </sec:authorize>

            <div class="col-xs-12 table-responsive">
                <table class="table table-hover">
                    <thead class="headerTable">
                        <tr>
                            <th>Selecionar</th>
                            <th>Nome</th>
                            <th>Cargo</th>
                            <th>Área</th>   
                                <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">
                                <th>Relatórios Individuais</th>                        
                                </sec:authorize>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-click="isEmployeeSelected(employee, $index)"
                            dir-paginate="employee in employees| filter:filtro | itemsPerPage: tamanhoPagina" 
                            total-items="total2" current-page="atualPagina" pagination-id="entityPagination2">
                            <td>
                                <input name="choosenEntity" type="radio" ng-checked="employee.nome === selecionado.nome">
                            </td>
                            <td>
                                {{employee.nome}}
                            </td>
                            <td>{{employee.cargo}}</td>
                            <td>{{employee.area}}</td>
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">
                                <td><button class="btn btn-info btn-sm" ng-click="singleReport(employee.idFuncionario)">Relatório</button></td>
                            </sec:authorize>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-xs-6 pull-right">
                <div class="text-center">
                    <dir-pagination-controls boundary-links="true" pagination-id="entityPagination2"
                                             ruleset-url="resources/angularjs/directive/pagination/pagination.html"
                                             on-page-change="pageChanged(atualPagina,tamanhoPagina,filtro)"></dir-pagination-controls>
                </div>
            </div>
        </div>
    </body>
</html>
