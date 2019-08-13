package com.bz.hos.model.ReportModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserReport {

    //Amount of order where user is assign like a manager or recruiter or supplier
    int userOrderAmount = 0;

    //Researched status of orders
    int waitingAmount = 0;
    int acceptedAmount = 0;
    int realizationAmount = 0;
    int deletedAmount = 0;
    int delayedAmount = 0;
    int realizedAmount = 0;

    List<String> orderIds = new ArrayList<>();

    //List per localization showing an amount of recruiting new candidates by this user.
    Map<String, CandidateRealizationReport> candidateRealizationList = new HashMap<>();
    public static class CandidateRealizationReport{

        String organization;
        String localization;
        int orderedCandidatesAmount;
        int acceptedCandidatesAmount;
        int realizedCandidatesAmount;

        public void addOrderedCandidatesAmount(int il){orderedCandidatesAmount+= il;}
        public void addAcceptedCandidatesAmount(int il){acceptedCandidatesAmount+= il;}
        public void addRealizedCandidatesAmount(int il){realizedCandidatesAmount+= il;}

        public CandidateRealizationReport(String organization, String localization, int orderedCandidatesAmount, int acceptedCandidatesAmount, int realizedCandidatesAmount) {
            this.organization = organization;
            this.localization = localization;
            this.orderedCandidatesAmount = orderedCandidatesAmount;
            this.acceptedCandidatesAmount = orderedCandidatesAmount;
            this.realizedCandidatesAmount = realizedCandidatesAmount;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getLocalization() {
            return localization;
        }

        public void setLocalization(String localization) {
            this.localization = localization;
        }

        public int getOrderedCandidatesAmount() {
            return orderedCandidatesAmount;
        }

        public void setOrderedCandidatesAmount(int orderedCandidatesAmount) {
            this.orderedCandidatesAmount = orderedCandidatesAmount;
        }

        public int getAcceptedCandidatesAmount() {
            return acceptedCandidatesAmount;
        }

        public void setAcceptedCandidatesAmount(int accpetedCandidatesAmount) {
            this.acceptedCandidatesAmount = accpetedCandidatesAmount;
        }

        public int getRealizedCandidatesAmount() {
            return realizedCandidatesAmount;
        }

        public void setRealizedCandidatesAmount(int realizedCandidatesAmount) {
            this.realizedCandidatesAmount = realizedCandidatesAmount;
        }
    }

    //KPI - user orders / total orders
    //userOrderAmount;
    int totalOrderAmount = 0;

    //KPI - user candidate / total candidate
    int userCandidateAmount = 0;
    int totalCandidateAmount = 0;

    //KPI - delayed order / total order
    //delayedAmount;
    //totalOrderAmount

    //KPI - deleted order / total order
    //deletedAmount;
    //totalOrderAmount


    public int getUserOrderAmount() {
        return userOrderAmount;
    }

    public void setUserOrderAmount(int userOrderAmount) {
        this.userOrderAmount = userOrderAmount;
    }

    public int getWaitingAmount() {
        return waitingAmount;
    }

    public void setWaitingAmount(int waitingAmount) {
        this.waitingAmount = waitingAmount;
    }

    public int getAcceptedAmount() {
        return acceptedAmount;
    }

    public void setAcceptedAmount(int acceptedAmount) {
        this.acceptedAmount = acceptedAmount;
    }

    public int getRealizationAmount() {
        return realizationAmount;
    }

    public void setRealizationAmount(int realizationAmount) {
        this.realizationAmount = realizationAmount;
    }

    public int getDeletedAmount() {
        return deletedAmount;
    }

    public void setDeletedAmount(int deletedAmount) {
        this.deletedAmount = deletedAmount;
    }

    public int getDelayedAmount() {
        return delayedAmount;
    }

    public void setDelayedAmount(int delayedAmount) {
        this.delayedAmount = delayedAmount;
    }

    public int getRealizedAmount() {
        return realizedAmount;
    }

    public void setRealizedAmount(int realizedAmount) {
        this.realizedAmount = realizedAmount;
    }

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }

    public Map<String, CandidateRealizationReport> getCandidateRealizationList() {
        return candidateRealizationList;
    }

    public void setCandidateRealizationList(Map<String, CandidateRealizationReport> candidateRealizationList) {
        this.candidateRealizationList = candidateRealizationList;
    }

    public int getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(int totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public int getUserCandidateAmount() {
        return userCandidateAmount;
    }

    public void setUserCandidateAmount(int userCandidateAmount) {
        this.userCandidateAmount = userCandidateAmount;
    }

    public int getTotalCandidateAmount() {
        return totalCandidateAmount;
    }

    public void setTotalCandidateAmount(int totalCandidateAmount) {
        this.totalCandidateAmount = totalCandidateAmount;
    }
}
