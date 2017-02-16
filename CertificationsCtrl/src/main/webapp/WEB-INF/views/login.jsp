<%-- 
    Document   : login
    Created on : 22/08/2016, 14:48:07
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
        <link rel="shortcut icon" href="resources/imgs/Telesul.png" type="image/x-icon"/>

        <script src="resources/jquery/jquery-2.1.4.js" type="text/javascript"></script>

        <script src="resources/js/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
        <script src="<c:url value="resources/angularjs/external/angular.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="resources/angularjs/external/angular-animate.js"/>" type="text/javascript"></script>
        <script src="resources/angularjs/module/module.js" type="text/javascript"></script>
        <script src="resources/angularjs/controller/loginController.js" type="text/javascript"></script>
        <script src="resources/angularjs/controller/alertController.js" type="text/javascript"></script>
        <script src="resources/bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <script src="resources/angularjs/external/ui-bootstrap-tpls-1.3.3.js" type="text/javascript"></script>

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
        <link href="resources/css/background-img.css" rel="stylesheet" type="text/css" media='screen and (max-width: 1400px)'/>
        <link href="resources/css/background-img-desktop.css" rel="stylesheet" type="text/css" media='screen and (min-width: 1401px)' media='screen and (min-width: 1401px)'/>
        <link href="resources/css/angular.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div ng-controller="alertController" id="allalerts">
            <uib-alert class="alert-success" ng-show="alert.type === 'success'"  ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</uib-alert>
            <uib-alert class="alert-danger" ng-show="alert.type === 'danger'" ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</uib-alert>
        </div>
        <section class="col-sm-6" ng-controller="loginController">
            <c:url value="/j_spring_security_check" var="loginUrl"></c:url>
            <form name="loginForm" class="col-sm-12 col-sm-push-6" action="${loginUrl}?${_csrf.parameterName}=${_csrf.token}" method="POST">
                <div class="panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-sm-4">
                                <img id="logon" src="resources/imgs/LogoTelesulwithBackGround.JPG"/>
                            </div>
                            <div class="col-sm-6">
                                <label>Usuário</label>
                                <input type="text" name="j_username" class="form-control"/>
                                <br>
                                <label>Senha</label>
                                <input type="password" name="j_password" class="form-control"/>

                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-4">

                            </div>
                            <div class="col-sm-6">
                                <button type="submit" class="btn btn-primary btn-sm">Logar</button>
                                <button class="btn btn-danger btn-sm">Cancelar</button>
                            </div>
                        </div>
                    </div>                    
                </div>                    
            </form>
        </section>
    </body>
</html>
