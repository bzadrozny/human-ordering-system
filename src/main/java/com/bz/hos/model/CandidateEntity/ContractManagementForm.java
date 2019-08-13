package com.bz.hos.model.CandidateEntity;

import java.util.ArrayList;
import java.util.List;

public class ContractManagementForm {

    private String locationId;
    private String locationName;

    private int totalContracts = 0;
    private int newContracts = 0;
    private int replacementContracts = 0;
    private int expiringContracts = 0;

    private List<String> departureList = new ArrayList<>();
    private List<Contract> contractList = new ArrayList<Contract>();

    private String tempJson;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getTotalContracts() {
        return totalContracts;
    }

    public void setTotalContracts(int totalContracts) {
        this.totalContracts = totalContracts;
    }

    public int getNewContracts() {
        return newContracts;
    }

    public void setNewContracts(int newContracts) {
        this.newContracts = newContracts;
    }

    public int getReplacementContracts() {
        return replacementContracts;
    }

    public void setReplacementContracts(int replacementContracts) {
        this.replacementContracts = replacementContracts;
    }

    public int getExpiringContracts() {
        return expiringContracts;
    }

    public void setExpiringContracts(int expiringContracts) {
        this.expiringContracts = expiringContracts;
    }

    public List<String> getDepartureList() {
        return departureList;
    }

    public void setDepartureList(List<String> departureList) {
        this.departureList = departureList;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }

    public String getTempJson() {
        return tempJson;
    }

    public void setTempJson(String tempJson) {
        this.tempJson = tempJson;
    }
}
