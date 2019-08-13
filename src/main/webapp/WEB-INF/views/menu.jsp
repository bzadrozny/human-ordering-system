<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html lang="pl">
<head>
    <title>Adecco - menu</title>
    <link href="<spring:url value="/resources/css/menu/menu-style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/fontello/css/fontello.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body style="background: none;">

    <%@include file="../../resources/htmlElements/header.html" %>

    <div class="menu-cart-box" style="background-image: url('/resources/backgrounds/menu/newOrder.png');background-size: cover;">
        <div class="menu-button">
            <form action="/newOrder" method="POST">
                <input type="submit" value="Nowe"/>
            </form>
        </div>
        <div class="menu-description">
            W tej zakładce możesz dodać zamówienie dla wybranej lokalizacji i zdefiniować na jakich stanowiskach pracowników potrzebujesz.
        </div>
    </div>

    <div class="menu-cart-box">
        <div class="menu-button" style="float: right;">
            <form action="/list" method="POST">
                <input type="submit" value="Lista" style="color: rgb(239, 30, 30); font-weight: bold;"/>
            </form>
        </div>
        <div class="menu-description" style="float: right; color: black;">
            Tutaj możesz wyszukać zamówienie po jego numerze ID, jak i je filtrować.
        </div>
        <div class="menu-description" style="float: right; height: 300px; width: 20%; margin: 0 10% 0 0;">
            <img src="<spring:url value="/resources/backgrounds/menu/list.png"/>" style="height:300px !important; width: 100%!important;"/>
        </div>
    </div>

    <div class="menu-cart-box" style="background-image: url('/resources/backgrounds/menu/contracts.png');background-size: cover;">
        <div class="menu-button">
            <form action="/contractManagement" method="POST">
                <input type="submit" value="Kontrakty"/>
            </form>
        </div>
        <div class="menu-description">
            Karta ta odpowiada za zarządzanie kontraktami dla wybranej lokalizacji. Możesz przedłużyć umowę bądź ją wcześniej rozwiązać poprzez zmianę daty zakończenia współpracy.
        </div>
    </div>

    <div class="menu-cart-box">
        <div class="menu-button" style="float: right;">
            <form action="/report" method="POST">
                <input type="submit" value="Statystyki" style="color: rgb(239, 30, 30); font-weight: bold;"/>
            </form>
        </div>
        <div class="menu-description" style="float: right; color: black;">
            Statystyki pozwalają na podsumowanie działań wybranej lokalizacji i pracowników Adecco oraz kontrolować poziom ich obsługi.
        </div>
        <div class="menu-description" style="float: right; height: 300px; width: 20%; margin: 0 10% 0 0;">
                <img src="<spring:url value="/resources/backgrounds/menu/stats.png"/>" style="height:300px !important; width: auto;"/>
        </div>
    </div>

    <div class="menu-cart-box" style="background-image: url('/resources/backgrounds/menu/setting.png');background-size: cover;">
        <div class="menu-button">
            <form action="/account-management" method="POST">
                <input type="submit" value="Ustawienia"/>
            </form>
        </div>
        <div class="menu-description">
            Dzięki ustawieniom możesz zminić hasło logowania oraz zobaczyć swoje dane osobowe i organizacyjne.
        </div>
    </div>

    <div class="menu-cart-box">
        <div class="menu-button" style="float: right;">
            <form action="/support" method="POST">
                <input type="submit" value="Wsparcie" style="color: rgb(239, 30, 30); font-weight: bold;"/>
            </form>
        </div>
        <div class="menu-description" style="float: right; color: black;">
            W tej zakładce możesz przekazać zespołowi swoje propozycje i uwagi poprzez gotowy formularz kontaktu, w tym złożyć wniosek o zmianę uprawnień konta.
        </div>
        <div class="menu-description" style="float: right; height: 300px; width: 20%; margin: 0 10% 0 0;">
            <img src="<spring:url value="/resources/backgrounds/menu/support.png"/>" style="height:300px !important; width: auto;"/>
        </div>
    </div>

    <%@include file="../../resources/htmlElements/footer.html" %>

</body>
</html>
