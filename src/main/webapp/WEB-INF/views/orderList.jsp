<%--@elvariable id="user" type="com.bz.hos.model.UserAndLocationEntity.User"--%>

<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.bz.hos.db.OrderTable" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bz.hos.model.UserAndLocationEntity.Location" %>
<%@ page import="com.bz.hos.model.UserAndLocationEntity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <title>Adecco - wszystkie zamowienia</title>
    <link href="<spring:url value="/resources/css/list/list.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/fontello/css/fontello.css"/>" rel="stylesheet" type="text/css"/>

    <style>

        td .input-information{
            width: 100%!important;
        }

    </style>

</head>
<body>

    <%@include file="../../META-INF/resources/htmlElements/header.html" %>

    <div class="main-container">

        <div id="order-list-title" style="border-bottom: solid 1px black; margin-bottom: 20px;">
            Lista złożonych zamówień
        </div>

        <c:if test="${not empty waitingList}">
            <div class="chapter-title" style="text-align: left; margin-left: 20px;">
                Oczekujące zamówienia:
            </div>
            <div class="chapter-border" style="border-color: rgb(84,195,189)">
                <table>
                    <thead>
                    <tr>
                        <th style="width: 125px; background-color: rgb(84,195,189);">ID</th>
                        <th style="width: 125px; background-color: rgb(84,195,189);">Status</th>
                        <th style="width: 140px; background-color: rgb(84,195,189);">Data zamówienia</th>
                        <th style="width: 140px; background-color: rgb(84,195,189);">Data realizacji</th>
                        <th style="background-color: rgb(84,195,189);">Lokalizacja</th>
                        <th style="background-color: rgb(84,195,189);">Uwagi</th>
                        <th style="width: 100px; background-color: rgb(84,195,189);">Akcje</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${waitingList.values()}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.orderStatus}</td>
                            <td>${order.orderDate}</td>
                            <td>${order.realizationDate}</td>
                            <td>${order.location}</td>
                            <td style="font-size: 12px;">${order.comment}</td>
                            <td>
                                <form method="post" action="/showOrder" style="margin-bottom: 0;">
                                    <input type="submit" value="Pokaż">
                                    <input type="hidden" value="${order.id}" name="orderId">
                                </form>
                            </td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${not empty openList and !user.role.equals('client')}">
            <div class="chapter-title" style="text-align: left; margin-left: 20px;">
                Zamówienia do weryfikacji:
            </div>
            <div class="chapter-border" style="border-color: rgb(84,195,189)">
                <table>
                    <thead>
                    <tr>
                        <th style="width: 125px; background-color: rgb(84,195,189);">ID</th>
                        <th style="width: 125px; background-color: rgb(84,195,189);">Status</th>
                        <th style="width: 140px; background-color: rgb(84,195,189);">Data zamówienia</th>
                        <th style="width: 140px; background-color: rgb(84,195,189);">Data realizacji</th>
                        <th style="background-color: rgb(84,195,189);">Ostatnia weryfikacja</th>
                        <th style="background-color: rgb(84,195,189);">Lokalizacja</th>
                        <th style="width: 100px; background-color: rgb(84,195,189);">Akcje</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${openList.values()}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.orderStatus}</td>
                            <td>${order.orderDate}</td>
                            <td>${order.realizationDate}</td>
                            <td>${order.updateDate}</td>
                            <td>${order.location}</td>
                            <td>
                                <form method="post" action="/showOrder" style="margin-bottom: 0;">
                                    <input type="submit" value="Pokaż">
                                    <input type="hidden" value="${order.id}" name="orderId">
                                </form>
                            </td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <div class="chapter-title" style="margin-left: 20px;">
            Znajdź zamówienie po ID
            <input class="input-information roll-up-button" type="button" value="+" id="find-ID-button" onclick="showAndHied(this)"/>
        </div>
        <div class="chapter-border" id="find-ID" style="display: none;">
            <form method="post" action="/list" style="margin-bottom: 0; vertical-align: center;">

                &nbsp; &nbsp; Wyszukaj zamówienie po ID: &nbsp;
                <input type="text" style="width: 30%;" class="input-information" placeholder="#ZAM / 3 litery kodu / numer zamówienia" name="inputedId">
                <input type="submit" style="width: 100px; margin-top: 0.5em;" value="Szukaj" name="findId">

            </form>
        </div>

        <div class="chapter-title" style="margin-left: 20px;">
            Wyfiltruj listę zamówień
            <input class="input-information roll-up-button" type="button" value="+" id="filter-form-button" onclick="showAndHied(this)"/>
        </div>
        <div class="chapter-border" id="filter-form" style="display: none">

            <form:form method="post" action="/list" modelAttribute="filterForm">

                <div class="chapter-title">
                    Specyfika zamówienia
                </div>

                <table style="margin-bottom: 5px;">
                    <tbody>
                        <tr>
                            <td>
                                Region
                                <div class="bottom-cell">
                                    <form:select cssClass="input-information" path="area">
                                        <form:option value="any">Dowolny</form:option>
                                        <form:option value="N">Północ</form:option>
                                        <form:option value="S">Południe</form:option>
                                        <form:option value="E">Wschód</form:option>
                                        <form:option value="W">Zachód</form:option>
                                    </form:select>
                                </div>
                            </td>
                            <td>
                                Lokalizacja
                                <div class="bottom-cell">
                                    <form:select cssClass="input-information" path="localization">
                                        <form:option value="any">Dowolna</form:option>
                                        <c:forEach var="loc" items="${user.locationList.values()}">

                                            <%--@elvariable id="loc" type="com.bz.hos.model.UserAndLocationEntity.Location"--%>
                                                <form:option value="${loc.id}">${loc.name}</form:option>

                                        </c:forEach>
                                    </form:select>
                                </div>
                            </td>
                            <td>
                                Obszar
                                <%
                                    List<String> departments = new ArrayList<>();
                                    for(Location locat: ((User)session.getAttribute("user")).getLocationList().values())
                                        for(String depart: locat.getDepartment().values())
                                            if(!departments.contains(depart))
                                                departments.add(depart);
                                    request.setAttribute("departments", departments);
                                %>
                                <div class="bottom-cell">
                                    <form:select cssClass="input-information" path="department">
                                        <form:option value="any">           Dowolny</form:option>
                                        <c:forEach var="dep" items="${departments}">
                                                <form:option value="${dep}"> ${dep} </form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <table style="margin-bottom: 20px;">
                    <tbody>
                        <tr>
                            <td>
                                Status
                                <div class="bottom-cell">
                                    <form:select cssClass="input-information" path="orderStatus">
                                        <form:option value="any">           Dowolny</form:option>
                                        <form:option value="Oczekuje">      Oczekuje </form:option>
                                        <form:option value="Zatwierdzone">  Zatwierdzone </form:option>
                                        <form:option value="Odrzucone">     Odrzucone </form:option>
                                        <form:option value="Realizacja">  Realizacja </form:option>
                                        <form:option value="Anulowane">     Anulowane </form:option>
                                        <form:option value="Zrealizowane">  Zrealizowane </form:option>
                                    </form:select>
                                </div>
                            </td>
                            <td>
                                Wprowadzający zamówienie
                                <div class="bottom-cell">
                                    <form:select cssClass="input-information" path="supplier">
                                        <form:option value="any">           Dowolny</form:option>
                                        <c:forEach var="orderSupplier" items="${filterForm.supplierList}">
                                            <form:option value="${orderSupplier.toString()}">${orderSupplier.toString()}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </td>
                            <td>
                                Opiekun zamówienia:
                                <div class="bottom-cell">
                                    <form:select cssClass="input-information" path="assignWorker">
                                        <form:option value="any">           Dowolny</form:option>
                                        <c:forEach var="assWorker" items="${filterForm.assignWorkerList}">

                                            <form:option value="${assWorker}">${assWorker}</form:option>

                                        </c:forEach>
                                    </form:select>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <div style="width: 100%; border-bottom: 2px solid #D80000"></div>

                <div class="chapter-title" style="margin-top: 1em;">Daty zamówienia i realizacji</div>

                <table style="margin-bottom: 20px; width: 100%">
                    <tbody>
                        <tr>
                            <td>
                                Zamówiono po:
                                <div class="bottom-cell">
                                    <input class="input-information" type="date" title="Wyświetl zamówienia zamówione po tej dacie" name="orderDateFrom" value="${filterForm.orderDateFrom}">
                                </div>
                            </td>
                            <td>
                                Zamówiono do:
                                <div class="bottom-cell">
                                    <input class="input-information" type="date" title="Wyświetl zamówienia zamówione przed tą datą" name="orderDateTo" value="${filterForm.orderDateTo}">
                                </div>
                            </td>
                            <td>
                                Realizacja po:
                                <div class="bottom-cell">
                                    <input class="input-information" type="date" title="Wyświetl zamówienia z datą realizacji wcześniejszą niż wskazana" name="realizationDateFrom" value="${filterForm.realizationDateFrom}">
                                </div>
                            </td>
                            <td>
                                Realizacja do:
                                <div class="bottom-cell">
                                    <input class="input-information" type="date" title="Wyświetl zamówienia z datą realizacji późnijeszą niż wskazana" name="realizationDateTo" value="${filterForm.realizationDateTo}">
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <c:if test="${user.role.equals('recruiter') or ( user.role.equals('manager') and user.admin )}">

                    <div style="width: 100%; border-bottom: 2px solid #D80000"></div>

                    <div class="chapter-title" style="margin-top: 1em;"> Koordynacja wewnętrzna </div>

                    <table style="margin-right: 20%; margin-left: 20%; width: 60%">
                        <tbody>
                        <tr>
                            <td>
                                Organizacja:
                                <div class="bottom-cell">
                                    <form:select cssClass="input-information" path="organization">
                                        <form:option value="any">           Dowolna</form:option>
                                        <c:forEach var="org" items="${user.organizationList.values()}">

                                            <form:option value="${org}">${org}</form:option>

                                        </c:forEach>
                                    </form:select>
                                </div>
                            </td>
                            <td>
                                Manager:
                                <div class="bottom-cell">
                                    <form:select cssClass="input-information" path="manager">
                                        <form:option value="any">           Dowolna</form:option>
                                        <c:forEach var="man" items="${filterForm.managerList}">

                                            <form:option value="${man}">${man}</form:option>

                                        </c:forEach>
                                    </form:select>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </c:if>

                <br/>
                <input style="width: 100px;" type="submit" value="Filtruj" name="launchFilter"/>
                <input style="width: 200px;" type="reset" value="Zresetuj filtry" name="dateReset"/>
                <br/>

            </form:form>

        </div>

        <div class="chapter-title" style="text-align: left; margin-left: 20px;">
            Podsumowanie wyników
            <!--<input style="width: 2em; height: 2em; border-radius: 1em; font-size: 20px; font-weight: bold; color: rgb(239, 3, 0);" class="input-information" type="button" value="+" id="order-summary-button" onclick="showAndHied(this)"/>-->
        </div>
        <div class="chapter-border" id="order-summary">
            <%
                Map<String, OrderTable> orderMap = (Map<String, OrderTable>) request.getAttribute("orderList");
                int ordersAmount = orderMap.size();
                int realizedAmount = 0;
                int processingAmount = 0;
                int delayedAmount = 0;
                for(OrderTable ord: orderMap.values()) {
                    if (ord.getOrderStatus().equals("Zrealizowane"))
                        realizedAmount++;
                    if (ord.getOrderStatus().equals("Realizacja"))
                        processingAmount++;
                    if ( (ord.getOrderStatus().equals("Realizacja") || ord.getOrderStatus().equals("Zatwierdzone") || ord.getOrderStatus().equals("Oczekuje"))
                         && ( ord.getRealizationDate().isBefore(LocalDate.now()) ) )
                        delayedAmount++;
                }
                String realizedPercent = String.valueOf(realizedAmount*100/(double)ordersAmount);
                if( realizedPercent.length() > 4 ) realizedPercent = realizedPercent.substring(0, 4);

                String processingPercent = String.valueOf(processingAmount*100/(double)ordersAmount);
                if( processingPercent.length() > 4 ) processingPercent = processingPercent.substring(0, 4);

                String delayedPercent = String.valueOf(delayedAmount*100/(double)ordersAmount);
                if( delayedPercent.length() > 4 ) delayedPercent = delayedPercent.substring(0, 4);

            %>
            <table id="overview">
                <thead>
                    <tr>
                        <th>Wyświetlone zamówienia</th>
                        <th>Zrealizowane</th>
                        <th>W realizacji</th>
                        <th>Opóźnione</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><%=ordersAmount%></td>
                        <td><%=realizedAmount%> <br/>
                            <div class="bottom-cell" style="border-top: 1px solid grey;">( <%=realizedPercent%> % )</div>
                        </td>
                        <td><%=processingAmount%> <br/>
                            <div class="bottom-cell" style="border-top: 1px solid grey;">( <%=processingPercent %> % )</div>
                        </td>
                        <td><%=delayedAmount%> <br/>
                            <div class="bottom-cell" style="border-top: 1px solid grey;">( <%=delayedPercent%> % )</div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="chapter-title" style="text-align: left; margin-left: 20px;">
            Zamówienia<c:if test="${filterForm.searchedId.length()!=0 and not empty filterForm.searchedId}"> dla wyszukiwania "${filterForm.searchedId}"</c:if>:
        </div>
        <c:if test="${empty orderList}">
            <div class="chapter-border" style="margin-bottom: 0;">
                <div class="chapter-title" style="color: grey;"> Brak wyników</div>
                <div style="text-align: center;">Aby wyświetlić zamówienia zmień kryteria filtrowania lub dodaj zamówienie.</div>
            </div>
        </c:if>
        <c:if test="${not empty orderList}">
            <div class="chapter-border" style="margin-bottom: 0;">
                <table>
                    <thead>
                        <tr>
                            <th style="width: 125px;">ID</th>
                            <th style="width: 125px;">Status</th>
                            <th style="width: 140px;">Data zamówienia</th>
                            <th style="width: 140px;">Data realizacji</th>
                            <th>Lokalizacja</th>
                            <th>Uwagi</th>
                            <th style="width: 100px;">Akcje</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orderList.values()}">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.orderStatus}</td>
                                <td>${order.orderDate}</td>
                                <td>${order.realizationDate}</td>
                                <td>${order.location}</td>
                                <td style="font-size: 12px;">${order.comment}</td>
                                <td>
                                    <form method="post" action="/showOrder" style="margin-bottom: 0;">
                                        <input type="submit" value="Pokaż">
                                        <input type="hidden" value="${order.id}" name="orderId">
                                    </form>
                                </td>
                            </tr>

                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <br/>
        <form:form action="/menu" method="post" style="float: right; margin: 20px;">
            <input type="submit" value="Cofnij" style="width: 100px;">
        </form:form>
        <div style="clear:both;"></div>

    </div>

    <%@include file="../../META-INF/resources/htmlElements/footer.html" %>

