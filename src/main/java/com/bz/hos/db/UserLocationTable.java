package com.bz.hos.db;

public class UserLocationTable {

    int id;
    String user;
    String location;
    boolean userLocationStatus = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isUserLocationStatus() {
        return userLocationStatus;
    }

    public void setUserLocationStatus(boolean userLocationStatus) {
        this.userLocationStatus = userLocationStatus;
    }

    public UserLocationTable(int id, String user, String location) {
        this.id = id;
        this.user = user;
        this.location = location;
    }
}
