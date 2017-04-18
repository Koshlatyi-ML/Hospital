<%--
  Created by IntelliJ IDEA.
  User: nickolay
  Date: 18.04.17
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <%@include file="/jsp/registrations/error-messages.jsp" %>
    <h1>PATIENT REGISTRATION</h1>
    <form action="${pageContext.request.contextPath}/registration/patient" method="post">
        <%@include file="/jsp/registrations/patient-form.jsp" %>
        <input type="submit" value="Register">
    </form>
</body>
</html>
