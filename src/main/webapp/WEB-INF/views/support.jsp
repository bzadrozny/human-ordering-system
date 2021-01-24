<%--@elvariable id="user" type="com.bz.hos.model.UserAndLocationEntity.User"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Wsparcie</title>
    <link href="<spring:url value="/resources/css/support/support.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/fontello/css/fontello.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>

    <%@include file="../../META-INF/resources/htmlElements/header.html" %>

    <div class="main-container">

        <div class="chapter-title">Przekaż dalej swoje propozycje i uwagi!</div>
        <div class="chapter-border" style="margin-bottom: 0;">

            <form method="post" action="$}support">

                <div style="color: #000; font-size: 24px; font-weight: bold;">Temat: &nbsp;</div>
                <select class="input-information" required="required" name="subject" >
                    <option value="Wyglad aplikacji">Wygląd aplikacji</option>
                    <option value="Zarzadzanie kontem">Zarządzanie kontem</option>
                    <option value="Składanie zamowienia">Składanie nowego zamówienia</option>
                    <option value="Lista zamowien">Lista zamówień</option>
                    <option value="Podglad zamowienia">Podgląd zamówienia</option>
                    <option value="Obsluga zamowienia">Obsługa zamówienia</option>
                    <option value="Statystyki">Statystyki</option>
                </select>
                <div style="clear: both;"></div>
                Aby ułatwić nam lepsze zrozumienie Twojej potrzeby wskaż temat, którego dotyczy poniższa adnotacja<br/>
                <br/>

                <div style="color: #000; font-size: 24px; font-weight: bold; float: left;">Opis: &nbsp;</div>
                <textarea class="input-information" style="max-width: 100%; min-width: 100%; min-height: 8em;"
                          placeholder="Opisz swoją uwagę (20 - 200 znaków)" minlength="20" maxlength="200"
                          required="required" name="description"></textarea>

                <div style="clear: both;"></div>
                Opis ma na celu bardziej szczegółowe określenie Twojej uwagi. W przypadku, gdy nie będzie on jasny, bądź będzie zbyt ogólny uwaga może zosatć odrzucona albo skontaktujemy się w celu jej sprecyzowania.<br/>

                <br/>
                <input type="submit" value="Wyślij uwagę" style="width: 300px;" name="addNewComment">
                <br/>
            </form>

        </div>

        <c:if test="${user.admin}">
            <div class="chapter-title" style="margin-top: 50px;"> Wprowadzone adnotacje:</div>
            <div class="chapter-border">
                <c:if test="${empty commentsList}">
                    Brak adnotacji od użytkowników!
                </c:if>
                <c:if test="${not empty commentsList}">
                    <c:forEach items="${commentsList}" var="comment">

                        <div class="chapter-title">${comment.title}</div>

                        <table>
                            <tr>
                                <td>
                                    <div style="color: rgb(17, 74, 82); font-weight: bold;">Data dodania</div>
                                    <div class="bottom-cell">
                                            ${comment.inputDate}
                                    </div>
                                </td>
                                <td>
                                    <div style="color: rgb(17, 74, 82); font-weight: bold;">Status</div>
                                    <div class="bottom-cell">
                                            ${comment.status}
                                    </div>
                                </td>
                                <td>
                                    <div style="color: rgb(17, 74, 82); font-weight: bold;">Użytkownik</div>
                                    <div class="bottom-cell">
                                            ${comment.supplierMail}
                                    </div>
                                </td>
                            </tr>
                        </table>

                        <textarea class="input-information" title="Opis" style="margin: 20px 15%; min-width: 70%; max-width: 70%; max-height: 8em;" disabled="disabled">${comment.description}</textarea>

                        <div class="bottom-cell" style="text-align: center; font-size: 18px; border-top: 2px solid rgb(17, 74, 82); width: 90%; margin-left: 5%"></div>

                    </c:forEach>
                </c:if>
            </div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/menu" method="post" style="float: right; margin: 20px;"><input type="submit" value="Cofnij" style="width: 100px;"></form:form>
        <div style="clear:both;"></div>

    </div>

    <%@include file="../../META-INF/resources/htmlElements/footer.html" %>

</body>
</html>
