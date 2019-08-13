package com.bz.hos.db;

import java.time.LocalDate;

public class CandidateTable {

    private String candidateId;

    private String candidateName;
    private String candidateSurname;

    private String candidatePhone;
    private String candidateEmail;

    private boolean candidateApproval;

    private String candidateShoeSize;
    private String candidateWaistSize;
    private String candidateSize;

    private String candidateForkliftLicense;
    private String candidateUDT;
    private String candidate4B;
    private String candidateSEP;
    private String candidateLaw;

    private LocalDate candidateVisaTerm;
    private LocalDate candidateSanitaryBookTerm;

    private LocalDate candidateLastUpdateDate;
    private String candidateAuthor;

    public CandidateTable(){}

    public CandidateTable(String candidateId, String candidateName, String candidateSurname, String candidatePhone, String candidateEmail, Boolean candidateApproval, String candidateShoeSize, String candidateWaistSize, String candidateSize, String candidateForkliftLicense, String candidateUDT, String candidate4B, String candidateSEP, String candidateLaw, LocalDate candidateVisaTerm, LocalDate candidateSanitaryBookTerm, LocalDate candidateLastUpdateDate, String candidateAuthor) {
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
        this.candidateLastUpdateDate = candidateLastUpdateDate;
        this.candidateAuthor = candidateAuthor;
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

    public boolean getCandidateApproval() {
        return candidateApproval;
    }

    public void setCandidateApproval(Boolean candidateApproval) {
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

    public LocalDate getCandidateVisaTerm() {
        return candidateVisaTerm;
    }

    public void setCandidateVisaTerm(LocalDate candidateVisaTerm) {
        this.candidateVisaTerm = candidateVisaTerm;
    }

    public LocalDate getCandidateSanitaryBookTerm() {
        return candidateSanitaryBookTerm;
    }

    public void setCandidateSanitaryBookTerm(LocalDate candidateSanitaryBookTerm) {
        this.candidateSanitaryBookTerm = candidateSanitaryBookTerm;
    }

    public LocalDate getCandidateLastUpdateDate() {
        return candidateLastUpdateDate;
    }

    public void setCandidateLastUpdateDate(LocalDate candidateLastUpdateDate) {
        this.candidateLastUpdateDate = candidateLastUpdateDate;
    }

    public String getCandidateAuthor() {
        return candidateAuthor;
    }

    public void setCandidateAuthor(String candidateAuthor) {
        this.candidateAuthor = candidateAuthor;
    }
}
