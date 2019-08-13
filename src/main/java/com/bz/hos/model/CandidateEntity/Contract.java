package com.bz.hos.model.CandidateEntity;

import java.time.LocalDate;

public class Contract {

    //Main Object Variable
    private String contractId;
    private String candidateId;

    private String candidateName;
    private String candidateSurname;

    private String candidatePhone;
    private String candidateEmail;

    private String candidateApproval;

    private String candidateShoeSize;
    private String candidateWaistSize;
    private String candidateSize = "L";

    private String candidateForkliftLicense = "Niedotyczy";
    private String candidateUDT = "Niedotyczy";
    private String candidate4B = "Niedotyczy";
    private String candidateSEP = "Niedotyczy";
    private String candidateLaw = "Niedotyczy";

    private String candidateVisaTerm;
    private String candidateSanitaryBookTerm;

    private String recordId;
    private String location;
    private String departure;

    private String candidateWorkFrom;
    private String candidateWorkTo;

    private String candidateSalary;
    private String candidateSalaryType = "h";
    private String candidateCostAccount;
    private String candidateManager;
    private String candidateWorkTimeType;

    private LocalDate contractDate;
    private LocalDate contractLastUpdateDate;
    private String contractAuthor;

    //Contract Management Variable
    private String jobName;

    public Contract(){
    }

    public Contract(String contractId, String jobName, String candidateId, String recordId, String departure, String candidateName, String candidateSurname, String candidateWorkFrom, String candidateWorkTo) {
        this.contractId = contractId;
        this.jobName = jobName;
        this.candidateId = candidateId;
        this.recordId = recordId;

        this.departure = departure;
        this.candidateName = candidateName;
        this.candidateSurname = candidateSurname;

        this.candidateWorkFrom = candidateWorkFrom;
        this.candidateWorkTo = candidateWorkTo;
    }

    public Contract(String contractId, String candidateId, String candidateName, String candidateSurname, String candidatePhone, String candidateEmail, String candidateApproval, String candidateShoeSize, String candidateWaistSize, String candidateSize, String candidateForkliftLicense, String candidateUDT, String candidate4B, String candidateSEP, String candidateLaw, String candidateVisaTerm, String candidateSanitaryBookTerm, String recordId, String location, String departure, String candidateWorkFrom, String candidateWorkTo, String candidateSalary, String candidateSalaryType, String candidateCostAccount, String candidateManager, String candidateWorkTimeType, LocalDate contractDate, LocalDate contractLastUpdateDate, String contractAuthor) {
        this.contractId = contractId;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.candidateSurname = candidateSurname;
        this.candidatePhone = candidatePhone;
        this.candidateEmail = candidateEmail;
        this.candidateApproval = candidateApproval;
        this.candidateShoeSize = candidateShoeSize;
        this.candidateWaistSize = candidateWaistSize;
        this.candidateSize = candidateSize;
        this.candidateForkliftLicense = candidateForkliftLicense;
        this.candidateUDT = candidateUDT;
        this.candidate4B = candidate4B;
        this.candidateSEP = candidateSEP;
        this.candidateLaw = candidateLaw;
        this.candidateVisaTerm = candidateVisaTerm;
        this.candidateSanitaryBookTerm = candidateSanitaryBookTerm;
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

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidateSurname() {
        return candidateSurname;
    }

    public void setCandidateSurname(String candidateSurname) {
        this.candidateSurname = candidateSurname;
    }

    public String getCandidatePhone() {
        return candidatePhone;
    }

    public void setCandidatePhone(String candidatePhone) {
        this.candidatePhone = candidatePhone;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getCandidateApproval() {
        return candidateApproval;
    }

    public void setCandidateApproval(String candidateApproval) {
        this.candidateApproval = candidateApproval;
    }

    public String getCandidateShoeSize() {
        return candidateShoeSize;
    }

    public void setCandidateShoeSize(String candidateShoeSize) {
        this.candidateShoeSize = candidateShoeSize;
    }

    public String getCandidateWaistSize() {
        return candidateWaistSize;
    }

    public void setCandidateWaistSize(String candidateWaistSize) {
        this.candidateWaistSize = candidateWaistSize;
    }

    public String getCandidateSize() {
        return candidateSize;
    }

    public void setCandidateSize(String candidateSize) {
        this.candidateSize = candidateSize;
    }

    public String getCandidateForkliftLicense() {
        return candidateForkliftLicense;
    }

    public void setCandidateForkliftLicense(String candidateForkliftLicense) {
        this.candidateForkliftLicense = candidateForkliftLicense;
    }

    public String getCandidateUDT() {
        return candidateUDT;
    }

    public void setCandidateUDT(String candidateUDT) {
        this.candidateUDT = candidateUDT;
    }

    public String getCandidate4B() {
        return candidate4B;
    }

    public void setCandidate4B(String candidate4B) {
        this.candidate4B = candidate4B;
    }

    public String getCandidateSEP() {
        return candidateSEP;
    }

    public void setCandidateSEP(String candidateSEP) {
        this.candidateSEP = candidateSEP;
    }

    public String getCandidateLaw() {
        return candidateLaw;
    }

    public void setCandidateLaw(String candidateLaw) {
        this.candidateLaw = candidateLaw;
    }

    public String getCandidateVisaTerm() {
        return candidateVisaTerm;
    }

    public void setCandidateVisaTerm(String candidateVisaTerm) {
        this.candidateVisaTerm = candidateVisaTerm;
    }

    public String getCandidateSanitaryBookTerm() {
        return candidateSanitaryBookTerm;
    }

    public void setCandidateSanitaryBookTerm(String candidateSanitaryBookTerm) {
        this.candidateSanitaryBookTerm = candidateSanitaryBookTerm;
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

    public String getCandidateWorkFrom() {
        return candidateWorkFrom;
    }

    public void setCandidateWorkFrom(String candidateWorkFrom) {
        this.candidateWorkFrom = candidateWorkFrom;
    }

    public String getCandidateWorkTo() {
        return candidateWorkTo;
    }

    public void setCandidateWorkTo(String candidateWorkTo) {
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



    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

}
