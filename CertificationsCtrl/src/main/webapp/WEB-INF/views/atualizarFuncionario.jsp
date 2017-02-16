<%-- 
    Document   : atualizar-funcionario
    Created on : 23/06/2016, 20:18:38
    Author     : Eder Rodrigues
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="rhApp" ng-init="userLogged = '${pageContext.request.userPrincipal.name}'"
      <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestão de RH</title>
        <link rel="shortcut icon" href="resources/imgs/Telesul.png" type="image/x-icon"/>
        <link rel="shortcut icon" href="resources/imgs/Telesul.png" type="image/x-icon"/>
        <script src="resources/jquery/jquery-2.1.4.js" type="text/javascript"></script>

        <script src="resources/js/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
        <script src="<c:url value="resources/angularjs/external/angular.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="resources/angularjs/external/angular-animate.js"/>" type="text/javascript"></script>
        <script src="<c:url value="resources/angularjs/module/module.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/angularjs/controller/atualizarFuncionarioController.js"/>" type="text/javascript"></script>

        <script src="resources/angularjs/controller/alertController.js" type="text/javascript"></script>
        <script src="resources/bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <script src="resources/angularjs/external/ui-bootstrap-tpls-1.3.3.js" type="text/javascript"></script>
        <script src="resources/angularjs/directive/search.js" type="text/javascript"></script>
        <script src="resources/angularjs/services/restService.js" type="text/javascript"></script>
        <script src="resources/angularjs/services/crudService.js" type="text/javascript"></script>
        <script src="resources/angularjs/services/exportService.js" type="text/javascript"></script>
        <script src="resources/angularjs/services/broadCastService.js" type="text/javascript"></script>
        <script src="resources/js/datepicker/datepicker.js" type="text/javascript"></script>
        <script src="resources/bootstrap/js/bootstrap-datepicker.pt-BR.js" type="text/javascript"></script>
        <script src="resources/angularjs/external/dirPagination.js" type="text/javascript"></script>


        <link href="resources/css/datepicker.css" rel="stylesheet" type="text/css" />

        <link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="resources/bootstrap/css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/estilo.css" rel="stylesheet" type="text/css"/>
    </head>
    <body ng-controller="atualizarFuncionario">
        <header ng-include="'navBar'"></header>
        <section>
            <div ng-controller="alertController" id="allalerts">
                <uib-alert class="alert-success" ng-show="alert.type === 'success'"  ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</uib-alert>
                <uib-alert class="alert-danger" ng-show="alert.type === 'danger'"  ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</uib-alert>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Funcionário</h4>
                </div>
                <div class="panel panel-body" id="atualizarFuncionario">
                    <form class="formulario">

                        <div search habilitado="true"></div>
                        <div ng-show="selecionado">
                            <sec:authorize access="hasAnyRole('ROLE_EMPLOYEE')">
                                <ul class="nav nav-tabs">
                                    <li id="tabs" ng-class="indexColor === $index ? 'active' : 'none'" ng-click="applyClass(op, $index)" ng-repeat="op in options" ng-hide='$index > 0 && userLogged !== selecionado.userandrole.userName'><a href="#" ng-click="ativarForm($index)">{{op}}</a></li>
                                </ul>
                            </sec:authorize>
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">
                                <ul class="nav nav-tabs">
                                    <li id="tabs" ng-class="indexColor === $index ? 'active' : 'none'" ng-click="applyClass(op, $index)" ng-hide="$index === 5" ng-repeat="op in options"><a href="#" ng-click="ativarForm($index)">{{op}}</a></li>
                                </ul>
                            </sec:authorize>
                            <div ng-show="optionsBoolean[0]">
                                <div class="border">
                                    <div class="form-group">
                                        <label>Nome*</label>
                                        <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                            <input type="text" ng-disabled="userLogged !== selecionado.userandrole.userName" class="form-control" ng-model="selecionado.nome" ng-change="selecionado.nome = selecionado.nome.toUpperCase();"/>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                            <input type="text" class="form-control" ng-model="selecionado.nome" ng-change="selecionado.nome = selecionado.nome.toUpperCase();"/>                           
                                        </sec:authorize>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-3"><label>Cargo*</label></div>
                                        <div class="col-sm-3"><label>Data de Admissão*</label></div>                                    
                                        <div class="col-sm-3"><label>Área*</label></div>  
                                        <div class="col-sm-3">
                                            <label>Gestor*</label>
                                        </div>

                                    </div>

                                    <div class="row">
                                        <div class="col-sm-3">
                                            <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="selecionado.cargo" ng-change="selecionado.cargo = selecionado.cargo.toUpperCase();"/>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                <input type="text" class="form-control" ng-model="selecionado.cargo" ng-change="selecionado.cargo = selecionado.cargo.toUpperCase();"/>
                                            </sec:authorize>
                                        </div>
                                        <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                            <div class="col-sm-3">
                                                <div class='input-group date dtpicker' data-provide="datepicker">                                                
                                                    <input type="text" ng-disabled="userLogged !== selecionado.userandrole.userName" class="form-control" ng-model="selecionado.dtAdmissao"/>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                </div>
                                            </div>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                            <div class="col-sm-3">
                                                <div class='input-group date dtpicker' data-provide="datepicker">                                                
                                                    <input type="text" class="form-control" ng-model="selecionado.dtAdmissao"/>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                </div>
                                            </div>
                                        </sec:authorize>
                                        <div class="col-sm-3"> 
                                            <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                <input type="text" ng-disabled="userLogged !== selecionado.userandrole.userName" class="form-control" ng-model="selecionado.area" ng-change="selecionado.area = selecionado.area.toUpperCase();"/>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                <input type="text" class="form-control" ng-model="selecionado.area" ng-change="selecionado.area = selecionado.area.toUpperCase();"/>
                                            </sec:authorize>
                                        </div>
                                        <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                            <div class="col-sm-3">
                                                <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="selecionado.gestor" ng-change="selecionado.gestor = selecionado.gestor.toUpperCase();"/>
                                            </div>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                            <div class="col-sm-3">
                                                <input type="text" class="form-control" ng-model="selecionado.gestor" ng-change="selecionado.gestor = selecionado.gestor.toUpperCase();"/>
                                            </div>
                                        </sec:authorize>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <label>Email</label>
                                        </div>
                                        <div class="col-sm-3">
                                            <label>Telefone</label>
                                        </div>
                                        <div class="col-sm-3">
                                            <label>Celular</label>
                                        </div>
                                        <div class="col-sm-3">
                                            <label>Habilitado*</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                            <div class="col-sm-3">
                                                <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="email" class="form-control" ng-model="selecionado.email" ng-change="selecionado.email = selecionado.email.toUpperCase();">
                                            </div>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                            <div class="col-sm-3">
                                                <input type="email" class="form-control" ng-model="selecionado.email" ng-change="selecionado.email = selecionado.email.toUpperCase();">
                                            </div>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                            <div class="col-sm-3">
                                                <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="selecionado.telefone">
                                            </div>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                            <div class="col-sm-3">
                                                <input type="text" class="form-control" ng-model="selecionado.telefone">
                                            </div>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')">
                                            <div class="col-sm-3">
                                                <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="selecionado.celular">
                                            </div>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                            <div class="col-sm-3">
                                                <input type="text" class="form-control" ng-model="selecionado.celular">
                                            </div>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')">
                                            <div class="col-sm-3">
                                                <input type="text" class="form-control" readonly="true" value="Sim"/>
                                            </div>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                            <div class="col-sm-3">
                                                <select class="form-control" ng-model="selecionado.habilitado">
                                                    <option ng-value="true" ng-selected="selecionado.habilitado" ng-click="changeEnable(true)">Sim</option>
                                                    <option ng-value="false" ng-selected="selecionado.habilitado === false" ng-click="changeEnable(false)">Não</option>
                                                </select>
                                            </div>
                                        </sec:authorize>
                                    </div>
                                    <div class="row">
                                        <sec:authorize access="hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')">
                                            <div class="col-sm-3">
                                                <label>Data de Saída</label>
                                                <div class='input-group date dtpicker' data-provide="datepicker">
                                                    <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="selecionado.dtSaida"/>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                </div>
                                            </div>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                            <div class="col-sm-3">
                                                <label>Data de Saída</label>
                                                <div class='input-group date dtpicker' data-provide="datepicker">
                                                    <input type="text" class="form-control" ng-model="selecionado.dtSaida"/>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                </div>
                                            </div>
                                        </sec:authorize>
                                    </div>
                                    <br>

                                </div>
                                <br>
                            </div>
                            <div ng-show="optionsBoolean[1]">
                                <table class="table table-hover table-condensed">
                                    <thead class="headerTable">
                                    <th>Nivel*</th>
                                    <th>Curso*</th>
                                    <th>Instituicao*</th>
                                    <th>Copia de Certificação</th>                                        
                                    <th></th>                                        
                                    <th></th>                                        
                                    </thead>
                                    <tbody>
                                        <tr ng-repeat="formacao in selecionado.formacoes">
                                            <td>
                                                <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" readonly="true" ng-model="formacao.nivel" ng-hide="selecionado.checked">

                                                <select class="form-control" ng-model="formacao.nivel" ng-show="selecionado.checked">
                                                    <option>Segundo Grau Completo</option>
                                                    <option>Superior Incompleto</option>
                                                    <option>Superior em Andamento</option>
                                                    <option>Superior Completo</option>
                                                    <option>Pós Graduação Completa</option>
                                                    <option>Pós Graduação em Andamento</option>
                                                    <option>Mestrado Completo</option>
                                                    <option>Mestrado em Andamento</option>
                                                    <option>MBA Completo</option>
                                                    <option>MBA em andamento</option>
                                                    <option>Doutorado Completo</option>
                                                    <option>Doutorado em Andamento</option>
                                                </select>

                                            </td>
                                            <td>
                                                <input type="text" class="form-control" readonly="true" ng-model="formacao.curso" ng-hide="selecionado.checked">
                                                <input type="text" class="form-control" ng-model="formacao.curso" ng-show="selecionado.checked" ng-change="formacao.curso = formacao.curso.toUpperCase();">

                                            </td>
                                            <td>
                                                <input type="text" class="form-control" readonly="true" ng-model="formacao.instituicao" ng-hide="selecionado.checked">
                                                <input type="text" class="form-control"  ng-model="formacao.instituicao" ng-show="selecionado.checked" ng-change="formacao.instituicao = formacao.instituicao.toUpperCase();">
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" readonly="true" ng-model="formacao.copiaCertificado" ng-hide="selecionado.checked">

                                                <select class="form-control" ng-model="formacao.copiaCertificado" ng-show="selecionado.checked">
                                                    <option>Sim</option>
                                                    <option>Não</option>
                                                </select>

                                            </td>
                                            <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                <td>
                                                    <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="editRow()" ng-hide="selecionado.checked">Editar</button>
                                                    <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="saveRow()" ng-show="selecionado.checked">Salvar</button>
                                                    <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-warning" class="btn btn-block" ng-click="removeRow($index, 'formacao')">Remover</button>
                                                </td>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                <td>
                                                    <button type="button"  class="btn btn-info" class="btn btn-block" ng-click="editRow()" ng-hide="selecionado.checked">Editar</button>
                                                    <button type="button"  class="btn btn-info" class="btn btn-block" ng-click="saveRow()" ng-show="selecionado.checked">Salvar</button>
                                                    <button type="button"  class="btn btn-warning" class="btn btn-block" ng-click="removeRow($index, 'formacao')">Remover</button>
                                                </td>
                                            </sec:authorize>
                                        </tr>
                                        <tr>
                                            <td>

                                                <select class="form-control" ng-model="newFormacoes.nivel">
                                                    <option>Segundo Grau Completo</option>
                                                    <option>Superior Incompleto</option>
                                                    <option>Superior em Andamento</option>
                                                    <option>Superior Completo</option>
                                                    <option>Pós Graduação Completa</option>
                                                    <option>Pós Graduação em Andamento</option>
                                                    <option>Mestrado Completo</option>
                                                    <option>Mestrado em Andamento</option>
                                                    <option>MBA Completo</option>
                                                    <option>MBA em andamento</option>
                                                    <option>Doutorado Completo</option>
                                                    <option>Doutorado em Andamento</option>
                                                </select>

                                            </td>
                                            <td>
                                                <input type="text" class="form-control" ng-model="newFormacoes.curso" ng-change="newFormacoes.curso = newFormacoes.curso.toUpperCase();">

                                            </td>
                                            <td>
                                                <input type="text" class="form-control" ng-model="newFormacoes.instituicao" ng-change="newFormacoes.instituicao = newFormacoes.instituicao.toUpperCase();">
                                            </td>
                                            <td>
                                                <select class="form-control" ng-model="newFormacoes.copiaCertificado">
                                                    <option>Sim</option>
                                                    <option>Não</option>
                                                </select>
                                            </td>
                                            <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                <td>
                                                    <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="addRow(newFormacoes, 'formacao')">Adicionar</button>
                                                    <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-warning" class="btn btn-block" ng-click="cancelRow('formacao')">Cancelar</button>
                                                </td>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                <td>
                                                    <button type="button"  class="btn btn-info" class="btn btn-block" ng-click="addRow(newFormacoes, 'formacao')">Adicionar</button>
                                                    <button type="button"  class="btn btn-warning" class="btn btn-block" ng-click="cancelRow('formacao')">Cancelar</button>
                                                </td>
                                            </sec:authorize>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div ng-show="optionsBoolean[2]">
                                <table class="table table-condensed table-hover">
                                    <thead class="headerTable">
                                    <th>Idioma*</th>
                                    <th>Nível*</th>                                        
                                    <th></th>                                        
                                    <th></th>                                        
                                    </thead>
                                    <tbody>
                                        <tr ng-repeat="idioma in selecionado.idiomas">
                                            <td>
                                                <input type="text" class="form-control" readonly="true" ng-model="idioma.nome" ng-hide="selecionado.checked">
                                                <select class="form-control" id="sel1" ng-model="idioma.nome" ng-show="selecionado.checked">
                                                    <option  ng-repeat="language in languages">{{language}}</option>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" readonly="true" ng-model="idioma.nivel" ng-hide="selecionado.checked">
                                                <select class="form-control" id="sel1" ng-model="idioma.nivel" ng-show="selecionado.checked">
                                                    <option  ng-repeat="nivel in niveis">{{nivel}}</option>
                                                </select>

                                            </td>
                                            <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                <td>
                                                    <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="editRow()" ng-hide="selecionado.checked">Editar</button>
                                                    <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="saveRow(formacao)" ng-show="selecionado.checked">Salvar</button>
                                                    <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-warning" class="btn btn-block" ng-click="removeRow($index, 'idioma')">Remover</button>
                                                </td>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                <td>
                                                    <button type="button"  class="btn btn-info" class="btn btn-block" ng-click="editRow()" ng-hide="selecionado.checked">Editar</button>
                                                    <button type="button"  class="btn btn-info" class="btn btn-block" ng-click="saveRow(formacao)" ng-show="selecionado.checked">Salvar</button>
                                                    <button type="button"  class="btn btn-warning" class="btn btn-block" ng-click="removeRow($index, 'idioma')">Remover</button>
                                                </td>
                                            </sec:authorize>
                                        </tr>
                                        <tr>
                                            <td>
                                                <select class="form-control" id="sel1" ng-model="novoIdioma.nome">
                                                    <option  ng-repeat="language in languages">{{language}}</option>
                                                </select>

                                            </td>
                                            <td>
                                                <select class="form-control" id="sel1" ng-model="novoIdioma.nivel">
                                                    <option  ng-repeat="nivel in niveis">{{nivel}}</option>
                                                </select>
                                            </td>
                                            <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                <td>
                                                    <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="addRow(novoIdioma, 'idioma')">Adicionar</button>
                                                    <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-warning" class="btn btn-block" ng-click="cancelRow('idiomas')">Cancelar</button>
                                                </td>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                <td>
                                                    <button type="button"  class="btn btn-info" class="btn btn-block" ng-click="addRow(novoIdioma, 'idioma')">Adicionar</button>
                                                    <button type="button"  class="btn btn-warning" class="btn btn-block" ng-click="cancelRow('idiomas')">Cancelar</button>
                                                </td>
                                            </sec:authorize>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div ng-show="optionsBoolean[3]">
                                <div class="border">
                                    <h4 ng-show="funcionario.certificacoes.length > 0">Certificação</h4>
                                    <div class="border" ng-repeat="certificacao in selecionado.certificacoes">
                                        <div>
                                            <h4>{{certificacao.nome}}</h4>
                                            <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName"  class="" id="removeBottom" ng-click="removeRow($index, 'certificacao')">X</button>
                                            </sec:authorize>

                                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                <button type="button" class="" id="removeBottom" ng-click="removeRow($index, 'certificacao')">X</button>
                                            </sec:authorize>
                                        </div>
                                        <div class="border">
                                            <div class="row">
                                                <div class="col-sm-2"><label>Código</label></div>
                                                <div class="col-sm-3"><label>Certificadora</label></div>
                                                <div class="col-sm-3"><label>Exame</label></div>

                                            </div>
                                            <div class="row">
                                                <div class="col-sm-2"> 
                                                    <input type="text" class="form-control" readonly="true" ng-model="certificacao.codigo" ng-hide="selecionado.checked">
                                                    <input type="text" class="form-control"  ng-model="certificacao.codigo" ng-show="selecionado.checked" ng-change="certificacao.codigo = certificacao.codigo.toUpperCase();">
                                                </div>
                                                <div class="col-sm-3">
                                                    <input type="text" class="form-control" readonly="true" ng-model="certificacao.empresa" ng-hide="selecionado.checked">
                                                    <input type="text" class="form-control"  ng-model="certificacao.empresa" ng-show="selecionado.checked" ng-change="certificacao.empresa = certificacao.empresa.toUpperCase();">
                                                </div>
                                                <div class="col-sm-6">
                                                    <input type="text" class="form-control" readonly="true" ng-model="certificacao.nome" ng-hide="selecionado.checked">
                                                    <input type="text" class="form-control" ng-model="certificacao.nome" ng-show="selecionado.checked" ng-change="certificacao.nome = certificacao.nome.toUpperCase();">
                                                </div>

                                            </div>
                                            <div class="row">
                                                <div class="col-sm-3"><label>Data de Exame</label></div>
                                                <div class="col-sm-3"><label>Data Validade</label></div>
                                                <div class="col-sm-3"><label>Cópia</label></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-sm-3">
                                                    <input type="text" class="form-control" readonly="true" ng-model="certificacao.dtExame" ng-hide="selecionado.checked">
                                                    <div class='input-group date dtpicker' data-provide="datepicker" ng-show="selecionado.checked">
                                                        <input type="text" class="form-control" ng-model="certificacao.dtExame"/>
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="col-sm-3">
                                                    <input type="text" class="form-control" readonly="true" ng-model="certificacao.dtValidade" ng-hide="selecionado.checked">
                                                    <div class='input-group date dtpicker' data-provide="datepicker" ng-show="selecionado.checked">
                                                        <input type="text" class="form-control" ng-model="certificacao.dtValidade"/>
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="col-sm-3">
                                                    <input type="text" class="form-control" readonly="true" ng-model="certificacao.copia" ng-hide="selecionado.checked">
                                                    <select class="form-control" ng-model="certificacao.copia" ng-show="selecionado.checked">
                                                        <option>Sim</option>
                                                        <option>Não</option>
                                                    </select>
                                                </div>
                                                <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                    <div class="col-sm-3">
                                                        <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="saveRow()" ng-show="selecionado.checked">Salvar</button>
                                                        <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="editRow()" ng-hide="selecionado.checked">Editar</button>

                                                    </div>
                                                </sec:authorize>
                                                <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                    <div class="col-sm-3">
                                                        <button type="button"  class="btn btn-info" class="btn btn-block" ng-click="saveRow()" ng-show="selecionado.checked">Salvar</button>
                                                        <button type="button"  class="btn btn-info" class="btn btn-block" ng-click="editRow()" ng-hide="selecionado.checked">Editar</button>

                                                    </div>
                                                </sec:authorize>
                                                <br>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-2"><label>Código</label></div>
                                        <div class="col-sm-3"><label>Certificadora*</label></div>
                                        <div class="col-sm-3"><label>Exame*</label></div>

                                    </div>
                                    <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                        <div class="row">
                                            <div class="col-sm-2"><input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newCertificacoes.codigo" ng-change="newCertificacoes.codigo = newCertificacoes.codigo.toUpperCase();"></div>
                                            <div class="col-sm-3"><input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newCertificacoes.empresa" ng-change="newCertificacoes.empresa = newCertificacoes.empresa.toUpperCase();"></div>
                                            <div class="col-sm-6"><input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newCertificacoes.nome" ng-change="newCertificacoes.nome = newCertificacoes.nome.toUpperCase();"></div>
                                        </div>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                        <div class="row">
                                            <div class="col-sm-2"><input type="text" class="form-control" ng-model="newCertificacoes.codigo" ng-change="newCertificacoes.codigo = newCertificacoes.codigo.toUpperCase();"></div>
                                            <div class="col-sm-3"><input type="text" class="form-control" ng-model="newCertificacoes.empresa" ng-change="newCertificacoes.empresa = newCertificacoes.empresa.toUpperCase();"></div>
                                            <div class="col-sm-6"><input type="text" class="form-control" ng-model="newCertificacoes.nome" ng-change="newCertificacoes.nome = newCertificacoes.nome.toUpperCase();"></div>
                                        </div>
                                    </sec:authorize>
                                    <div class="row">
                                        <div class="col-sm-3"><label>Data Exame</label></div>
                                        <div class="col-sm-3"><label>Data Validade</label></div>
                                        <div class="col-sm-3"><label>Cópia</label></div>
                                    </div>
                                    <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <div class='input-group date dtpicker' data-provide="datepicker">
                                                    <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newCertificacoes.dtExame"/>
                                                    <div class="input-group-addon">
                                                        <div class="glyphicon glyphicon-calendar"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class='input-group date dtpicker' data-provide="datepicker">
                                                    <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newCertificacoes.dtValidade"/>
                                                    <div class="input-group-addon">
                                                        <div class="glyphicon glyphicon-calendar"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <select ng-disabled="userLogged !== selecionado.userandrole.userName" class="form-control" ng-model="newCertificacoes.copia">
                                                    <option>Sim</option>
                                                    <option>Não</option>
                                                </select>
                                            </div>
                                            <div class="col-sm-3">
                                                <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="addRow(newCertificacoes, 'certificacao')">Adicionar</button>
                                                <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-warning" class="btn btn-block" ng-click="cancelRow('certificacao')">Cancelar</button>    
                                            </div>
                                        </div>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <div class='input-group date dtpicker' data-provide="datepicker">
                                                    <input type="text" class="form-control" ng-model="newCertificacoes.dtExame"/>
                                                    <div class="input-group-addon">
                                                        <div class="glyphicon glyphicon-calendar"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class='input-group date dtpicker' data-provide="datepicker">
                                                    <input type="text" class="form-control" ng-model="newCertificacoes.dtValidade"/>
                                                    <div class="input-group-addon">
                                                        <div class="glyphicon glyphicon-calendar"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <select class="form-control" ng-model="newCertificacoes.copia">
                                                    <option>Sim</option>
                                                    <option>Não</option>
                                                </select>
                                            </div>
                                            <div class="col-sm-3">
                                                <button type="button"  class="btn btn-info" class="btn btn-block" ng-click="addRow(newCertificacoes, 'certificacao')">Adicionar</button>
                                                <button type="button"  class="btn btn-warning" class="btn btn-block" ng-click="cancelRow('certificacao')">Cancelar</button>    
                                            </div>
                                        </div>
                                    </sec:authorize>                                    
                                    <br>
                                </div>
                            </div>
                            <br ng-show="optionsBoolean[3]">
                            <div ng-show="optionsBoolean[4]">
                                <div class="border">
                                    <h4 ng-show="funcionario.treinamentos.length > 0">Treinamentos</h4>
                                    <div class="border" ng-repeat="treinamento in selecionado.treinamentos">
                                        <div>
                                            <h4>{{treinamento.treinamento}}</h4>
                                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                <button type="button" class="" id="removeBottom" ng-click="removeRow($index, 'treinamento')">X</button>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="" id="removeBottom" ng-click="removeRow($index, 'treinamento')">X</button>
                                            </sec:authorize>
                                        </div>


                                        <div class="border">

                                            <div class="row">
                                                <div class="col-sm-2"><label>Código</label></div>
                                                <div class="col-sm-3"><label>Certificadora</label></div>
                                                <div class="col-sm-3"><label>Treinamento</label></div>

                                            </div>
                                            <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                <div class="row">
                                                    <div class="col-sm-2"> 
                                                        <input type="text" ng-disabled="userLogged !== selecionado.userandrole.userName" class="form-control" readonly="true" ng-model="treinamento.codigo" ng-hide="selecionado.checked">
                                                        <input type="text" ng-disabled="userLogged !== selecionado.userandrole.userName" class="form-control"  ng-model="treinamento.codigo" ng-show="selecionado.checked" ng-change="treinamento.codigo = treinamento.codigo.toUpperCase();">
                                                    </div>
                                                    <div class="col-sm-3">
                                                        <input type="text" ng-disabled="userLogged !== selecionado.userandrole.userName" class="form-control" readonly="true" ng-model="treinamento.certificadora" ng-hide="selecionado.checked">
                                                        <input type="text" ng-disabled="userLogged !== selecionado.userandrole.userName" class="form-control"  ng-model="treinamento.certificadora" ng-show="selecionado.checked" ng-change="treinamento.certificadora = treinamento.certificadora.toUpperCase();">
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <input type="text" ng-disabled="userLogged !== selecionado.userandrole.userName" class="form-control" readonly="true" ng-model="treinamento.treinamento" ng-hide="selecionado.checked">
                                                        <input type="text" ng-disabled="userLogged !== selecionado.userandrole.userName" class="form-control" ng-model="treinamento.treinamento" ng-show="selecionado.checked" ng-change="treinamento.treinamento = treinamento.treinamento.toUpperCase();">
                                                    </div>

                                                </div>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                <div class="row">
                                                    <div class="col-sm-2"> 
                                                        <input type="text" class="form-control" readonly="true" ng-model="treinamento.codigo" ng-hide="selecionado.checked">
                                                        <input type="text" class="form-control"  ng-model="treinamento.codigo" ng-show="selecionado.checked" ng-change="treinamento.codigo = treinamento.codigo.toUpperCase();">
                                                    </div>
                                                    <div class="col-sm-3">
                                                        <input type="text" class="form-control" readonly="true" ng-model="treinamento.certificadora" ng-hide="selecionado.checked">
                                                        <input type="text" class="form-control"  ng-model="treinamento.certificadora" ng-show="selecionado.checked" ng-change="treinamento.certificadora = treinamento.certificadora.toUpperCase();">
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <input type="text" class="form-control" readonly="true" ng-model="treinamento.treinamento" ng-hide="selecionado.checked">
                                                        <input type="text" class="form-control" ng-model="treinamento.treinamento" ng-show="selecionado.checked" ng-change="treinamento.treinamento = treinamento.treinamento.toUpperCase();">
                                                    </div>

                                                </div>
                                            </sec:authorize>
                                            <div class="row">
                                                <div class="col-sm-3"><label>Versão</label></div>
                                                <div class="col-sm-3"><label>Data de Início</label></div>
                                                <div class="col-sm-3"><label>Data de Término</label></div>

                                            </div>
                                            <div class="row">
                                                <div class="col-sm-3">
                                                    <input type="text" class="form-control" readonly="true" ng-model="treinamento.versao" ng-hide="selecionado.checked">
                                                    <input type="text" class="form-control" ng-model="treinamento.versao" ng-show="selecionado.checked" ng-change="treinamento.versao = treinamento.versao.toUpperCase();">
                                                </div>
                                                <div class="col-sm-3" ng-init="initializeDatepicker()">
                                                    <input type="text" class="form-control" readonly="true" ng-model="treinamento.dtInicio" ng-hide="selecionado.checked">
                                                    <div class='input-group date dtpickerMonth' data-provide="datepicker" ng-show="selecionado.checked" id="thisdiv">
                                                        <input type="text" class="form-control" ng-model="treinamento.dtInicio"/>
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="col-sm-3" ng-init="initializeDatepicker()">
                                                    <input type="text" class="form-control" readonly="true" ng-model="treinamento.dtTermino" ng-hide="selecionado.checked">
                                                    <div class='input-group date dtpickerMonth' data-provide="datepicker" ng-show="selecionado.checked">
                                                        <input type="text" class="form-control" ng-model="treinamento.dtTermino"/>
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>
                                                </div>
                                                <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                                    <div class="col-sm-3">
                                                        <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName"  class="btn btn-info" class="btn btn-block" ng-click="saveRow()" ng-show="selecionado.checked">Salvar</button>
                                                        <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="editRow()" ng-hide="selecionado.checked">Editar</button>

                                                    </div>
                                                </sec:authorize>
                                                <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                                    <div class="col-sm-3">
                                                        <button type="button" class="btn btn-info" class="btn btn-block" ng-click="saveRow()" ng-show="selecionado.checked">Salvar</button>
                                                        <button type="button"  class="btn btn-info" class="btn btn-block" ng-click="editRow()" ng-hide="selecionado.checked">Editar</button>

                                                    </div>
                                                </sec:authorize>
                                                <br>

                                            </div>
                                        </div>
                                    </div>
                                    <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
                                        <div class="row">
                                            <div class="col-sm-2"><label>Código</label></div>
                                            <div class="col-sm-3"><label>Certificadora*</label></div>
                                            <div class="col-sm-3"><label>Treinamento*</label></div>

                                        </div>
                                        <div class="row">
                                            <div class="col-sm-2"><input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newTreinamentos.codigo" ng-change="newTreinamentos.codigo = newTreinamentos.codigo.toUpperCase();"></div>
                                            <div class="col-sm-3"><input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newTreinamentos.certificadora" ng-change="newTreinamentos.certificadora = newTreinamentos.certificadora.toUpperCase();"></div>
                                            <div class="col-sm-6"><input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newTreinamentos.treinamento" ng-change="newTreinamentos.treinamento = newTreinamentos.treinamento.toUpperCase();"></div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-3"><label>Versão</label></div>
                                            <div class="col-sm-3"><label>Data de Início</label></div>
                                            <div class="col-sm-3"><label>Data de Término</label></div>

                                        </div>

                                        <div class="row">
                                            <div class="col-sm-3">
                                                <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newTreinamentos.versao" ng-change="newTreinamentos.versao = newTreinamentos.versao.toUpperCase();"/>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class='input-group date dtpickerMonth' data-provide="datepicker">
                                                    <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newTreinamentos.dtInicio"/>
                                                    <div class="input-group-addon">
                                                        <div class="glyphicon glyphicon-calendar"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class='input-group date dtpickerMonth' data-provide="datepicker">
                                                    <input ng-disabled="userLogged !== selecionado.userandrole.userName" type="text" class="form-control" ng-model="newTreinamentos.dtTermino"/>
                                                    <div class="input-group-addon">
                                                        <div class="glyphicon glyphicon-calendar"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <button type="button" ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-info" class="btn btn-block" ng-click="addRow(newTreinamentos, 'treinamento')">Adicionar</button>
                                                <button type="button"  ng-disabled="userLogged !== selecionado.userandrole.userName" class="btn btn-warning" class="btn btn-block" ng-click="cancelRow('treinamento')">Cancelar</button>    
                                            </div>
                                        </div>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                        <div class="row">
                                            <div class="col-sm-2"><label>Código</label></div>
                                            <div class="col-sm-3"><label>Certificadora</label></div>
                                            <div class="col-sm-3"><label>Treinamento</label></div>

                                        </div>
                                        <div class="row">
                                            <div class="col-sm-2"><input type="text" class="form-control" ng-model="newTreinamentos.codigo" ng-change="newTreinamentos.codigo = newTreinamentos.codigo.toUpperCase();"></div>
                                            <div class="col-sm-3"><input type="text" class="form-control" ng-model="newTreinamentos.certificadora" ng-change="newTreinamentos.certificadora = newTreinamentos.certificadora.toUpperCase();"></div>
                                            <div class="col-sm-6"><input  type="text" class="form-control" ng-model="newTreinamentos.treinamento" ng-change="newTreinamentos.treinamento = newTreinamentos.treinamento.toUpperCase();"></div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-3"><label>Versão</label></div>
                                            <div class="col-sm-3"><label>Data de Início</label></div>
                                            <div class="col-sm-3"><label>Data de Término</label></div>

                                        </div>

                                        <div class="row">
                                            <div class="col-sm-3">
                                                <input type="text" class="form-control" ng-model="newTreinamentos.versao" ng-change="newTreinamentos.versao = newTreinamentos.versao.toUpperCase();"/>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class='input-group date dtpickerMonth' data-provide="datepicker">
                                                    <input type="text" class="form-control" ng-model="newTreinamentos.dtInicio"/>
                                                    <div class="input-group-addon">
                                                        <div class="glyphicon glyphicon-calendar"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class='input-group date dtpickerMonth' data-provide="datepicker">
                                                    <input type="text" class="form-control" ng-model="newTreinamentos.dtTermino"/>
                                                    <div class="input-group-addon">
                                                        <div class="glyphicon glyphicon-calendar"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <button type="button" class="btn btn-info" class="btn btn-block" ng-click="addRow(newTreinamentos, 'treinamento')">Adicionar</button>
                                                <button type="button" class="btn btn-warning" class="btn btn-block" ng-click="cancelRow('treinamento')">Cancelar</button>    
                                            </div>
                                        </div>
                                    </sec:authorize>

                                </div>
                            </div>
                            <br ng-show="optionsBoolean[4]">
                            <div ng-show="optionsBoolean[5]">
                                <div class="border">
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <label>Usuário</label>
                                            <input type="text" disabled="true" class="form-control" ng-model="selecionado.userandrole.userName">
                                        </div>
                                        <div class="col-sm-4">
                                            <label>Senha</label>
                                            <input type="password" class="form-control" ng-model="selecionado.userandrole.password"> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br ng-show="optionsBoolean[5]">

                            <div ng-show="optionsBoolean[0] || optionsBoolean[1] || optionsBoolean[2] || optionsBoolean[3] || optionsBoolean[4] || optionsBoolean[5]">
                                <button class="btn btn-primary" ng-click="save(selecionado)">Salvar</button>
                                <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                    <button class="btn btn-danger" ng-click="remove(selecionado)">Deletar</button>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">
                                    <button class="btn btn-info" ng-click="exportType('xls')" type="button">Gerar Relatório</button>
                                </sec:authorize>
                            </div><br><br><br>
                        </div>
                        <div class="dropdown" ng-hide="optionsBoolean[0] || optionsBoolean[1] || optionsBoolean[2] || optionsBoolean[3] || optionsBoolean[4] || optionsBoolean[5]">
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                <button class="btn btn-danger" ng-click="remove(selecionado)" ng-show="selecionado">Deletar</button>
                            </sec:authorize>
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">
                                <button class="btn btn-info" ng-click="exportType('xls')" type="button">Gerar Relatório</button>
                            </sec:authorize>
                        </div>
                        <div ng-show="tamanhoPagina > 4 && optionsBoolean[0] || optionsBoolean[1] || optionsBoolean[2] || optionsBoolean[3] || optionsBoolean[4] || optionsBoolean[5]">
                            <button class="btn btn-default" id="backTop" ng-click="backToTop('top')">Voltar para o Topo</button>
                        </div>
                    </form>
                </div>
            </div>
        </section>
        <div id="loader">
            <img class="gifLoader" src="resources/imgs/loader4.gif" class="ajax-loader"/>
        </div>
    </body>
</html>
