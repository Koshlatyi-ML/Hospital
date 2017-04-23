<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        #patient-msg {
            margin-top: 10px;
            margin-bottom: 10px;
            text-align: center;
            font-size: 25px;
        }
    </style>
</head>
<body>
<%@include file="/jsp/header-logout.jsp" %>
<fmt:setBundle basename="i18n/patient" var="patientBundle"/>
<c:set var="pageSize" value="5"/>
<div id="patient-msg"><c:out value="${sessionScope.stateMsg}"/></div>
<c:if test="${sessionScope.user.state eq ('REGISTERED' or 'DISCHARGED')}">
    <div class="list-group">
        <c:forEach items="${sessionScope.departments}" var="department">
            <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 justify-content-between">
                    <h2 class="mb-1">${department.name}</h2>
                </div>
                <div id="changeDepartmentsWrapper">
                    <form class="changeDepartmentForm" method="get"
                          action="${pageContext.request.contextPath}/admin/change-department/rename">
                        <input name="departmentId" value="${department.id}" type="hidden">
                        <button class="mb-1">
                            <fmt:message key="choose.department" bundle="${patientBundle}"/>
                        </button>
                    </form>
                </div>
            </a>
        </c:forEach>
        <mytag:paginator totalSize="${sessionScope.totalSize}" pageSize="${pageSize}"
                         currentPage="${sessionScope.page}" pageAttribute="page"
                         url="${pageContext.request.contextPath}/patient"/>
    </div>
</c:if>
</body>
</html>
