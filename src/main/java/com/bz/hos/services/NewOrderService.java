package com.bz.hos.services;

import com.bz.hos.db.BazaDanych;
import com.bz.hos.db.OrderTable;
import com.bz.hos.db.RecordTable;
import com.bz.hos.model.NewOrderForm.NewOrder;
import com.bz.hos.model.NewOrderForm.NewRecord;
import com.bz.hos.model.UserAndLocationEntity.Location;
import com.bz.hos.model.UserAndLocationEntity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NewOrderService {

    public NewOrder newOrderInitialization(NewOrder savedOrder, User user){

        if(savedOrder == null || savedOrder.getManager() == null){
            savedOrder = new NewOrder();
            savedOrder.initialize();

            if(user.getLocationList().values().iterator().hasNext()) {
                Location loc = user.getLocationList().values().iterator().next();
                savedOrder.setLocation(loc.getName());
                savedOrder.setLocationId(loc.getId());
                if(loc.getDepartment().values().iterator().hasNext())
                    savedOrder.setDepartment(loc.getDepartment().values().iterator().next());
            }

            savedOrder.setSupplier(user.getMail());
            savedOrder.setManager(user.getManager());
            savedOrder.setAssignWorker(user.getAssignWorker());
        }

        return savedOrder;
    }

    public NewOrder updateSpecification(NewOrder newOrder, NewOrder savedOrder){

        if(newOrder.getRealizationDate()!=null && !newOrder.getRealizationDate().isEmpty())
            savedOrder.setRealizationDate(newOrder.getRealizationDate());

        if(newOrder.getLocationId()!=null && !newOrder.getLocationId().isEmpty()) {
            savedOrder.setLocationId(newOrder.getLocationId());
        }

        if(newOrder.getDepartment()!=null && !newOrder.getDepartment().isEmpty())
            savedOrder.setDepartment(newOrder.getDepartment());

        if(newOrder.getOrderCategory()!=null && !newOrder.getOrderCategory().isEmpty())
            savedOrder.setOrderCategory(newOrder.getOrderCategory());

        if(newOrder.getDepartmentManager()!=null && !newOrder.getDepartmentManager().isEmpty())
            savedOrder.setDepartmentManager(newOrder.getDepartmentManager());

        if(newOrder.getComment()!=null && !newOrder.getComment().isEmpty())
            savedOrder.setComment(newOrder.getComment());

        return savedOrder;
    }

    public NewOrder updateRealization(NewOrder newOrder, NewOrder savedOrder){

        if(newOrder.getAssignWorker() != null)
            savedOrder.setAssignWorker(newOrder.getAssignWorker());

        if(newOrder.getManager() != null)
            savedOrder.setManager(newOrder.getManager());

        if(newOrder.getOrderStatus() != null)
            savedOrder.setOrderStatus(newOrder.getOrderStatus());

        return savedOrder;
    }

    public NewOrder validateNewRecord(NewOrder newOrder, NewOrder savedOrder, String dateFrom, String dateTo){

        NewRecord newRecord = newOrder.getNewRecord();

        if(newRecord == null){
            savedOrder.initializeRecord();
            return savedOrder;
        }

        newRecord.setWorkingFrom(dateFrom);
        newRecord.setWorkingTo(dateTo);

        if(validateOrderedAccount(newRecord)) {
                savedOrder.addNewRecord(newRecord);
                savedOrder.initializeRecord();
        } else
            savedOrder.setNewRecord(newOrder.getNewRecord());

        return savedOrder;
    }

    private boolean validateOrderedAccount(NewRecord record) {

        String orderedAccount = record.getAccountOrdered();
        StringBuilder validatedValue = new StringBuilder();

        for(int i = 0; i < orderedAccount.length(); i++)
        {
            if(Character.isDigit(orderedAccount.charAt(i)))
                validatedValue.append(orderedAccount.charAt(i));
        }
        orderedAccount = validatedValue.toString();

        if(orderedAccount.length() < 1 || Integer.parseInt(orderedAccount) < 1 )
            return false;

        record.setAccountOrdered(orderedAccount);
        return true;
    }

    public NewOrder editRecord(String recNo, NewOrder savedOrder){

        int recordNo = Integer.parseInt(recNo);
        savedOrder.setNewRecord(savedOrder.getRecordList().get(recordNo));
        savedOrder.getRecordList().remove(recordNo);
        return savedOrder;
    }

    public NewOrder deleteRecord(String recNo, NewOrder savedOrder){

        int recordNo = Integer.parseInt(recNo);
        savedOrder.getRecordList().remove(recordNo);
        return savedOrder;
    }

    public boolean validateOrderConfirmation(NewOrder newOrder, User user){

        if(newOrder == null || newOrder.getLocationId()==null || newOrder.getDepartment()==null || newOrder.getOrderCategory()==null || newOrder.getDepartmentManager()==null)
            return false;

        if(newOrder.getRecordList().isEmpty())
            return false;

        if(newOrder.getSupplier() == null)
           newOrder.setSupplier(user.getMail());

        if(newOrder.getOrganization() == null) {
            Location location = user.getLocationList().get(newOrder.getLocationId());
            if(location==null)
                return false;

            newOrder.setLocation(location.getName());
            newOrder.setOrganization(location.getOrganization());
            newOrder.setArea(location.getArea());
        }

        if(newOrder.getAssignWorker()==null)
            newOrder.setAssignWorker(user.getAssignWorker());

        if(newOrder.getOrderStatus()==null)
            newOrder.setOrderStatus("Oczekuje");

        if(newOrder.getComment() != null)
            newOrder.setComment(newOrder.getComment().trim());
        else
            newOrder.setComment("");

        newOrder.setId( "#" + newOrder.getOrderCategory()  + "/" + newOrder.getLocationId() + "/" + String.valueOf(BazaDanych.getLocationList().get(newOrder.getLocationId()).getLastOrderNo()+1));

        return true;
    }

    public void addNewOrderToDataBase(NewOrder savedOrder){

        OrderTable order = new OrderTable(
                savedOrder.getId(),
                savedOrder.getOrderCategory(),
                LocalDate.parse(savedOrder.getOrderDate()),
                LocalDate.parse(savedOrder.getRealizationDate()),
                savedOrder.getArea(),
                savedOrder.getDepartment(),
                savedOrder.getDepartmentManager(),
                savedOrder.getComment(),
                savedOrder.getOrderStatus(),
                savedOrder.getSupplier(),
                savedOrder.getAssignWorker(),
                savedOrder.getManager(),
                savedOrder.getDelayed(),
                savedOrder.getLocationId(),
                savedOrder.getLocation(),
                savedOrder.getOrganization()
        );
        order.setLastRecordNo(savedOrder.getRecordList().size());
        BazaDanych.getOrderList().put(order.getId(),order);
        BazaDanych.getLocationList().get(order.getLocationId()).setLastOrderNo(
                BazaDanych.getLocationList().get(order.getLocationId()).getLastOrderNo()+1
        );

        int i = 1;
        RecordTable record;
        for (NewRecord savedRecord: savedOrder.getRecordList()) {

            record = new RecordTable(
                    savedOrder.getId() + "/" + i,
                    savedOrder.getId(),
                    savedRecord.getJobName(),
                    Integer.parseInt(savedRecord.getAccountOrdered()),
                    0,
                    0,
                    savedRecord.getContract(),
                    LocalDate.parse(savedRecord.getWorkingFrom()),
                    LocalDate.parse(savedRecord.getWorkingTo()),
                    savedRecord.getPaymentType(),
                    savedRecord.getPaymentValue(),
                    savedRecord.getQualification(),
                    savedRecord.getComment(),
                    "Oczekuje"
            );
            record.setAlerted(false);
            record.setUpdateDate(LocalDate.parse(savedOrder.getOrderDate()));

            BazaDanych.getRecordList().put(record.getRecordId(), record);
            i++;
        }

    }

}