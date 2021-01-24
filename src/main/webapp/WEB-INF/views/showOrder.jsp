<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page pageEncoding="utf-8" %>

<%--@elvariable id="user" type="com.bz.hos.model.UserAndLocationEntity.User"--%>
<%--@elvariable id="contract" type="com.bz.hos.model.CandidateEntity.Contract"--%>

<html>
<head>
    <title>Zamowienie: ${order.id}</title>
    <link href="<spring:url value="/resources/css/list/order.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/fontello/css/fontello.css"/>" rel="stylesheet" type="text/css"/>

    <meta charset="utf-8">

    <style>
        form{
            margin-bottom: 0;
        }
    </style>

</head>
<body>

    <%@include file="../../META-INF/resources/htmlElements/header.html" %>

    <div id="mainContainer" class="main-container" disabled="true">

        <div class="chapter-border">
            <div id="order-id" class="chapter-title">
                Zamówienie ID: <span style="color: #000;"> ${order.id}</span>
            </div>
            <c:if test="${!user.role.equals('client')}">
                <div style="text-align: center;">
                    <c:if test="${order.alerted}"><span style="color: red; font-weight: bold;"> Brak pełnej weryfikacji zamówienia | </span></c:if>
                    Ostatnia weryfikacja:
                        <c:if test="${order.alerted}"><span style=" font-weight: bold; color: red;"> ${order.updateDate} </span></c:if>
                        <c:if test="${not order.alerted}"><span style=" font-weight: bold; color: black;"> ${order.updateDate} </span></c:if>
                </div>
            </c:if>
        </div>

        <div class="chapter-title">Specyfikacja zamówienia</div>
        <div class="chapter-border">

            <div class="chapter-border-line-half">
                <div class="chapter-border-tile" style="width: 150px;">Data zamówienia:</div>
                <div class="chapter-border-tile-data">${order.orderDate}</div>
            </div>
            <div class="chapter-border-line-half">
                <div class="chapter-border-tile" style="width: 120px;">Lokalizacja:</div>
                <div class="chapter-border-tile-data">${order.location}</div>
            </div>
            <div style="clear:both"></div>

            <form method="post" action="${pageContext.request.contextPath}/showOrder">
                <div class="chapter-border-line-half">
                    <div class="chapter-border-tile" style="width: 150px; margin-top: 5px;">Data realizacji:</div>
                    <div class="chapter-border-tile-data">

                        <c:if test="${(user.role.equals('manager') or user.admin) and !(order.orderStatus.equals('Zrealizowane') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Odrzucone'))}">
                            <input type="date" class="input-information" value="${order.realizationDate}" title="Data realizacji" min="${order.orderDate}" style="width: 200px; margin: 0;" name="realizationDate">
                        </c:if>
                        <c:if test="${(!user.role.equals('manager') and !user.admin) or (order.orderStatus.equals('Zrealizowane') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Odrzucone'))}">
                            <div style="margin-top: 5px;">${order.realizationDate}</div>
                            <input type="hidden" name="realizationDate" value="${order.realizationDate}">
                        </c:if>

                    </div>
                </div>
                <div class="chapter-border-line-half">
                    <div class="chapter-border-tile" style="width: 120px; margin-top: 5px;">Status:</div>
                    <div class="chapter-border-tile-data">

                        <c:if test="${user.admin}">
                            <select class="input-information" title="Status zamówienia" style="width: 200px; margin: 0;" name="orderStatus">
                                <option value="Oczekuje" <c:if test="${order.orderStatus.equals('Oczekuje')}">selected="selected"</c:if> > Oczekuje </option>
                                <option value="Zatwierdzone" <c:if test="${order.orderStatus.equals('Zatwierdzone')}">selected="selected"</c:if> > Zatwierdzone </option>
                                <option value="Odrzucone" <c:if test="${order.orderStatus.equals('Odrzucone')}">selected="selected"</c:if> > Odrzucone </option>
                                <option value="Realizacja" <c:if test="${order.orderStatus.equals('Realizacja')}">selected="selected"</c:if> > Realizacja </option>
                                <option value="Anulowane" <c:if test="${order.orderStatus.equals('Anulowane')}">selected="selected"</c:if> > Anulowane </option>
                                <option value="Zrealizowane" <c:if test="${order.orderStatus.equals('Zrealizowane')}">selected="selected"</c:if> > Zrealizowane </option>
                            </select>
                        </c:if>
                        <c:if test="${user.role == 'manager' and !user.admin and !(order.orderStatus.equals('Zrealizowane') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Odrzucone'))}">
                            <select class="input-information" title="Status zamówienia" style="width: 200px;" name="orderStatus">
                                <c:if test="${order.orderStatus.equals('Oczekuje')}">
                                    <option value="Oczekuje" selected="selected"> Oczekuje </option>
                                    <option value="Zatwierdzone"> Zatwierdzone </option>
                                    <option value="Odrzucone"> Odrzucone </option>
                                </c:if>
                                <c:if test="${order.orderStatus.equals('Zatwierdzone')}">
                                    <option value="Zatwierdzone" selected="selected"> Zatwierdzone </option>
                                    <option value="Realizacja"> W realizacji </option>
                                    <option value="Zrealizowane"> Zrealizowane </option>
                                    <option value="Anulowane"> Anulowane</option>
                                </c:if>
                                <c:if test="${order.orderStatus.equals('Realizacja')}">
                                    <option value="Zatwierdzone"> Zatwierdzone </option>
                                    <option value="Realizacja" selected="selected"> W realizacji </option>
                                    <option value="Zrealizowane"> Zrealizowane </option>
                                    <option value="Anulowane"> Anulowane</option>
                                </c:if>
                                <c:if test="${order.orderStatus.equals('Zrealizowane')}">
                                    <option value="Zrealizowane" selected="selected"> Zrealizowane</option>
                                </c:if>
                                <c:if test="${order.orderStatus.equals('Anulowane')}">
                                    <option value="Anulowane" selected="selected"> Anulowane</option>
                                </c:if>
                                <c:if test="${order.orderStatus.equals('Odrzucone')}">
                                    <option value="Odrzucone" selected="selected"> Odrzucone </option>
                                </c:if>
                            </select>
                        </c:if>
                        <c:if test="${!user.admin and
                         (order.orderStatus.equals('Zrealizowane') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Odrzucone'))}">
                            <div style="margin-top: 5px;">${order.orderStatus}</div>
                        </c:if>
                        <c:if test="${user.role.equals('client') or user.role.equals('recruiter')}">
                            <div style="margin-top: 5px;">${order.orderStatus}</div>
                        </c:if>
                    </div>
                </div>
                <div style="clear:both"></div>

                <div class="chapter-border-line-half">
                    <div class="chapter-border-tile" style="width: 190px;">Kategoria zamówienia:</div>
                    <div class="chapter-border-tile-data">
                        <c:if test="${order.orderCategory=='ZAM'}">
                            Nowe zamówienie
                        </c:if>
                        <c:if test="${order.orderCategory=='REP'}">
                            Replacement
                        </c:if>
                    </div>
                </div>

                <div style="margin: 1em 1em; height: 1em;">
                    <c:if test="${(user.role.equals('manager') and !(order.orderStatus.equals('Zrealizowane') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Odrzucone')) ) or user.admin}">
                        <input type="submit" value="zapisz" style="width: 100px;" name="setSpecificationChange">
                        <input type="hidden" value="${order.id}" name="orderId">
                    </c:if>
                </div>

            </form>
        </div>

        <div class="chapter-title">Akredytacja</div>
        <div class="chapter-border">

            <div class="chapter-border-line-half">
                <div class="chapter-border-tile" style="width: 120px;">Organizacja:</div>
                <div class="chapter-border-tile-data"> ${order.organization} </div>
            </div>
            <div class="chapter-border-line-half">
                <div class="chapter-border-tile" style="width: 120px;">Region:</div>
                <div class="chapter-border-tile-data">
                    <c:if test="${order.area.equals('N')}">Północ</c:if>
                    <c:if test="${order.area.equals('S')}">Południe</c:if>
                    <c:if test="${order.area.equals('W')}">Zachód</c:if>
                    <c:if test="${order.area.equals('E')}">Wschód</c:if>
                </div>
            </div>
            <div style="clear:both"></div>

            <div class="chapter-border-line-half">
                <div class="chapter-border-tile" style="width: 120px;">Wprowadzający:</div>
                <div class="chapter-border-tile-data">${order.supplier}</div>
            </div>
            <div class="chapter-border-line-half">
                <div class="chapter-border-tile" style="width: 120px;">Manager:</div>
                <div class="chapter-border-tile-data">${order.manager}</div>
            </div>
            <div style="clear:both"></div>

            <form method="post" action="${pageContext.request.contextPath}/showOrder">

                <div class="chapter-border-line-half">
                    <div class="chapter-border-tile" style="width: 165px; margin-top: 5px;">Kierownik Obszaru:</div>
                    <div class="chapter-border-tile-data"> <div style="margin-top: 5px;">${order.departmentManager}</div></div>
                </div>
                <div class="chapter-border-line-half">
                    <div class="chapter-border-tile" style="width: 120px; margin-top: 5px;">Obszar:</div>
                    <div class="chapter-border-tile-data">

                        <c:if test="${(user.role.equals('manager') or user.admin) and !(order.orderStatus.equals('Zrealizowane') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Odrzucone'))}">
                            <select class="input-information" title="Obszar" style="width: 200px; margin: 0;" name="department">
                                <c:forEach items="${user.locationList.values()}" var="loc">
                                    <c:if test="${loc.id.equals(order.locationId)}">
                                        <c:forEach items="${loc.department.values()}" var="dep">
                                            <option value="${dep}" <c:if test="${order.department.equals(dep)}"> selected="selected" </c:if> > ${dep} </option>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </c:if>
                        <c:if test="${(!user.role.equals('manager') and !user.admin) or (order.orderStatus.equals('Zrealizowane') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Odrzucone'))}">
                            <div style="margin-top: 5px;">${order.department}</div>
                        </c:if>

                    </div>
                </div>
                <div style="clear:both"></div>

                <div class="chapter-border-line-half">
                    <div class="chapter-border-tile" style="width: 120px; margin-top: 5px;">Opiekun:</div>
                    <div class="chapter-border-tile-data">

                        <c:if test="${(user.role.equals('manager') or user.admin) and !(order.orderStatus.equals('Zrealizowane') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Odrzucone'))}">
                            <select class="input-information" title="Opiekun" style="width: 200px; margin: 0;" name="assignWorker">
                                <c:forEach var="staff" items="${user.staffList.values()}">
                                    <option value="${staff}" <c:if test="${order.assignWorker.equals(staff)}">selected="selected"</c:if> > ${staff} </option>
                                </c:forEach>
                            </select>
                        </c:if>
                        <c:if test="${(!user.role.equals('manager') and !user.admin) or (order.orderStatus.equals('Zrealizowane') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Odrzucone'))}">
                            <div style="margin-top: 5px;">${order.assignWorker}</div>
                        </c:if>

                    </div>
                </div>

                <div style="margin: 1em 1em; height: 1em;">
                    <c:if test="${(user.role.equals('manager') or user.admin) and !(order.orderStatus.equals('Zrealizowane') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Odrzucone'))}">
                        <input type="submit" value="zapisz" style="width: 100px;" name="setAccreditationChange">
                        <input type="hidden" value="${order.id}" name="orderId">
                    </c:if>
                </div>

            </form>

        </div>

        <div class="chapter-title">Zamawiani kandydaci</div>
        <c:forEach var="record" items="${order.recordList.values()}">
            <div class="chapter-border">

                <div class="record-line" style="height: 3em;">
                    <div class="record-title chapter-title" style="padding-bottom: 5px;">
                        ${record.jobName}
                    </div>
                    <c:if test="${!user.role.equals('client')}">
                        <div style="text-align: center;">
                            <c:if test="${record.alerted}"><span style="color: red; font-weight: bold;"> Brak weryfikacji w ostanich 7 dniach | </span></c:if>
                            Ostatnia weryfikacja:
                            <c:if test="${record.alerted}"><span style=" font-weight: bold; color: red;"> ${record.updateDate} </span></c:if>
                            <c:if test="${not record.alerted}"><span style=" font-weight: bold; color: black;"> ${record.updateDate} </span></c:if>
                        </div>
                    </c:if>
                </div>

                <div class="record-line" style="height: 4.5em;">
                    <form method="post" action="${pageContext.request.contextPath}/showOrder">
                        <table class="account">
                            <tr>
                                <th>Ilość zamówiona</th>
                                <th>Ilość zatwierdzona</th>
                                <th>Ilość zrealizowana</th>
                                <th>Status</th>
                                <c:if test="${user.admin or
                                (user.role.equals('manager') and !record.status.equals('Zrealizowane')) or
                                (user.role.equals('client') and record.status.equals('Oczekuje'))}">
                                    <th>Akcja</th>
                                </c:if>
                            </tr>
                            <tr>
                                <%--Ordered Amount--%>
                                <c:if test="${(user.role.equals('client') and !record.status.equals('Oczekuje') or
                                (order.orderStatus.equals('Odrzucone') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Zrealizowane'))) or
                                !(user.role.equals('client'))}">
                                    <td>
                                        ${record.accountOrdered}
                                    </td>
                                </c:if>
                                <c:if test="${user.role.equals('client') and
                                (record.status.equals('Oczekuje')) and
                                !(order.orderStatus.equals('Odrzucone') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Zrealizowane'))}">
                                    <td>
                                        <select title="Ilość zamówionych kandydatów" class="input-information" name="accountOrdered">
                                            <c:forEach var="i" begin="${record.accountAccepted}" end="2000">
                                                <option value="${i}" <c:if test="${record.accountOrdered.equals(i)}">selected="selected"</c:if> > ${i} </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </c:if>

                                <%--Accepted Amount--%>
                                <c:if test="${((user.role.equals('manager') or user.admin) and !record.status.equals('W realizacji')) or
                                (!user.role.equals('manager'))}">
                                    <td>
                                        ${record.accountAccepted}
                                    </td>
                                </c:if>
                                <c:if test="${(user.role.equals('manager') or user.admin) and record.status.equals('W realizacji')}">
                                    <td>
                                        <select class="input-information" name="accountAccepted" title="Zatwierdzona przez HOS ilość">
                                            <c:forEach var="i" begin="${record.accountDelivered}" end="${record.accountOrdered}">
                                                <option value="${i}" <c:if test="${record.accountAccepted.equals(i)}">selected="selected"</c:if> > ${i} </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </c:if>

                                <td>
                                    ${record.accountDelivered}
                                </td>

                                <%--Record Status--%>
                                <c:if test="${(user.role.equals('client') or user.role.equals('recruiter') and !user.admin) or
                                (order.orderStatus.equals('Oczekuje') or order.orderStatus.equals('Odrzucone') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Zrealizowane')) or
                                (user.role.equals('manager') and !user.admin and record.status.equals('Zrealizowane'))}">
                                    <td>
                                        ${record.status}
                                    </td>
                                </c:if>
                                <c:if test="${user.role.equals('client') and record.status.equals('Oczekuje') and
                                !(order.orderStatus.equals('Odrzucone') or order.orderStatus.equals('Anulowane') or order.orderStatus.equals('Zrealizowane'))}">
                                    <td>
                                        <input type="submit" value="zapisz" style="width: 100px; float: none;" name="setNewRecordChange">
                                        <input type="hidden" value="${record.recordId}" name="recordId">
                                        <input type="hidden" value="${order.id}" name="orderId">
                                    </td>
                                </c:if>
                                <c:if test="${(user.role.equals('manager') and !user.admin) and
                                ( order.orderStatus.equals('Zatwierdzone') or order.orderStatus.equals('Realizacja')) and
                                !record.status.equals('Zrealizowane')}">
                                    <td>
                                        <select style="width:80%" title="Status realizacji" class="input-information" name="recordStatus">
                                            <c:if test="${record.status.equals('Oczekuje')}">
                                                <option value="Oczekuje" selected="selected"> Oczekuje </option>
                                                <option value="W realizacji"> W realizacji </option>
                                                <option value="Anulowane"> Anulowane</option>
                                            </c:if>
                                            <c:if test="${record.status.equals('W realizacji')}">
                                                <option value="W realizacji" selected="selected"> W realizacji </option>
                                                <option value="Zrealizowane"> Zrealizowane </option>
                                                <option value="Anulowane"> Anulowane</option>
                                            </c:if>
                                            <c:if test="${record.status.equals('Anulowane')}">
                                                <option value="W realizacji"> W realizacji </option>
                                                <option value="Anulowane" selected="selected"> Anulowane </option>
                                            </c:if>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="submit" value="zapisz" style="width: 100px; float: none;" name="setNewRecordChange">
                                        <input type="hidden" value="${record.recordId}" name="recordId">
                                        <input type="hidden" value="${order.id}" name="orderId">
                                    </td>
                                </c:if>
                                <c:if test="${user.admin and
                                (order.orderStatus.equals('Zatwierdzone') or order.orderStatus.equals('Realizacja'))}">
                                    <td>
                                        <select style="width:80%" title="Status realizacji" class="input-information" name="recordStatus">
                                            <option value="Oczekuje" <c:if test="${record.status.equals('Oczekuje')}">selected="selected"</c:if> > Oczekuje </option>
                                            <option value="W realizacji" <c:if test="${record.status.equals('W realizacji')}">selected="selected"</c:if> > W realizacji </option>
                                            <option value="Zrealizowane" <c:if test="${record.status.equals('Zrealizowane')}">selected="selected"</c:if> > Zrealizowane </option>
                                            <option value="Anulowane" <c:if test="${record.status.equals('Anulowane')}">selected="selected"</c:if> > Anulowane</option>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="submit" value="zapisz" style="width: 100px; float: none;" name="setNewRecordChange">
                                        <input type="hidden" value="${record.recordId}" name="recordId">
                                        <input type="hidden" value="${order.id}" name="orderId">
                                    </td>
                                </c:if>
                            </tr>
                        </table>
                    </form>
                </div>

                <br/>
                <div class="record-line">
                    <div class="record-data">
                        Umowa:<br/>
                        <span style="color: black;">
                            <c:if test="${record.contract.equals('UOPS')}">Umowa o pracę stałą</c:if>
                            <c:if test="${record.contract.equals('ZLEC')}">Umowa zlecenie</c:if>
                            <c:if test="${record.contract.equals('UOPT')}">Umowa o pracę tymczasową</c:if>
                        </span>
                    </div>
                    <div class="record-data">
                        Praca od:<br/>
                        <span style="color: black;">${record.workingFrom}</span>
                    </div>
                    <div class="record-data" style="border: none;">
                        Praca do:<br/>
                        <span style="color: black;">${record.workingTo}</span>
                    </div>
                </div>

                <div class="record-line">
                    <div class="record-data" style="width: 50%">
                        ID zamówionego stanowiska:<br/>
                        <span style="color: black;">${record.recordId}</span>
                    </div>
                    <div class="record-data" style="width: 49%; border-right: none;">
                        Oferowane wynagrodzenie:<br/>
                        <span style="color: black;">${record.paymentValue}zł/${record.paymentType}</span>
                    </div>
                </div>

                <div class="record-data" style="width: 50%">
                    Kwalifikacje:<br/>
                    <span style="color: black;">${record.qualification}</span>
                </div>
                <div class="record-data" style="width: 49%; border-right: none;">
                    Uwagi:<br/>
                    <span style="color: black;">${record.comment}</span>
                </div>
                <div style="clear: both"></div>

                <c:if test="${record.status.equals('W realizacji') and !user.role.equals('client') and (record.accountAccepted > record.accountDelivered)}">
                    <button id="${'add-'.concat(record.recordId)}" class="show-hide-candidate-cart" onclick="showAndHideCandidateCart('${record.recordId}')">
                        Dodaj nowego kandydata +
                    </button>
                    <div id="${'info-'.concat(record.recordId)}" class="info-candidate-cart" style="display: none;">
                        <form:form action="${pageContext.request.contextPath}/showOrder" method="post" modelAttribute="contractForm">

                        <div class="candidateAdvanceChapterName">Dane osobowe</div>
                        <table class="candidateTable">
                            <tr>
                                <th> Imie </th>
                                <th> Nazwisko </th>
                                <th> Telefon </th>
                                <th> Email </th>
                                <th> Zgody </th>
                            </tr>
                            <tr style="font-size: 16px;">
                                <td><input type="text" name="candidateName" title="Imię kandydata" required="required"/></td>
                                <td><input type="text" name="candidateSurname" title="Nazwisko kandydata" required="required"/></td>
                                <td><input type="number" name="candidatePhone" title="Telefon kandydata" required="required"/></td>
                                <td><input type="email" name="candidateEmail" title="Email kandydata"/></td>
                                <td><input type="checkbox" name="candidateApproval" title="Czy kandydat wyraził zgody na przetwarzanie danych?"/></td>
                            </tr>
                        </table>

                        <div class="candidateAdvanceChapterName">Dane do umowy</div>
                        <table class="candidateTable">
                            <tr>
                                <th> Pesel </th>
                                <th> Umowa od </th>
                                <th> Umowa do </th>
                                <th> Wynagrodzenie </th>
                                <th> Typ pensji </th>
                            </tr>
                            <tr style="font-size: 16px;">
                                <td><input type="text" name="candidateId" title="Podaj pesel kandydata"/></td>
                                <td><input type="date" name="candidateWorkFrom" title="Umowa do" /></td>
                                <td><input type="date" name="candidateWorkTo" title="Umowa do" /></td>
                                <td><input type="number" name="candidateSalary" title="Wynagrodzenie kandydata" min="1"/></td>
                                <td>
                                    <input style="height: 1em; width:1em;" type="radio" name="candidateSalaryType" title="Wynagrodzenie kandydata" value="h" checked="checked"/>na godzinę<br/>
                                    <input style="height: 1em; width:1em;" type="radio" name="candidateSalaryType" title="Wynagrodzenie kandydata" value="m"/>miesięcznie
                                </td>
                            </tr>
                        </table>

                        <div class="candidateAdvanceChapterName">Dane szczegółowe</div>
                        <input type="button" id="${'show-hide-advance-'.concat(record.recordId)}" class="show-hide-advance" style="float: none;"
                               onclick="showAndHideAdvanceCandidateCart('${record.recordId}')" value="Rozwiń +"/>
                        <div id="${'advance-'.concat(record.recordId)}" class="advance-candidate-cart" style="display: none;">

                            <div class="candidateAdvanceChapterName">Rozliczenia</div>
                            <div class="record-line">
                                <div class="record-data">
                                    Konto kosztów:<br>
                                    <input style="border-radius: 0.25em; height: 2em; width: 50%;" type="number" name="candidateCostAccount" title="Konto kosztów" min="0"/>
                                </div>
                                <div class="record-data">
                                    Przełożony:<br>
                                    <input style="border-radius: 0.25em; height: 2em; width: 80%;" type="text" name="candidateManager" title="Imię i nazwisko przełożonego" min="0"/>
                                </div>
                                <div class="record-data" style="border: none">
                                    Tryb pracy:<br>
                                    <input style="border-radius: 0.25em; height: 2em; width: 80%;" type="text" name="candidateWorkTimeType" title="Tryb pracy: zmianowy, itp." minlength="1"/>
                                </div>
                            </div>

                            <div class="candidateAdvanceChapterName">Rozmiar ubrań</div>
                            <div class="record-line">
                                <div class="record-data">
                                    Rozmiar buta:<br>
                                    <input style="border-radius: 0.25em; height: 2em; width: 50%;" type="number" name="candidateShoeSize" title="Numer buta kandydata" min="0"/>
                                </div>
                                <div class="record-data">
                                    Rozmiar pasa:<br>
                                    <input style="border-radius: 0.25em; height: 2em; width: 50%;" type="number" name="candidateWaistSize" title="Rozmiar pasa" min="0"/>
                                </div>
                                <div class="record-data" style="border: none">
                                    Rozmiar góry:<br>
                                    <input type="radio" name="candidateSize" title="S" value="S"/> S |
                                    <input type="radio" name="candidateSize" title="M" value="M"/> M |
                                    <input type="radio" name="candidateSize" title="L" value="L" checked="checked" /> L |
                                    <input type="radio" name="candidateSize" title="XL" value="XL"/> XL
                                </div>
                            </div>

                            <div class="candidateAdvanceChapterName">Uprawnienia</div>
                            <div class="record-line">
                                <div class="record-data" style="width: 20%;">
                                    Wózki widłowe:<br>
                                    <input type="radio" name="candidateForkliftLicense" title="Posiada uprawnienia" value="true"/>Tak |
                                    <input type="radio" name="candidateForkliftLicense" title="Brak uprawnień" value="false"/>Nie |
                                    <input type="radio" name="candidateForkliftLicense" title="Nie dotyczy" value="Niedotyczy" checked="checked"/>-
                                </div>
                                <div class="record-data" style="width: 20%;">
                                    UDT:<br>
                                    <input type="radio" name="candidateUDT" title="Posiada uprawnienia" value="true"/>Tak |
                                    <input type="radio" name="candidateUDT" title="Brak uprawnień" value="false"/>Nie |
                                    <input type="radio" name="candidateUDT" title="Nie dotyczy" value="Niedotyczy" checked="checked"/>-
                                </div>
                                <div class="record-data" style="width: 20%;">
                                    4B:<br>
                                    <input type="radio" name="candidate4B" title="Posiada uprawnienia" value="true"/>Tak |
                                    <input type="radio" name="candidate4B" title="Brak uprawnień" value="false"/>Nie |
                                    <input type="radio" name="candidate4B" title="Nie dotyczy" value="Niedotyczy" checked="checked"/>-
                                </div>
                                <div class="record-data" style="width: 20%;">
                                    SEP:<br>
                                    <input type="radio" name="candidateSEP" title="Posiada uprawnienia" value="true"/>Tak |
                                    <input type="radio" name="candidateSEP" title="Brak uprawnień" value="false"/>Nie |
                                    <input type="radio" name="candidateSEP" title="Nie dotyczy" value="Niedotyczy" checked="checked"/>-
                                </div>
                                <div class="record-data" style="width: 19%; border: none">
                                    Karany:<br>
                                    <input type="radio" name="candidateLaw" title="Karany" value="true"/>Tak |
                                    <input type="radio" name="candidateLaw" title="Nie karany" value="false"/>Nie |
                                    <input type="radio" name="candidateLaw" title="Nie dotyczy" value="Niedotyczy" checked="checked"/>-
                                </div>
                            </div>

                            <div class="candidateAdvanceChapterName">Terminy</div>
                            <div class="record-line" style="border: none;">
                                <div class="record-data" style="width: 50%;">
                                    Wiza do:<br/>
                                    <input class="advance-candidate-date" type="date" name="candidateVisaTerm" title="Data wygaśnięcia wizy"/>
                                </div>
                                <div class="record-data" style="width: 49%; border: none;">
                                    Sanepid do:<br/>
                                    <input class="advance-candidate-date" type="date" name="candidateSanitaryBookTerm" title="Data wygaśnięcia książeczki sanepidu"/>
                                </div>
                            </div>
                        </div>

                        <div style="width: auto; margin: 10px 10px; height: 2.3em;">
                            <input style="font-size: 16px;" type="submit" name="setContract" value="Wprowadź kandydata"/>
                        </div>

                        <input type="hidden" name="orderId" value="${order.id}">
                        <input type="hidden" id="contractId" name="contractId" value="${''.concat(record.recordId).concat('/').concat(record.lastCandidateNo+1)}">
                        <input type="hidden" name="recordId" value="${record.recordId}">
                        <input type="hidden" name="location" value="${order.location}">
                        <input type="hidden" name="departure" value="${order.department}">
                        </form:form>
                    </div>
                </c:if>

                <c:if test="${!user.role.equals('client')}">
                    <button id="${'show-list-'.concat(record.recordId)}" class="show-hide-candidate-cart" onclick="showAndHideCandidateList('${record.recordId}')">
                        Lista dodanych kandydatów +
                    </button>
                    <div id="${'list-'.concat(record.recordId)}" class="info-candidate-cart" style="display: none;">
                        <table class="candidateTable" style="width: 100%;">
                            <thead>
                                <tr>
                                    <th> Id </th>
                                    <th> Imie </th>
                                    <th> Nazwisko </th>
                                    <th> Telefon </th>
                                    <th> Email </th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${record.contractList}" var="contract">
                                <tr>
                                    <td id="contractId-${contract.contractId}">${contract.contractId}</td>
                                    <td id="candidateName-${contract.contractId}">${contract.candidateName}</td>
                                    <td id="candidateSurname-${contract.contractId}">${contract.candidateSurname}</td>
                                    <td id="candidatePhone-${contract.contractId}">${contract.candidatePhone}</td>
                                    <td id="candidateEmail-${contract.contractId}">${contract.candidateEmail}</td>
                                </tr>
                                <tr>
                                    <td colspan="5">
                                        <button id="${'candidate-info-button-'.concat(contract.contractId)}" class="show-hide-advance" style="width: 30%;"
                                                onclick="showAndHideCandidateInfo('${contract.contractId}')">
                                            Rozwiń +
                                        </button>
                                        <div id="${'candidate-info-'.concat(contract.contractId)}" style="display: none;">

                                            <div class="candidateAdvanceChapterName">Dane do umowy</div>
                                            <div class="record-line" style="border-bottom: 1px gray dashed;">
                                                <div class="record-data" style="width: 20%;">
                                                    Pesel:<br>
                                                    <span id="candidateId-${contract.contractId}" style="color: black">${contract.candidateId}</span>
                                                </div>
                                                <div class="record-data" style="width: 20%;">
                                                    Umowa od:<br>
                                                    <span id="candidateWorkFrom-${contract.contractId}" style="color: black">${contract.candidateWorkFrom}</span>
                                                </div>
                                                <div class="record-data" style="width: 20%;">
                                                    Umowa do:<br>
                                                    <span id="candidateWorkTo-${contract.contractId}" style="color: black">${contract.candidateWorkTo}</span>
                                                </div>
                                                <div class="record-data" style="width: 20%;">
                                                    Wynagrodzenie:<br>
                                                    <span id="candidateSalary-${contract.contractId}" style="color: black">${contract.candidateSalary}</span>
                                                    /
                                                    <span id="candidateSalaryType-${contract.contractId}" style="color: black">${contract.candidateSalaryType}</span>
                                                </div>
                                                <div class="record-data" style="width: 18%; border: none;">
                                                    Wyraził zgody:<br>
                                                        <c:if test="${contract.candidateApproval=='true'}">
                                                            <span id="candidateApproval-${contract.contractId}" style="color: black">Akceptacja</span>
                                                        </c:if>
                                                        <c:if test="${contract.candidateApproval=='false'}">
                                                            <span id="candidateApproval-${contract.contractId}" style="color: black">Brak</span>
                                                        </c:if>
                                                </div>
                                            </div>
                                            <div class="record-line">
                                                <div class="record-data">
                                                    Konto kosztów:<br>
                                                    <span id="candidateCostAccount-${contract.contractId}" style="color: black">${contract.candidateCostAccount}</span>
                                                </div>
                                                <div class="record-data">
                                                    Przełożony:<br>
                                                    <span id="candidateManager-${contract.contractId}" style="color: black">${contract.candidateManager}</span>
                                                </div>
                                                <div class="record-data" style="border: none">
                                                    Tryb pracy:<br>
                                                    <span id="candidateWorkTimeType-${contract.contractId}" style="color: black">${contract.candidateWorkTimeType}</span>
                                                </div>
                                            </div>

                                            <div class="candidateAdvanceChapterName">Rozmiar ubrań</div>
                                            <div class="record-line">
                                                <div class="record-data">
                                                    Rozmiar buta:<br>
                                                    <span id="candidateShoeSize-${contract.contractId}" style="color: black">${contract.candidateShoeSize}</span>
                                                </div>
                                                <div class="record-data">
                                                    Rozmiar pasa:<br>
                                                    <span id="candidateWaistSize-${contract.contractId}" style="color: black">${contract.candidateWaistSize}</span>cm
                                                </div>
                                                <div class="record-data" style="border: none">
                                                    Rozmiar góry:<br>
                                                    <span id="candidateSize-${contract.contractId}" style="color: black">${contract.candidateSize}</span>
                                                </div>
                                            </div>

                                            <div class="candidateAdvanceChapterName">Uprawnienia</div>
                                            <div class="record-line">
                                                <div class="record-data" style="width: 20%;">
                                                    Wózki widłowe:<br>
                                                    <span id="candidateForkliftLicense-${contract.contractId}" style="color: black">${contract.candidateForkliftLicense}</span>
                                                </div>
                                                <div class="record-data" style="width: 20%;">
                                                    UDT:<br>
                                                    <span id="candidateUDT-${contract.contractId}" style="color: black">${contract.candidateUDT}</span>
                                                </div>
                                                <div class="record-data" style="width: 20%;">
                                                    4B:<br>
                                                    <span id="candidate4B-${contract.contractId}" style="color: black">${contract.candidate4B}</span>
                                                </div>
                                                <div class="record-data" style="width: 20%;">
                                                    SEP:<br>
                                                    <span id="candidateSEP-${contract.contractId}" style="color: black">${contract.candidateSEP}</span>
                                                </div>
                                                <div class="record-data" style="width: 19%; border: none">
                                                    Karany:<br>
                                                    <span id="candidateLaw-${contract.contractId}" style="color: black">${contract.candidateLaw}</span>
                                                </div>
                                            </div>

                                            <div class="candidateAdvanceChapterName">Terminy</div>
                                            <div class="record-line">
                                               <div class="record-data" style="width: 50%;">
                                                    Ważność wizy do:<br>
                                                    <span id="candidateVisaTerm-${contract.contractId}" style="color: black">${contract.candidateVisaTerm}</span>
                                                </div>
                                                <div class="record-data" style="width: 49%; border: none">
                                                    Ważność książeczki sanepidu:<br>
                                                    <span id="candidateSanitaryBookTerm-${contract.contractId}" style="color: black">${contract.candidateSanitaryBookTerm}</span>
                                                </div>
                                            </div>

                                            <input class="candidateTableAction" type="button" value="Edytuj" onclick="showCandidateEditionWindow('${contract.contractId}')"/>
                                            <input class="candidateTableAction" type="button" value="Usuń" onclick="showDeleteConfirmation('${contract.contractId}')"/>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>

            </div>
        </c:forEach>

        <form:form action="${pageContext.request.contextPath}/menu" method="post" style="float: right; margin-right: 20px; margin-bottom: 1em;"><input type="submit" value="Menu" name="cancelOrder" style="width: 100px!important;"></form:form>
        <form:form action="${pageContext.request.contextPath}/list" method="post" style="float: right; margin-right: 5px; margin-bottom: 1em;"><input type="submit" value="Cofnij" style="width: 100px!important;"></form:form>
        <div style="clear:both;"></div>

    </div>

    <%@include file="../../META-INF/resources/htmlElements/footer.html" %>

    <div id="shadowOnWindow" style="display: none;" onclick="hideEditeAndDeleteWindow()">  </div>

    <div id="editingCandidateForm" style="display: none;">
        <form:form action="${pageContext.request.contextPath}/showOrder" method="post" modelAttribute="contractForm">

        <div class="chapter-title" style="color: black; margin-top:10px;">Edycja kontraktu: <span id="showEditingContractId"></span></div>

        <div class="candidateAdvanceChapterName">Dane osobowe</div>
        <table class="candidateTable">
            <tr>
                <th> Imie </th>
                <th> Nazwisko </th>
                <th> Telefon </th>
                <th> Email </th>
                <th> Zgody </th>
            </tr>
            <tr style="font-size: 16px;">
                <td><input type="text" name="candidateName" title="Imię kandydata" required="required" id="editingCandidateName"/></td>
                <td><input type="text" name="candidateSurname" title="Nazwisko kandydata" required="required" id="editingCandidateSurname"/></td>
                <td><input type="number" name="candidatePhone" title="Telefon kandydata" required="required" id="editingCandidatePhone"/></td>
                <td><input type="email" name="candidateEmail" title="Email kandydata" id="editingCandidateEmail"/></td>
                <td><input type="checkbox" name="candidateApproval" title="Czy kandydat wyraził zgody na przetwarzanie danych?" id="editingCandidateApproval"/></td>
            </tr>
        </table>

        <div class="candidateAdvanceChapterName">Dane do umowy</div>
        <table class="candidateTable">
            <tr>
                <th> Pesel </th>
                <th> Umowa od </th>
                <th> Umowa do </th>
                <th> Wynagrodzenie </th>
                <th> Typ pensji </th>
            </tr>
            <tr style="font-size: 16px;">
                <td><input type="text" name="candidateId" title="Podaj pesel kandydata" id="editingCandidateId"/></td>
                <td><input type="date" name="candidateWorkFrom" title="Umowa do" id="editingCandidateWorkFrom"/></td>
                <td><input type="date" name="candidateWorkTo" title="Umowa do" id="editingCandidateWorkTo" /></td>
                <td><input type="number" name="candidateSalary" title="Wynagrodzenie kandydata" min="1" id="editingCandidateSalary"/></td>
                <td>
                    <input style="height: 1em; width:1em;" type="radio" name="candidateSalaryType" title="Wynagrodzenie kandydata" value="h" id="editingCandidateSalaryTypeH"/>na godzinę<br/>
                    <input style="height: 1em; width:1em;" type="radio" name="candidateSalaryType" title="Wynagrodzenie kandydata" value="m" id="editingCandidateSalaryTypeM"/>miesięcznie
                </td>
            </tr>
        </table>

        <div class="candidateAdvanceChapterName">Rozliczenia</div>
        <div class="record-line">
            <div class="record-data">
                Konto kosztów:<br>
                <input style="border-radius: 0.25em; height: 2em; width: 50%;" type="number" name="candidateCostAccount" title="Konto kosztów" min="0" id="editingCandidateCostAccount"/>
            </div>
            <div class="record-data">
                Przełożony:<br>
                <input style="border-radius: 0.25em; height: 2em; width: 80%;" type="text" name="candidateManager" title="Imię i nazwisko przełożonego" min="0" id="editingCandidateManager"/>
            </div>
            <div class="record-data" style="border: none">
                Tryb pracy:<br>
                <input style="border-radius: 0.25em; height: 2em; width: 80%;" type="text" name="candidateWorkTimeType" title="Tryb pracy: zmianowy, itp." minlength="1" id="editingCandidateWorkTimeType"/>
            </div>
        </div>

        <div class="candidateAdvanceChapterName">Rozmiar ubrań</div>
        <div class="record-line">
            <div class="record-data">
                Rozmiar buta:<br>
                <input style="border-radius: 0.25em; height: 2em; width: 50%;" type="number" name="candidateShoeSize" title="Numer buta kandydata" min="0" id="editingCandidateShoeSize"/>
            </div>
            <div class="record-data">
                Rozmiar pasa:<br>
                <input style="border-radius: 0.25em; height: 2em; width: 50%;" type="number" name="candidateWaistSize" title="Rozmiar pasa" min="0" id="editingCandidateWaistSize"/>
            </div>
            <div class="record-data" style="border: none">
                Rozmiar góry:<br>
                <input type="radio" name="candidateSize" title="S" value="S" id="editingCandidateSizeS"/> S |
                <input type="radio" name="candidateSize" title="M" value="M" id="editingCandidateSizeM"/> M |
                <input type="radio" name="candidateSize" title="L" value="L" id="editingCandidateSizeL"/> L |
                <input type="radio" name="candidateSize" title="XL" value="XL" id="editingCandidateSizeXL"/> XL
            </div>
        </div>

        <div class="candidateAdvanceChapterName">Uprawnienia</div>
        <div class="record-line">
            <div class="record-data" style="width: 20%;">
                Wózki widłowe:<br>
                <input type="radio" name="candidateForkliftLicense" title="Posiada uprawnienia" value="true" id="editingCandidateForkliftLicenseT"/>Tak |
                <input type="radio" name="candidateForkliftLicense" title="Brak uprawnień" value="false" id="editingCandidateForkliftLicenseF"/>Nie |
                <input type="radio" name="candidateForkliftLicense" title="Nie dotyczy" value="Niedotyczy" id="editingCandidateForkliftLicenseN" />-
            </div>
            <div class="record-data" style="width: 20%;">
                UDT:<br>
                <input type="radio" name="candidateUDT" title="Posiada uprawnienia" value="true" id="editingCandidateUDTT"/>Tak |
                <input type="radio" name="candidateUDT" title="Brak uprawnień" value="false" id="editingCandidateUDTF"/>Nie |
                <input type="radio" name="candidateUDT" title="Nie dotyczy" value="Niedotyczy" id="editingCandidateUDTN"/>-
            </div>
            <div class="record-data" style="width: 20%;">
                4B:<br>
                <input type="radio" name="candidate4B" title="Posiada uprawnienia" value="true" id="editingCandidate4BT"/>Tak |
                <input type="radio" name="candidate4B" title="Brak uprawnień" value="false" id="editingCandidate4BF"/>Nie |
                <input type="radio" name="candidate4B" title="Nie dotyczy" value="Niedotyczy" id="editingCandidate4BN"/>-
            </div>
            <div class="record-data" style="width: 20%;">
                SEP:<br>
                <input type="radio" name="candidateSEP" title="Posiada uprawnienia" value="true" id="editingCandidateSEPT"/>Tak |
                <input type="radio" name="candidateSEP" title="Brak uprawnień" value="false" id="editingCandidateSEPF"/>Nie |
                <input type="radio" name="candidateSEP" title="Nie dotyczy" value="Niedotyczy" id="editingCandidateSEPN"/>-
            </div>
            <div class="record-data" style="width: 19%; border: none">
                Karany:<br>
                <input type="radio" name="candidateLaw" title="Karany" value="true" id="editingCandidateLawT"/>Tak |
                <input type="radio" name="candidateLaw" title="Nie karany" value="false" id="editingCandidateLawF"/>Nie |
                <input type="radio" name="candidateLaw" title="Nie dotyczy" value="Niedotyczy" id="editingCandidateLawN"/>-
            </div>
        </div>

        <div class="candidateAdvanceChapterName">Terminy</div>
        <div class="record-line" style="border: none;">
            <div class="record-data" style="width: 50%;">
                Wiza do:<br/>
                <input class="advance-candidate-date" type="date" name="candidateVisaTerm" title="Data wygaśnięcia wizy" id="editingCandidateVisaTerm"/>
            </div>
            <div class="record-data" style="width: 49%; border: none;">
                Sanepid do:<br/>
                <input class="advance-candidate-date" type="date" name="candidateSanitaryBookTerm" title="Data wygaśnięcia książeczki sanepidu" id="editingCandidateSanitaryBookTerm"/>
            </div>
        </div>

        <div style="width: auto; margin: 10px 10px; height: 2.3em;">
            <input style="font-size: 16px;" type="submit" name="editContract" value="Zapisz zmiany"/>
        </div>

        <input type="hidden" name="orderId" value="${order.id}">
        <input type="hidden" id="editingContractId" name="contractId">
        <input type="hidden" name="departure" value="${order.department}">

        </form:form>
    </div>

    <div id="deleteCandidate" style="display: none; text-align: center;">

        <div class="chapter-title" style="color: black; margin-top:10px;">Usunięcie kontraktu: <span id="deletingContractId"></span></div>

        <div style="color: black;">Czy jesteś pewny, że chcesz usunąć kontrakt?</div>

        <div style="margin:1em auto; width: 400px;">
            <form:form action="${pageContext.request.contextPath}/showOrder" method="post" cssStyle="height: 3em;">
                <input type="submit" name="deleteContract" value="Usuń kontrakt"
                style=" clear:both; width: 150px;">
                <input type="button" name="deleteContract" value="Anuluj" onclick="hideEditeAndDeleteWindow()"
                style=" float: left; width: 150px;">

                <input type="hidden" name="orderId" value="${order.id}">
                <input type="hidden" id="deletingDataContractId" name="contractId">
            </form:form>
        </div>

    </div>

