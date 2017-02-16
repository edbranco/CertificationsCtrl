<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>                        
            </button>
            <a class="navbar-brand" href="index"><img id="banner" src="resources/imgs/Telesul.png"></a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav" id="navMenu">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Funcionários <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                            <li><a class="op" href="cadastrarFuncionario">Cadastrar</a></li>
                            </sec:authorize>
                        <li><a class="op" href="atualizarFuncionario">Atualizar/Consultar</a></li>
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                            <li><a class="op" href="gerenciarUsuarios">Usuários</a></li>
                            </sec:authorize>
                    </ul>
                </li>
            </ul>
            <c:url value='/j_spring_security_logout' var="logoutUrl"/>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form id="logout" action="${logoutUrl}" method="post" >
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <a href="javascript:document.getElementById('logout').submit()"><span class="glyphicon glyphicon-user"></span>${pageContext.request.userPrincipal.name}(Sair)</a>
                    </c:if>
                </li>
            </ul>
        </div>
    </div>
</nav>