<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<c:choose>
    <c:when test="${not empty sessionScope.logined}">
        <%@include file="header-logout.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="header-login.jsp" %>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="/i18n/admin/stuff" var="stuffBundle"/>
<form id="deparment-add" class="form-horizontal" action="${pageContext.request.contextPath}/registration" method="post">
    <div id="fail-Msg">
        <%@include file="/jsp/util/error-messages.jsp" %>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="div1">
            <fmt:message key="login" bundle="${stuffBundle}"/>
        </label>
        <div class="col-sm-8">
            <input name="login" type="text" class="form-control" id="div1"
                   pattern="^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="div24">
            <fmt:message key="password" bundle="${stuffBundle}"/>
        </label>
        <div class="col-sm-8">
            <input name="password" type="password" class="form-control" id="div24"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,20}$">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="div34">
            <fmt:message key="name" bundle="${stuffBundle}"/>
        </label>
        <div class="col-sm-8">
            <input name="name" type="text" class="form-control" id="div34"
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
                <fmt:message key="register" bundle="${stuffBundle}"/>
            </button>
        </div>
    </div>
</form>
</body>
</html>
