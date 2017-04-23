<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Doctor main</title>
</head>
<body>
    <%@include file="/jsp/header-logout.jsp"%>
    <fmt:setBundle basename="/i18n/doctor" var="docHeaderBundle"/>
    <ul class="nav nav-tabs">
        <li>
            <a href="${pageContext.request.contextPath}/doctor/applicants">
                <fmt:message key="applicants" bundle="${docHeaderBundle}"/>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/doctor/therapies">
                <fmt:message key="therapies" bundle="${docHeaderBundle}"/>
            </a>
        </li>
    </ul>
</body>
</html>
