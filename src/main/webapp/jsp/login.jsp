<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
</head>
<body>
<c:choose>
    <c:when test="${not empty sessionScope.logined}">
        <%@include file="header-logout.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="header-login.jsp" %>
    </c:otherwise>
</c:choose>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="i18n/login" var="loginBundle"/>
<form id="loginForm" class="form-horizontal" action="${pageContext.request.contextPath}/login" method="post">
    <div id="fail-Msg">
        <%@include file="/jsp/util/error-messages.jsp" %>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-4" for="div1">
            <fmt:message key="loginAs" bundle="${loginBundle}"/>
        </label>
        <div class="col-sm-8">
            <select id="div1" size="1" name="role">
                <option value="Patient"><fmt:message key="role.patient" bundle="${loginBundle}"/></option>
                <option value="Doctor"><fmt:message key="role.doctor" bundle="${loginBundle}"/></option>
                <option value="Medic"><fmt:message key="role.medic" bundle="${loginBundle}"/></option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-4" for="div2">
            <fmt:message key="login" bundle="${loginBundle}"/>
        </label>
        <div class="col-sm-8">
            <%--(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]{8,20}(?<![_.])--%>
            <input id="div2" name="login" type="text" pattern="^([^_.])(?!.*[_.]{2})[a-zA-Z0-9._]{8,20}(?<![_.])$"/><br>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-4" for="div3">
            <fmt:message key="password" bundle="${loginBundle}"/>
        </label>
        <div class="col-sm-8">
            <input id="div3" name="password" type="password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,20}$"/><br>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-4 col-sm-2">
            <button id="login-btn" type="submit" class="btn btn-primary">
                <fmt:message key="doLogin" bundle="${loginBundle}"/>
            </button>
        </div>
    </div>
</form>
</body>
</html>
