<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/tld/taglib.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${sessionScope.role == 'Admin'}">
        <c:set var="home" value="${pageContext.request.contextPath}/admin" scope="page"/>
    </c:when>
    <c:when test="${sessionScope.role == 'Doctor'}">
        <c:set var="home" value="${pageContext.request.contextPath}/doctor" scope="page"/>
    </c:when>
    <c:when test="${sessionScope.role == 'Medic'}">
        <c:set var="home" value="${pageContext.request.contextPath}/medic" scope="page"/>
    </c:when>
    <c:when test="${sessionScope.role == 'Patient'}">
        <c:set var="home" value="${pageContext.request.contextPath}/patient" scope="page"/>
    </c:when>
</c:choose>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="i18n/header" var="headerBundle"/>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/bootstrap-select.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/js/jQuery.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-select.js"></script>

</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${home}"><fmt:message key="home" bundle="${headerBundle}"/></a>
        </div>
        <ul id="navbar" class="nav navbar-nav navbar-right">
            <li>
                <form action="${requestScope['javax.servlet.forward.request_uri']}"
                      method="get" id="lang-picker-form" class="navbar-form" role="search">
                    <c:if test="${not empty param}">
                        <c:forEach var="p" items="${paramValues}">
                            <c:if test="${p.key ne 'language'}">
                                <input type="hidden" name="${p.key}" value="${p.value[0]}">
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <select id="lang-picker-select" name="language" class="selectpicker" data-size="5">
                        <option value="en_US" data-content='<img src="/img/usa.png" class="img-thumbnail icon-small">
                                              <span>    English</span>' ${sessionScope.language == 'en_US' ? 'selected' : ''}></option>
                        <option value="ru_RU" data-content='<img src="/img/ru.png" class="img-thumbnail icon-small">
                                              <span>    Русский</span>' ${sessionScope.language == 'ru_RU' ? 'selected' : ''}></option>
                    </select>
                </form>
            </li>
            <li>
                <form class="navbar-form" action="${pageContext.request.contextPath}/logout" method="post">
                    <button id="logout" class="btn btn-default" type="submit">
                        <span class="glyphicon glyphicon-log-out"></span>
                        <fmt:message key="logout" bundle="${headerBundle}"/>
                    </button>
                </form>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>
