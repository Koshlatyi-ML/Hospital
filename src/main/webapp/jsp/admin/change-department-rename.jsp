<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%@ include file="main.jsp" %>
<fmt:setBundle basename="i18n/admin/department" var="departmentBundle"/>
<form id="deparment-rename" class="form-horizontal"
      action="${pageContext.request.contextPath}/admin/change-department/rename" method="post">
    <div id="fail-Msg">
        <c:out value="${sessionScope.usedDepartmentNameMsg}"/>
    </div>
    <input name="departmentId" value="${param.departmentId}" type="hidden">
    <div class="form-group">
        <label class="control-label col-sm-2" for="email">
            <fmt:message key="name" bundle="${departmentBundle}"/>
        </label>
        <div class="col-sm-8">
            <input name="department-name" type="text" class="form-control" id="email"
                   placeholder="${param.departmentName}" pattern="^[a-zA-Zа-яА-ЯёЁ0-9 №-]{1,64}$">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-8">
            <button type="submit" class="btn btn-default">
                <fmt:message key="rename" bundle="${departmentBundle}"/>
            </button>
        </div>
    </div>
</form>
</body>
</html>
