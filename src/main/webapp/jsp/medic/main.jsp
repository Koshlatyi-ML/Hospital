<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%@include file="/jsp/header-logout.jsp" %>
<fmt:setBundle basename="i18n/medic" var="medicBundle"/>
<fmt:setBundle basename="/i18n/therapy" var="therapyBundle"/>
<c:set var="pageSize" value="5"/>
<div class="list-group">
    <c:forEach items="${sessionScope.therapies}" var="therapy" varStatus="status">
        <a href="#" class="medic-therapy list-group-item list-group-item-action flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
                <h2 class="mb-1">
                        ${sessionScope.patients[status.index].name} ${sessionScope.patients[status.index].surname}
                    - ${therapy.title} (<fmt:message key="${therapy.type}" bundle="${therapyBundle}"/>)
                </h2>
            </div>
            <div>
                <textarea class="mb-1" rows="5" disabled>${therapy.description}</textarea>
            </div>
            <form class="medic-form changeDepartmentForm" method="post"
                  action="${pageContext.request.contextPath}/medic">
                <input name="therapyId" value="${therapy.id}" type="hidden">
                <button type="submit" id="perform" class="medic-submit btn btn-primary mb-1">
                    <fmt:message key="perform" bundle="${medicBundle}"/>
                </button>
            </form>
        </a>
    </c:forEach>
    <mytag:paginator totalSize="${sessionScope.totalSize}" pageSize="${pageSize}"
                     currentPage="${sessionScope.page}" pageAttribute="page"
                     url="${pageContext.request.contextPath}/medic"/>
</div>
</body>
</html>
