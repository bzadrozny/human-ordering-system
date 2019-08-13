<%--@elvariable id="user" type="com.bz.hos.model.UserAndLocationEntity.User"--%>
<%--@elvariable id="loc" type="com.bz.hos.model.UserAndLocationEntity.Location"--%>
<%--@elvariable id="userReport" type="com.bz.hos.model.ReportModels.UserReport"--%>
<%--@elvariable id="locationReport" type="com.bz.hos.model.ReportModels.LocationReport"--%>
<%--@elvariable id="supplier" type="com.bz.hos.model.ReportModels.LocationReport.OrderSupplierReport"--%>
<%--@elvariable id="staffUserReport" type="com.bz.hos.model.ReportModels.UserReport"--%>

<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page contentType="text/html;charset=UTF-8"%>

<html>
<head>
    <title>Statystyki</title>
    <link href="<spring:url value="/resources/css/report/report-st.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/fontello/css/fontello.css"/>" rel="stylesheet" type="text/css"/>

    <style>

        .chapter-border{
            margin-bottom: 20px !important;
        }

        .chart-title{
            padding-bottom: 0;
        }

    </style>

</head>
<body>

    <%@include file="../../resources/htmlElements/header.html" %>

    <div class="main-container" id="adeccoUserReport" style="margin-bottom: 20px !important; <c:if test="${user.role.equals('client')}">display:none;</c:if>">

        <div class="chapter-title">Podsumowanie pracy zalogowanego użytkownika</div>

        <div class="chapter-border">
            <div class="chart-title" style="height: auto; border-bottom: none">
                <form:form modelAttribute="rangesForReport" action="/report" method="post" style="width: 90%; margin-left: auto; margin-right: auto; margin-bottom: 0;">
                    <div style="text-align: center; font-size: 24px;">
                        Wprowadź zakres raportu
                    </div>

                    <div style="margin: 10px 0; background: white; padding: 15px; border-radius: 10px;">
                        <table style="border: none">
                            <tr>
                                <td style="width: 50px;"> od: </td>
                                <td style="width: 150px; padding: 0 10px;"> <input type="date" name="formUserRangeFrom" class="input-information" title="data od" value="${rangesForReport.userRangeFrom}" min="<%=LocalDate.now().minusYears(1)%>" max="<%=LocalDate.now()%>" required/> </td>
                                <td style="width: 50px;"> do: </td>
                                <td style="width: 150px; padding: 0 10px;"> <input type="date" name="formUserRangeTo" class="input-information" title="data do" value="${rangesForReport.userRangeTo}" min="<%=LocalDate.now().minusYears(1)%>" max="<%=LocalDate.now()%>" required/> </td>
                            </tr>
                        </table>
                        <table style="border: none">
                            <tr>
                                <td style="width: 100%;"> <input type="submit" name="reportRange" value="Zatwierdź zakres" style="border-radius: 1em; background-color: rgb(239, 3, 0); "> </td>
                            </tr>
                        </table>
                    </div>
                </form:form>
            </div>
        </div>

        <div style="width: 20%; margin: 0 auto; height: 3em;">
            <input type="button" id="user-report-button" value="Pokaż" class="input-information" onclick="showAndHide(this)">
        </div>
        <div id="user-report" style="display: none">

            <div class="chapter-border">
                <div class="chapter-title"> Realizacja zadań Managerskich </div>

                <div class="chart-place">

                    <div class="chart-title" style="height: auto;">
                        Ilość zamówień przypisanych do użytkownika: &nbsp;
                        <input type="text" title="Ilość zamówień" disabled="disabled" value="${userReport.userOrderAmount}" class="input-information"
                               style="background: white !important; text-align: center; width: 40px!important; margin-bottom: 0;" />
                    </div>

                    <div style="width: 100%; height: 250px; background: repeating-linear-gradient(to bottom, #cccccc, #cccccc 1px, #fff 1px, #fff 10%);">
                        <div class="adecco-chart-scale">
                            <div id="waiting-label" class="chart-label" style="height: ${fn:substringBefore(250*(1 - userReport.waitingAmount/(userReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="waiting-post" class="chart-post" style="height: ${fn:substringBefore(250*(userReport.waitingAmount/userReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((userReport.waitingAmount*100/userReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="accepted-label" class="chart-label" style="height: ${fn:substringBefore(250*(1-userReport.acceptedAmount/(userReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="accepted-post" class="chart-post" style="height: ${fn:substringBefore(250*(userReport.acceptedAmount/userReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((userReport.acceptedAmount*100/userReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="realization-label" class="chart-label" style="height: ${fn:substringBefore(250*(1-userReport.realizationAmount/(userReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="realization-post" class="chart-post" style="height: ${fn:substringBefore(250*(userReport.realizationAmount/userReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((userReport.realizationAmount*100/userReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="deleted-label" class="chart-label" style="height: ${fn:substringBefore(250*(1-userReport.deletedAmount/(userReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="deleted-post" class="chart-post" style="height: ${fn:substringBefore(250*(userReport.deletedAmount/userReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((userReport.deletedAmount*100/userReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="delayed-label" class="chart-label" style="height: ${fn:substringBefore(250*(1-userReport.delayedAmount/(userReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="delayed-post" class="chart-post" style="height: ${fn:substringBefore(250*(userReport.delayedAmount/userReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((userReport.delayedAmount*100/userReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="realized-label" class="chart-label" style="height:  ${fn:substringBefore(250*(1-userReport.realizedAmount/(userReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="realized-post" class="chart-post" style="height:  ${fn:substringBefore(250*(userReport.realizedAmount/userReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((userReport.realizedAmount*100/userReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                    </div>

                    <div style="width: 100%; height: 50px; border-top: 1px solid black;">
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Oczekuje</div>
                            <div class="chart-label">${userReport.waitingAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Zatwierdzone</div>
                            <div class="chart-label">${userReport.acceptedAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Realizacja</div>
                            <div class="chart-label">${userReport.realizationAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Anulowano</div>
                            <div class="chart-label">${userReport.deletedAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Opóźnione</div>
                            <div class="chart-label">${userReport.delayedAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Zrealizowane</div>
                            <div class="chart-label">${userReport.realizedAmount}</div>
                        </div>
                    </div>

                </div>
            </div>

            <div class="chapter-border">
                <div class="chapter-title"> Realizacja zamówień / lokalizacja </div>

                <div class="chart-place" style="height: auto;">

                    <div class="chart-title">
                        Poziom realizacji zamówień w danym okresie
                    </div>

                    <div class="candidateSumUpTable">
                        <c:if test="${empty userReport.candidateRealizationList}">
                            <div class="chapter-border" style="margin-top: 1em;">
                                <div class="chapter-title" style="color: grey;"> Brak wyników</div>
                                <div style="text-align: center;">Wprowadź nowych kandydatów aby zobaczyć wynink.</div>
                            </div>
                            <div style="height: 0.1em;"></div>
                        </c:if>
                        <c:if test="${not empty userReport.candidateRealizationList}">
                            <table>
                                <tr>
                                    <th>Organizacja</th>
                                    <th>Lokalizacja</th>
                                    <th>Zamówiono</th>
                                    <th>Zatwierdzono</th>
                                    <th>Zrealizowano</th>
                                </tr>
                                <c:forEach items="${userReport.candidateRealizationList.values()}" var="locRep">
                                    <tr>
                                        <td>${locRep.organization}</td>
                                        <td>${locRep.localization}</td>
                                        <td>${locRep.orderedCandidatesAmount}</td>
                                        <td>${locRep.acceptedCandidatesAmount}</td>
                                        <td>${locRep.realizedCandidatesAmount} | ${fn:substringBefore((locRep.realizedCandidatesAmount * 100 / locRep.orderedCandidatesAmount),'.') + 0}%</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>

                </div>
            </div>

            <div class="chapter-border">

                <div class="chart-place" style="height: auto;">
                    <div class="chart-title">Realizacja KPI użytkownia</div>

                    <div style="padding: 10px 0; background: repeating-linear-gradient(to right, #ddd, #ddd 2px, #fff 2px, #fff 10%);">

                        <div class="chart-post-KPI" style="width: ${fn:substringBefore(100*userReport.userOrderAmount/userReport.totalOrderAmount,'.') + 0}%;">
                            ${fn:substringBefore(100*userReport.userOrderAmount/userReport.totalOrderAmount,'.') + 0}%
                        </div>
                        <div class="chart-label-KPI">
                            Zamówienia przypisane do użytkownika / Total zamówień (${userReport.userOrderAmount} / ${userReport.totalOrderAmount})
                        </div>

                        <div class="chart-post-KPI" style="width: ${fn:substringBefore(100*userReport.delayedAmount/userReport.totalOrderAmount,'.') + 0}%;">
                            ${fn:substringBefore(100*userReport.delayedAmount/userReport.totalOrderAmount,'.') + 0}%
                        </div>
                        <div class="chart-label-KPI">
                            Opóźnione zamówienia / Total zamówień (${userReport.delayedAmount} / ${userReport.totalOrderAmount})
                        </div>

                        <div class="chart-post-KPI" style="width: ${fn:substringBefore(100*userReport.deletedAmount/userReport.totalOrderAmount,'.') + 0 }%;">
                            ${fn:substringBefore(100*userReport.deletedAmount/userReport.totalOrderAmount,'.') + 0}%
                        </div>
                        <div class="chart-label-KPI">
                            Anulowane zamówienia / Total zamówień (${userReport.deletedAmount} / ${userReport.totalOrderAmount})
                        </div>

                    </div>

                    <div style="width: 100%; height: 50px; border-top: 1px solid black;">
                        Podsumowanie KPI
                    </div>

                </div>
            </div>

            <div style="height: 1em;"></div>
        </div>
        <div style="height: 1em;"></div>
    </div>

    <div class="main-container" id="adeccoLocationReport" style="margin-bottom: 20px !important;">
        <div class="chapter-title">Podsumwanie działań lokalizacji</div>

        <div class="chapter-border">
            <div class="chart-title" style="height: auto; border-bottom: none">
                <form:form modelAttribute="rangesForReport" action="/report" method="post" style="width: 90%; margin-left: auto; margin-right: auto; margin-bottom: 0;">
                    <div style="text-align: center; font-size: 24px;">
                        Wprowadź zakres raportu
                    </div>

                    <div style="margin: 10px 0; background: white; padding: 15px; border-radius: 10px;">
                        <table style="border: none; width: 50%; margin: 0 auto 10px;">
                            <tr>
                                <td style="width: 33%;"> Lokalizacja: </td>
                                <td style="width: 67%;">
                                    <form:select cssClass="input-information" path="location">
                                        <c:forEach items="${rangesForReport.userLocationList}" var="userLoc">
                                            <form:option value="${userLoc.id}">${userLoc.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>
                        </table>
                        <table style="border: none">
                            <tr>
                                <td style="width: 50px;"> od: </td>
                                <td style="width: 150px; padding: 0 10px;"> <input type="date" name="formLocationRangeFrom" class="input-information" title="data od" value="${rangesForReport.locationRangeFrom}" min="<%=LocalDate.now().minusYears(1)%>" max="<%=LocalDate.now()%>" required/> </td>
                                <td style="width: 50px;"> do: </td>
                                <td style="width: 150px; padding: 0 10px;"> <input type="date" name="formLocationRangeTo" class="input-information" title="data do" value="${rangesForReport.locationRangeTo}" min="<%=LocalDate.now().minusYears(1)%>" max="<%=LocalDate.now()%>" required/> </td>
                            </tr>
                        </table>
                        <table style="border: none">
                            <tr>
                                <td style="width: 100%;"> <input type="submit" name="submitLocationReportRange" value="Zatwierdź zakres" style="border-radius: 1em; background-color: rgb(239, 3, 0); "> </td>
                            </tr>
                        </table>
                    </div>
                </form:form>
            </div>
        </div>

        <div class="chapter-title"> Raport dla lokalizacji: ${rangesForReport.location}</div>

        <div style="width: 20%; margin: 0 auto; height: 3em;">
            <input type="button" id="location-report-button" value="Pokaż" class="input-information" onclick="showAndHide(this)">
        </div>
        <div id="location-report" style="display: none">

            <div class="chapter-border">
                <div class="chapter-title"> Realizacja zamówień dla lokalizacji</div>

                <div class="chart-place">

                    <div class="chart-title" style="height: auto;">
                        Ilość zamówień przypisanych do lokalizacji: &nbsp;
                        <input type="text" title="Ilość zamówień" disabled="disabled" value="${locationReport.totalOrderAmount + 0}" class="input-information"
                               style="background: white !important; text-align: center; width: 40px!important; margin-bottom: 0;" />
                    </div>

                    <div style="width: 100%; height: 250px; background: repeating-linear-gradient(to bottom, #cccccc, #cccccc 1px, #fff 1px, #fff 10%);">
                        <div class="adecco-chart-scale">
                            <div id="waiting-label-loc" class="chart-label" style="height: ${fn:substringBefore(250*(1 - locationReport.waitingAmount/(locationReport.totalOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="waiting-post-loc" class="chart-post" style="height: ${fn:substringBefore(250*(locationReport.waitingAmount/locationReport.totalOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((locationReport.waitingAmount*100/locationReport.totalOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="accepted-label-loc" class="chart-label" style="height: ${fn:substringBefore(250*(1-locationReport.acceptedAmount/(locationReport.totalOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="accepted-post-loc" class="chart-post" style="height: ${fn:substringBefore(250*(locationReport.acceptedAmount/locationReport.totalOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((locationReport.acceptedAmount*100/locationReport.totalOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="realization-label-loc" class="chart-label" style="height: ${fn:substringBefore(250*(1-locationReport.realizationAmount/(locationReport.totalOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="realization-post-loc" class="chart-post" style="height: ${fn:substringBefore(250*(locationReport.realizationAmount/locationReport.totalOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((locationReport.realizationAmount*100/locationReport.totalOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="deleted-label-loc" class="chart-label" style="height: ${fn:substringBefore(250*(1-locationReport.deletedAmount/(locationReport.totalOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="deleted-post-loc" class="chart-post" style="height: ${fn:substringBefore(250*(locationReport.deletedAmount/locationReport.totalOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((locationReport.deletedAmount*100/locationReport.totalOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="delayed-label-loc" class="chart-label" style="height: ${fn:substringBefore(250*(1-locationReport.delayedAmount/(locationReport.totalOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="delayed-post-loc" class="chart-post" style="height: ${fn:substringBefore(250*(locationReport.delayedAmount/locationReport.totalOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((locationReport.delayedAmount*100/locationReport.totalOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="realized-label-loc" class="chart-label" style="height: ${fn:substringBefore(250*(1-locationReport.realizedAmount/(locationReport.totalOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="realized-post-loc" class="chart-post" style="height: ${fn:substringBefore(250*(locationReport.realizedAmount/locationReport.totalOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((locationReport.realizedAmount*100/locationReport.totalOrderAmount),'.')}%
                            </div>
                        </div>
                    </div>

                    <div style="width: 100%; height: 50px; border-top: 1px solid black;">
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Oczekuje</div>
                            <div class="chart-label">${locationReport.waitingAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Zatwierdzone</div>
                            <div class="chart-label">${locationReport.acceptedAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Realizacja</div>
                            <div class="chart-label">${locationReport.realizationAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Anulowano</div>
                            <div class="chart-label">${locationReport.deletedAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Opóźnione</div>
                            <div class="chart-label">${locationReport.delayedAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Zrealizowane</div>
                            <div class="chart-label">${locationReport.realizedAmount}</div>
                        </div>
                    </div>

                </div>
            </div>

            <div class="chapter-border">

                <div class="chapter-title"> Nowe zamówienia lokalizacji </div>

                <div class="chart-place" style="height: auto;">

                    <div class="chart-title">
                        Wykaz pracowników składających zamówienia dla wybranej lokalizacji
                    </div>

                    <div class="candidateSumUpTable">
                        <c:if test="${empty locationReport.orderSupplierReportList}">
                            <div class="chapter-border" style="margin-top: 1em;">
                                <div class="chapter-title" style="color: grey;"> Brak wyników</div>
                                <div style="text-align: center;">Wprowadź nowych kandydatów aby zobaczyć wynink.</div>
                            </div>
                            <div style="height: 0.1em;"></div>
                        </c:if>
                        <c:if test="${not empty locationReport.orderSupplierReportList}">
                            <table>
                                <tr>
                                    <th>Wprowadzający</th>
                                    <th>Organizacja</th>
                                    <th>Stanowisko</th>
                                    <th>Ilość zamówień</th>
                                </tr>
                                <c:forEach items="${locationReport.orderSupplierReportList.values()}" var="supplier">
                                    <tr>
                                        <td>${supplier.mail}</td>
                                        <td>${supplier.organization}</td>
                                        <td>${supplier.role}</td>
                                        <td>${supplier.ordersAmount} | ${fn:substringBefore((supplier.ordersAmount * 100 / locationReport.totalOrderAmount),'.') + 0}%</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>

                </div>
            </div>

            <c:if test="${!user.role.equals('client')}">
                <div class="chapter-border">

                    <div class="chart-place" style="height: auto;">

                        <div class="chart-title">
                            Poziom obsługi zamówień
                        </div>

                        <div class="candidateSumUpTable">
                            <table>
                                <tr>
                                    <th>Zamówieni pracownicy</th>
                                    <th>Zaakceptowana ilość</th>
                                    <th>Dostarczona ilość</th>
                                    <th>Do zrealizowania (otwarte) </th>
                                    <th>Niezrealizowane (zamknięte)</th>
                                </tr>
                                <tr>
                                    <td>${locationReport.orderedCandidateAmount + 0}</td>
                                    <td>${locationReport.acceptedCandidateAmount + 0}</td>
                                    <td>${locationReport.deliveredCandidateAmount + 0}</td>
                                    <td>${locationReport.waitingCandidateAmount + 0}</td>
                                    <td>${locationReport.unaccomplishCandidateAmount + 0}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </c:if>

            <div style="height: 1em;"></div>
        </div>
        <div style="height: 1em;"></div>
    </div>

    <div class="main-container" id="adeccoStaffReport" style="margin-bottom: 20px !important; <c:if test="${not user.role.equals('manager')}">display:none;</c:if>">

        <div class="chapter-title">Podsumwanie działań pracownika</div>

        <div class="chapter-border">
            <div class="chart-title" style="height: auto; border-bottom: none">
                <form:form modelAttribute="rangesForReport" action="/report" method="post" cssStyle="width: 90%; margin-left: auto; margin-right: auto; margin-bottom: 0;">
                    <div style="text-align: center; font-size: 24px;">
                        Wprowadź zakres raportu
                    </div>

                    <div style="margin: 10px 0; background: white; padding: 15px; border-radius: 10px;">
                        <table style="border: none; width: 50%; margin: 0 auto 10px;">
                            <tr>
                                <td style="width: 30%;"> Pracownik: </td>
                                <td style="width: 70%;">
                                    <form:select cssClass="input-information" path="staffUser">
                                        <c:forEach items="${rangesForReport.userStaffList}" var="staffMail">
                                            <form:option value="${staffMail}">${staffMail}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>
                        </table>
                        <table style="border: none">
                            <tr>
                                <td style="width: 50px;"> od: </td>
                                <td style="width: 150px; padding: 0 10px;"> <input type="date" name="formStaffRangeFrom" class="input-information" title="data od" value="${rangesForReport.staffRangeFrom}" min="<%=LocalDate.now().minusYears(1)%>" max="<%=LocalDate.now()%>" required/> </td>
                                <td style="width: 50px;"> do: </td>
                                <td style="width: 150px; padding: 0 10px;"> <input type="date" name="formStaffRangeTo" class="input-information" title="data do" value="${rangesForReport.staffRangeTo}" min="<%=LocalDate.now().minusYears(1)%>" max="<%=LocalDate.now()%>" required/> </td>
                            </tr>
                        </table>
                        <table style="border: none">
                            <tr>
                                <td style="width: 100%;"> <input type="submit" name="submitStaffReportRange" value="Zatwierdź zakres" style="border-radius: 1em; background-color: rgb(239, 3, 0); "> </td>
                            </tr>
                        </table>
                    </div>
                </form:form>
            </div>
        </div>

        <div class="chapter-title"> Raport dla użytkownika: ${rangesForReport.staffUser}</div>

        <div style="width: 20%; margin: 0 auto; height: 3em;">
            <input type="button" id="staff-report-button" value="Pokaż" class="input-information" onclick="showAndHide(this)">
        </div>
        <div id="staff-report" style="display: none">

            <div class="chapter-border">
                <div class="chapter-title"> Realizacja zadań Managerskich </div>

                <div class="chart-place">

                    <div class="chart-title" style="height: auto;">
                        Ilość zamówień przypisanych do użytkownika: &nbsp;
                        <input type="text" title="Ilość zamówień" disabled="disabled" value="${staffUserReport.userOrderAmount}" class="input-information"
                               style="background: white !important; text-align: center; width: 40px!important; margin-bottom: 0;" />
                    </div>

                    <div style="width: 100%; height: 250px; background: repeating-linear-gradient(to bottom, #cccccc, #cccccc 1px, #fff 1px, #fff 10%);">
                        <div class="adecco-chart-scale">
                            <div id="waiting-label-staff" class="chart-label" style="height: ${fn:substringBefore(250*(1 - staffUserReport.waitingAmount/(staffUserReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="waiting-post-staff" class="chart-post" style="height: ${fn:substringBefore(250*(staffUserReport.waitingAmount/staffUserReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((staffUserReport.waitingAmount*100/staffUserReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="accepted-label-staff" class="chart-label" style="height: ${fn:substringBefore(250*(1-staffUserReport.acceptedAmount/(staffUserReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="accepted-post-staff" class="chart-post" style="height: ${fn:substringBefore(250*(staffUserReport.acceptedAmount/staffUserReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((staffUserReport.acceptedAmount*100/staffUserReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="realization-label-staff" class="chart-label" style="height: ${fn:substringBefore(250*(1-staffUserReport.realizationAmount/(staffUserReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="realization-post-staff" class="chart-post" style="height: ${fn:substringBefore(250*(staffUserReport.realizationAmount/staffUserReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((staffUserReport.realizationAmount*100/staffUserReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="deleted-label-staff" class="chart-label" style="height: ${fn:substringBefore(250*(1-staffUserReport.deletedAmount/(staffUserReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="deleted-post-staff" class="chart-post" style="height: ${fn:substringBefore(250*(staffUserReport.deletedAmount/staffUserReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((staffUserReport.deletedAmount*100/staffUserReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="delayed-label-staff" class="chart-label" style="height: ${fn:substringBefore(250*(1-staffUserReport.delayedAmount/(staffUserReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="delayed-post-staff" class="chart-post" style="height: ${fn:substringBefore(250*(staffUserReport.delayedAmount/staffUserReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((staffUserReport.delayedAmount*100/staffUserReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                        <div class="adecco-chart-scale">
                            <div id="realized-label-staff" class="chart-label" style="height:  ${fn:substringBefore(250*(1-staffUserReport.realizedAmount/(staffUserReport.userOrderAmount + 0.0001)),'.')}px; width: 0;"></div>
                            <div id="realized-post-staff" class="chart-post" style="height:  ${fn:substringBefore(250*(staffUserReport.realizedAmount/staffUserReport.userOrderAmount),'.') + 0 }px;">
                                ${fn:substringBefore((staffUserReport.realizedAmount*100/staffUserReport.userOrderAmount),'.')}%
                            </div>
                        </div>
                    </div>

                    <div style="width: 100%; height: 50px; border-top: 1px solid black;">
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Oczekuje</div>
                            <div class="chart-label">${staffUserReport.waitingAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Zatwierdzone</div>
                            <div class="chart-label">${staffUserReport.acceptedAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Realizacja</div>
                            <div class="chart-label">${staffUserReport.realizationAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Anulowano</div>
                            <div class="chart-label">${staffUserReport.deletedAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Opóźnione</div>
                            <div class="chart-label">${staffUserReport.delayedAmount}</div>
                        </div>
                        <div class="adecco-chart-scale" style="height: 50px;">
                            <div class="chart-label">Zrealizowane</div>
                            <div class="chart-label">${staffUserReport.realizedAmount}</div>
                        </div>
                    </div>

                </div>
            </div>

            <div class="chapter-border">

                <div class="chapter-title"> Realizacja zamówień / lokalizacja </div>

                <div class="chart-place" style="height: auto;">

                    <div class="chart-title">
                        Poziom realizacji zamówień w danym okresie
                    </div>

                    <div class="candidateSumUpTable">
                        <c:if test="${empty staffUserReport.candidateRealizationList}">
                            <div class="chapter-border" style="margin-top: 1em;">
                                <div class="chapter-title" style="color: grey;"> Brak wyników</div>
                                <div style="text-align: center;">Wprowadź nowych kandydatów aby zobaczyć wynink.</div>
                            </div>
                            <div style="height: 0.1em;"></div>
                        </c:if>
                        <c:if test="${not empty staffUserReport.candidateRealizationList}">
                            <table>
                                <tr>
                                    <th>Organizacja</th>
                                    <th>Lokalizacja</th>
                                    <th>Zamówiono</th>
                                    <th>Zatwierdzono</th>
                                    <th>Zrealizowano</th>
                                </tr>
                                <c:forEach items="${staffUserReport.candidateRealizationList.values()}" var="locRep">
                                    <tr>
                                        <td>${locRep.organization}</td>
                                        <td>${locRep.localization}</td>
                                        <td>${locRep.orderedCandidatesAmount}</td>
                                        <td>${locRep.acceptedCandidatesAmount}</td>
                                        <td>${locRep.realizedCandidatesAmount} | ${fn:substringBefore((locRep.realizedCandidatesAmount * 100 / locRep.orderedCandidatesAmount),'.') + 0}%</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>

                </div>
            </div>

            <div class="chapter-border">

                <div class="chart-place" style="height: auto;">

                    <div class="chart-title">
                        Realizacja KPI użytkownia
                    </div>

                    <div style="padding: 10px 0; background: repeating-linear-gradient(to right, #ddd, #ddd 2px, #fff 2px, #fff 10%);">

                        <div class="chart-post-KPI" style="width: ${fn:substringBefore(100*staffUserReport.userOrderAmount/staffUserReport.totalOrderAmount,'.') + 0}%;">
                            ${fn:substringBefore(100*staffUserReport.userOrderAmount/staffUserReport.totalOrderAmount,'.') + 0}%
                        </div>
                        <div class="chart-label-KPI">
                            Zamówienia przypisane do użytkownika / Total zamówień (${staffUserReport.userOrderAmount} / ${staffUserReport.totalOrderAmount})
                        </div>

                        <div class="chart-post-KPI" style="width: ${fn:substringBefore(100*staffUserReport.delayedAmount/staffUserReport.totalOrderAmount,'.') + 0}%;">
                            ${fn:substringBefore(100*staffUserReport.delayedAmount/staffUserReport.totalOrderAmount,'.') + 0}%
                        </div>
                        <div class="chart-label-KPI">
                            Opóźnione zamówienia / Total zamówień (${staffUserReport.delayedAmount} / ${staffUserReport.totalOrderAmount})
                        </div>

                        <div class="chart-post-KPI" style="width: ${fn:substringBefore(100*staffUserReport.deletedAmount/staffUserReport.totalOrderAmount,'.') + 0 }%;">
                            ${fn:substringBefore(100*staffUserReport.deletedAmount/staffUserReport.totalOrderAmount,'.') + 0}%
                        </div>
                        <div class="chart-label-KPI">
                            Anulowane zamówienia / Total zamówień (${staffUserReport.deletedAmount} / ${staffUserReport.totalOrderAmount})
                        </div>

                    </div>

                    <div style="width: 100%; height: 50px; border-top: 1px solid black;">
                        Podsumowanie KPI
                    </div>

                </div>
            </div>

            <div style="height: 1em;"></div>
        </div>
        <div style="height: 1em;"></div>
    </div>

    <div class="main-container" style="height: auto; padding-bottom: 1em;">
        <div style="height: auto; width: 30%; margin: 0 auto;">
            <form:form action="/menu" method="post" cssStyle="align-content: center; margin: 0;">
                <input type="submit" value="Powrót do menu" style="float: none; margin: 0; width: 100%;">
            </form:form>
        </div>

    </div>

    <%@include file="../../resources/htmlElements/footer.html" %>

    <script type="text/javascript">
        function showAndHide(clickedId)
        {
            if(clickedId.id === "user-report-button"){
                if(document.getElementById("user-report").style.display === 'none')
                    document.getElementById("user-report").style.display = 'block';
                else
                    document.getElementById("user-report").style.display = 'none';
            }

            if(clickedId.id === "location-report-button"){
                if(document.getElementById("location-report").style.display === 'none')
                    document.getElementById("location-report").style.display = 'block';
                else
                    document.getElementById("location-report").style.display = 'none';
            }

            if(clickedId.id === "staff-report-button"){
                if(document.getElementById("staff-report").style.display === 'none')
                    document.getElementById("staff-report").style.display = 'block';
                else
                    document.getElementById("staff-report").style.display = 'none';
            }

            if(document.getElementById(clickedId.id).getAttribute("value") === 'Pokaż')
                document.getElementById(clickedId.id).setAttribute("value","Schowaj");
            else
                document.getElementById(clickedId.id).setAttribute("value","Pokaż");
        }
    </script>

</body>
</html>
