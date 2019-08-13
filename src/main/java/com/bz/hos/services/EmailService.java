package com.bz.hos.services;

import com.bz.hos.db.BazaDanych;
import com.bz.hos.db.CandidateTable;
import com.bz.hos.db.ContractTable;
import com.bz.hos.db.LocationEmailTable;
import com.bz.hos.model.NewOrderForm.NewOrder;
import com.bz.hos.model.NewOrderForm.NewRecord;
import com.bz.hos.model.OrdersEntity.Order;
import com.bz.hos.model.OrdersEntity.Record;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendOrderConfirmation(NewOrder savedOrder) {

        String[] to;
        String subject;
        StringBuilder content = new StringBuilder();

        List<String> distributionList = new ArrayList<>();

        distributionList.add(savedOrder.getSupplier());
        if(!distributionList.contains(savedOrder.getAssignWorker()))
            distributionList.add(savedOrder.getAssignWorker());
        if(!distributionList.contains(savedOrder.getManager()))
            distributionList.add(savedOrder.getManager());

        for(LocationEmailTable distribution: BazaDanych.getLocationEmailTableList().values())
            if(distribution.getLocation().equals(savedOrder.getLocationId()) && !distributionList.contains(distribution.getEmail()))
                distributionList.add(distribution.getEmail());

        to = new String[distributionList.size()];
        distributionList.toArray(to);

        subject = "AOS - Nowe zamówienie: " + savedOrder.getId();

        content.append("    <div>\n").
                append("        <div style=\"width: 450px; text-align:left; font-family: 'Gill Sans MT';border-radius:2em; margin: 1em; padding: 2em;\" >\n").

                append("            <div style=\"font-weight: bold; text-align: center;\">Witaj!</div>\n").

                append("            <div style=\"font-weight: bold; text-align: center;\"> Zostalo wprowadzone zamowienie: ").append(savedOrder.getId()).append("</div>\n").
                append("            <div>Autor: ").append(savedOrder.getSupplier()).append("</div>\n").
                append("            <br/>\n").

                append("            <div>Lokalizacja: ").append(savedOrder.getLocation()).append("</div>\n").
                append("            <div>Obszar: ").append(savedOrder.getDepartment()).append("</div>\n").
                append("            <br/>\n").

                append("            <div>Opiekun: ").append(savedOrder.getAssignWorker()).append(" </div>\n").
                append("            <div>Manager: ").append(savedOrder.getManager()).append("</div>\n").
                append("            <div>Kierownik obszaru: ").append(savedOrder.getDepartmentManager()).append("</div>\n").
                append("            <br/>\n").

                append("            <div>Data zamówienia: ").append(savedOrder.getOrderDate()).append("</div>\n").
                append("            <div>Proponowana data realizacji: ").append(savedOrder.getRealizationDate()).append("</div>\n").

                append("            <br/>\n").
                append("            <div>Uwagi: ").append(savedOrder.getComment()==null?"Brak uwag do zamówienia.":savedOrder.getComment()).append("</div>\n").
                append("            <br/>\n").

                append("            <table style=\"margin: 20px;\">\n").

                append("                <caption>Zamawiane stanowiska: ").append(savedOrder.getRecordList().size()).append("</caption>\n").

                append("                <thead>\n").
                append("                    <tr>\n").
                append("                        <td style=\"background-color: ghostwhite; padding: 10px;\">Nazwa stanowiska</td>\n").
                append("                        <td style=\"background-color: ghostwhite; padding: 10px;\">Zamówiona ilosc</td>\n").
                append("                        <td style=\"background-color: ghostwhite; padding: 10px;\">Praca od</td>\n").
                append("                        <td style=\"background-color: ghostwhite; padding: 10px;\">Praca do</td>\n").
                append("                        <td style=\"background-color: ghostwhite; padding: 10px;\">Wynagrodzenie </td>\n").
                append("                    </tr>\n").
                append("                </thead>\n").

                append("                <tbody>\n");

        for(NewRecord record: savedOrder.getRecordList())
            content.append("                   <tr>\n").
                    append(   "                     <td style=\"border: 1px solid black; padding: 10px;\"> ").append(record.getJobName()).append(" </td>\n").
                    append(   "                     <td style=\"border: 1px solid black; padding: 10px;\"> ").append(record.getAccountOrdered()).append(" </td>\n").
                    append(   "                     <td style=\"border: 1px solid black; padding: 10px;\"> ").append(record.getWorkingFrom()).append(" </td>\n").
                    append(   "                     <td style=\"border: 1px solid black; padding: 10px;\"> ").append(record.getWorkingTo()).append(" </td>\n").
                    append(   "                     <td style=\"border: 1px solid black; padding: 10px;\"> ").append(record.getPaymentValue()).append("zl/").append(record.getPaymentType()).append(" </td>\n").
                    append(   "                 </tr>\n");

        content.append(  "                 </tbody>\n").
                append(   "             </table>\n").

                append(   "         Udanego dnia! <br/>\n").
                append(   "         <a href=\"https://aos-poc.azurewebsites.net/\">System AOS </a> <br/>\n").

                append(   "     </div>\n").
                append(   " </div>\n");

        sendMail(to, subject, content.toString());
    }

    public void sendStatusChangeInformation(Order order, boolean isRecordChanged, boolean isAcceptedAmountChanged, boolean isOrderedAmountChanged, String changedRecordId, String user){

        List<String> distributionList = new ArrayList<>();

        distributionList.add(user);
        if(!distributionList.contains(order.getAssignWorker()))
            distributionList.add(order.getAssignWorker());
        if(!distributionList.contains(order.getManager()))
            distributionList.add(order.getManager());
        if(!distributionList.contains(order.getSupplier()) && !isAcceptedAmountChanged)
            distributionList.add(order.getSupplier());

        for(LocationEmailTable distribution: BazaDanych.getLocationEmailTableList().values())
            if( distribution.getLocation().equals(order.getLocationId()) && !distributionList.contains(distribution.getEmail()) &&
              ( !isAcceptedAmountChanged || (BazaDanych.getUserList().get(distribution.getEmail())!=null && !BazaDanych.getUserList().get(distribution.getEmail()).getRole().equals("client"))))
                    distributionList.add(distribution.getEmail());

        String[] to = new String[distributionList.size()];
        distributionList.toArray(to);

        String subject;
        if(isRecordChanged)
            subject = "AOS - Zmiana statusu stanowiska: " + order.getRecordList().get(changedRecordId).getRecordId()
                        + " " + order.getRecordList().get(changedRecordId).getJobName();
        else if(isAcceptedAmountChanged)
            subject = "AOS - Zmiana zatwierdzonej ilosci dla stanowiska: " + order.getRecordList().get(changedRecordId).getRecordId()
                    + " " + order.getRecordList().get(changedRecordId).getJobName();
        else if (isOrderedAmountChanged)
            subject = "AOS - Zmiana zamówionej ilosci dla stanowiska: " + order.getRecordList().get(changedRecordId).getRecordId()
                    + " " + order.getRecordList().get(changedRecordId).getJobName();
        else
            subject = "AOS - Zmiana statusu zamówienia: " + order.getId();

        StringBuilder content = new StringBuilder().
                append("<div> \n").

                append("Witaj!<br/> \n").
                append("Uzytkownik: ").append(user).append(" zmodyfikowal zamowienie: \n").append(order.getId()).append("<br/><br/> \n").

                append("Dane zamówienia: <br/> \n").
                append("Autor: ").append(order.getSupplier()).append("</br> \n").
                append("Status: ").append(order.getOrderStatus()).append("</br><br/> \n").


                append("Lokalizacja: ").append(order.getLocation()).append("</br> \n").
                append("Obszar: ").append(order.getDepartment()).append("</br> </br> \n").

                append("Opiekun: ").append(order.getAssignWorker()).append(" </br> \n").
                append("Manager: ").append(order.getManager()).append("</br> \n").
                append("Kierownik obszaru: ").append(order.getDepartmentManager()).append("</br> </br> \n").

                append("Data zamówienia: ").append(order.getOrderDate()).append("</br> \n").
                append("Proponowana data realizacji: ").append(order.getRealizationDate()).append("</br> \n").

                append("Uwagi: ").append(order.getComment()==null?"Brak uwag do zamówienia.":order.getComment()).append("</br> <br/> \n").

                append("<table style=\"margin: 20px;\">\n").
                append("<caption>Zamawiane stanowiska: ").append(order.getRecordList().size()).append("</caption>\n").

                append("<thead>\n").
                append("<tr>\n").
                append("    <td style=\"background-color: ghostwhite; padding: 10px;\">Id i nazwa</td>\n").
                append("    <td style=\"background-color: ghostwhite; padding: 10px;\">Zamówiona ilosc</td>\n").
                append("    <td style=\"background-color: ghostwhite; padding: 10px;\">Zatwierdzona ilosc</td>\n").
                append("    <td style=\"background-color: ghostwhite; padding: 10px;\">Zrealizowana ilosc</td>\n").
                append("    <td style=\"background-color: ghostwhite; padding: 10px;\">Praca od</td>\n").
                append("    <td style=\"background-color: ghostwhite; padding: 10px;\">Status</td>\n").
                append("</tr>\n").
                append("</thead>\n").

                append("<tbody>\n");

        for(Record record: order.getRecordList().values())
            content.append("<tr>\n").
                    append("    <td style=\"border-left: 1px solid black; border-right: 1px solid black; text-align: center;\"> ").
                                append(record.getRecordId()).append(' ').append(record.getJobName()).append(" </td>\n").
                    append("    <td style=\"border-right: 1px solid black; text-align: center;\"> ").append(record.getAccountOrdered()).append(" </td>\n").
                    append("    <td style=\"border-right: 1px solid black; text-align: center;\"> ").append(record.getAccountAccepted()).append(" </td>\n").
                    append("    <td style=\"border-right: 1px solid black; text-align: center;\"> ").append(record.getAccountDelivered()).append(" </td>\n").
                    append("    <td style=\"border-right: 1px solid black; text-align: center;\"> ").append(record.getWorkingFrom()).append(" </td>\n").
                    append("    <td style=\"border-right: 1px solid black; text-align: center;\"> ").append(record.getStatus()).append(" </td>\n").
                    append("</tr>\n");

        content.append("</tbody>\n").
                append("</table>\n").

                append("Udanego dnia! <br/>\n").
                append("<a href=\"https://aos-poc.azurewebsites.net/\">System AOS </a> <br/>\n").

                append("<div> \n");

        sendMail(to, subject, content.toString());
    }

    void sendOrderAnalyticsReport(Map<String, List<String[]>> reportMap) {

        if(reportMap.isEmpty())
            return;

        String[] to;
        String subject = "AOS - Raport z dnia: " + java.time.LocalDate.now().toString() + " dla: ";
        StringBuilder content;

        List<String[]> alertedOrderList;

        for(String user: reportMap.keySet()){

            alertedOrderList = reportMap.get(user);
            if(alertedOrderList.isEmpty())
                continue;

            content = new StringBuilder();
            content.append("<div> \n").
                    append("Witaj!").
                    append("Ilosc niezweryfikowanych lub opóznionych zamówien: ").append(alertedOrderList.size()).append("<br/>\n").
                    append("<br/> \n").
                    append("<table> \n").
                    append("    <tr> \n").
                    append("        <th>").append("Id zamówienia").append("</th>").
                    append("        <th>").append("Data aktualizacji").append("</th>").
                    append("        <th>").append("Lokalizacja").append("</th>").
                    append("        <th>").append("Status zamówienia").append("</th>").
                    append("    </tr> \n");

            for(String[] orderData: alertedOrderList)
                content.append("<tr>").
                        append("    <td>").append(orderData[0]).append("</td>").
                        append("    <td>").append(orderData[1]).append("</td>").
                        append("    <td>").append(orderData[2]).append("</td>").
                        append("    <td>").append(orderData[3]).append("</td>").
                        append("<tr>");

            content.append("</table> \n").
                    append("<br/> \n").
                    append("Zaleca sie weryfikacje stanu zamówien i kontakt z klientem <br/> \n").
                    append("<br/> \n").
                    append("Udanego dnia! <br/>\n").
                    append("<a href=\"https://aos-poc.azurewebsites.net/\">System AOS </a> <br/>\n").
                    append("</div> \n");

            to = new String[] {user};
            sendMail(to, subject + user, content.toString());
        }
    }

    void sendNewContractsEndDate(String user, String locationId, Map<String, java.time.LocalDate> contractDateMap){

        String[] to;
        String subject;
        StringBuilder content = new StringBuilder();

        List<String> distributionList = new ArrayList<>();
        distributionList.add(user);
        for(LocationEmailTable distribution: BazaDanych.getLocationEmailTableList().values())
            if(distribution.getLocation().equals(locationId) && !distributionList.contains(distribution.getEmail()))
                distributionList.add(distribution.getEmail());

        to = new String[distributionList.size()];
        distributionList.toArray(to);

        subject = "Uwaga! Zmiana rozwiązania umów dla poniższych kontraktów";

        content.append("Witaj!<br/> \n").
                append("Uzytkownik: ").append(user).append(" zmienil daty rozwiazania umów<br/>\n").
                append("Lokalizacja: ").append(BazaDanych.getLocationList().get(locationId).getName()).append("<br/>\n").
                append("<br/>\n").
                append("<table>").
                append("    <tr>").
                append("        <th>Id kontraktu</th>").
                append("        <th>Id pracownika</th>").
                append("        <th>Imie</th>").
                append("        <th>Nazwisko</th>").
                append("        <th>Stara data</th>").
                append("        <th>Nowa data</th>").
                append("    </tr>");

        ContractTable contractTable;
        CandidateTable candidateTable;
        String oldDate, newDate;
        for(String contractId: contractDateMap.keySet()){

            contractTable = BazaDanych.getContractList().get(contractId);
            candidateTable = BazaDanych.getCandidateList().get(contractTable.getCandidateId());
            oldDate = contractDateMap.get(contractId)!=null ? contractDateMap.get(contractId).toString() : "brak";
            newDate = contractTable.getCandidateWorkTo()!=null ? contractTable.getCandidateWorkTo().toString() : "brak";

            content.append("<tr>").
                    append("    <td>").append(contractId).append("</td>\n").
                    append("    <td>").append(contractTable.getCandidateId()).append("</td>\n").
                    append("    <td>").append(candidateTable.getCandidateName()).append("</td>\n").
                    append("    <td>").append(candidateTable.getCandidateSurname()).append("</td>\n").
                    append("    <td>").append(oldDate).append("</td>\n").
                    append("    <td>").append(newDate).append("</td>\n").
                    append("</tr>\n");
        }

        content.append("</table> \n").
                append("<br/> \n").
                append("Zaleca sie weryfikacje zatrudnionych pracowników i kontakt z klientem <br/> \n").
                append("<br/> \n").
                append("Udanego dnia! <br/>\n").
                append("<a href=\"https://aos-poc.azurewebsites.net/\">System AOS </a> <br/>\n").
                append("</div> \n");

        sendMail(to, subject, content.toString());
    }

    private void sendMail(String[] to, String subject, String content){

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);

            message.setContent(content, "text/html");
            message.setHeader("test","value");

//            emailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    void sendDataBaseOnMails(String pathToFile){
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo("aos.info.noresponce@gmail.com");
            helper.setSubject("data-base recovery");
            helper.setText(LocalDate.now().toString().concat("\n").
                            concat(String.valueOf(LocalTime.now().getHourOfDay())).concat(":").
                            concat(String.valueOf(LocalTime.now().getMinuteOfHour())).concat(":").
                            concat(String.valueOf(LocalTime.now().getSecondOfMinute())).concat("\n").
                            concat(String.valueOf(LocalTime.now().getMillisOfDay()).concat("\n")));

            helper.addAttachment("DataBase.xml", new FileSystemResource(new File(pathToFile)));

//            emailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}