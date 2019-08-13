package com.bz.hos.db;

import java.time.LocalDate;

public class CommentTable {

    String title;
    String description;

    String supplierMail;
    LocalDate inputDate;
    String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplierMail() {
        return supplierMail;
    }

    public void setSupplierMail(String supplierMail) {
        this.supplierMail = supplierMail;
    }

    public LocalDate getInputDate() {
        return inputDate;
    }

    public void setInputDate(LocalDate inputDate) {
        this.inputDate = inputDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CommentTable(String title, String description, String supplierMail, LocalDate inputDate, String status) {
        this.title = title;
        this.description = description;
        this.supplierMail = supplierMail;
        this.inputDate = inputDate;
        this.status = status;
    }
}
