package com.bz.hos.model.ReportModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationReport {

    //Amount of order for selected Location
    int totalOrderAmount = 0;

    //Researched status of orders
    int waitingAmount = 0;
    int acceptedAmount = 0;
    int realizationAmount = 0;
    int deletedAmount = 0;
    int delayedAmount = 0;
    int realizedAmount = 0;

    List<String> orderIds = new ArrayList<>();

    //List of users supplying orders to that location showing amount of orders, ids and dates
    Map<String, OrderSupplierReport> orderSupplierReportList = new HashMap<>();

    public String getUserOrderAmount() {
        return null;
    }

    public String getUserCandidateAmount() {
        return null;
    }

    public String getTotalCandidateAmount() {
        return null;
    }

    public static class OrderSupplierReport{

        String mail;
        String organization;
        String role;
        int ordersAmount = 0;

        public void addToOrderAmount(int i) {
            this.ordersAmount += i;
        }

        public OrderSupplierReport(String mail, String organization, String role, int ordersAmount) {
            this.mail = mail;
            this.organization = organization;
            this.role = role;
            this.ordersAmount = ordersAmount;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public int getOrdersAmount() {
            return ordersAmount;
        }

        public void setOrdersAmount(int ordersAmount) {
            this.ordersAmount = ordersAmount;
        }
    }

    int orderedCandidateAmount = 0;
    int acceptedCandidateAmount = 0;
    int deliveredCandidateAmount = 0;
    int waitingCandidateAmount = 0;
    int unaccomplishCandidateAmount = 0;

    public int getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(int totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public int getWaitingAmount() {
        return waitingAmount;
    }

    public void setWaitingAmount(int waitingAmount) {
        this.waitingAmount = waitingAmount;
    }

    public int getRealizationAmount() {
        return realizationAmount;
    }

    public void setRealizationAmount(int realizationAmount) {
        this.realizationAmount = realizationAmount;
    }

    public int getDeletedAmount() {
        return deletedAmount;
    }

    public void setDeletedAmount(int deletedAmount) {
        this.deletedAmount = deletedAmount;
    }

    public int getAcceptedAmount() {
        return acceptedAmount;
    }

    public void setAcceptedAmount(int acceptedAmount) {
        this.acceptedAmount = acceptedAmount;
    }

    public int getDelayedAmount() {
        return delayedAmount;
    }

    public void setDelayedAmount(int delayedAmount) {
        this.delayedAmount = delayedAmount;
    }

    public int getRealizedAmount() {
        return realizedAmount;
    }

    public void setRealizedAmount(int realizedAmount) {
        this.realizedAmount = realizedAmount;
    }

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }

    public Map<String, OrderSupplierReport> getOrderSupplierReportList() {
        return orderSupplierReportList;
    }

    public void setOrderSupplierReportList(Map<String, OrderSupplierReport> orderSupplierReportList) {
        this.orderSupplierReportList = orderSupplierReportList;
    }

    public int getOrderedCandidateAmount() {
        return orderedCandidateAmount;
    }

    public void setOrderedCandidateAmount(int orderedCandidateAmount) {
        this.orderedCandidateAmount = orderedCandidateAmount;
    }

    public int getAcceptedCandidateAmount() {
        return acceptedCandidateAmount;
    }

    public void setAcceptedCandidateAmount(int acceptedCandidateAmount) {
        this.acceptedCandidateAmount = acceptedCandidateAmount;
    }

    public int getDeliveredCandidateAmount() {
        return deliveredCandidateAmount;
    }

    public void setDeliveredCandidateAmount(int deliveredCandidateAmount) {
        this.deliveredCandidateAmount = deliveredCandidateAmount;
    }

    public int getWaitingCandidateAmount() {
        return waitingCandidateAmount;
    }

    public void setWaitingCandidateAmount(int waitingCandidateAmount) {
        this.waitingCandidateAmount = waitingCandidateAmount;
    }

    public int getUnaccomplishCandidateAmount() {
        return unaccomplishCandidateAmount;
    }

    public void setUnaccomplishCandidateAmount(int unaccomplishCandidateAmount) {
        this.unaccomplishCandidateAmount = unaccomplishCandidateAmount;
    }
}
