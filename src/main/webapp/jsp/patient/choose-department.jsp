<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%@include file="/jsp/header-logout.jsp"%>
<c:set var="pageSize" value="5"/>
<div class="list-group">
    <c:forEach items="${sessionScope.departments}" var="department">
    <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
        <div class="d-flex w-100 justify-content-between">
            <h2 class="mb-1">${department.name}</h2>
        </div>
        <div id="changeDepartmentsWrapper">
            <form class="changeDepartmentForm" method="get" action="${pageContext.request.contextPath}/admin/change-department/rename">
                <input name="departmentId" value="${department.id}" type="hidden">
                <button class="mb-1">Choose department:</button>
            </form>
        </div>
    </a>
    </c:forEach>
    <mytag:paginator totalSize="${sessionScope.totalSize}" pageSize="${pageSize}"
                     currentPage="${sessionScope.page}" pageAttribute="page"
                     url="${pageContext.request.contextPath}/patient"/>
</body>
</html>
