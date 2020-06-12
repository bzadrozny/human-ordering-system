<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--@elvariable id="user" type="com.bz.hos.model.UserAndLocationEntity.User"--%>

<html>
<head>
    <title>Zarządzanie kontraktami</title>
    <link href="<spring:url value="/resources/css/contractManagement/contractManagement.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/fontello/css/fontello.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>

<%@include file="../../META-INF/resources/htmlElements/header.html" %>

<div class="main-container">

    <div id="localization-selection" >

        <div class="chapter-title">
            Wybierz lokalizację
        </div>
        <div class="chapter-border">
            <div style="width: 50%; margin: auto; text-align: center;">

                <form action="/contractManagement" method="post" style="margin: 0;">
                    <select name="selectedLocationId" title="Wybierz lokalizację" class="input-information" style="float: none;">
                        <c:forEach items="${user.locationList}" var="loc">
                            <option name="selectedLocation" value="${loc.value.id}">${loc.value.name}</option>
                        </c:forEach>
                    </select>
                    <input type="submit" name="setLocation" value="Zatwierdź" style="float: none;"/>
                </form>
                
            </div>
        </div>

    </div>

    <c:if test="${not empty contractForm.locationId}">

        <div id="contracts-support">

            <div class="chapter-title">
                Wybrano lokalizację: ${contractForm.locationName}
                <input type="hidden" name="locationId" value="${contractForm.locationId}">
            </div>

            <table>
                <tr>
                    <th style="border-top-left-radius: 1em;">Całkowite zatrudnienie</th>
                    <th>Nowi pracownicy<br/>(2 tygodnie)</th>
                    <th>Replacement</th>
                    <th style="border-top-right-radius: 1em;">Bliski koniec umowy<br/>(2 tygodnie)</th>
                </tr>
                <tr>
                    <td>${contractForm.totalContracts}</td>
                    <td>${contractForm.newContracts}</td>
                    <td>${contractForm.replacementContracts}</td>
                    <td style="border-right: none;">${contractForm.expiringContracts}</td>
                </tr>
            </table>

        </div>

        <c:if test="${not empty contractForm.contractList}">

            <div id="contracts-support">

                <div class="chapter-title">
                    Filtry kontraktów
                </div>

                <table>
                    <tr>
                        <th style="border-top-left-radius: 1em;">Id kontraktu</th>
                        <th>Pesel</th>
                        <th>Imię</th>
                        <th style="border-top-right-radius: 1em;">Nazwisko</th>
                    </tr>
                    <tr>
                        <td>
                            <input id="contract-id-filter" title="Podaj id kontraktu" type="text"
                                   class="input-information" maxlength="25" onchange="filterData()"/>
                        </td>
                        <td>
                            <input id="candidate-id-filter" title="Podaj pesel" type="text"
                                   class="input-information" maxlength="25" onchange="filterData()"/>
                        </td>
                        <td>
                            <input id="name-filter" title="Podaj Imię" type="text"
                                   class="input-information" maxlength="25" onchange="filterData()"/>
                        </td>
                        <td>
                            <input id="surname-filter" title="Podaj Nazwisko" type="text"
                                   class="input-information" maxlength="25" onchange="filterData()"/>
                        </td>
                    </tr>
                </table>

            </div>

            <div id="contracts-view">
                <div class="chapter-border">

                    <c:forEach items="${contractForm.departureList}" var="depart">
                        <table id="${depart}">
                            <caption>
                                Oddział: <span class="departmentName">${depart}</span>
                            </caption>
                            <tr>
                                <th>Id kontraktu</th>
                                <th>Imię</th>
                                <th>Nazwisko</th>
                                <th>Umowa od</th>
                                <th>Umowa do</th>
                                <th>Stanowisko</th>
                                <th>Nowa data rozwiązania</th>
                            </tr>

                            <script>
                                function checkIfNewContract(dateFrom, elementId) {

                                    if(dateFrom === undefined || dateFrom == null || dateFrom === 'null')
                                        return;

                                    var td = new Date('<%=LocalDate.now().minusDays(14)%>');
                                    var todayMinus14Days = new Date(td.getFullYear(), td.getMonth(), td.getDate(), 0);

                                    var cf = new Date(dateFrom);
                                    var contractFrom = new Date(cf.getFullYear(), cf.getMonth(), cf.getDate(), 0);

                                    if(todayMinus14Days.getTime() < contractFrom.getTime()){
                                        document.getElementById(elementId).style.backgroundColor = 'rgb(84,195,189)';
                                        document.getElementById(elementId).style.color = '#ffffff';
                                    }
                                }

                                function checkIfExpiringContract(dateTo, elementId) {

                                    if(dateTo === undefined || dateTo == null || dateTo === 'null')
                                        return;

                                    var td = new Date('<%=LocalDate.now().plusDays(14)%>');
                                    var todayPlus14Days = new Date(td.getFullYear(), td.getMonth(), td.getDate(), 0);

                                    var ct = new Date(dateTo);
                                    var contractTo = new Date(ct.getFullYear(), ct.getMonth(), ct.getDate(), 0);

                                    if(todayPlus14Days.getTime() > contractTo.getTime()){
                                        document.getElementById(elementId).style.backgroundColor = 'rgb(239, 3, 0)';
                                        document.getElementById(elementId).style.color = '#ffffff';
                                    }
                                }
                            </script>

                            <c:forEach items="${contractForm.contractList}" var="contract">
                            <c:if test="${contract.departure.equals(depart)}">
                                <tr class="${contract.contractId} ${contract.candidateId}">
                                    <td>
                                        <input type="button" name="orderId" value="${contract.contractId}"
                                               style="background: none; border: none; color: black; float: none;"
                                               onclick="showOrder('${(contract.recordId.split('/')[0]).concat('/').concat(contract.recordId.split('/')[1]).concat('/').concat(contract.recordId.split('/')[2])}')">
                                    </td>
                                    <td>${contract.candidateName}</td>
                                    <td>${contract.candidateSurname}</td>
                                    <td id="${contract.contractId}-from">${contract.candidateWorkFrom}</td>
                                    <td id="${contract.contractId}-to">${contract.candidateWorkTo}</td>
                                    <td>${contract.jobName}</td>
                                    <td class="actionCell">
                                        <input type="checkbox" id="m-${contract.contractId}" title="Modyfikować datę rozwiąznia?"
                                               style="width: 20%; height: 1em;" class="input-information ${'m-'.concat(depart)}"/>
                                        <input type="date" id="d-${contract.contractId}" title="Nowa data rozwiązania"
                                               dataformatas="RRRR-mm-dd" class="input-information ${'d-'.concat(depart)}" min="${contract.candidateWorkFrom}"/>
                                    </td>
                                </tr>

                                <script>
                                    checkIfNewContract('${contract.candidateWorkFrom}','${contract.contractId}-from');
                                    checkIfExpiringContract('${contract.candidateWorkTo}','${contract.contractId}-to');
                                </script>

                            </c:if>
                            </c:forEach>

                            <tr>
                                <td colspan="7" class="contractsOptions">

                                    <div class="caption">
                                        Szybkie wprowadzanie
                                    </div>

                                    <div class="chapter-border-line" style="margin-bottom: 1em;">
                                        <div class="record-data">
                                            <input type="button" value="Zaznacz wszystkich" onclick="checkAll('${depart}')"/>
                                        </div>
                                        <div class="record-data">
                                            <input type="date" id="date-${depart}" class="input-information" title="Wprowadź nową datę rozwiązania umowy" min="<%=LocalDate.now()%>"/>
                                            <input type="button" value="Ustaw zaznaczonym" style="margin-top: 0;" onclick="setDataToChecked('${depart}')"/>
                                        </div>
                                        <div class="record-data">
                                            <input type="button" value="Odznacz wszystkich" onclick="uncheckAll('${depart}')"/>
                                        </div>
                                    </div>
                                </td>
                            </tr>

                        </table>
                    </c:forEach>

                    <form:form modelAttribute="contractForm" method="post">

                        <div style="width:100%; text-align: center;">
                            <input type="submit" name="updateContracts" value="Zatwierdź" style="float: none !important; width: 200px;" onclick="createJson()"/>
                            <input type="hidden" id="tempJson" name="tempJson" value=""/>
                            <input type="hidden" name="locationId" value="${contractForm.locationId}">
                        </div>

                        <script>
                            function checkAll(depart) {
                                depart = 'm-' + depart;
                                var collection = document.getElementsByClassName(depart);
                                for(var i = 0; i < collection.length; i++)
                                    collection[i].checked = true;
                            }

                            function uncheckAll(depart) {
                                depart = 'm-' + depart;
                                var collection = document.getElementsByClassName(depart);
                                for(var i = 0; i < collection.length; i++)
                                    collection.item(i).checked = false;
                            }

                            function setDataToChecked(depart) {
                                var dateValue = document.getElementById('date-'+depart).value;
                                depart = 'm-' + depart;
                                var checkboxes = document.getElementsByClassName(depart);
                                var dateElementId;
                                for(var i = 0; i< checkboxes.length; i++)
                                    if(checkboxes[i].checked === true){
                                        dateElementId = 'd-' + checkboxes[i].id.substring(2);
                                        document.getElementById(dateElementId).value = dateValue;
                                    }
                            }

                            function createJson() {
                                var data = '';
                                var departmentList = document.getElementsByClassName('departmentName');
                                var checkboxes;
                                var contractId;
                                var dateValue;
                                for(var i=0; i < departmentList.length; i++) {
                                    checkboxes = document.getElementsByClassName('m-' + departmentList.item(i).innerText);
                                    for (var j = 0; j < checkboxes.length; j++) {
                                        if(checkboxes[j].checked === true) {
                                            contractId = checkboxes[j].id.substring(2);
                                            dateValue = document.getElementById('d-' + contractId).value;
                                            data = data.concat(contractId).concat('=').concat(dateValue).concat(';');
                                        }
                                    }
                                }
                                console.log(data);
                                document.getElementById('tempJson').value = data;
                            }

                            function filterData() {
                                var contractId = document.getElementById('contract-id-filter').value;
                                var candidateId = document.getElementById('candidate-id-filter').value;
                                var candidateName = document.getElementById('name-filter').value;
                                var candidateSurname = document.getElementById('surname-filter').value;

                                var tables = document.getElementsByTagName('table');
                                var cells;
                                var ids;
                                var show;
                                for(var i = 2; i < tables.length; i++)
                                    for(var j = 1; j < tables[i].rows.length - 1; j++){
                                        show = true;
                                        cells = tables[i].rows[j].getElementsByTagName("td");
                                        ids = tables[i].rows[j].classList;

                                        if(!contractId.isEmpty && ids[0].search(contractId)===-1)
                                            show = false;

                                        if(!candidateId.isEmpty && ids[1]!==undefined && ids[1].search(candidateId)===-1)
                                            show = false;
                                        else if (!candidateId.isEmpty && ids[1]===undefined && ids[0].search(candidateId)===-1)
                                            show = false;

                                        if(!candidateName.isEmpty && cells[1].textContent.search(candidateName)===-1)
                                            show = false;

                                        if(!candidateSurname.isEmpty && cells[2].textContent.search(candidateSurname)===-1)
                                            show = false;

                                        if(show){
                                            tables[i].rows[j].style.display = 'table-row';
                                        } else
                                            tables[i].rows[j].style.display = 'none';

                                    }
                            }

                            function showOrder(orderId) {
                                document.getElementById("orderId").value = orderId.toString();
                                document.getElementById("showOrder").click();
                            }
                        </script>

                    </form:form>

                </div>
            </div>

        </c:if>
    </c:if>

    <form:form action="/menu" method="post" style="float: right; margin: 0 20px 20px;">
        <input type="submit" style="width: 100px;" value="Cofnij"/>
    </form:form>
    <div style="clear:both;"></div>

    <div style="display: none">
        <form method="post" action="/showOrder" style="margin-bottom: 0;">
            <input type="submit" value="Show Order" id="showOrder">
            <input type="hidden" value="" name="orderId" id="orderId">
        </form>
    </div>

</div>

<%@include file="../../META-INF/resources/htmlElements/footer.html" %>

<c:if test="${empty contractForm.locationId or empty contractForm.contractList}">
    <script>
            footer = document.getElementsByClassName("footer").item(0);
            footer.style.position = 'fixed';
            footer.style.bottom = '0';
            footer.style.left = '0';
            footer.style.right = '0';
    </script>
</c:if>

</body>
</html>
