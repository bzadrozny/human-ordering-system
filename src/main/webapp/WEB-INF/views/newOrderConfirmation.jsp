<%--@elvariable id="savedOrder" type="com.bz.hos.model.NewOrderForm.NewOrder"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Potwierdzenie zamówienia</title>
    <link href="<spring:url value="/resources/css/newOrder/confirmation.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/fontello/css/fontello.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>

    <%@include file="../../META-INF/resources/htmlElements/header.html" %>

    <div class="main-container" style="padding-bottom: 10px;">

        <div class="chapter-border">
            <div id="order-id">Zamówienie otrzyma ID: <span style="color: #000;"> ${savedOrder.id}</span></div>
        </div>

        <div class="chapter-title">Specyfikacja zamówienia</div>
        <div class="chapter-border">

            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Data zamówienia:</div>
                <div class="chapter-border-tile-data">${savedOrder.orderDate}</div>
            </div>
            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Lokalizacja:</div>
                <div class="chapter-border-tile-data">${savedOrder.location}</div>
            </div>
            <div style="clear:both"></div>

            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Data realizacji:</div>
                <div class="chapter-border-tile-data">${savedOrder.realizationDate}</div>
            </div>
            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Region:</div>
                <c:if test="${savedOrder.area.equals('N')}">
                    <div class="chapter-border-tile-data">Północ</div>
                </c:if>
                <c:if test="${savedOrder.area.equals('E')}">
                    <div class="chapter-border-tile-data">Wschód</div>
                </c:if>
                <c:if test="${savedOrder.area.equals('S')}">
                    <div class="chapter-border-tile-data">Południe</div>
                </c:if>
                <c:if test="${savedOrder.area.equals('W')}">
                    <div class="chapter-border-tile-data">Zachód</div>
                </c:if>
            </div>
            <div style="clear:both"></div>

            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Kategoria zamówienia:</div>
                <div class="chapter-border-tile-data">
                <c:if test="${savedOrder.orderCategory=='ZAM'}">
                    Nowe zamówienie
                </c:if>
                <c:if test="${savedOrder.orderCategory=='REP'}">
                    Replacement
                </c:if>
                </div>
            </div>
            <div style="clear:both"></div>

        </div>

        <div class="chapter-title">Akredytacja</div>
        <div class="chapter-border">

            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Organizacja:</div>
                <div class="chapter-border-tile-data">${savedOrder.organization}</div>
            </div>
            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Status:</div>
                <div class="chapter-border-tile-data">${savedOrder.orderStatus}</div>
            </div>
            <div style="clear:both"></div>


            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Obszar:</div>
                <div class="chapter-border-tile-data">${savedOrder.department}</div>
            </div>
            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Kierownik obszaru:</div>
                <div class="chapter-border-tile-data">${savedOrder.departmentManager}</div>
            </div>
            <div style="clear:both"></div>

            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Opiekun:</div>
                <div class="chapter-border-tile-data">${savedOrder.assignWorker}</div>
            </div>
            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Manager:</div>
                <div class="chapter-border-tile-data">${savedOrder.manager}</div>
            </div>
            <div style="clear:both"></div>

            <div class="chapter-border-line-half">
                <div class="chapter-border-tile">Wprowadzający:</div>
                <div class="chapter-border-tile-data">${savedOrder.supplier}</div>
            </div>
            <div style="clear:both"></div>

        </div>

        <div class="chapter-title">Zamawiane stanowiska</div>
        <div class="chapter-border">

            <table>
                <thead>
                    <tr>
                        <th style="width: 40px;">Ilość</th>
                        <th style="width: 90px;">Stanowisko</th>
                        <th style="width: 100px;">Praca od</th>
                        <th style="width: 100px;">Praca do</th>
                        <th style="width: 65px;">Umowa</th>
                        <th style="width: 70px;">Płaca</th>
                        <th>Kwalifikacje</th>
                        <th>Uwagi</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="record" items="${savedOrder.recordList}">
                        <tr style="color: black">
                            <td>${record.accountOrdered}</td>
                            <td>${record.jobName}</td>
                            <td>${record.workingFrom}</td>
                            <td>${record.workingTo}</td>
                            <td>${record.contract}</td>
                            <td>${record.paymentValue}zł/${record.paymentType}</td>
                            <td>${record.qualification}</td>
                            <td>${record.comment}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>

        <div class="chapter-title">Zatwierdzenie zamówienia</div>
        <div class="chapter-border">
            <div id="approval">
                Uwagi: <br/>
                <c:if test="${empty savedOrder.comment}">
                    Brak wprowadzonych uwag.
                </c:if>
                <c:if test="${savedOrder.comment.length() != 0}">
                    ${savedOrder.comment}
                </c:if>
                <br/><br/>
                <form:form method="post" action="${pageContext.request.contextPath}/newOrder" style="float: right"> <input type="submit" value="Zamów" name="saveOrder"> </form:form>
                <form:form method="post" action="${pageContext.request.contextPath}/newOrder" style="float: right"> <input type="submit" value="Edytuj" name="editFromConf"> </form:form>

                <div style="clear: both"></div>
            </div>
        </div>

    </div>

    <%@include file="../../META-INF/resources/htmlElements/footer.html" %>

</body>
</html>