<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%@include file="../main.jsp" %>
<fmt:setBundle basename="i18n/admin/department" var="departmentBundle"/>
<c:set var="pageSize" value="5"/>
<c:if test="${not empty param.departmentId}">
    <h3 id="rename-dep-hdr">
        <fmt:message key="rename" bundle="${departmentBundle}"/> "<c:out value="${sessionScope.departmentName}"/>"
    </h3>
    <form id="deparment-rename" class="form-horizontal"
          action="${pageContext.request.contextPath}/admin/change-department/rename" method="post">
        <div id="fail-Msg">
            <c:out value="${sessionScope.notEmptyDepartmentMsg}"/>
        </div>
        <input name="departmentId" value="${param.departmentId}" type="hidden">
        <div class="form-group">
            <label class="control-label col-sm-2" for="email">
                <fmt:message key="name" bundle="${departmentBundle}"/>
            </label>
            <div class="col-sm-8">
                <input name="department-name" type="text" class="form-control" id="email"
                       pattern="^[a-zA-Zа-яА-ЯёЁ0-9 №-]{1,64}$">
            </div>
        </div>
        <div class="form-group" style="margin-bottom: 25px">
            <div class="col-sm-offset-2 col-sm-8">
                <button type="submit" class="btn btn-default">
                    <fmt:message key="rename" bundle="${departmentBundle}"/>
                </button>
            </div>
        </div>
    </form>
</c:if>
<div class="list-group">
    <div class="fail">
        <c:out value="${sessionScope.notEmptyDepartmentMsg}"/>
    </div>
    <c:forEach items="${sessionScope.departments}" var="department">
        <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
                <h2 class="mb-1">${department.name}</h2>
            </div>
            <div id="changeDepartmentsWrapper">
                <form class="changeDepartmentForm" method="get"
                      action="${pageContext.request.contextPath}/admin/change-department">
                    <input name="departmentId" value="${department.id}" type="hidden">
                    <button class="mb-1"><fmt:message key="rename" bundle="${departmentBundle}"/></button>
                </form>

                <form class="changeDepartmentForm" method="post"
                      action="${pageContext.request.contextPath}/admin/change-department/delete">
                    <input name="departmentId" value="${department.id}" type="hidden">
                    <input name="departmentName" value="${department.name}" type="hidden"/>
                    <button class="mb-1"><fmt:message key="remove" bundle="${departmentBundle}"/></button>
                </form>
            </div>
        </a>
    </c:forEach>
    <mytag:paginator totalSize="${sessionScope.totalSize}" pageSize="${pageSize}"
                     currentPage="${sessionScope.page}" pageAttribute="page"
                     url="${pageContext.request.contextPath}/admin/change-department"/>
</div>
</body>
</html>
