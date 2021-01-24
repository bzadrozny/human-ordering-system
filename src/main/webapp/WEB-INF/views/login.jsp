<%@ page import="org.springframework.web.filter.CharacterEncodingFilter" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    session.removeAttribute("user");
    String requstMethod = request.getMethod();
    request.setAttribute("requestMethod", requstMethod);
%>
<html>
<head>
    <meta charset="utf-8">
    <title>Panel Logowania</title>
    <link href="<spring:url value="resources/css/login/logowanie-style.css"/>" rel="stylesheet" type="text/css">
    <link href="<spring:url value="resources/css/main.css"/>" rel="stylesheet" type="text/css">
    <link href="<spring:url value="resources/fontello/css/fontello.css"/>" rel="stylesheet" type="text/css">

    <style>
        .footer {
            position: fixed !important;
            bottom: 0 !important;
            left: 0 !important;
            right: 0 !important;
        }

        body {
            background-image: url("${pageContext.request.contextPath}/resources/backgrounds/login/LoginBackground.png");
            background-size: cover;
        }

    </style>

</head>
<body>

<div id="page">

    <div class="logo">
        <a style="margin-left: 2em; float: left;" href="http://Adecco.com">
            <div class="logo-Adecco-name">
                <div id="logo">HOS</div>
            </div>
        </a>
        <div style="clear: both"></div>
    </div>

    <div class="container">
        <c:if test="${ requestMethod == 'GET'}">
            <div class="loginInfo">
                System zamówień pracowników - Zapraszamy!
            </div>
        </c:if>
        <c:if test="${ requestMethod == 'POST' }">
            <div class="loginInfo">
                Niestety podano błędne dane, spróbuj ponownie!
            </div>
        </c:if>

        <form:form method="post" action="${pageContext.request.contextPath}/" modelAttribute="loginForm">
            <div>
                Login: <form:input cssStyle="font-size: 16px; width: 40%; height: 2.5em;" cssClass="input-information"
                                   path="login"/> <br/><br/>
                Haslo: <form:password cssStyle="font-size: 16px; width: 40%; height: 2.5em;"
                                      cssClass="input-information" path="password"/> <br/><br/>
            </div>
            <div>
                <input style="float: none; width: 40%;" type="submit" value="Zaloguj"/>
            </div>
        </form:form>
    </div>

    <%@include file="../../META-INF/resources/htmlElements/footer.html" %>

</div>

</body>
</html>