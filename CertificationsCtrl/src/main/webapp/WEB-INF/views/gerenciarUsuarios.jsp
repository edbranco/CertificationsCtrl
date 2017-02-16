<%-- 
    Document   : gerenciarUsuarios
    Created on : 24/08/2016, 15:03:26
    Author     : ebranco
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="rhApp">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestão de RH</title>
        <link rel="shortcut icon" href="resources/imgs/Telesul.png" type="image/x-icon"/>
        <script src="resources/jquery/jquery-2.1.4.js" type="text/javascript"></script>
        <script src="resources/js/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
        <script src="<c:url value="resources/angularjs/external/angular.js"/>" type="text/javascript"></script>
        <script src="resources/angularjs/external/angular-animate.js" type="text/javascript"></script>
        <script src="resources/angularjs/module/module.js" type="text/javascript"></script>
        <script src="resources/angularjs/controller/gerenciarUsuariosController.js" type="text/javascript"></script>
        <script src="resources/angularjs/controller/alertController.js" type="text/javascript"></script>
        <script src="resources/angularjs/external/ui-bootstrap-tpls-1.3.3.js" type="text/javascript"></script>
        <script src="resources/bootstrap/js/bootstrap.js" type="text/javascript"></script>

        <script src="resources/angularjs/services/restService.js" type="text/javascript"></script>
        <script src="resources/angularjs/services/crudService.js" type="text/javascript"></script>

        <script src="resources/angularjs/services/broadCastService.js" type="text/javascript"></script>
        <script src="resources/js/datepicker/datepicker.js" type="text/javascript"></script>
        <script src="resources/bootstrap/js/bootstrap-datepicker.pt-BR.js" type="text/javascript"></script>
        <script src="resources/angularjs/external/dirPagination.js" type="text/javascript"></script>
        <script src="resources/angularjs/directive/search.js" type="text/javascript"></script>
        <link href="resources/css/datepicker.css" rel="stylesheet" type="text/css" />

        <link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="resources/bootstrap/css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/estilo.css" rel="stylesheet" type="text/css"/>
    </head>
    <style>
        #loader{
            top: -300px;
        }
    </style>
    <body ng-controller="gerenciarUsuariosController">
        <header ng-include="'navBar'">

        </header>
        <div ng-controller="alertController" id="allalerts">
            <uib-alert class="alert-success" ng-show="alert.type === 'success'"  ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</uib-alert>
            <uib-alert class="alert-danger" ng-show="alert.type === 'danger'"  ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</uib-alert>
        </div>
        <section>
            <article>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4>Funcionário</h4>
                    </div>
                    <div class="panel-body" search habilitado="true">

                    </div>
                </div>
            </article>
            <article class="panel-default" ng-show="selecionado">
                <form class="panel-body">
                    <fieldset>
                        <legend>Usuário para: {{selecionado.nome}}</legend>
                    </fieldset>
                    <div class="row">
                        <div class="col-sm-4">
                            <label>Usuário</label>
                            <input type="text" class="form-control" ng-model="selecionado.userandrole.userName">
                        </div>
                        <div class="col-sm-4">
                            <label>Senha</label>
                            <input type="password" class="form-control" ng-model="selecionado.userandrole.password"> 
                        </div>


                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <label>Permissão:</label>
                            <label><input ng-disabled="!selecionado.userandrole.userName" id="checkEmployee" type="checkbox" ng-checked="checkRole === 1" ng-click="selectRole('ROLE_EMPLOYEE',1)"/>Colaborador</label> 
                            <label><input ng-disabled="!selecionado.userandrole.userName" id="checkAdmin" type="checkbox" ng-checked="checkRole === 2" ng-click="selectRole('ROLE_ADMIN',2)"/>Administrador</label> 
                            <label><input ng-disabled="!selecionado.userandrole.userName" id="checkManager" type="checkbox" ng-checked="checkRole === 3" ng-click="selectRole('ROLE_MANAGER',3)"/>Gestor</label>
                        </div>
                        <div class="col-sm-6">
                            <label>Habilitado:</label>
                            <label><input ng-disabled="!selecionado.userandrole.userName" id="checkEmployee" type="checkbox" ng-checked="checkEnable === 1" ng-click="selectEnables(true)"/> Ativo</label> 
                            <label><input ng-disabled="!selecionado.userandrole.userName" id="checkAdmin" type="checkbox" ng-checked="checkEnable === 2" ng-click="selectEnables(false)"/> Inativo</label> 
                        </div>
                    </div>
                    <br>
                    <button class="btn btn-primary" ng-click="save(selecionado)">Salvar</button>
                    <button class="btn btn-danger">Cancelar</button>
                </form>
            </article>
        </section>
        <div id="loader">
            <img class="gifLoader" src="resources/imgs/loader4.gif" class="ajax-loader"/>
        </div>
    </body>
</html>
