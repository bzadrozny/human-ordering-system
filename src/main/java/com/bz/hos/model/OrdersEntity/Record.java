package com.bz.hos.model.OrdersEntity;

import com.bz.hos.model.CandidateEntity.Contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Record {

    private String recordId;

    private String jobName;

    private int accountOrdered;

    private int accountAccepted;

    private int accountDelivered;

    private String contract;

    private LocalDate workingFrom;

    private LocalDate workingTo;

    private String paymentValue;

    private String paymentType;

    private String qualification;

    private String comment;

    private String status;

    private LocalDate updateDate;

    private boolean alerted = false;

    private List<Contract> contractList = new ArrayList<>();

    private int lastCandidateNo = 0;

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
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

    //getter & setter
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public int getLastCandidateNo() {
        return lastCandidateNo;
    }

    public void setLastCandidateNo(int lastCandidateNo) {
        this.lastCandidateNo = lastCandidateNo;
    }

    public Record(String recordId, String jobName, int accountOrdered, String contract, LocalDate workingFrom, LocalDate workingTo, String paymentType, String paymentValue, String qualification, String comment, String status) {
        this.recordId = recordId;
        this.jobName = jobName;
        this.accountOrdered = accountOrdered;
        this.accountAccepted = 0;
        this.accountDelivered = 0;
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