</body>
</html>

<script type="text/javascript">

    function showCandidateEditionWindow(contractId) {
        document.getElementById('shadowOnWindow').style.display = 'block';
        document.getElementById('editingCandidateForm').style.display = 'block';
        var text;

        text = document.getElementById('contractId-'+contractId).innerHTML;
        document.getElementById('editingContractId').setAttribute('value',text);
        document.getElementById('showEditingContractId').innerText = text;

        text = document.getElementById('candidateName-'+contractId).innerHTML;
        document.getElementById('editingCandidateName').setAttribute('value',text);

        text = document.getElementById('candidateSurname-'+contractId).innerHTML;
        document.getElementById('editingCandidateSurname').setAttribute('value',text);

        text = document.getElementById('candidatePhone-'+contractId).innerHTML;
        document.getElementById('editingCandidatePhone').setAttribute('value',text);

        text = document.getElementById('candidateEmail-'+contractId).innerHTML;
        document.getElementById('editingCandidateEmail').setAttribute('value',text);

        text = document.getElementById('candidateApproval-' + contractId).innerHTML;
        if(text.toString() === 'Akceptacja')
            document.getElementById('editingCandidateApproval').checked = true;
        else
            document.getElementById('editingCandidateApproval').checked = false;

        text = document.getElementById('candidateId-'+contractId).innerHTML;
        document.getElementById('editingCandidateId').setAttribute('value',text);

        text = document.getElementById('candidateWorkFrom-'+contractId).innerHTML;
        document.getElementById('editingCandidateWorkFrom').setAttribute('value',text);

        text = document.getElementById('candidateWorkTo-'+contractId).innerHTML;
        document.getElementById('editingCandidateWorkTo').setAttribute('value',text);

        text = document.getElementById('candidateSalary-'+contractId).innerHTML;
        document.getElementById('editingCandidateSalary').setAttribute('value',text);

        text = document.getElementById('candidateSalaryType-'+contractId).innerHTML;
        if(text.toString() === 'h')
            document.getElementById('editingCandidateSalaryTypeH').checked = true;
        else
            document.getElementById('editingCandidateSalaryTypeM').checked = true;

        text = document.getElementById('candidateCostAccount-'+contractId).innerHTML;
        document.getElementById('editingCandidateCostAccount').setAttribute('value',text);

        text = document.getElementById('candidateManager-'+contractId).innerHTML;
        document.getElementById('editingCandidateManager').setAttribute('value',text);

        text = document.getElementById('candidateWorkTimeType-'+contractId).innerHTML;
        document.getElementById('editingCandidateWorkTimeType').setAttribute('value',text);

        text = document.getElementById('candidateShoeSize-'+contractId).innerHTML;
        document.getElementById('editingCandidateShoeSize').setAttribute('value',text);

        text = document.getElementById('candidateWaistSize-'+contractId).innerHTML;
        document.getElementById('editingCandidateWaistSize').setAttribute('value',text);

        text = document.getElementById('candidateSize-'+contractId).innerHTML;
        if(text.toString()==='S')
            document.getElementById('editingCandidateSizeS').checked = true;
        else if(text.toString()==='M')
            document.getElementById('editingCandidateSizeM').checked = true;
        else if(text.toString()==='L')
            document.getElementById('editingCandidateSizeL').checked = true;
        else
            document.getElementById('editingCandidateSizeXL').checked = true;

        text = document.getElementById('candidateForkliftLicense-'+contractId).innerHTML;
        if(text.toString()==='true')
            document.getElementById('editingCandidateForkliftLicenseT').checked = true;
        else if (text.toString()==='false')
            document.getElementById('editingCandidateForkliftLicenseF').checked = true;
        else
            document.getElementById('editingCandidateForkliftLicenseN').checked = true;

        text = document.getElementById('candidateUDT-'+contractId).innerText;
        if(text.toString()==='true')
            document.getElementById('editingCandidateUDTT').checked = true;
        else if (text.toString()==='false')
            document.getElementById('editingCandidateUDTF').checked = true;
        else
            document.getElementById('editingCandidateUDTN').checked = true;

        text = document.getElementById('candidate4B-'+contractId).innerHTML;
        if(text.toString()==='true')
            document.getElementById('editingCandidate4BT').checked = true;
        else if (text.toString()==='false')
            document.getElementById('editingCandidate4BF').checked = true;
        else
            document.getElementById('editingCandidate4BN').checked = true;

        text = document.getElementById('candidateSEP-'+contractId).innerHTML;
        if(text.toString()==='true')
            document.getElementById('editingCandidateSEPT').checked = true;
        else if (text.toString()==='false')
            document.getElementById('editingCandidateSEPF').checked = true;
        else
            document.getElementById('editingCandidateSEPN').checked = true;

        text = document.getElementById('candidateLaw-'+contractId).innerHTML;
        if(text.toString()==='true')
            document.getElementById('editingCandidateLawT').checked = true;
        else if (text.toString()==='false')
            document.getElementById('editingCandidateLawF').checked = true;
        else
            document.getElementById('editingCandidateLawN').checked = true;

        text = document.getElementById('candidateVisaTerm-'+contractId).innerHTML;
        document.getElementById('editingCandidateVisaTerm').setAttribute('value',text);

        text = document.getElementById('candidateSanitaryBookTerm-'+contractId).innerHTML;
        document.getElementById('editingCandidateSanitaryBookTerm').setAttribute('value',text);
    }

    function showDeleteConfirmation(contractId) {
        document.getElementById('shadowOnWindow').style.display = 'block';
        document.getElementById('deleteCandidate').style.display = 'block';

        document.getElementById('deletingContractId').innerText = contractId;
        document.getElementById('deletingDataContractId').setAttribute('value', contractId);
    }

    function hideEditeAndDeleteWindow(){
        document.getElementById('shadowOnWindow').style.display = 'none';
        document.getElementById('editingCandidateForm').style.display = 'none';
        document.getElementById('deleteCandidate').style.display = 'none';
    }

    function showAndHideCandidateCart(recordId) {

        var buttonId = 'add-'+recordId;
        var infoId = 'info-'+recordId;

        if(document.getElementById(infoId).style.display === 'none'){
            document.getElementById(infoId).style.display = 'block';
            document.getElementById(buttonId).textContent = 'Zwiń kartę nowego kandydata -';

        } else if(document.getElementById(infoId).style.display === 'block'){
            document.getElementById(infoId).style.display = 'none';
            document.getElementById(buttonId).textContent = 'Dodaj nowego kandydata +';
        }
    }

    function showAndHideAdvanceCandidateCart(recordId) {

        var buttonId = 'show-hide-advance-'+recordId;
        var infoId = 'advance-'+recordId;

        if(document.getElementById(infoId).style.display === 'none'){
            document.getElementById(infoId).style.display = 'block';
            document.getElementById(buttonId).setAttribute('value','Ukryj -');

        } else if(document.getElementById(infoId).style.display === 'block'){
            document.getElementById(infoId).style.display = 'none';
            document.getElementById(buttonId).setAttribute('value','Rozwiń +');
        }
    }

    function showAndHideCandidateList(recordId) {

        var showButtonId = 'show-list-'+recordId;
        var listId = 'list-'+recordId;

        if(document.getElementById(listId).style.display === 'none'){
            document.getElementById(listId).style.display = 'block';
            document.getElementById(showButtonId).textContent = 'Zwiń listę -';

        } else if(document.getElementById(listId).style.display === 'block'){
            document.getElementById(listId).style.display = 'none';
            document.getElementById(showButtonId).textContent = 'Lista dodanych kandydatów +';
        }
    }

    function showAndHideCandidateInfo(candidateId){

        var showButtonId = 'candidate-info-button-'+candidateId;
        var infoId = 'candidate-info-'+candidateId;

        if(document.getElementById(infoId).style.display === 'none'){
            document.getElementById(infoId).style.display = 'block';
            document.getElementById(showButtonId).textContent = 'Zwiń -';

        } else if(document.getElementById(infoId).style.display === 'block'){
            document.getElementById(infoId).style.display = 'none';
            document.getElementById(showButtonId).textContent = 'Rozwiń +';
        }
    }

</script>
