package com.bz.hos.services;

import com.bz.hos.db.*;
import com.bz.hos.model.CandidateEntity.Contract;
import com.bz.hos.model.CandidateEntity.ContractManagementForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContractManagementService {

    @Autowired
    EmailService emailService;

    public ContractManagementForm loadLocationContracts(String locId){

        ContractManagementForm contractForm = new ContractManagementForm();
        contractForm.setLocationId(locId);
        contractForm.setLocationName(BazaDanych.getLocationList().get(locId).getName());

        int lastOrderNo = BazaDanych.getLocationList().get(locId).getLastOrderNo();
        StringBuilder orderZamId;
        StringBuilder orderRepId;
        OrderTable order;
        RecordTable record;
        ContractTable contractTable;
        CandidateTable candidateTable;

        int total=0, newContracts=0, repContracts=0, expiring=0;

        for(int j=1; j<=lastOrderNo ; j++) {

            orderZamId = new StringBuilder("#ZAM/").append(locId).append('/').append(j);
            orderRepId = new StringBuilder("#REP/").append(locId).append('/').append(j);
            order = BazaDanych.getOrderList().get(orderZamId.toString()) != null ?
                    BazaDanych.getOrderList().get(orderZamId.toString()) :
                    BazaDanych.getOrderList().get(orderRepId.toString());

            if (order != null)
                for (int k=1; k<=lastOrderNo; k++) {
                    record = BazaDanych.getRecordList().get(order.getId().concat("/").concat(String.valueOf(k)));
                    if (record !=null)
                        for (int i = 1; i <= record.getLastCandidateNo(); i++) {
                            contractTable = BazaDanych.getContractList().get(record.getRecordId() + "/" + i);
                            if (contractTable != null &&
                                    contractTable.getCandidateWorkFrom() != null && !contractTable.getCandidateWorkFrom().isAfter(LocalDate.now()) &&
                                    contractTable.getCandidateWorkTo() != null && contractTable.getCandidateWorkTo().minusDays(1).isBefore(LocalDate.now())) {

                                if (!contractForm.getDepartureList().contains(order.getDepartment()))
                                    contractForm.getDepartureList().add(order.getDepartment());

                                candidateTable = BazaDanych.getCandidateList().get(contractTable.getCandidateId());
                                contractForm.getContractList().add(new Contract(
                                        contractTable.getContractId(),
                                        record.getJobName(),
                                        contractTable.getCandidateId(),
                                        record.getRecordId(),
                                        order.getDepartment(),
                                        candidateTable.getCandidateName(),
                                        candidateTable.getCandidateSurname(),
                                        String.valueOf(contractTable.getCandidateWorkFrom()),
                                        String.valueOf(contractTable.getCandidateWorkTo())));

                                total++;
                                if ((order.getId().split("/"))[0].equals("#REP"))
                                    repContracts++;
                                if (contractTable.getCandidateWorkFrom().plusWeeks(2).isAfter(LocalDate.now()))
                                    newContracts++;
                                if (contractTable.getCandidateWorkTo() != null && contractTable.getCandidateWorkTo().minusWeeks(2).isBefore(LocalDate.now()))
                                    expiring++;
                            }
                        }
                }
        }

        contractForm.setTotalContracts(total);
        contractForm.setNewContracts(newContracts);
        contractForm.setReplacementContracts(repContracts);
        contractForm.setExpiringContracts(expiring);

        return contractForm;
    }

    public void saveContractsChanges(String json, String user, String locationId){

        if(json.isEmpty())
            return;

        String[] contractsAndDatesList = json.split(";");
        if(contractsAndDatesList.length<1)
            return;

        String contractId;
        LocalDate oldDate, newDate;
        Map<String, LocalDate> contractDateMap = new HashMap<>();

        for(String contractAndDate: contractsAndDatesList) {

            contractId = contractAndDate.split("=")[0];
            newDate = contractAndDate.split("=").length > 1 ? LocalDate.parse(contractAndDate.split("=")[1]) : null;
            oldDate = BazaDanych.getContractList().get(contractId).getCandidateWorkTo();

            BazaDanych.getContractList().get(contractId).setCandidateWorkTo(newDate);
            contractDateMap.put(contractId, oldDate);
        }

        emailService.sendNewContractsEndDate(user, locationId ,contractDateMap);
    }

}