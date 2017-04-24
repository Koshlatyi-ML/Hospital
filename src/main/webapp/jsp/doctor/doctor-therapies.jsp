<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%@include file="main.jsp" %>
<fmt:setBundle basename="/i18n/doctor" var="docBundle"/>
<fmt:setBundle basename="/i18n/therapy" var="therapyBundle"/>
<c:set var="pageSize" value="5"/>
<c:out value="${sessionScope.invalidInputMsg}"/>
<div class="list-group">
    <c:forEach items="${sessionScope.therapies}" var="therapy" varStatus="status">
        <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
                <h2 class="mb-1">
                        ${sessionScope.patients[status.index].name} ${sessionScope.patients[status.index].surname}
                    - ${therapy.title} (<fmt:message key="${therapy.type}" bundle="${therapyBundle}"/>)
                </h2>
            </div>
            <div id="choosePatientWrapper">
                <div id="doctorActionsDiv" class="col-sm-8">
                    <div>
                        <textarea class="mb-1" rows="5" disabled>${therapy.description}</textarea>
                        <form class="changeDepartmentForm" method="post"
                              action="${pageContext.request.contextPath}/doctor/therapies">
                            <input name="therapyId" value="${therapy.id}" type="hidden">
                            <button type="submit" id="perform" class="btn btn-primary mb-1">
                                <fmt:message key="perform" bundle="${docBundle}"/>
                            </button>
                        </form>
                    </div>
                    <div class="col-sm-4">
                        <form class="changeDepartmentForm form-horizontal" method="post"
                              action="${pageContext.request.contextPath}/doctor/therapies">
                            <input name="therapyId" value="${therapy.id}" type="hidden">
                            <label class="diagnosis-label"><fmt:message key="diagnosis" bundle="${docBundle}"/></label>
                            <input class="diagnosis-input" name="diagnosis" pattern="^[a-zA-Zа-яА-ЯёЁ -]{1,128}$" type="text">
                            <button type="submit" class="btn btn-primary mb-1 discharge">
                                <fmt:message key="discharge" bundle="${docBundle}"/>
                            </button>
                        </form>
                    </div>
                </div>
                <c:if test="${therapy.type ne 'SURGERY_OPERATION'}">
                    <div id="divider"></div>
                    <div class="col-sm-4" id="redirect-div">
                        <form class="changeDepartmentForm" method="post"
                              action="${pageContext.request.contextPath}/doctor/therapies/redirect">
                            <input name="therapyId" value="${therapy.id}" type="hidden">
                            <select id="medics" name="medic">
                                <c:forEach items="${sessionScope.medics}" var="medic">
                                    <option value="${medic.id}">${medic.name} ${medic.surname}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" id="redirect" class="btn btn-primary mb-1">
                                <fmt:message key="redirect" bundle="${docBundle}"/>
                            </button>
                        </form>
                    </div>
                </c:if>
            </div>
        </a>
    </c:forEach>
    <mytag:paginator totalSize="${sessionScope.totalSize}" pageSize="${pageSize}"
                     currentPage="${sessionScope.page}" pageAttribute="page"
                     url="${pageContext.request.contextPath}/doctor/therapies"/>
</div>
</body>
</html>
