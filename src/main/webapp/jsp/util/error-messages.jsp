<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <c:out value="${sessionScope.invalidNameMsg}"/>
    <c:out value="${sessionScope.invalidLoginMsg}"/>
    <c:out value="${sessionScope.invalidPasswordMsg}"/>
    <c:out value="${sessionScope.wrongCredentialsMsg}"/>
    <c:out value="${sessionScope.alreadyUsedLoginMsg}"/>
</body>
</html>
