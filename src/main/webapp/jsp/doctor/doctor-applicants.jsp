<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%@include file="main.jsp" %>
<fmt:setBundle basename="/i18n/doctor" var="docBundle"/>
<c:set var="patientId" value="${not empty param.patientId ? param.patientId : patientId}" scope="page"/>
<c:set var="pageSize" value="5"/>
<c:if test="${empty param.patientId and empty sessionScope.invalidInputMsg}">
    <div class="list-group">
        <c:forEach items="${sessionScope.patients}" var="patient">
            <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 justify-content-between">
                    <h2 class="mb-1">${patient.name} ${patient.surname}</h2>
                </div>
                <div id="choosePatientWrapper">
                    <textarea class="mb-1" rows="5" disabled>${patient.complaints}</textarea>
                    <form class="changeDepartmentForm" method="get"
                          action="${pageContext.request.contextPath}/doctor/applicants">
                        <input name="patientId" value="${patient.id}" type="hidden">
                        <button type="submit" id="prescribe" class="btn btn-primary mb-1">
                            <fmt:message key="prescribe" bundle="${docBundle}"/>
                        </button>
                    </form>
                </div>
            </a>
        </c:forEach>
        <mytag:paginator totalSize="${sessionScope.totalSize}" pageSize="${pageSize}"
                         currentPage="${sessionScope.page}" pageAttribute="page"
                         url="${pageContext.request.contextPath}/doctor/applicants"/>
    </div>
</c:if>
<c:if test="${not empty param.patientId or not empty sessionScope.invalidInputMsg}">
    <fmt:setBundle basename="/i18n/therapy" var="therapyBundle"/>
    <div id="description-msg"></div>
    <div id="fail-Msg">
        <c:out value="${sessionScope.invalidInputMsg}"/>
    </div>
    <div id="therapy-prescription-id">
        <form class="form-horizontal" method="post"
              action="${pageContext.request.contextPath}/doctor/applicants?patientId=${patientId}">
            <input type="hidden" name="patientId" value="${patientId}">
            <div>
                <label for="title">
                    <fmt:message key="title" bundle="${therapyBundle}"/>
                </label>
            </div>
            <div class="therapy-prescription">
                <input name="therapyTitle" class="input-prescription" type="text"
                       pattern="^[a-zA-Zа-яА-ЯёЁ -]{1,128}$" id="title">
            </div>
            <div>
                <label>
                    <fmt:message key="type" bundle="${therapyBundle}"/>
                </label>
            </div>
            <div class="therapy-prescription">
                <select class="input-prescription" name="therapyType">
                    <c:forEach items="${sessionScope.therapyTypes}" var="type">
                        <option value="${type}">
                            <fmt:message key="${type}" bundle="${therapyBundle}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label>
                    <fmt:message key="description" bundle="${therapyBundle}"/>
                </label>
            </div>
            <div class="therapy-prescription">
                <textarea name="therapyDescription" id="therapy-description" class="input-prescription" rows="10"></textarea>
            </div>
            <div>
                <label>
                    <fmt:message key="time.appointment" bundle="${therapyBundle}"/>
                </label>
            </div>
            <div class="therapy-prescription">
                <input name="dateTime" class="input-prescription" type="datetime-local">
            </div>
            <div>
                <button id="submit-prescription-btn" class="btn bth-primary therapy-prescription" type="submit">
                    <fmt:message key="submit" bundle="${therapyBundle}"/>
                </button>
            </div>
        </form>
    </div>
</c:if>
</body>
</html>
