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
                          action="${pageContext.request.contextPath}/admin/change-medic">
                        <input name="departmentId" value="${department.id}" type="hidden">
                        <button class="mb-1"><fmt:message key="select" bundle="${stuffBundle}"/></button>
                    </form>
                </div>
            </a>
        </c:forEach>
        <mytag:paginator totalSize="${sessionScope.totalDepartmentSize}" pageSize="${pageSize}"
                         currentPage="${sessionScope.page}" pageAttribute="page"
                         url="${pageContext.request.contextPath}/admin/change-medic"/>
    </div>
</c:if>
<c:if test="${not empty param.departmentId}">
    <c:if test="${sessionScope.totalMedicSize == 0}">
        <div class="empty-department">
            <fmt:message key="noMedics" bundle="${stuffBundle}"/>
        </div>
    </c:if>
    <div class="list-group">
        <c:forEach items="${sessionScope.medics}" var="medic">
            <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 justify-content-between">
                    <h2 class="mb-1">${medic.name} ${medic.surname}</h2>
                </div>
                <div id="changeDoctorWrapper">
                    <form class="changeDepartmentForm" method="post"
                          action="${pageContext.request.contextPath}/admin/change-medic/move?medicId=${medic.id}">
                        <input name="doctorId" value="${medic.id}" type="hidden"/>
                        <select class="selectpicker" name="departmentId">
                            <c:forEach items="${sessionScope.departments}" var="department">
                                <option value="${department.id}">${department.name}</option>
                            </c:forEach>
                        </select>
                        <button class="mb-1 moveBtn"><fmt:message key="move" bundle="${stuffBundle}"/></button>
                    </form>
                    <c:if test="${param.departmentId != -1}">
                        <div class="doctorDivider"></div>
                        <form class="changeDepartmentForm" method="post"
                              action="${pageContext.request.contextPath}/admin/change-medic/retire">
                            <input name="medicId" value="${medic.id}" type="hidden"/>
                            <button class="mb-1 retireBtn"><fmt:message key="retire" bundle="${stuffBundle}"/></button>
                        </form>
                    </c:if>
                </div>
            </a>
        </c:forEach>
        <mytag:paginator totalSize="${sessionScope.totalMedicSize}" pageSize="${pageSize}"
                         currentPage="${sessionScope.page}" pageAttribute="page"
                         url="${pageContext.request.contextPath}/admin/change-medic"/>
    </div>
</c:if>
</body>
</html>
