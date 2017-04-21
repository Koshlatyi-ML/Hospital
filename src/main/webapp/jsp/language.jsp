<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<c:set var="language" value="${not empty param.language
                                        ? param.language
                                        : not empty language
                                                    ? language
                                                    : pageContext.request.locale}"
       scope="session"/>
</body>
</html>
