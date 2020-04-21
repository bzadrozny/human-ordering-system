package com.bz.hos.services;

import com.bz.hos.WebApplicationSettings.controller.HomeController;
import com.bz.hos.db.BazaDanych;
import com.bz.hos.db.LocationTable;
import com.bz.hos.db.OrderTable;
import com.bz.hos.db.RecordTable;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class OrderDataAnalyticsService extends Thread{

    private static int dayOfReport = 5;
    private final String xmlFilepath;
    private boolean alive;

    final private EmailService emailService;
    final private UserServices userServices;

    @Autowired
    public OrderDataAnalyticsService(EmailService emailService, UserServices userServices) {
        this.emailService = emailService;
        this.userServices = userServices;
        xmlFilepath = HomeController.xmlFilepath;
        alive = true;
    }

    @Override
    public void run() {
        this.setName("OrderDataAnalyticsThread");
        long nextWaitingPeriodValue = 0;

        while(alive){
            startValidation();
            if (LocalDate.now().getDayOfWeek() == dayOfReport) sendReports();

            nextWaitingPeriodValue = getNextWaitingPeriodValue();
            try{ sleep(nextWaitingPeriodValue); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    void stopAnalytics() { alive = false; interrupt(); }

    synchronized
    private Long getNextWaitingPeriodValue(){
        //Every 2 minutes - TESTs
//        return 2*60*1000L;

        //Accurate Analytic in period of
        final int days = 1;

        //At clock:
        final int hour = 1;
        final int minutes = 1;
        final int seconds = 1;

        return  + 1000*60*60*24L* days
                + 1000*60*60L* hour
                + 1000*60L* minutes
                + 1000L* seconds
                - LocalTime.now().getMillisOfDay();
    }

    synchronized
    private void startValidation(){

        userServices.saveXmlFile(xmlFilepath);
        emailService.sendDataBaseOnMails(xmlFilepath);

        OrderTable orderTable;
        RecordTable recordTable;
        StringBuilder orderZamId;
        StringBuilder orderRepId;
        boolean isAlerted = false;

        for(LocationTable locationList: BazaDanych.getLocationList().values()){
            for(int i=1; i<=locationList.getLastOrderNo(); i++) {

                orderZamId = new StringBuilder("#ZAM/").append(locationList.getId()).append('/').append(i);
                orderRepId = new StringBuilder("#REP/").append(locationList.getId()).append('/').append(i);
                orderTable = BazaDanych.getOrderList().get(orderZamId.toString())!=null ?
                        BazaDanych.getOrderList().get(orderZamId.toString()) :
                        BazaDanych.getOrderList().get(orderRepId.toString());

                if(orderTable!=null) {
                    if (orderTable.getOrderStatus().equals("Oczekuje") || orderTable.getOrderStatus().equals("Zatwierdzone") || orderTable.getOrderStatus().equals("Realizacja")) {

                        if (orderTable.getOrderStatus().equals("Oczekuje") && LocalDate.now().isAfter(LocalDate.parse(orderTable.getOrderDate().plusDays(7L).toString())))
                            isAlerted = true;

                        for(int j=1; j<=orderTable.getLastRecordNo(); j++)
                        {
                            recordTable = BazaDanych.getRecordList().get(orderTable.getId().concat("/").concat(String.valueOf(j)));
                            if (recordTable!=null)
                            {
                                if(recordTable.getUpdateDate().isAfter(orderTable.getUpdateDate()))
                                    orderTable.setUpdateDate(recordTable.getUpdateDate());

                                if(LocalDate.now().isAfter(LocalDate.parse(recordTable.getUpdateDate().plusDays(7L).toString()))
                                && (recordTable.getStatus().equals("Oczekuje") || recordTable.getStatus().equals("Zatwierdzone") || recordTable.getStatus().equals("W realizacji")))
                                {
                                    recordTable.setAlerted(true);
                                    isAlerted = true;
                                }
                                else
                                    recordTable.setAlerted(false);
                            }
                        }

                        orderTable.setAlerted(isAlerted);
                        isAlerted = false;

                        orderTable.setDelayed(LocalDate.now().isAfter(LocalDate.parse(orderTable.getRealizationDate().toString())));

                    } else
                        orderTable.setAlerted(false);
                }
            }
        }
    }

    synchronized
    private void sendReports(){

        Map<String, List<String[]>> reportMap = new HashMap<>();

        for(OrderTable orderTable: BazaDanych.getOrderList().values())
            if(orderTable.isAlerted() || orderTable.isDelayed()){
                if(!reportMap.containsKey(orderTable.getManager()))
                    reportMap.put(orderTable.getManager(), new ArrayList<>());

                reportMap.get(orderTable.getManager()).add(new String[]{orderTable.getId(), orderTable.getUpdateDate().toString(), orderTable.getLocation(), orderTable.getOrderStatus()});
            }

        emailService.sendOrderAnalyticsReport(reportMap);
    }


}