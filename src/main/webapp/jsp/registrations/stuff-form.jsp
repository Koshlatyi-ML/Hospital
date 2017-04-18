<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nickolay
  Date: 18.04.17
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <%@include file="patient-form.jsp"%>
    Select department: <select name="department">
        <c:forEach items="${sessionScope.departments}" var="department">
            <option value="${department.id}">${department.name}</option>
        </c:forEach>
    </select><br>
</body>
</html>
