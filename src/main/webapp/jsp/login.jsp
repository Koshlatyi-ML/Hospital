<%--
  Created by IntelliJ IDEA.
  User: nickolay
  Date: 16.04.17
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<html>
<head>
    <title>Title</title>
    <style>
        .btn-link {
            border:none;
            outline:none;
            background:none;
            cursor:pointer;
            color:#0000EE;
            padding:0;
            text-decoration:underline;
            font-family:inherit;
            font-size:inherit;
        }
    </style>
</head>
<body>
    <font color="red">
        <c:out value="${sessionScope.invalidLoginMsg}"/>
        <c:out value="${sessionScope.invalidPasswordMsg}"/>
        <c:out value="${sessionScope.wrongCredentialsMsg}"/>
    </font>

    <h1>LOGIN PAGE</h1>
    <form action="${pageContext.request.contextPath}/login" method="post">
        Login as: <select size="1" name="role">
                    <option value="Patient">Patient</option>
                    <option value="Doctor">Doctor</option>
                    <option value="Medic">Medic</option>
                  </select><br>
        <input name="login" type="text"/><br>
        <input name="password" type="password"/><br>
        <input type="submit" value="Log in">
    </form>

    <form action="${pageContext.request.contextPath}/registration" method="get">
        <button type="submit" class="btn-link">Register</button>
    </form>
</body>
</html>
