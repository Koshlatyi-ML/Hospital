<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
</head>
<body>
    <%@include file="header-login.jsp"%>
    <%@include file="/jsp/registrations/error-messages.jsp"%>
    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="i18n/login" var="loginBundle"/>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <fmt:message key="loginAs" bundle="${loginBundle}"/>
        <select size="1" name="role">
                    <option value="Patient"><fmt:message key="role.patient" bundle="${loginBundle}"/></option>
                    <option value="Doctor"><fmt:message key="role.doctor" bundle="${loginBundle}"/></option>
                    <option value="Medic"><fmt:message key="role.medic" bundle="${loginBundle}"/></option>
        </select><br>
        <input name="login" type="text" pattern="^((?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.]){8,20}$"/><br>
        <input name="password" type="password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,20}$"/><br>
        <input type="submit" value="<fmt:message key="login" bundle="${loginBundle}"/>">
    </form>
</body>
</html>
