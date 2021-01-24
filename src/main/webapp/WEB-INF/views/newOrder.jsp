<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>HOS - nowe zamówienie</title>
    <link href="<spring:url value="/resources/css/newOrder/newOrder.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/fontello/css/fontello.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>

    <%@include file="../../META-INF/resources/htmlElements/header.html" %>

    <div class="main-container">
        <%--@elvariable id="order" type="com.bz.hos.model.NewOrderForm.NewOrder"--%>
        <%--@elvariable id="savedOrder" type="com.bz.hos.model.NewOrderForm.NewOrder"--%>
        <%--@elvariable id="user" type="com.bz.hos.model.UserAndLocationEntity.User"--%>
        <%--@elvariable id="locations" type="com.bz.hos.model.UserAndLocationEntity.Location"--%>


        <div id="orderd-specyfication" class="chapter-title">Specyfika zamówienia</div>
        <form:form method="POST" action="${pageContext.request.contextPath}/newOrder" modelAttribute="order" >

            <div class="chapter-border">
                <div id="main-data">

                    <div class="main-data-tile">
                        Data zamówienia:<br/>
                        <form:input cssStyle="width: 80%;" cssClass="input-information" path="orderDate" disabled="true"/>
                    </div>

                    <div class="main-data-tile">
                        Data realizacji:<br/>
                        <input style="width: 80%;" type="date" class="input-information" title="data realizacji"
                                    <c:if test="${savedOrder.realizationDate != null}">
                                        value="${savedOrder.realizationDate}"
                                    </c:if>
                                    <c:if test="${savedOrder.realizationDate == null}">
                                        value=""
                                    </c:if>
                                    min="${savedOrder.orderDate}" required="required" name="realizationDate"/>
                    </div>

                    <div class="main-data-tile">
                        Lokalizacja:<br/>
                        <form:select cssStyle="width: 80%;" cssClass="input-information" path="locationId" itemValue="${savedOrder.location}" onchange="showDepartment()">
                            <c:forEach items="${user.locationList.values()}" var="loc">

                                <form:option value="${loc.id}"> ${loc.name}</form:option>

                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="main-data-tile" style="border-right: none;">
                        Obszar:<br/>
                        <form:select cssStyle="width: 80%;" cssClass="input-information" id="department" path="department" itemValue="${savedOrder.department}">
                            <c:forEach items="${user.locationList.values()}" var="locat">
                                <c:forEach items="${locat.department.values()}" var="dep">

                                    <form:option cssClass="${locat.id}" value="${dep}">
                                        ${dep}
                                    </form:option>

                                </c:forEach>
                            </c:forEach>
                        </form:select>

                        <script>
                            var init = false;
                            showDepartment();

                            function showDepartment() {
                                var locId = document.getElementById("locationId").value;
                                var optionList = document.getElementById("department").getElementsByTagName("option");

                                for (var i = 0; i < optionList.length; i++) {
                                    if (optionList[i].className === locId)
                                        optionList[i].style.display = '';
                                    else
                                        optionList[i].style.display = 'none';
                                }

                                if (init) {
                                    document.getElementById("department").value = null;
                                    document.getElementById("department").getElementsByClassName(locId.toString())[0].selected = true;
                                    init = true;
                                }
                            }
                        </script>

                    </div>
                    <div style="clear:both;"></div>

                    <div class="new-record-line">
                        <div class="new-record-line-tile" style = "border-right: 1px solid #d29292;">
                            Kategoria zamówienia:<br/><br/>
                            <form:radiobutton name="category" value="REP" path="orderCategory" title="REP"/>Replacement &nbsp
                            <form:radiobutton name="category" value="ZAM" path="orderCategory" title="ZAM"/>Nowe &nbsp
                        </div>
                        <div class="new-record-line-tile">
                            Kierownik obszaru:<br/><br/>
                            <input class="input-information" title="Kierownik obszaru"  style="width: 70%; height: 30px;" minlength="5"
                                   value="${savedOrder.departmentManager}" placeholder="Podaj kierownika obszaru" required="required" name="departmentManager"/>
                        </div>
                        <div style="clear:both;"></div>
                    </div>

                    Uwagi:<br/>
                    <form:textarea cssClass="input-information" title="uwagi" style="max-width: 100%; min-width: 100%; height: 50px; min-height: 30px" placeholder="podaj uwagi do zamówienia" path="comment"/>


                </div>

                <div style="margin: 1em 1em; height: 1em;">
                    <input type="submit" name="addSpecification" value="Zapisz specyfikację"/>
                </div>

            </div>
        </form:form>

        <c:if test="${ user.role == 'manager'}">
            <div class="chapter-title">Specyfika realizacji</div>
            <form:form method="POST" action="${pageContext.request.contextPath}/newOrder" modelAttribute="order" >
            <div class="chapter-border">
                <div style="height: 7em;">

                    <div class="chapter-border-line-half">
                        <div class="chapter-border-tile"  style="margin-top: 0.5em;">Opiekun: &nbsp;</div>
                            <%--@elvariable id="user" type="com.bz.hos.model.UserAndLocationEntity.User"--%>
                        <div class="chapter-border-tile-data">
                            <form:select cssClass="input-information" path="assignWorker" itemValue="${order.assignWorker}" cssStyle="width: 200px;">
                                <c:forEach var="staff" items="${user.staffList.values()}">
                                    <form:option value="${staff}">${staff}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>

                    <div class="chapter-border-line-half">
                        <div class="chapter-border-tile" style="margin-top: 0.5em;">Manager: &nbsp;</div>
                        <div class="chapter-border-tile-data">
                            <form:input cssClass="input-information" path="manager" disabled="true" cssStyle="width: 200px;"/>
                        </div>
                    </div>
                    <div class="clear: both;"></div>

                    <div class="chapter-border-line-half">
                        <div class="chapter-border-tile" style="margin-top: 0.5em;">Status: &nbsp; &nbsp;</div>
                        <div class="chapter-border-tile-data">
                            <form:select cssClass="input-information" path="orderStatus" itemValue="${order.orderStatus}" cssStyle="width: 150px;">
                                <form:option value="Oczekuje">      Oczekuje </form:option>
                                <form:option value="Zatwierdzone">  Zatwierdzone </form:option>
                                <form:option value="Odrzucone">     Odrzucone </form:option>
                                <form:option value="Realizacja">  Realizacja </form:option>
                                <form:option value="Anulowane">     Anulowane </form:option>
                                <form:option value="Zrealizowane">  Zrealizowane </form:option>
                            </form:select>
                        </div>
                    </div>

                </div>

                <div style="margin: 1em 1em; height: 1em;">
                    <input type="submit" name="addRealizationSpecification" value="Zapisz akredytację"/>
                </div>

            </div>
            </form:form>
        </c:if>

        <div class="chapter-title">Nowi kandydaci</div>

        <form:form method="POST" action="${pageContext.request.contextPath}/newOrder" modelAttribute="order" >
        <div class="chapter-border">
            <div id="new-record">
                <div class="new-record-tile">
                    Ilość:<br/>
                    <form:input cssStyle="width: 80%;" cssClass="input-information" placeholder="ilość" title="ilość pracowników" path="newRecord.accountOrdered"/>
                </div>
                <div class="new-record-tile">
                    Nazwa stanowiska:<br/>
                    <input style="width: 80%;" class="input-information" placeholder="Nazwa stanowiska" title="Nazwa stanowiska" value="${order.newRecord.jobName}" name="newRecord.jobName" required="required"/>
                </div>
                <div class="new-record-tile">
                    Okres pracy od:<br/>
                    <input style="width: 80%;" type="date" class="input-information" title="praca od" value="${savedOrder.newRecord.workingFrom}"
                            <c:if test="${savedOrder.realizationDate != null}">
                                min="${savedOrder.realizationDate}"
                            </c:if>
                            <c:if test="${savedOrder.realizationDate == null}">
                                min="${savedOrder.orderDate}"
                            </c:if>
                           required="required" name="workingFrom"/>
                </div>
                <div class="new-record-tile" style="border-right: none;">
                    Okres pracy do:<br/>
                    <input style="width: 80%;" type="date" class="input-information" title="praca do" value="${savedOrder.newRecord.workingTo}" min="${savedOrder.realizationDate}" required="required" name="workingTo"/>
                </div>
                <div style="clear:both; height: 15px;"></div>

                <div class="new-record-line">
                    <div class="new-record-line-tile" style = "border-right: 1px solid #d29292;">
                        Wynagrodzenie:<br/>
                        <form:input cssClass="input-information" cssStyle="width: 200px;" name="paymentValue" path="newRecord.paymentValue" placeholder="Podaj stawkę"/>
                        <form:radiobutton name="paymentType" value="m" path="newRecord.paymentType" title="na miesiąc"/> /mies. &nbsp
                        lub
                        <form:radiobutton name="paymentType" value="h" path="newRecord.paymentType" title="na godzine"/> /godz. &nbsp
                        <div style="clear: both;"></div>
                    </div>
                    <div class="new-record-line-tile">
                        Typ umowy:<br/>
                        <c:set var="contract" value="${savedOrder.newRecord.contract}"/>
                        <form:select cssStyle="width: 80%;" cssClass="input-information" path="newRecord.contract" itemValue="${contract}">
                            <form:option value="UOPS">Umowa o pracę stałą</form:option>
                            <form:option value="ZLEC">Umowa zlecenie</form:option>
                            <form:option value="UOPT">Umowa o pracę tymczasową</form:option>
                        </form:select>
                    </div>
                    <div style="clear:both;"></div>
                </div>

                <div class="new-record-line">
                    <div class="new-record-line-tile" style = "border-right: 1px solid #d29292; height: 100px">
                        Uwagi:<br/>
                        <form:textarea cssClass="input-information" title="Uwagi" style="max-width: 90%; min-width: 90%; height: 50px; min-height: 30px; max-height: 70px " placeholder="Uwagi dotyczące pracownika" path="newRecord.comment"/>
                    </div>
                    <div class="new-record-line-tile">
                        Kwalifikacje:<br/>
                        <form:textarea cssClass="input-information" title="Kwalifikacje" style="max-width: 90%; min-width: 90%; height: 50px; min-height: 30px; max-height: 70px " placeholder="Wymagane kwalifikacje pracownika" path="newRecord.qualification"/>
                    </div>
                    <div style="clear:both;"></div>
                </div>
            </div>

            <div style="margin: 1em 1em; height: 1em;">
                <input type="submit" name="addNewRecord" value="Zatwierdź kandydata"/>
            </div>

        </div>
        </form:form>

        <c:if test="${savedOrder.recordList.size() > 0}">
        <div id="records" style="padding-bottom: 20px;">
            <div class="chapter-title">Wprowadzone zapotrzebowanie</div>
            <div class="chapter-border" style="margin: 0; padding: 0;">
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
                            <th style="width: 130px;">Akcje</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% int i = 0; %>
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
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/newOrder" style="float: right; margin-top: 10px;">
                                        <input type="submit" name="deleteRecord" value="usuń">
                                        <input type="hidden" name="recordNo" value="<%=i%>">
                                    </form>
                                    <form method="post" action="${pageContext.request.contextPath}/newOrder" style="float: right; margin-top: 10px;">
                                        <input type="submit" name="editRecord" value="edytuj">
                                        <input type="hidden" name="recordNo" value="<%=i++%>">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        </c:if>

        <br/>
        <form:form action="${pageContext.request.contextPath}/neworder-confirmation" method="POST" style="float: right; margin-right: 20px;"><input type="submit" value="Dalej"/></form:form>
        <form:form action="${pageContext.request.contextPath}/newOrder" method="post" style="float: right;"><input type="submit" value="Wyczyść" name="cancelOrder"></form:form>
        <form:form action="${pageContext.request.contextPath}/menu" method="post" style="float: right;"><input type="submit" value="Menu" name="goMenu"></form:form>
        <div style="clear:both;"></div>

    </div>

    <%@include file="../../META-INF/resources/htmlElements/footer.html" %>

</body>
</html>
