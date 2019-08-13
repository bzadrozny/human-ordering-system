package com.bz.hos.db;

public class DepartmentTable {

    int id;
    String department;
    String location;
    boolean departmentStatus = true;

    //getter & setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDepartmentStatus() {
        return departmentStatus;
    }

    public void setDepartmentStatus(boolean departmentStatus) {
        this.departmentStatus = departmentStatus;
    }

    public DepartmentTable(int id, String department, String location) {
        this.id = id;
        this.department = department;
        this.location = location;
    }
}
