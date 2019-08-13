package com.bz.hos.services;

import com.bz.hos.db.BazaDanych;
import com.bz.hos.db.OrderTable;
import com.bz.hos.db.RecordTable;
import com.bz.hos.model.ReportModels.LocationReport;
import com.bz.hos.model.ReportModels.RangesForReport;
import com.bz.hos.model.ReportModels.UserReport;
import com.bz.hos.model.UserAndLocationEntity.User;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ReportServices {


    public RangesForReport getUpdateRangesForReport(User user, RangesForReport rangesForReport, RangesForReport savedRangesForReport) {

        //Check if there isn't any saved range to initiate rangesForReport
        if( savedRangesForReport == null || savedRangesForReport.getUserMail() == null || savedRangesForReport.getUserMail().isEmpty()){

            //Initiation new report
            savedRangesForReport = new RangesForReport();

            //identity of user
            savedRangesForReport.setUserRole(user.getRole());
            savedRangesForReport.setUserIsAdmin(user.isAdmin());
            savedRangesForReport.setUserMail(user.getMail());

            //location and staff of user
            savedRangesForReport.setUserLocationList(new ArrayList<>(user.getLocationList().values()));
            savedRangesForReport.setUserStaffList(new ArrayList<>(user.getStaffList().values()));

            if(!savedRangesForReport.getUserLocationList().isEmpty())
                savedRangesForReport.setLocation(savedRangesForReport.getUserLocationList().get(0).getId());

            //set dates ranges
            LocalDate today = LocalDate.now();
            LocalDate monthAgo = today.minusMonths(1);

            savedRangesForReport.setUserRangeFrom(monthAgo); savedRangesForReport.setUserRangeTo(today);
            savedRangesForReport.setLocationRangeFrom(monthAgo); savedRangesForReport.setLocationRangeTo(today);
            savedRangesForReport.setStaffRangeFrom(monthAgo); savedRangesForReport.setStaffRangeTo(today);
        }

        //Download ranges from sent FORM if exist
        if(rangesForReport.getFormUserRangeFrom()!=null && rangesForReport.getFormUserRangeTo()!=null){

            savedRangesForReport.setUserRangeFrom(LocalDate.parse(rangesForReport.getFormUserRangeFrom()));
            savedRangesForReport.setUserRangeTo(LocalDate.parse(rangesForReport.getFormUserRangeTo()));
        }

        if(rangesForReport.getFormLocationRangeFrom()!=null && rangesForReport.getFormLocationRangeTo()!=null){

            savedRangesForReport.setLocationRangeFrom(LocalDate.parse(rangesForReport.getFormLocationRangeFrom()));
            savedRangesForReport.setLocationRangeTo(LocalDate.parse(rangesForReport.getFormLocationRangeTo()));

            savedRangesForReport.setLocation(rangesForReport.getLocation());
        }

        if(rangesForReport.getFormStaffRangeFrom()!=null && rangesForReport.getFormStaffRangeTo()!=null){

            savedRangesForReport.setStaffRangeFrom(LocalDate.parse(rangesForReport.getFormStaffRangeFrom()));
            savedRangesForReport.setStaffRangeTo(LocalDate.parse(rangesForReport.getFormStaffRangeTo()));

            savedRangesForReport.setStaffUser(rangesForReport.getStaffUser());
        }

        //Validate dates
        checkDates(savedRangesForReport.getUserRangeFrom(), savedRangesForReport.getUserRangeTo());
        checkDates(savedRangesForReport.getLocationRangeFrom(), savedRangesForReport.getLocationRangeTo());
        checkDates(savedRangesForReport.getStaffRangeFrom(), savedRangesForReport.getStaffRangeTo());

        return savedRangesForReport;
    }

    private void checkDates(LocalDate from, LocalDate to){

        //if dates are empty they are supplying
        if( from==null || to==null ){

            to = LocalDate.now();
            from = to.minusMonths(1);
        }

        //checking if timeline is correct, if not replace date FROM with date TO
        if(from.isAfter(to)){

            LocalDate temp = to;
            to = from;
            from = temp;
        }
    }


    public UserReport getUserReportWithinRange(RangesForReport rangesForReport, String user) {

        UserReport userReport = new UserReport();
        LocalDate from = rangesForReport.getUserRangeFrom();
        LocalDate to = rangesForReport.getUserRangeTo();

        OrderTable orderTable;

        for(RecordTable recordTable: BazaDanych.getRecordList().values()){

            orderTable = BazaDanych.getOrderList().get(recordTable.getOrderId());

            if(orderTable.getOrderDate().isAfter(from.minusDays(1)) && orderTable.getOrderDate().isBefore(to.plusDays(1)) ) {

                if(!userReport.getOrderIds().contains(orderTable.getId())) {

                    if (orderTable.getSupplier().equals(user) || orderTable.getAssignWorker().equals(user) || orderTable.getManager().equals(user)) {

                        userReport.setUserOrderAmount(userReport.getUserOrderAmount() + 1);
                        switch (orderTable.getOrderStatus()) {
                            case "Oczekuje": userReport.setWaitingAmount(userReport.getWaitingAmount() + 1); break;
                            case "Zatwierdzone": userReport.setAcceptedAmount(userReport.getAcceptedAmount() + 1); break;
                            case "Odrzucone": userReport.setDeletedAmount(userReport.getDeletedAmount() + 1); break;
                            case "Realizacja": userReport.setRealizationAmount(userReport.getRealizationAmount() + 1); break;
                            case "Anulowane": userReport.setDelayedAmount(userReport.getDelayedAmount() + 1); break;
                            case "Zrealizowane": userReport.setRealizedAmount(userReport.getRealizedAmount() + 1); break;
                        }
                    }

                    userReport.setTotalOrderAmount(userReport.getTotalOrderAmount() + 1);
                    userReport.getOrderIds().add(orderTable.getId());
                }

                if (!userReport.getCandidateRealizationList().containsKey(orderTable.getLocationId()))
                    userReport.getCandidateRealizationList().put(orderTable.getLocationId(), new UserReport.CandidateRealizationReport(
                            orderTable.getOrganization(), orderTable.getLocationId(), 0, 0, 0)
                    );

                userReport.getCandidateRealizationList().get(orderTable.getLocationId()).addOrderedCandidatesAmount(recordTable.getAccountOrdered());
                userReport.getCandidateRealizationList().get(orderTable.getLocationId()).addAcceptedCandidatesAmount(recordTable.getAccountAccepted());
                userReport.getCandidateRealizationList().get(orderTable.getLocationId()).addRealizedCandidatesAmount(recordTable.getAccountDelivered());

                userReport.setTotalCandidateAmount( userReport.getTotalCandidateAmount() + recordTable.getAccountDelivered() );
            }
        }

        return userReport;
    }

    public LocationReport getLocationReport(RangesForReport rangesForReport) {

        LocationReport locationReport = new LocationReport();
        LocalDate from = rangesForReport.getLocationRangeFrom();
        LocalDate to = rangesForReport.getLocationRangeTo();

        String locationId = rangesForReport.getLocation();
        OrderTable orderTable;

        LocationReport.OrderSupplierReport orderSupplierReport;

        if(locationId != null && !locationId.isEmpty())
            for(RecordTable recordTable: BazaDanych.getRecordList().values()){

                orderTable = BazaDanych.getOrderList().get(recordTable.getOrderId());

                if(orderTable.getOrderDate().isAfter(from.minusDays(1)) && orderTable.getOrderDate().isBefore(to.plusDays(1)) && orderTable.getLocationId().equals(locationId)){

                    if(!locationReport.getOrderIds().contains(orderTable.getId())){

                        switch (orderTable.getOrderStatus()) {
                            case "Oczekuje": locationReport.setWaitingAmount(locationReport.getWaitingAmount() + 1); break;
                            case "Zatwierdzone": locationReport.setAcceptedAmount(locationReport.getAcceptedAmount() + 1); break;
                            case "Realizacja": locationReport.setRealizationAmount(locationReport.getRealizationAmount() + 1); break;
                            case "Anulowane": locationReport.setDelayedAmount(locationReport.getDelayedAmount() + 1); break;
                            case "Zrealizowane": locationReport.setRealizedAmount(locationReport.getRealizedAmount() + 1); break;
                            case "Odrzucone": locationReport.setDeletedAmount(locationReport.getDeletedAmount() + 1); break;
                        }

                        locationReport.setTotalOrderAmount(locationReport.getTotalOrderAmount()+1);
                        locationReport.getOrderIds().add(orderTable.getId());

                        if(!locationReport.getOrderSupplierReportList().containsKey(orderTable.getSupplier())){

                            orderSupplierReport = new LocationReport.OrderSupplierReport(
                                    orderTable.getSupplier(), BazaDanych.getUserList().get(orderTable.getSupplier()).getOrganization(),
                                    BazaDanych.getUserList().get(orderTable.getSupplier()).getRole(),1 );

                            locationReport.getOrderSupplierReportList().put( orderTable.getSupplier(), orderSupplierReport );

                        }
                        else
                            locationReport.getOrderSupplierReportList().get(orderTable.getSupplier()).addToOrderAmount(1);
                    }

                    locationReport.setOrderedCandidateAmount( locationReport.getOrderedCandidateAmount() + recordTable.getAccountOrdered());
                    locationReport.setAcceptedCandidateAmount( locationReport.getAcceptedCandidateAmount() + recordTable.getAccountAccepted());
                    locationReport.setDeliveredCandidateAmount( locationReport.getDeliveredCandidateAmount() + recordTable.getAccountDelivered());

                    if( (orderTable.getOrderStatus().equals("Oczekuje") || orderTable.getOrderStatus().equals("Zatwierdzone") || orderTable.getOrderStatus().equals("Realizacja")) &&
                            (recordTable.getStatus().equals("Oczekuje") || recordTable.getStatus().equals("Zatwierdzone") || recordTable.getStatus().equals("W realizacji")) )
                        locationReport.setWaitingCandidateAmount( locationReport.getWaitingCandidateAmount() +  recordTable.getAccountAccepted() - recordTable.getAccountDelivered() );

                    if( (orderTable.getOrderStatus().equals("Anulowane") || orderTable.getOrderStatus().equals("Zrealizowane")) ||
                             recordTable.getStatus().equals("Anulowane") || recordTable.getStatus().equals("Zrealizowane") )
                        locationReport.setUnaccomplishCandidateAmount( locationReport.getUnaccomplishCandidateAmount() + recordTable.getAccountAccepted() - recordTable.getAccountDelivered() );
                }
            }

        return locationReport;
    }

    public UserReport getStaffReportList(RangesForReport rangesForReport) {

        return getUserReportWithinRange(rangesForReport, rangesForReport.getStaffUser());
    }
}