</body>
</html>

<script type="text/javascript">
    function showAndHied(buttonID){

        if(buttonID.id === "filter-form-button"){
            if(document.getElementById("filter-form-button").getAttribute("value") === '+'){
                document.getElementById("filter-form").style.display = 'block';
                document.getElementById("filter-form-button").setAttribute("value","-");
            }
            else {
                document.getElementById("filter-form").style.display = 'none';
                document.getElementById("filter-form-button").setAttribute("value","+");
            }
        }
        else if(buttonID.id === "order-summary-button"){
            if(document.getElementById("order-summary-button").getAttribute("value") === '+'){
                document.getElementById("order-summary").style.display = 'block';
                document.getElementById("order-summary-button").setAttribute("value","-");
            }
            else {
                document.getElementById("order-summary").style.display = 'none';
                document.getElementById("order-summary-button").setAttribute("value","+");
            }
        }
        else if(buttonID.id === "find-ID-button"){
            if(document.getElementById("find-ID-button").getAttribute("value") === '+'){
                document.getElementById("find-ID").style.display = 'block';
                document.getElementById("find-ID-button").setAttribute("value","-");
            }
            else {
                document.getElementById("find-ID").style.display = 'none';
                document.getElementById("find-ID-button").setAttribute("value","+");
            }
        }
    }
</script>