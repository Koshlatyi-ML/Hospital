<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/css/full-width-pics.css" rel="stylesheet">
</head>

<body>
<%@include file="header-login.jsp"%>
<fmt:setBundle basename="i18n/main" var="mainBundle"/>
<header class="image-bg-fluid-height">
    <img class="img-responsive img-center" src="${pageContext.request.contextPath}/img/logo.png" alt="">
</header>

<section>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="section-heading"><fmt:message key="hospital.name" bundle="${mainBundle}"/></h1>
                <p class="lead section-lead"><fmt:message key="hospital.slogan" bundle="${mainBundle}"/></p>
            </div>
        </div>
    </div>
</section>

</body>

</html>