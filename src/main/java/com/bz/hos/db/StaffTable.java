package com.bz.hos.db;

public class StaffTable {

    int id;
    String manager;
    String staffWorker;
    boolean staffWorkerStatus = true;

    //getter & setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getStaffWorker() {
        return staffWorker;
    }

    public void setStaffWorker(String staffWorker) {
        this.staffWorker = staffWorker;
    }

    public boolean isStaffWorkerStatus() {
        return staffWorkerStatus;
    }

    public void setStaffWorkerStatus(boolean staffWorkerStatus) {
        this.staffWorkerStatus = staffWorkerStatus;
    }

    public StaffTable(int id, String manager, String staffWorker) {
        this.id = id;
        this.manager = manager;
        this.staffWorker = staffWorker;
    }
}
