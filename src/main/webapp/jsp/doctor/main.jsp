<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="controller.constants.WebPaths" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Doctor main</title>
</head>
<body>
    <jsp:include page="/jsp/header.jsp"/>
    <p>Doctor's main page</p>
    <% String path = request.getRequestURI().substring(request.getContextPath().length());
       String roleAttribute = (String) session.getAttribute("role"); %>
    <%=WebPaths.roleAccessiblePaths.get(roleAttribute).toString()%> <br>
    <c:out value="${sessionScope.i}"/>
</body>
</html>
