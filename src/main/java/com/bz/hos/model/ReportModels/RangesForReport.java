package com.bz.hos.model.ReportModels;

import com.bz.hos.model.UserAndLocationEntity.Location;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RangesForReport {

    //mail and role - Necessary to identify user.
    String userMail = null;
    String userRole = null;
    boolean userIsAdmin = false;

    //staffList nad locationList - to generate Report for Locations and Staff
    List<Location> userLocationList = new ArrayList<>();
    List<String> userStaffList = new ArrayList<>();

    //User Date range of Report
    LocalDate userRangeFrom = null;
    LocalDate userRangeTo = null;

    //Location Date range of Report
    LocalDate locationRangeFrom = null;
    LocalDate locationRangeTo = null;
    String organization = null;
    String location = null;

    //Staff Date range of Report
    LocalDate staffRangeFrom = null;
    LocalDate staffRangeTo = null;
    String staffUser = null;

    //String to get dates from forms
    String formUserRangeFrom = null;
    String formUserRangeTo = null;

    String formLocationRangeFrom = null;
    String formLocationRangeTo = null;

    String formStaffRangeFrom = null;
    String formStaffRangeTo = null;

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public boolean isUserIsAdmin() {
        return userIsAdmin;
    }

    public void setUserIsAdmin(boolean userIsAdmin) {
        this.userIsAdmin = userIsAdmin;
    }

    public List<Location> getUserLocationList() {
        return userLocationList;
    }

    public void setUserLocationList(List<Location> userLocationList) {
        this.userLocationList = userLocationList;
    }

    public List<String> getUserStaffList() {
        return userStaffList;
    }

    public void setUserStaffList(List<String> userStaffList) {
        this.userStaffList = userStaffList;
    }

    public LocalDate getUserRangeFrom() {
        return userRangeFrom;
    }

    public void setUserRangeFrom(LocalDate userRangeFrom) {
        this.userRangeFrom = userRangeFrom;
    }

    public LocalDate getUserRangeTo() {
        return userRangeTo;
    }

    public void setUserRangeTo(LocalDate userRangeTo) {
        this.userRangeTo = userRangeTo;
    }

    public LocalDate getLocationRangeFrom() {
        return locationRangeFrom;
    }

    public void setLocationRangeFrom(LocalDate locationRangeFrom) {
        this.locationRangeFrom = locationRangeFrom;
    }

    public LocalDate getLocationRangeTo() {
        return locationRangeTo;
    }

    public void setLocationRangeTo(LocalDate locationRangeTo) {
        this.locationRangeTo = locationRangeTo;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStaffRangeFrom() {
        return staffRangeFrom;
    }

    public void setStaffRangeFrom(LocalDate staffRangeFrom) {
        this.staffRangeFrom = staffRangeFrom;
    }

    public LocalDate getStaffRangeTo() {
        return staffRangeTo;
    }

    public void setStaffRangeTo(LocalDate staffRangeTo) {
        this.staffRangeTo = staffRangeTo;
    }

    public String getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(String staffUser) {
        this.staffUser = staffUser;
    }

    public String getFormUserRangeFrom() {
        return formUserRangeFrom;
    }

    public void setFormUserRangeFrom(String formUserRangeFrom) {
        this.formUserRangeFrom = formUserRangeFrom;
    }

    public String getFormUserRangeTo() {
        return formUserRangeTo;
    }

    public void setFormUserRangeTo(String formUserRangeTo) {
        this.formUserRangeTo = formUserRangeTo;
    }

    public String getFormLocationRangeFrom() {
        return formLocationRangeFrom;
    }

    public void setFormLocationRangeFrom(String formLocationRangeFrom) {
        this.formLocationRangeFrom = formLocationRangeFrom;
    }

    public String getFormLocationRangeTo() {
        return formLocationRangeTo;
    }

    public void setFormLocationRangeTo(String formLocationRangeTo) {
        this.formLocationRangeTo = formLocationRangeTo;
    }

    public String getFormStaffRangeFrom() {
        return formStaffRangeFrom;
    }

    public void setFormStaffRangeFrom(String formStaffRangeFrom) {
        this.formStaffRangeFrom = formStaffRangeFrom;
    }

    public String getFormStaffRangeTo() {
        return formStaffRangeTo;
    }

    public void setFormStaffRangeTo(String formStaffRangeTo) {
        this.formStaffRangeTo = formStaffRangeTo;
    }
}
