<%--
  Created by IntelliJ IDEA.
  User: nickolay
  Date: 16.04.17
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
    <p>Logout: </p>
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Log out">
    </form>
</body>
</html>
