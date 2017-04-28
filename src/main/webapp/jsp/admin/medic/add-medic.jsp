<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%@include file="/jsp/admin/main.jsp" %>
<fmt:setBundle basename="/i18n/admin/stuff" var="stuffBundle"/>
<form id="deparment-add" class="form-horizontal" action="${pageContext.request.contextPath}/admin/add-medic"
      method="post">
    <div id="fail-Msg">
        <%@include file="/jsp/util/error-messages.jsp" %>
    </div>
    <div id="success-Msg">
        <c:out value="${sessionScope.successfulAddMedic}"/>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="div5">
            <fmt:message key="selectDepartment" bundle="${stuffBundle}"/>
        </label>
        <div class="col-sm-8">
            <select name="departmentId" id="div5">
                <c:forEach items="${sessionScope.departments}" var="doctor">
                    <option value="${doctor.id}">${doctor.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="div11">
            <fmt:message key="login" bundle="${stuffBundle}"/>
        </label>
        <div class="col-sm-8">
            <input name="login" type="text" class="form-control" id="div11"
                   pattern="^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="div42">
            <fmt:message key="password" bundle="${stuffBundle}"/>
        </label>
        <div class="col-sm-8">
            <input name="password" type="password" class="form-control" id="div42"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,20}$">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="div43">
            <fmt:message key="name" bundle="${stuffBundle}"/>
        </label>
        <div class="col-sm-8">
            <input name="name" type="text" class="form-control" id="div43"
                   pattern="^[a-zA-Zа-яА-ЯёЁ ,.'-]{1,64}$">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="div4">
            <fmt:message key="surname" bundle="${stuffBundle}"/>
        </label>
        <div class="col-sm-8">
            <input name="surname" type="text" class="form-control" id="div4"
                   pattern="^[a-zA-Zа-яА-ЯёЁ ,.'-]{1,64}$">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-4 col-sm-8">
            <button id="add-btn" type="submit" class="btn btn-primary">
                <fmt:message key="add" bundle="${stuffBundle}"/>
            </button>
        </div>
    </div>
</form>
</body>
</html>
