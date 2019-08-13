package com.bz.hos.db;

import java.util.HashMap;
import java.util.Map;

public class BazaDanych {

    //Users
    private static Map<String , UserTable> userList = new HashMap<>();
    public static Map<String, UserTable> getUserList() { return userList; }
    public static void setUserList(Map<String, UserTable> userList) { BazaDanych.userList = userList; }

    //Staff
    private static Map<Integer, StaffTable> staffList = new HashMap<>();
    public static Map<Integer, StaffTable> getStaffList() { return staffList; }
    public static void setStaffList(Map<Integer , StaffTable> staffList) { BazaDanych.staffList = staffList; }

    //UserLocation
    private  static Map<Integer, UserLocationTable> userLocationList = new HashMap<>();
    public static Map<Integer, UserLocationTable> getUserLocationList() { return userLocationList; }
    public static void setUserLocationList(Map<Integer, UserLocationTable> userLocationList) { BazaDanych.userLocationList = userLocationList; }

    //Locations
    private static Map<String , LocationTable> locationList = new HashMap<>();
    public static Map<String, LocationTable> getLocationList() { return locationList; }
    public static void setLocationList(Map<String, LocationTable> locationList) { BazaDanych.locationList = locationList; }

    //Distribution Email for Location
    private static Map<Integer, LocationEmailTable> locationEmailTableList = new HashMap<>();
    public static Map<Integer, LocationEmailTable> getLocationEmailTableList() { return locationEmailTableList; }
    public static void setLocationEmailTableList(Map<Integer, LocationEmailTable> locationEmailTableList) { BazaDanych.locationEmailTableList = locationEmailTableList; }

    //Departments
    private static Map<Integer , DepartmentTable> departmentList = new HashMap<>();
    public static Map<Integer, DepartmentTable> getDepartmentList() { return departmentList; }
    public static void setDepartmentList(Map<Integer, DepartmentTable> departmentList) { BazaDanych.departmentList = departmentList; }

    //Orders
    private static Map<String, OrderTable> orderList = new HashMap<>();
    public static Map<String, OrderTable> getOrderList() { return orderList; }
    public static void setOrderList(Map<String, OrderTable> orderList) { BazaDanych.orderList = orderList; }

    //Records
    private static Map<String, RecordTable> recordList = new HashMap<>();
    public static Map<String, RecordTable> getRecordList() { return recordList; }
    public static void setRecordList(Map<String, RecordTable> recordList) { BazaDanych.recordList = recordList; }

    //Contracts
    private static Map<String, ContractTable> contractList = new HashMap<>();
    public static Map<String, ContractTable> getContractList() { return contractList; }
    public static void setContractList(Map<String, ContractTable> contractList) { BazaDanych.contractList = contractList; }

    //Candidates
    private static Map<String, CandidateTable> candidateList = new HashMap<>();
    public static Map<String, CandidateTable> getCandidateList() { return candidateList; }
    public static void setCandidateList(Map<String, CandidateTable> candidateList) { BazaDanych.candidateList = candidateList; }

    //Comments
    private static Map<Integer, CommentTable> commentList = new HashMap<>();
    public static Map<Integer, CommentTable> getCommentList() { return commentList; }
    public static void setCommentList(Map<Integer, CommentTable> commentList) { BazaDanych.commentList = commentList; }

}
