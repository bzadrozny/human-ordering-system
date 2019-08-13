package com.bz.hos.db;

import java.time.LocalDate;

public class ContractTable {

    private String contractId;
    private String candidateId;

    private String recordId;
    private String location;
    private String departure;

    private LocalDate candidateWorkFrom;
    private LocalDate candidateWorkTo;

    private String candidateSalary;
    private String candidateSalaryType;
    private String candidateCostAccount;
    private String candidateManager;
    private String candidateWorkTimeType;

    private LocalDate contractDate;
    private LocalDate contractLastUpdateDate;
    private String contractAuthor;

    public ContractTable(String contractId, String candidateId, String recordId, String location, String departure, LocalDate candidateWorkFrom, LocalDate candidateWorkTo, String candidateSalary, String candidateSalaryType, String candidateCostAccount, String candidateManager, String candidateWorkTimeType, LocalDate contractDate, LocalDate contractLastUpdateDate, String contractAuthor) {
        this.contractId = contractId;
        this.candidateId = candidateId;
        this.recordId = recordId;
        this.location = location;
        this.departure = departure;

        this.candidateWorkFrom = candidateWorkFrom;
        this.candidateWorkTo = candidateWorkTo;

        this.candidateSalary = candidateSalary;
        this.candidateSalaryType = candidateSalaryType;
        this.candidateCostAccount = candidateCostAccount;
        this.candidateManager = candidateManager;
        this.candidateWorkTimeType = candidateWorkTimeType;

        this.contractDate = contractDate;
        this.contractLastUpdateDate = contractLastUpdateDate;
        this.contractAuthor = contractAuthor;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public LocalDate getCandidateWorkFrom() {
        return candidateWorkFrom;
    }

    public void setCandidateWorkFrom(LocalDate candidateWorkFrom) {
        this.candidateWorkFrom = candidateWorkFrom;
    }

    public LocalDate getCandidateWorkTo() {
        return candidateWorkTo;
    }

    public void setCandidateWorkTo(LocalDate candidateWorkTo) {
        this.candidateWorkTo = candidateWorkTo;
    }

    public String getCandidateSalary() {
        return candidateSalary;
    }

    public void setCandidateSalary(String candidateSalary) {
        this.candidateSalary = candidateSalary;
    }

    public String getCandidateSalaryType() {
        return candidateSalaryType;
    }

    public void setCandidateSalaryType(String candidateSalaryType) {
        this.candidateSalaryType = candidateSalaryType;
    }

    public String getCandidateCostAccount() {
        return candidateCostAccount;
    }

    public void setCandidateCostAccount(String candidateCostAccount) {
        this.candidateCostAccount = candidateCostAccount;
    }

    public String getCandidateManager() {
        return candidateManager;
    }

    public void setCandidateManager(String candidateManager) {
        this.candidateManager = candidateManager;
    }

    public String getCandidateWorkTimeType() {
        return candidateWorkTimeType;
    }

    public void setCandidateWorkTimeType(String candidateWorkTimeType) {
        this.candidateWorkTimeType = candidateWorkTimeType;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public LocalDate getContractLastUpdateDate() {
        return contractLastUpdateDate;
    }

    public void setContractLastUpdateDate(LocalDate contractLastUpdateDate) {
        this.contractLastUpdateDate = contractLastUpdateDate;
    }

    public String getContractAuthor() {
        return contractAuthor;
    }

    public void setContractAuthor(String contractAuthor) {
        this.contractAuthor = contractAuthor;
    }
}
