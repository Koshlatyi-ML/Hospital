<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%@include file="/jsp/header-logout.jsp" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="i18n/admin/header" var="adminHeaderBundle"/>
<ul class="nav nav-tabs">
    <li>
        <a data-toggle="tab" href="#departmentMenu">
            <fmt:message key="departments" bundle="${adminHeaderBundle}"/>
        </a>
    </li>
    <li>
        <a data-toggle="tab" href="#menu1">
            <fmt:message key="doctors" bundle="${adminHeaderBundle}"/>
        </a>
    </li>
    <li>
        <a data-toggle="tab" href="#menu2">
            <fmt:message key="medics" bundle="${adminHeaderBundle}"/>
        </a>
    </li>
</ul>
<div class="tab-content">
    <div id="departmentMenu" class="tab-pane fade">
        <ul class="nav nav-pills nav-stacked">
            <li class="active">
                <a href="${pageContext.request.contextPath}/admin/add-department">
                    <fmt:message key="add" bundle="${adminHeaderBundle}"/>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/change-department">
                    <fmt:message key="change" bundle="${adminHeaderBundle}"/>
                </a>
            </li>
        </ul>
    </div>
    <div id="menu1" class="tab-pane fade">
        <ul class="nav nav-pills nav-stacked">
            <li class="active">
                <a href="#">
                    <fmt:message key="add" bundle="${adminHeaderBundle}"/>
                </a>
            </li>
            <li>
                <a href="#">
                    <fmt:message key="change" bundle="${adminHeaderBundle}"/>
                </a>
            </li>
        </ul>
    </div>
    <div id="menu2" class="tab-pane fade">
        <ul class="nav nav-pills nav-stacked">
            <li class="active">
                <a href="#">
                    <fmt:message key="add" bundle="${adminHeaderBundle}"/>
                </a>
            </li>
            <li>
                <a href="#">
                    <fmt:message key="change" bundle="${adminHeaderBundle}"/>
                </a>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
