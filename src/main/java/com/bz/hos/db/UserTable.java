package com.bz.hos.db;

public class UserTable {

    String mail;
    String name;
    String surname;
    String password;

    String role;
    boolean admin;

    String organization;

    boolean status;

    String assignWorker;
    String manager;

    boolean moderator = false;

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

    public boolean isModerator() {
        return moderator;
    }

    public void setModerator(boolean moderator) {
        this.moderator = moderator;
    }

    public UserTable(String mail, String name, String surname, String password, String role, boolean admin, String organization, boolean status, String assignWorker, String manager) {
        this.mail = mail;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
        this.admin = admin;
        this.organization = organization;
        this.status = status;
        this.assignWorker = assignWorker;
        this.manager = manager;
    }
}
