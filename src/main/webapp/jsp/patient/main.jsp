<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%@include file="/jsp/header-logout.jsp" %>
<fmt:setBundle basename="i18n/patient" var="patientBundle"/>
<c:set var="pageSize" value="5"/>
<div id="patient-msg"><c:out value="${sessionScope.stateMsg}"/></div>
<c:if test="${sessionScope.user.state eq 'REGISTERED' or sessionScope.user.state eq 'DISCHARGED'}">
    <c:if test="${empty param.departmentId}">
        <div class="list-group">
            <c:forEach items="${sessionScope.departments}" var="doctor">
                <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                    <div class="d-flex w-100 justify-content-between">
                        <h2 class="mb-1">${doctor.name}</h2>
                    </div>
                    <div id="changeDepartmentsWrapper">
                        <form id="prescribe-form" method="get"
                              action="${pageContext.request.contextPath}/patient">
                            <input name="departmentId" value="${doctor.id}" type="hidden">
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
    <c:if test="${not empty param.departmentId}">
        <c:if test="${sessionScope.totalSize == 0}">
            <h2 id="empty-department">
                <fmt:message key="empty" bundle="${patientBundle}"/>
            </h2>
        </c:if>
        <c:if test="${sessionScope.totalSize != 0}">
            <form class="changeDepartmentForm" id="selectDoctor" method="post"
                  action="${pageContext.request.contextPath}/patient/choose-doctor">
                <div id="complaints-msg"></div>
                <div class="form-group">
                    <label id="complaints-label" for="complaints"><fmt:message key="complaints"
                                                                               bundle="${patientBundle}"/></label>
                    <textarea class="form-control" rows="10" id="complaints" form="selectDoctor"
                              name="complaints"></textarea>
                </div>
                <c:set var="pageSize" value="5"/>
                <div class="list-group">
                    <c:forEach items="${sessionScope.doctors}" var="doctor">
                    <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                        <div class="d-flex w-100 justify-content-between">
                            <h2 class="mb-1">${doctor.name} ${doctor.surname}</h2>
                        </div>
                        <div id="chooseDoctorWrapper">
                            <button type="submit" name="doctorId" value="${doctor.id}" class="mb-1 apply-to-doctor" disabled>
                                <fmt:message key="choose.doctor" bundle="${patientBundle}"/>
                            </button>
                        </div>
                    </a>
                    </c:forEach>
            </form>
            <mytag:paginator totalSize="${sessionScope.totalSize}" pageSize="${pageSize}"
                             currentPage="${sessionScope.page}" pageAttribute="page"
                             url="${pageContext.request.contextPath}/patient?departmentId=${param.departmentId}"/>
            </div>
        </c:if>
    </c:if>
</c:if>
</body>
</html>
