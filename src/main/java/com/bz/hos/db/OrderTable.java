package com.bz.hos.db;

import java.time.LocalDate;

public class OrderTable {

    private String id;
    private String orderCategory;

    private LocalDate orderDate;
    private LocalDate realizationDate;

    private String area;
    private String department;
    private String departmentManager;

    private String comment;
    private String orderStatus;

    private String supplier;
    private String assignWorker;
    private String manager;

    private boolean delayed = false;
    private boolean alerted = false;
    private LocalDate updateDate;

    private String location;
    private String locationId;
    private String organization;

    private int lastRecordNo = 0;

    //getter & setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getRealizationDate() {
        return realizationDate;
    }

    public void setRealizationDate(LocalDate realizationDate) {
        this.realizationDate = realizationDate;
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

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public boolean isAlerted() {
        return alerted;
    }

    public void setAlerted(boolean alerted) {
        this.alerted = alerted;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getLastRecordNo() {
        return lastRecordNo;
    }

    public void setLastRecordNo(int lastRecordNo) {
        this.lastRecordNo = lastRecordNo;
    }

    public OrderTable(String id, String orderCategory, LocalDate orderDate, LocalDate realizationDate, String area, String department, String departmentManager, String comment, String orderStatus, String supplier, String assignWorker, String manager, boolean delayed, String locationId, String location, String organization) {
        this.id = id;
        this.orderCategory = orderCategory;
        this.orderDate = orderDate;
        this.realizationDate = realizationDate;
        this.area = area;
        this.department = department;
        this.departmentManager = departmentManager;
        this.comment = comment;
        this.orderStatus = orderStatus;
        this.supplier = supplier;
        this.assignWorker = assignWorker;
        this.manager = manager;
        this.delayed = delayed;
        this.alerted = false;
        this.updateDate = orderDate;
        this.location = location;
        this.locationId = locationId;
        this.organization = organization;
    }
}
