package com.bz.hos.db;

import java.time.LocalDate;

public class RecordTable {

    String recordId;

    String orderId;

    String jobName;

    int accountOrdered;

    int accountAccepted;

    int accountDelivered;

    String contract;

    LocalDate workingFrom;

    LocalDate workingTo;

    String paymentValue;

    String paymentType;

    String qualification;

    String comment;

    String status;

    LocalDate updateDate;

    boolean alerted = false;

    int lastCandidateNo = 0;

    //getter & setter
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getAccountOrdered() {
        return accountOrdered;
    }

    public void setAccountOrdered(int accountOrdered) {
        this.accountOrdered = accountOrdered;
    }

    public int getAccountAccepted() {
        return accountAccepted;
    }

    public void setAccountAccepted(int accountAccepted) {
        this.accountAccepted = accountAccepted;
    }

    public int getAccountDelivered() {
        return accountDelivered;
    }

    public void setAccountDelivered(int accountDelivered) {
        this.accountDelivered = accountDelivered;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public LocalDate getWorkingFrom() {
        return workingFrom;
    }

    public void setWorkingFrom(LocalDate workingFrom) {
        this.workingFrom = workingFrom;
    }

    public LocalDate getWorkingTo() {
        return workingTo;
    }

    public void setWorkingTo(LocalDate workingTo) {
        this.workingTo = workingTo;
    }


    public String getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(String paymentValue) {
        this.paymentValue = paymentValue;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isAlerted() {
        return alerted;
    }

    public void setAlerted(boolean alerted) {
        this.alerted = alerted;
    }

    public int getLastCandidateNo() {
        return lastCandidateNo;
    }

    public void setLastCandidateNo(int lastCandidateNo) {
        this.lastCandidateNo = lastCandidateNo;
    }

    public RecordTable(String recordId, String orderId, String jobName, int accountOrdered, int accountAccepted, int accountDelivered, String contract, LocalDate workingFrom, LocalDate workingTo, String paymentType, String paymentValue, String qualification, String comment, String status) {
        this.recordId = recordId;
        this.orderId = orderId;
        this.jobName = jobName;
        this.accountOrdered = accountOrdered;
        this.accountAccepted = accountAccepted;
        this.accountDelivered = accountDelivered;
        this.contract = contract;
        this.workingFrom = workingFrom;
        this.workingTo = workingTo;
        this.paymentType = paymentType;
        this.paymentValue = paymentValue;
        this.qualification = qualification;
        this.comment = comment;
        this.status = status;
    }
}
