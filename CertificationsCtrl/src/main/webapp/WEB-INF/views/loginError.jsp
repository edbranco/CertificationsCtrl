<%-- 
    Document   : loginError
    Created on : 23/09/2016, 10:43:18
    Author     : ebranco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestão de RH</title>
        <link rel="shortcut icon" href="resources/imgs/Telesul.png" type="image/x-icon"/>
        <script src="resources/jquery/jquery-2.1.4.js" type="text/javascript"></script>
        <script src="resources/js/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>        <script src="<c:url value="resources/angularjs/external/angular.js"/>" type="text/javascript"></script>
        <script src="resources/angularjs/module/module.js" type="text/javascript"></script>
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
        <link href="resources/css/background-img.css" rel="stylesheet" type="text/css" media='screen and (max-width: 1400px)'/>
        <link href="resources/css/background-img-desktop.css" rel="stylesheet" type="text/css" media='screen and (min-width: 1401px)' media='screen and (min-width: 1401px)'/>
    </head>
    <body>
        <a href="login"><img id='arrow-back' src="resources/imgs/arrow-back.png"/></a><br>
        <img id='imgDenied'src="resources/imgs/access-denied.png"/>
        <h4>ACESSO NÃO PERMITIDO</h4>
    </body>
</html>
