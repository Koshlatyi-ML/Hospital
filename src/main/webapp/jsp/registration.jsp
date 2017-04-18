<%--
  Created by IntelliJ IDEA.
  User: nickolay
  Date: 18.04.17
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <p>Register as:</p>
    <form action="${pageContext.request.contextPath}/registration/patient" method="get">
        <input type="submit" value="Patient">
    </form>
    <form action="${pageContext.request.contextPath}/registration/doctor" method="get">
        <input type="submit" value="Doctor">
    </form>
    <form action="${pageContext.request.contextPath}/registration/medic" method="get">
        <input type="submit" value="Medic">
    </form>
</body>
</html>
