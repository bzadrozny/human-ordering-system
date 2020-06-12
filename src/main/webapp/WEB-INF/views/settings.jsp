<%@ page import="com.bz.hos.model.SettingForm.SettingForm" %>
<%@ page import="com.bz.hos.model.UserAndLocationEntity.User" %>
<%--@elvariable id="user" type="com.bz.hos.model.UserAndLocationEntity.User"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
     session.setAttribute("savedSettingForm",request.getAttribute("settingForm"));
%>
<html>
<head>
    <title>Ustawienia konta</title>
    <link href="<spring:url value="/resources/css/settings/settings.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<spring:url value="/resources/fontello/css/fontello.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>

    <%@include file="../../META-INF/resources/htmlElements/header.html" %>

    <div class="main-container">

        <div class="chapter-title"> Dane osobowe</div>
        <div class="chapter-border">

            <table>
                <tr>
                    <td style="padding: 5px;">
                        Imię
                        <div class="bottom-cell">
                            ${user.name}
                        </div>
                    </td>
                    <td style="padding: 5px;">
                        Nazwisko
                        <div class="bottom-cell">
                            ${user.surname}
                        </div>
                    </td>
                    <td style="padding: 5px;">
                        Mail
                        <div class="bottom-cell">
                            ${user.mail}
                        </div>
                    </td>
                </tr>
            </table>

        </div>

        <div class="chapter-title"> Dane organizacyjne</div>
        <div class="chapter-border">

            <table>
                <tr>
                    <td style="padding: 5px;">
                        Stanowisko
                        <div class="bottom-cell">
                            ${user.role}<c:if test="${user.admin}"> - admin</c:if>
                        </div>
                    </td>
                    <td style="padding: 5px;">
                        Organizacja
                        <div class="bottom-cell">
                            ${user.organization}
                        </div>
                    </td>
                    <td style="padding: 5px;">
                        Opiekun
                        <div class="bottom-cell">
                            ${user.assignWorker}
                        </div>
                    </td>
                    <td style="padding: 5px;">
                        Manager
                        <div class="bottom-cell">
                            ${user.manager}
                        </div>
                    </td>
                </tr>
            </table>

            <table>
                <tr>
                    <td style="padding: 5px;">
                        Przypisane organizacje:
                        <div class="bottom-cell">
                            <select class="input-information" title="organizacje">
                                <c:forEach var="org" items="${user.organizationList.values()}">
                                    <option value="${org}">${org}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </td>
                    <td style="padding: 5px;">
                        Dostępne lokalizacje:
                        <div class="bottom-cell">
                            <select class="input-information" title="lokalizacje">
                                <c:forEach var="loc" items="${user.locationList.values()}">
                                    <option value="${loc.id}">${loc.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </td>
                </tr>
            </table>

        </div>

        <div class="chapter-title"> Zmiana hasla</div>
        <div class="chapter-border" style="text-align: center; width: 70%; margin-left: 15%;">

            <c:if test="${settingForm.passwordChangeSucces}">Hasło zostało zmienione</c:if>
            <c:if test="${!settingForm.passwordChangeSucces}">Wprowadź poprawnie nowe dane</c:if>

            <form method="post" action="/account-management">

                <div style="float: left; width: 200px;">Stare hasło:</div>
                <input style="float: left;" class="input-information" type="password" name="oldPass" title="Stare hasło" required="required" value="${settingForm.oldPass}"/>
                <div style="clear: both"></div>

                <div style="float: left; width: 200px;">Nowe hasło:</div>
                <input style="float: left;" class="input-information" type="password" name="newPass" title="Nowe hasło" required="required" minlength="8" maxlength="50" value="${settingForm.newPass}"/>
                <div style="clear: both"></div>

                <div style="float: left; width: 200px;">Potwierdź nowe hasło:</div>
                <input style="float: left;" class="input-information" type="password" name="newPassConfirmation" title="Potwierdzenie hasła" required="required" minlength="8" maxlength="50" value="${settingForm.newPassConfirmation}"/>
                <div style="clear: both"></div>

                <input type="submit" value="Zapisz nowe haslo" name="changeUserPassword"/>
                <br/>

            </form>
        </div>

        <c:if test="${user.admin}">

            <div class="chapter-title">
                Nowy użytkownik
            </div>
            <div class="chapter-border" style="text-align: center; width: 70%; margin-left: 15%;">

                <c:if test="${!settingForm.showNewUserCart}">
                    <form method="post" action="/account-management" style="margin-bottom: 0">
                        <input type="submit" style="float: none;" name="displayNewUserCart" value="Wyświetl kartę nowego użytkownika"/>
                    </form>
                </c:if>

                <c:if test="${settingForm.showNewUserCart}">
                    <form:form modelAttribute="settingForm" method="post" action="/account-management">
                        <div class="chapter-title" style="margin-top: 20px;">
                            Dane osobowe
                        </div>
                        <div>

                            <div>${settingForm.userMailInfo}</div>

                            <div style="float: left; width: 200px; margin-top: 15px;">Podaj adres e-mail:</div>
                                <input type="email" name="userMail" value="${settingForm.userMail}" style="float: left;" class="input-information" placeholder="Podaj e-mail" minlength="5" maxlength="50" required="required" />
                            <div style="clear: both"></div>

                            <div style="float: left; width: 200px;  margin-top: 15px;">Podaj imię:</div>
                                <input type="text" name="userName" value="${settingForm.userName}" style="float: left;" class="input-information" title="Podaj imię" placeholder="Podaj imię" minlength="3" maxlength="20" required="required" />
                            <div style="clear: both"></div>

                            <div style="float: left; width: 200px;  margin-top: 15px;">Podaj nazwisko:</div>
                                <input type="text" name="userSurname" value="${settingForm.userSurname}" style="float: left;" class="input-information" title="Podaj nazwisko" placeholder="Podaj nazwisko" minlength="3" maxlength="20" required="required" />
                            <div style="clear: both"></div>
                        </div>


                        <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                        <div class="chapter-title" style="margin-top: 20px;">
                            Hasło tymczasowe
                        </div>

                        <div>
                            <div> ${settingForm.passInfo} </div>

                            <div style="float: left; width: 200px;  margin-top: 15px;">Podaj hasło:</div>
                                <input type="password" name="password" style="float: left; font-size: 13.33px;" class="input-information" title="Podaj hasło" placeholder="Podaj hasło" minlength="8" maxlength="20" required="required" />
                            <div style="clear: both"></div>

                            <div style="float: left; width: 200px;  margin-top: 15px;">Powtórz hasło:</div>
                                <input type="password" name="confirmPassword" style="float: left; font-size: 13.33px;" class="input-information" title="Powtórz hasło" placeholder="Powtórz hasło" minlength="8" maxlength="20" required="required" />
                            <div style="clear: both"></div>
                        </div>


                        <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                        <div class="chapter-title" style="margin-top: 20px;">
                            Stanowisko i uprawnienia
                        </div>
                        <div>
                            <div style="float: left; width: 200px;  margin-top: 15px;">Wybierz stanowisko:</div>
                            <form:select path="userRole" style="float: left;" class="input-information" title="Wybierz stanowisko">
                                <form:option value="client"> klient </form:option>
                                <form:option value="recruiter"> rekruter </form:option>
                                <form:option value="manager"> manager </form:option>
                            </form:select>
                            <div style="clear: both"></div>

                            <div style="float: left; width: 200px; margin-top: 15px;">Podaj nazwę organizacji:</div>
                            <form:select path="userOrganization" style="float: left;" cssClass="input-information" title="Podaj nazwę organizacji">
                                <form:options items="${settingForm.organisationMap}"/>
                            </form:select>
                            <div style="clear: both"></div>

                            <div style="float: none; margin-top: 15px;">Uprawnienia administracyjne</div>

                                <div class="chapter-border-line-half">
                                    <div style="float: none; margin-top: 15px;">Aktywne:</div>
                                    <form:radiobutton path="userAdmin" value="true" title="true" cssClass="input-information" cssStyle="float: none; width: 50%;"/><br/>
                                </div>

                                <div class="chapter-border-line-half">
                                    <div style="float: none; margin-top: 15px;">Nieaktywne:</div>
                                    <form:radiobutton path="userAdmin" value="false" title="false" cssClass="input-information" cssStyle="float: none; width: 50%;"/><br/>
                                </div>

                            <div style="clear: both"></div>
                        </div>


                        <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                        <div class="chapter-title" style="margin-top: 20px;">
                            Obsługa zamówień
                        </div>
                        <div>
                            <div style="float: left; width: 300px; margin-top: 15px;">Przypisany opiekun zamówień:</div>
                            <form:select path="userAssignWorker" cssClass="input-information" cssStyle="float: left;">

                                <c:forEach var="recruiters" items="${settingForm.allRecruiterList}">
                                    <form:option value="${recruiters.mail}">${recruiters.mail}</form:option>
                                </c:forEach>
                                <c:forEach var="managers" items="${settingForm.allManagerList}">
                                    <form:option value="${managers.mail}">${managers.mail}</form:option>
                                </c:forEach>

                            </form:select>
                            <div style="clear: both"></div>

                            <div style="float: left; width: 300px; margin-top: 15px;">Przypisany manager zamówień:</div>
                            <form:select path="userManager"  cssClass="input-information" cssStyle="float: left;">

                                <c:forEach var="managers" items="${settingForm.allManagerList}">
                                    <form:option value="${managers.mail}">${managers.mail}</form:option>
                                </c:forEach>

                            </form:select>
                            <div style="clear: both"></div>
                        </div>


                        <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                        <div class="chapter-title" style="margin-top: 20px;">
                            Stauts użytkownika
                        </div>
                        <div>
                            <div class="chapter-border-line-half">
                                <div style="float: none; margin-top: 15px;">Użytkownik aktywny:</div>
                                <form:radiobutton path="userStatus" value="true" title="true" cssClass="input-information" cssStyle="float: none; width: 50%;"/><br/>
                            </div>

                            <div class="chapter-border-line-half">
                                <div style="float: none; margin-top: 15px;">Użytkownik zdezaktywowany:</div>
                                <form:radiobutton path="userStatus" value="false" title="false" cssClass="input-information" cssStyle="float: none; width: 50%;"/><br/>
                            </div>
                            <div style="clear: both"></div>
                        </div>


                        <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                        <div style="margin-top: 20px;">
                            <input type="submit" value="Dodaj nowego użytkownika" name="addNewUser">
                        </div>
                    </form:form>

                    <div style="clear: both"></div>
                    <form method="post" action="/account-management" style="margin-bottom: 0">
                        <input type="submit" style="float: none"  name="hideNewUserCart" value="Ukryj kartę nowego użytkownika"/>
                    </form>
                </c:if>

            </div>


            <div class="chapter-title">
                Zarządzanie użytkownikami
            </div>
            <div class="chapter-border" style="text-align: center; width: 70%; margin-left: 15%;">

                <div> Wybrany użytkownik: ${settingForm.selectedUser.mail}
                    <form:form modelAttribute="settingForm" method="post" action="/account-management">
                        <form:select path="selectedUser.mail" name="selectedUser" cssClass="input-information">
                            <c:forEach items="${settingForm.allClientList}" var="staff">
                                <form:option value="${staff.mail.toString()}">${staff.mail}</form:option>
                            </c:forEach>
                            <c:forEach items="${settingForm.allRecruiterList}" var="staff">
                                <form:option value="${staff.mail.toString()}">${staff.mail}</form:option>
                            </c:forEach>
                            <c:forEach items="${settingForm.allManagerList}" var="staff">
                                <form:option value="${staff.mail.toString()}">${staff.mail}</form:option>
                            </c:forEach>
                        </form:select>
                        <input type="submit" style="margin-top: 12px; width: 100px;" value="Wybierz" name="selectUserFromStaff">
                    </form:form>
                </div>

                <c:if test="${not empty settingForm.selectedUser.mail}">

                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div class="chapter-title" style="margin-top: 20px;">
                        Zmiana danych osobistych
                    </div>
                    <div style="height: 10em;">
                        <form:form modelAttribute="settingForm" method="post" action="/account-management">
                            <div style="float: left; width: 200px; margin-top: 15px;">Podaj nowe imię:</div>
                            <form:input path="name" style="float: left;" class="input-information" title="Nowe imię" placeholder="Podaj Imię" minlength="3" maxlength="20" />
                            <div style="clear: both"></div>

                            <div style="float: left; width: 200px;  margin-top: 15px;">Podaj nowe nazwisko:</div>
                            <form:input path="surname" style="float: left;" class="input-information" title="Nowe nazwisko" placeholder="Podaj nazwisko" minlength="3" maxlength="20" />
                            <div style="clear: both"></div>

                            <input type="submit" value="Zapisz nowe dane" name="changeSelectedUserData">
                        </form:form>
                    </div>

                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div class="chapter-title" style="margin-top: 20px;">
                        Zmiana organizacji użytkownika
                    </div>
                    <div style="height: 7em;">
                        <form:form modelAttribute="settingForm" method="post" action="/account-management">
                            <div style="float: left; width: 200px; margin-top: 15px;">Wybierz organizację:</div>
                            <form:select path="organization" style="float: left;" cssClass="input-information" title="Podaj nazwę organizacji">
                                <form:options items="${settingForm.organisationMap}"/>
                            </form:select>
                            <div style="clear: both"></div>

                            <input type="submit" value="Zapisz nowe dane" name="changeOrganization">
                        </form:form>
                    </div>

                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div class="chapter-title" style="margin-top: 20px;">
                        Reset hasła wybranego użytkownika
                        <c:if test="${user.moderator}">
                            ${settingForm.selectedUser.password}
                        </c:if>
                    </div>
                    <div style="height: 10em;">
                        <form:form modelAttribute="settingForm" method="post" action="/account-management">
                            <div style="float: left; width: 200px; margin-top: 15px;">Podaj nowe hasło tymczasowe:</div>
                            <form:input path="tempPass" style="float: left;" class="input-information" title="Nowe imię"  minlength="3" maxlength="20" />
                            <div style="clear: both"></div>

                            <div style="float: left; width: 200px;  margin-top: 15px;">Powtórz podane hasło tymczasowe:</div>
                            <form:input path="tempPassConfirmation" style="float: left;" class="input-information" title="Nowe nazwisko" minlength="3" maxlength="20" />
                            <div style="clear: both"></div>

                            <input type="submit" value="Zapisz haslo tymczasowe" name="resetUserPassword"/>
                        </form:form>
                    </div>


                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div class="chapter-title" style="margin-top: 20px;">
                        Zarządzanie lokalizacjami użytkownika
                    </div>
                    <div>

                        Ilość lokalizacji użytkownika: ${settingForm.selectedUser.locationList.size()}<br/>
                        <c:if test="${not empty settingForm.selectedUser.locationList}">
                            <br/>
                            <table>

                                <tr>
                                    <th>Nazwa lokalizacji</th>
                                    <th>Organizacja</th>
                                    <th>Obszar</th>
                                    <th style="width: 75px;">Akcja</th>
                                </tr>

                                <c:forEach var="loc" items="${settingForm.selectedUser.locationList.values()}">
                                    <tr>
                                        <td>${loc.id} | ${loc.name}</td>
                                        <td>${loc.organization}</td>
                                        <td>
                                            <c:if test="${loc.area.equals('S')}">Południe</c:if>
                                            <c:if test="${loc.area.equals('N')}">Północ</c:if>
                                            <c:if test="${loc.area.equals('E')}">Wschód</c:if>
                                            <c:if test="${loc.area.equals('W')}">Zachód</c:if>
                                        </td>
                                        <td>
                                            <form:form modelAttribute="settingForm" method="post" action="/account-management">
                                                <input type="submit" style="float: none" value="Usuń" name="removeUsersLocation"/>
                                                <input type="hidden" name="selectedUserLocationId" value="${loc.id}">
                                            </form:form>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </table>
                        </c:if>

                        <br/>
                        <form:form modelAttribute="settingForm" method="post" action="/account-management">
                            <form:select path="selectedLocationId" cssClass="input-information" cssStyle="float: none;">
                                <c:forEach items="${settingForm.allLocationList}" var="loc">

                                    <form:option value="${loc.locationId}"> ${loc} </form:option>

                                </c:forEach>
                            </form:select >
                            <input type="submit" value="Dodaj lokalizacje" name="addUsersLocation" style="float: none" />
                        </form:form>
                    </div>


                    <c:if test="${!settingForm.selectedUser.role.equals('client')}">
                        <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                        <div class="chapter-title" style="margin-top: 20px;">
                            Zarządzanie podwładnymi użytkownika
                        </div>
                        <div>

                            Ilość podwładnych użytkownika: ${settingForm.selectedUserStaff.size()}<br/>
                            <c:if test="${not empty settingForm.selectedUserStaff}">
                                <br/>
                                <table>

                                    <tr>
                                        <th>Mail</th>
                                        <th>Manager</th>
                                        <th>Stanowisko</th>
                                        <th>Organizacja</th>
                                        <th style="width: 75px;">Akcja</th>
                                    </tr>

                                    <c:forEach var="staff" items="${settingForm.selectedUserStaff}">
                                        <tr>
                                            <td>${staff.mail}</td>
                                            <td>${staff.manager}</td>
                                            <td>${staff.role}</td>
                                            <td>${staff.organization}</td>
                                            <td>
                                                <form:form modelAttribute="settingForm" method="post" action="/account-management">
                                                    <input type="submit" style="float: none" value="Usuń" name="removeMangersStaff"/>
                                                    <input type="hidden" name="selectedAssignWorker" value="${staff.mail}">
                                                </form:form>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                </table>
                            </c:if>

                            <br/>
                            <form:form modelAttribute="settingForm" method="post" action="/account-management">
                                <form:select path="selectedAssignWorker" cssClass="input-information" cssStyle="float: none;">
                                    <c:forEach items="${settingForm.allRecruiterList}" var="recruiter">
                                        <form:option value="${recruiter.mail}"> ${recruiter.mail} | ${recruiter.role} </form:option>
                                    </c:forEach>
                                    <c:forEach items="${settingForm.allManagerList}" var="manager">
                                        <form:option value="${manager.mail}"> ${manager.mail} | ${manager.role} </form:option>
                                    </c:forEach>
                                </form:select >
                                <input type="submit" value="Dodaj użytkownika" name="addMangersStaff" style="float: none" />
                            </form:form>

                        </div>
                    </c:if>


                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div class="chapter-title" style="margin-top: 20px;"> Obsługa dla złożonych zamówień </div>
                    <div>

                        <form:form modelAttribute="settingForm" method="post" action="/account-management">

                            <div style="float: left; width: 300px; margin-top: 15px;">Przypisany opiekun zamówień:</div>
                            <form:select path="selectedUser.assignWorker" cssClass="input-information" cssStyle="float: left;">

                                <c:forEach var="recruiters" items="${settingForm.allRecruiterList}">
                                    <form:option value="${recruiters.mail}">${recruiters.mail}</form:option>
                                </c:forEach>
                                <c:forEach var="managers" items="${settingForm.allManagerList}">
                                    <form:option value="${managers.mail}">${managers.mail}</form:option>
                                </c:forEach>

                            </form:select>
                            <div style="clear: both"></div>

                            <div style="float: left; width: 300px; margin-top: 15px;">Przypisany manager zamówień:</div>
                            <form:select path="selectedUser.manager"  cssClass="input-information" cssStyle="float: left;">

                                <c:forEach var="managers" items="${settingForm.allManagerList}">
                                    <form:option value="${managers.mail}">${managers.mail}</form:option>
                                </c:forEach>

                            </form:select>
                            <div style="clear: both"></div>
                            <br/>

                            <input type="submit" value="Zatwierdź wprowadzone zmiany" name="changeUserAssignWorkerAndManager">
                            <br/>

                        </form:form>
                    </div>


                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div class="chapter-title" style="margin-top: 20px;"> Zmiana stanowika użytkownika </div>
                    <div>
                        <form:form modelAttribute="settingForm" method="post" action="/account-management">

                            <div style="float: left; width: 300px; margin-top: 15px;">Stanowisko użytkownika:</div>
                            <form:select path="selectedUser.role" cssClass="input-information" cssStyle="float: left;">

                                <form:option value="client"> klient </form:option>
                                <form:option value="recruiter"> rekruter </form:option>
                                <form:option value="manager"> manager </form:option>

                            </form:select>
                            <div style="clear: both"></div>

                            <div class="chapter-border-line-half">
                                <div style="float: none; margin-top: 15px;">Aktywne uprawnienia administratora:</div>
                                <form:radiobutton path="selectedUser.admin" value="true" title="true" cssClass="input-information" cssStyle="float: none; width: 50%;"/><br/>
                            </div>

                            <div class="chapter-border-line-half">
                                <div style="float: none; margin-top: 15px;">Brak uprawnień administratora:</div>
                                <form:radiobutton path="selectedUser.admin" value="false" title="false" cssClass="input-information" cssStyle="float: none; width: 50%;"/><br/>
                            </div>
                            <div style="clear: both"></div>
                            <br/>

                            <input type="submit" value="Zatwierdź nowe uprawnienia" name="changeUserRoleAndStatus">
                            <br/>

                        </form:form>
                    </div>


                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div class="chapter-title" style="margin-top: 20px;"> Zmiana statusu użytkownika </div>
                    <div>
                        <form:form modelAttribute="settingForm" method="post" action="/account-management">

                            <div class="chapter-border-line-half">
                                <div style="float: none; margin-top: 15px;">Użytkownik aktywny:</div>
                                <form:radiobutton path="selectedUser.status" value="true" title="true" cssClass="input-information" cssStyle="float: none; width: 50%;"/><br/>
                            </div>

                            <div class="chapter-border-line-half">
                                <div style="float: none; margin-top: 15px;">Użytkownik zdezaktywowany:</div>
                                <form:radiobutton path="selectedUser.status" value="false" title="false" cssClass="input-information" cssStyle="float: none; width: 50%;"/><br/>
                            </div>
                            <div style="clear: both"></div>
                            <br/>

                            <input type="submit" value="Zatwierdź status użytkownika" name="changeUserStatus">
                            <br/>

                        </form:form>
                    </div>

                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div style="margin-top: 20px;">
                        <form modelAttribute="settingForm" method="post" action="/account-management">
                            <input type="submit" value="Zakończ edycję użytkownika" name="closeUserEditionCart" style="float: none;">
                        </form>
                    </div>

                </c:if>
            </div>


            <div class="chapter-title">
                Nowa lokalizacja
            </div>
            <div class="chapter-border" style="text-align: center; width: 70%; margin-left: 15%;">

                <c:if test="${!settingForm.showNewLocationCart}">
                    <form method="post" action="/account-management" style="margin-bottom: 0">
                        <input type="submit" style="float: none;" name="displayNewLocationCart" value="Wyświetl kartę nowej lokalizacji"/>
                    </form>
                </c:if>
                <c:if test="${settingForm.showNewLocationCart}">
                    <form:form modelAttribute="settingForm" cssStyle="margin-bottom: 0;" method="post" action="/account-management">

                        <div style="float: left; width: 200px; margin-top: 15px;">Podaj kod nowej lokalizacji:</div>
                            <input type="text" name="locationId" value="${settingForm.locationId}" style="float: left;" class="input-information" placeholder="Podaj kod nowej lokalizacji" minlength="3" maxlength="5" required="required" />
                        <div style="clear: both"></div>

                        <div style="float: left; width: 200px; margin-top: 15px;">Podaj nazwę lokalizacji:</div>
                            <input type="text" name="locationName" value="${settingForm.locationName}" style="float: left;" class="input-information" placeholder="Podaj nazwę lokalizacji" minlength="3" maxlength="50" required="required" />
                        <div style="clear: both"></div>

                        <div style="float: left; width: 200px; margin-top: 15px;">Wybierz region nowej lokalizacji:</div>
                        <form:select path="locationArea" style="float: left;" class="input-information" placeholder="Wybierz region" required="required">
                            <form:option value="N">Północ</form:option>
                            <form:option value="S">Południe</form:option>
                            <form:option value="E">Wschód</form:option>
                            <form:option value="W">Zachód</form:option>
                        </form:select>
                        <div style="clear: both"></div>

                        <div style="float: left; width: 200px; margin-top: 15px;">Podaj nazwę organizacji:</div>
                            <input type="text" name="locationOrganization" value="${settingForm.locationOrganization}" style="float: left;" class="input-information" placeholder="Podaj nazwę organizacji lokalizacji" minlength="3" maxlength="50" required="required" />
                        <div style="clear: both"></div>

                        <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em; margin-bottom: 1em;"></div>
                            <input type="submit" value="Dodaj nową lokalizację" name="addNewLocation">
                        <div style="clear: both"></div>

                    </form:form>

                    <form method="post" action="/account-management" style="margin-bottom: 0;">
                        <input type="submit" style="float: none;" name="hideNewLocationCart" value="Ukryj kartę nowej lokalizacji"/>
                    </form>
                </c:if>
            </div>


            <div class="chapter-title">
                Zarządzanie obszarami lokalizacji
            </div>
            <div class="chapter-border" style="text-align: center; width: 70%; margin-left: 15%;">

                <div> Wybrana lokalizacja: ${settingForm.locationInstance.name}
                <form:form modelAttribute="settingForm" cssStyle="margin-bottom: 0;" method="post" action="/account-management">
                    <form:select path="editingLocationId" name="editingLocation" cssClass="input-information">
                        <c:forEach items="${settingForm.allLocationList}" var="loc">
                            <form:option value="${loc.locationId}">${loc.locationId} | ${loc.organization} | ${loc.locationName}</form:option>
                        </c:forEach>
                    </form:select>
                    <input type="submit" style="margin-top: 12px; width: 100px;" value="Wybierz" name="selectLocation">
                </form:form>
                </div>

                <c:if test="${not empty settingForm.editingLocationId}">
                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div class="chapter-title" style="margin-top: 20px;">
                        Zmiana nazwy organizacji
                    </div>
                    <div style="height: 7em;">
                        <form:form modelAttribute="settingForm" method="post" action="/account-management">
                            <div style="float: left; width: 200px; margin-top: 15px;">Podaj nową organizację:</div>
                            <input name="editingLocationOrganization" value="${settingForm.editingLocationOrganization}" style="float: left;" class="input-information" title="Nowe organizacja" placeholder="Podaj organizacje" minlength="3" maxlength="30" required/>
                            <div style="clear: both"></div>
                            <input type="submit" value="Zapisz nowe dane" name="changeLocationOrganization">
                        </form:form>
                    </div>

                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div style="height: 1em;"></div>

                    <div class="chapter-title" style="margin-top: 20px;">
                        Zarządzanie obszarami
                    </div>
                    <table style="border-bottom: darkgrey 1px solid; margin-bottom: 10px" title="Oddział">
                        <thead>
                            <tr>
                                <th>Nazwa obszaru</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${settingForm.locationInstance.department.values()}" var="dep">
                            <tr>
                                <td>${dep}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div style="clear: both"></div>

                    <div class="chapter-border-line-half" style="border-right: 1px solid grey">
                        <form:form modelAttribute="settingForm" cssStyle="margin-bottom: 0;" method="post" action="/account-management">
                            <input type="text" name="selectedLocationDepartment" value="${settingForm.selectedLocationDepartment}"
                                   style="margin-bottom: 0;" class="input-information"
                                   placeholder="Podaj nazwę nowego obszaru" required="required" minlength="4" maxlength="70"/><br/>
                            <input type="submit" style="margin-top: 12px; width: 200px; float: none;" value="Dodaj" name="addDepartment">
                        </form:form>
                    </div>

                    <div class="chapter-border-line-half" style="width: 49%;">
                        <form:form modelAttribute="settingForm" cssStyle="margin-bottom: 0;" method="post" action="/account-management">
                            <form:select path="departmentToRemove" cssStyle="margin-bottom: 0;" class="input-information"><br/>
                                <c:forEach items="${settingForm.locationInstance.department.values()}" var="dep">
                                    <form:option value="${dep}">${dep}</form:option>
                                </c:forEach>
                            </form:select><br/>
                            <input type="submit" style="margin-top: 12px; width: 200px; float: none;" value="Usuń" name="removeDepartment">
                        </form:form>
                    </div>

                    <div style="clear: both"></div>
                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div style="height: 1em;"></div>

                    <div class="chapter-title" style="margin-top: 20px;">
                        Lista dystrybucyjna
                    </div>
                    <table style="border-bottom: darkgrey 1px solid; margin-bottom: 10px" title="Lista dystrybucyjna">
                        <thead>
                        <tr>
                            <th>Adres email</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${settingForm.locationInstance.distributionList.values()}" var="dep">
                            <tr>
                                <td>${dep}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div style="clear: both"></div>

                    <div class="chapter-border-line-half" style="border-right: 1px solid grey">
                        <form:form modelAttribute="settingForm" cssStyle="margin-bottom: 0;" method="post" action="/account-management">
                            <input type="email" name="inputedEmail" value="${settingForm.inputedEmail}"
                                   style="margin-bottom: 0;" class="input-information"
                                   placeholder="Podaj email" required="required" minlength="5" maxlength="50"/><br/>
                            <input type="submit" style="margin-top: 12px; width: 200px; float: none;" value="Dodaj" name="addEmail">
                        </form:form>
                    </div>

                    <div class="chapter-border-line-half" style="width: 49%;">
                        <form:form modelAttribute="settingForm" cssStyle="margin-bottom: 0;" method="post" action="/account-management">
                            <form:select path="emailIdToRemove" cssStyle="margin-bottom: 0;" class="input-information"><br/>
                                <c:forEach items="${settingForm.locationInstance.distributionList.keySet()}" var="emailId">
                                    <form:option value="${emailId}">${settingForm.locationInstance.distributionList.get(emailId)}</form:option>
                                </c:forEach>
                            </form:select>
                            <input type="submit" style="margin-top: 12px; width: 250px; float: none;" value="Usuń" name="removeEmail">
                        </form:form>
                    </div>

                    <div style="clear: both"></div>
                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div style="height: 1em;"></div>

                    <form style="margin-bottom: 0;" method="post" action="/account-management">
                        <input type="submit" value="Zakończ edycję lokalizacji" name="closeLocationEditionCart" style="float: none;">
                    </form>
                </c:if>
            </div>

            <c:if test="${user.moderator}">
                <div class="chapter-title">
                    Zarządzanie bazą danych
                </div>
                <div class="chapter-border" style="text-align: center; width: 70%; margin-left: 15%;">

                        <form  style="margin-bottom: 0;" method="post" action="/getDataBase">
                            <input type="submit" value="Pobierz bazę danych do pliku XML" name="getDataBase" style="float: none;">
                        </form>

                    <div style="clear: both"></div>
                    <div style="border-bottom: 2px solid #D80000; margin-left: 5%; width: 90%; height: 1em;"></div>
                    <div style="height: 1em;"></div>

                    <form:form  style="margin-bottom: 0;" method="post" action="/setDataBase" enctype="multipart/form-data" modelAttribute="fileModel">

                        <form:input type="file" title="Plik XML" placeholder="Wprowadź plik XML" class="input-information" path="file" name="newDataBase" required="required"/>
                        <br/>
                        <input type="submit" value="Wprowadź nową bazę danych z pliku XML" name="setNewDataBase" style="float: none;">

                    </form:form>

                </div>

                <div class="chapter-title" style="margin-top: 20px;">
                    Reset Analityki Zamówień
                </div>
                <div class="chapter-border" style="text-align: center; width: 70%; margin-left: 15%; height: auto;">
                    <form action="/account-management" method="post" style="height: auto; margin: 0">
                        <input type="submit" value="RESET" name="analyticsReset" title="Uruchom ponownie wątek analityki biznesowej zamówień" style="float: none;"/>
                    </form>
                </div>
        </c:if>

        </c:if>

        <form:form action="/menu" method="post" style="float: right; margin: 20px;"><input type="submit" value="Cofnij" style="width: 100px;"></form:form>
        <div style="clear:both;"></div>
    </div>

    <%@include file="../../META-INF/resources/htmlElements/footer.html" %>

</body>
</html>
