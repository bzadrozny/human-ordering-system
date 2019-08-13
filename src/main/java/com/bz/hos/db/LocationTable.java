package com.bz.hos.db;

public class LocationTable {

    String id;
    String name;
    String area;
    String organization;
    int lastOrderNo = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getLastOrderNo() {
        return lastOrderNo;
    }

    public void setLastOrderNo(int lastOrderNo) {
        this.lastOrderNo = lastOrderNo;
    }

    public LocationTable() {}

    public LocationTable(String id, String name, String area, String organization, int lastOrderNo) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.organization = organization;
        this.lastOrderNo = lastOrderNo;
    }
}
