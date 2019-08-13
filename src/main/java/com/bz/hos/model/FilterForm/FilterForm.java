package com.bz.hos.model.FilterForm;

import java.util.ArrayList;
import java.util.List;

public class FilterForm {

    String department;
    String area;
    String localization;
    String orderStatus;
    String supplier;

    List<String> supplierList = new ArrayList<>();

    String orderDateFrom;
    String orderDateTo;
    String realizationDateFrom;
    String realizationDateTo;

    String assignWorker;
    String manager;

    String organization;

    List<String> assignWorkerList = new ArrayList<>();
    List<String> managerList = new ArrayList<>();

    String searchedId;

    //getters and setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
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

    public List<String> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<String> supplierList) {
        this.supplierList = supplierList;
    }

    public String getOrderDateFrom() {
        return orderDateFrom;
    }

    public void setOrderDateFrom(String orderDateFrom) {
        this.orderDateFrom = orderDateFrom;
    }

    public String getOrderDateTo() {
        return orderDateTo;
    }

    public void setOrderDateTo(String orderDateTo) {
        this.orderDateTo = orderDateTo;
    }

    public String getRealizationDateFrom() {
        return realizationDateFrom;
    }

    public void setRealizationDateFrom(String realizationDateFrom) {
        this.realizationDateFrom = realizationDateFrom;
    }

    public String getRealizationDateTo() {
        return realizationDateTo;
    }

    public void setRealizationDateTo(String realizationDateTo) {
        this.realizationDateTo = realizationDateTo;
    }



    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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

    public List<String> getAssignWorkerList() {
        return assignWorkerList;
    }

    public void setAssignWorkerList(List<String> assignWorkerList) {
        this.assignWorkerList = assignWorkerList;
    }

    public List<String> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<String> managerList) {
        this.managerList = managerList;
    }

    public String getSearchedId() {
        return searchedId;
    }

    public void setSearchedId(String searchedId) {
        this.searchedId = searchedId;
    }
}
