<%@ page import="controller.constants.WebPaths" %><%--  Created by IntelliJ IDEA.
  User: nickolay
  Date: 15.04.17
  Time: 19:55
  To change this template use File | Settings | File Templates.--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error 404</title>
</head>
<body>
    <h1>Ooops!</h1>
    <p>Something went wrong.</p>
    <%= request.getRequestURI().substring(request.getContextPath().length()) %>
    <%= session.getAttribute("first_path")%>
    <%= WebPaths.webPaths.containsValue(session.getAttribute("first_path"))%>
    <p>${pageContext.request.contextPath}</p>
</body>
</html>
