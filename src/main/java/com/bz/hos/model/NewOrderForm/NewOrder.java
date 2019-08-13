package com.bz.hos.model.NewOrderForm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewOrder {

    private String id;

    private String orderDate;

    private String realizationDate;

    private String organization;

    private String location;
    private String locationId;

    private String department;

    private String orderCategory;

    private String departmentManager;

    private String comment;

    private NewRecord newRecord;

    private List<NewRecord> recordList = new ArrayList<>();

    private String orderStatus;

    private String supplier;

    private String assignWorker;

    private String manager;

    private Boolean delayed = false;

    private String area;

//methods

    public void initialize(){
        this.orderDate = LocalDate.now().toString();
        this.realizationDate = null;
        this.orderCategory = "ZAM";
        initializeRecord();
    }

    public void initializeRecord(){
        this.newRecord = new NewRecord();
        this.newRecord.setAccountOrdered("1");
        this.newRecord.setPaymentType("h");
        this.newRecord.setWorkingFrom(LocalDate.parse(this.orderDate).plusMonths(3L).toString());
        this.newRecord.setWorkingTo(LocalDate.parse(this.newRecord.getWorkingFrom()).plusMonths(1L).toString());
    }

    public void addNewRecord(NewRecord newRecord){
        recordList.add(newRecord);
    }

//getters & setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getRealizationDate() {
        return realizationDate;
    }

    public void setRealizationDate(String realizationDate) {
        this.realizationDate = realizationDate;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }

    public String getDepartmentManager() {
        return departmentManager;
    }

    public void setDepartmentManager(String departmentManager) {
        this.departmentManager = departmentManager;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<NewRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<NewRecord> recordList) {
        this.recordList = recordList;
    }

    public NewRecord getNewRecord() {
        return newRecord;
    }

    public void setNewRecord(NewRecord newRecord) {
        this.newRecord = newRecord;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getAssignWorker() {
        return assignWorker;
    }

    public void setAssignWorker(String assignWorker) {
        this.assignWorker = assignWorker;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Boolean getDelayed() {
        return delayed;
    }

    public void setDelayed(Boolean delayed) {
        this.delayed = delayed;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString(){
        return orderDate + " " + realizationDate + " " + department + " " + area + "<br/>" + recordList;
    }

}
