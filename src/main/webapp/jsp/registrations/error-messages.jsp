<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nickolay
  Date: 18.04.17
  Time: 23:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<font color="red">
    <c:out value="${sessionScope.invalidNameMsg}"/>
    <c:out value="${sessionScope.invalidLoginMsg}"/>
    <c:out value="${sessionScope.invalidPasswordMsg}"/>
    <c:out value="${sessionScope.wrongCredentialsMsg}"/>
    <c:out value="${sessionScope.alreadyUsedLoginMsg}"/>
</font>
</body>
</html>
