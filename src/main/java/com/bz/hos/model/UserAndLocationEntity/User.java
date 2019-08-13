package com.bz.hos.model.UserAndLocationEntity;

import java.util.HashMap;
import java.util.Map;

public class User{

    String mail;
    String name;
    String surname;
    String password;

    String role;
    boolean admin;
    boolean moderator;

    String organization;

    boolean status;

    String assignWorker;
    String manager;

    Map<String, Location> locationList = new HashMap<>();
    Map<String, String> organizationList = new HashMap<>();
    Map<String, String> staffList = new HashMap<>();

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isModerator() {
        return moderator;
    }

    public void setModerator(boolean moderator) {
        this.moderator = moderator;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public Map<String, Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(Map<String, Location> locationList) {
        this.locationList = locationList;
    }

    public Map<String, String> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(Map<String, String> organizationList) {
        this.organizationList = organizationList;
    }

    public Map<String, String> getStaffList() {
        return staffList;
    }

    public void setStaffList(Map<String, String> staffList) {
        this.staffList = staffList;
    }

    public User(String mail, String name, String surname, String password, String role, boolean admin, boolean moderator, String organization, boolean status, String assignWorker, String manager) {
        this.mail = mail;
        this.name = name;
        this.surname = surname;
        this.password = password;

        this.role = role;
        this.admin = admin;
        this.moderator = moderator;

        this.organization = organization;

        this.status = status;

        this.assignWorker = assignWorker;
        this.manager = manager;
    }

    public User(){ ; }

    @Override
    public String toString(){
        return name+" "+surname;
    }
}
