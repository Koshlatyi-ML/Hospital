<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%@include file="/jsp/admin/main.jsp" %>
<fmt:setBundle basename="i18n/admin/stuff" var="stuffBundle"/>
<c:set var="pageSize" value="5"/>
<c:if test="${empty param.departmentId}">
    <div class="list-group">
        <c:forEach items="${sessionScope.departments}" var="department">
            <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 justify-content-between">
                    <h2 class="mb-1">${department.name}</h2>
                </div>
                <div id="changeDepartmentsWrapper">
                    <form class="changeDepartmentForm" method="get"
                          action="${pageContext.request.contextPath}/admin/change-doctor">
                        <input name="departmentId" value="${department.id}" type="hidden">
                        <button class="mb-1"><fmt:message key="selectDepartment" bundle="${stuffBundle}"/></button>
                    </form>
                </div>
            </a>
        </c:forEach>
        <mytag:paginator totalSize="${sessionScope.totalDepartmentSize}" pageSize="${pageSize}"
                         currentPage="${sessionScope.page}" pageAttribute="page"
                         url="${pageContext.request.contextPath}/admin/change-doctor"/>
    </div>
</c:if>
<c:if test="${not empty param.departmentId}">
    <div class="list-group">
        <c:forEach items="${sessionScope.doctors}" var="doctor">
            <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 justify-content-between">
                    <h2 class="mb-1">${doctor.name} ${doctor.surname}</h2>
                </div>
                <div id="changeDoctorWrapper">
                    <form class="changeDepartmentForm" method="post"
                          action="${pageContext.request.contextPath}/admin/change-doctor/move?doctorId=${doctor.id}">
                        <input name="doctorId" value="${doctor.id}" type="hidden"/>
                        <select class="selectpicker" name="departmentId">
                            <c:forEach items="${sessionScope.departments}" var="department">
                                <option value="${department.id}">${department.name}</option>
                            </c:forEach>
                        </select>
                        <button class="mb-1 moveBtn"><fmt:message key="move" bundle="${stuffBundle}"/></button>
                    </form>
                    <div class="doctorDivider"></div>
                    <form class="changeDepartmentForm" method="post"
                          action="${pageContext.request.contextPath}/admin/change-doctor/retire">
                        <input name="doctorId" value="${doctor.id}" type="hidden"/>
                        <button class="mb-1 retireBtn"><fmt:message key="retire" bundle="${stuffBundle}"/></button>
                    </form>
                </div>
            </a>
        </c:forEach>
        <mytag:paginator totalSize="${sessionScope.totalDoctorSize}" pageSize="${pageSize}"
                         currentPage="${sessionScope.page}" pageAttribute="page"
                         url="${pageContext.request.contextPath}/admin/change-doctor"/>
    </div>
</c:if>
</body>
</html>
