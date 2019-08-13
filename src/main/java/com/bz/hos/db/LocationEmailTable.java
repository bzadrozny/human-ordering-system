package com.bz.hos.db;

public class LocationEmailTable {

    private int id;
    private String location;
    private String email;
    boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocationEmailTable(int id, String location, String email, boolean status) {
        this.id = id;
        this.location = location;
        this.email = email;
        this.status = status;
    }
}
