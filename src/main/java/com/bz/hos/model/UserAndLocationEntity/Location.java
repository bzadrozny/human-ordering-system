package com.bz.hos.model.UserAndLocationEntity;

import java.util.HashMap;
import java.util.Map;

public class Location {

    private String id;
    private String name;
    private String area;
    private String organization;
    private int lastOrderNo;

    private Map<Integer, String> department = new HashMap<>();
    private Map<Integer, String> distributionList = new HashMap<>();

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

    public Map<Integer, String> getDepartment() {
        return department;
    }

    public void setDepartment(Map<Integer, String> department) {
        this.department = department;
    }

    public Map<Integer, String> getDistributionList() {
        return distributionList;
    }

    public void setDistributionList(Map<Integer, String> distributionList) {
        this.distributionList = distributionList;
    }

    public int getLastOrderNo() {
        return lastOrderNo;
    }

    public void setLastOrderNo(int lastOrderNo) {
        this.lastOrderNo = lastOrderNo;
    }

    public Location(String id, String name, String area, String organization) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.organization = organization;
    }

    public Location() { ; }

}
