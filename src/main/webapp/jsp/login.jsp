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
        <c:out value="${sessionScope.wrongRoleMsg}"/>
        <c:out value="${sessionScope.invalidLoginMsg}"/>
        <c:out value="${sessionScope.invalidPasswordMsg}"/>
        <c:out value="${sessionScope.wrongCredentialsMsg}"/>
    </font>

   <%-- <c:if test="${sessionScope.invalidLogin == 'yes'}" >
        <p><font color="red">Invalid login</font></p>
    </c:if>

    <c:if test="${sessionScope.invalidPassword == 'yes'}" >
        <p><font color="red">Invalid user - minimum 8 characters maximum 20 characters
            at least 1 Uppercase Alphabet, 1 Lowercase Alphabet and 1 Number</font></p>
    </c:if>

    <c:if test="${sessionScope.wrongCredentials == 'yes'}" >
        <p><font color="red">Wrong credentials</font></p>
    </c:if>--%>


    <h1>LOGIN PAGE</h1>
    <form action="${pageContext.request.contextPath}/login" method="post">
        Patient: <input name="role" type="radio" value="Patient"> <br>
        Medic: <input name="role" type="radio" value="Medic"> <br>
        Doctor: <input name="role" type="radio" value="Doctor"> <br>
        <input name="login" type="text"/><br>
        <input name="password" type="password"/><br>
        <input type="submit" value="Log in">
    </form>

    <form action="${pageContext.request.contextPath}/registration" method="get">
        <button type="submit" class="btn-link">Register</button>
    </form>
    <p><%= request.getRequestURI()%> / <%=request.getMethod()%></p>
    <p>hasError =  <%= session.getAttribute("hasError")%></p>
    <p>logined =  <%= session.getAttribute("logined")%></p>
    <p>login =  <%= session.getAttribute("login")%></p>
    <p>user =  <%= session.getAttribute("user")%></p>
    <p>role =  <%= session.getAttribute("role")%></p>
</body>
</html>
