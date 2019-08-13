package com.bz.hos.services;

import com.bz.hos.db.*;
import com.bz.hos.model.CandidateEntity.Contract;
import com.bz.hos.model.OrdersEntity.Order;
import com.bz.hos.model.OrdersEntity.Record;
import com.bz.hos.model.UserAndLocationEntity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderService {

    public Order findOrder(String id){

        OrderTable orderTable = BazaDanych.getOrderList().get(id);
        Order order =  new Order(
                orderTable.getId(),
                orderTable.getOrderCategory(),
                orderTable.getOrderDate(),
                orderTable.getRealizationDate(),
                orderTable.getArea(),
                orderTable.getDepartment(),
                orderTable.getDepartmentManager(),
                orderTable.getComment(),
                orderTable.getOrderStatus(),
                orderTable.getSupplier(),
                orderTable.getAssignWorker(),
                orderTable.getManager(),
                orderTable.isDelayed(),
                orderTable.getLocationId(),
                orderTable.getLocation(),
                orderTable.getOrganization()
        );
        order.setUpdateDate(orderTable.getUpdateDate());
        order.setAlerted(orderTable.isAlerted());
        order.setLastRecordNo(orderTable.getLastRecordNo());

        return order;
    }

    public void getRecordsMapFromOrderId(Order order){

        String orderId = order.getId();
        RecordTable recordTable;
        Record record;
        Contract contract;
        ContractTable contractTable;
        CandidateTable candidateTable;

        Boolean hasAlertedRecords = false;

        for (int j=1; j<=order.getLastRecordNo(); j++) {
            recordTable = BazaDanych.getRecordList().get(order.getId().concat("/").concat(String.valueOf(j)));
            if (recordTable != null) {

                record = new Record(
                        recordTable.getRecordId(),
                        recordTable.getJobName(),
                        recordTable.getAccountOrdered(),
                        recordTable.getContract(),
                        recordTable.getWorkingFrom(),
                        recordTable.getWorkingTo(),
                        recordTable.getPaymentType(),
                        recordTable.getPaymentValue(),
                        recordTable.getQualification(),
                        recordTable.getComment(),
                        recordTable.getStatus()
                );
                record.setAccountAccepted(recordTable.getAccountAccepted());
                record.setAccountDelivered(recordTable.getAccountDelivered());
                record.setAlerted(recordTable.isAlerted());
                record.setUpdateDate(recordTable.getUpdateDate());
                record.setLastCandidateNo(recordTable.getLastCandidateNo());

                for (int i = 0; i <= record.getLastCandidateNo() || record.getContractList().size() < record.getAccountDelivered(); i++) {
                    contractTable = BazaDanych.getContractList().get(record.getRecordId() + "/" + i);
                    if (contractTable != null) {
                        candidateTable = BazaDanych.getCandidateList().get(contractTable.getCandidateId());
                        if (candidateTable != null) {
                            contract = new Contract(
                                    contractTable.getContractId(),
                                    candidateTable.getCandidateId(),
                                    candidateTable.getCandidateName(),
                                    candidateTable.getCandidateSurname(),
                                    candidateTable.getCandidatePhone(),
                                    candidateTable.getCandidateEmail(),
                                    candidateTable.getCandidateApproval()?"true":"false",
                                    candidateTable.getCandidateShoeSize(),
                                    candidateTable.getCandidateWaistSize(),
                                    candidateTable.getCandidateSize(),
                                    candidateTable.getCandidateForkliftLicense(),
                                    candidateTable.getCandidateUDT(),
                                    candidateTable.getCandidate4B(),
                                    candidateTable.getCandidateSEP(),
                                    candidateTable.getCandidateLaw(),
                                    candidateTable.getCandidateVisaTerm() != null ? candidateTable.getCandidateVisaTerm().toString() : null,
                                    candidateTable.getCandidateSanitaryBookTerm() != null ? candidateTable.getCandidateSanitaryBookTerm().toString() : null,
                                    contractTable.getRecordId(),
                                    contractTable.getLocation(),
                                    contractTable.getDeparture(),
                                    contractTable.getCandidateWorkFrom() != null ? contractTable.getCandidateWorkFrom().toString() : null,
                                    contractTable.getCandidateWorkTo() != null ? contractTable.getCandidateWorkTo().toString() : null,
                                    contractTable.getCandidateSalary(),
                                    contractTable.getCandidateSalaryType(),
                                    contractTable.getCandidateCostAccount(),
                                    contractTable.getCandidateManager(),
                                    contractTable.getCandidateWorkTimeType(),
                                    contractTable.getContractDate(),
                                    contractTable.getContractLastUpdateDate(),
                                    contractTable.getContractAuthor()
                            );
                            record.getContractList().add(contract);
                        }
                    }
                }

                if (record.getStatus().equals("Oczekuje") || record.getStatus().equals("Zatwierdzone") || record.getStatus().equals("W realizacji"))
                    hasAlertedRecords = record.isAlerted() ? record.isAlerted() : hasAlertedRecords;

                if (record.getUpdateDate().isAfter(order.getUpdateDate()))
                    order.setUpdateDate(record.getUpdateDate());

                order.getRecordList().put(record.getRecordId(), record);
            }
        }

        order.setAlerted(hasAlertedRecords);
        BazaDanych.getOrderList().get(orderId).setAlerted(hasAlertedRecords);
        BazaDanych.getOrderList().get(orderId).setUpdateDate(order.getUpdateDate());
    }

    public boolean specificationChange (Order order, String realizationDate, String status){

        boolean changed = false;
        OrderTable orderTable = BazaDanych.getOrderList().get(order.getId());
        if(orderTable!=null) {

            if (realizationDate != null && !realizationDate.isEmpty()) {
                changed = !order.getRealizationDate().toString().equals(realizationDate);
                order.setRealizationDate(LocalDate.parse(realizationDate));
                orderTable.setRealizationDate(LocalDate.parse(realizationDate));
            }

            changed = !order.getOrderStatus().equals(status) || changed;
            order.setOrderStatus(status);
            orderTable.setOrderStatus(status);
        }
        return changed;
    }

    public void accreditationChange (Order order, String assignWorker, String department){
        order.setAssignWorker(assignWorker);
        BazaDanych.getOrderList().get(order.getId()).setAssignWorker(assignWorker);
        order.setDepartment(department);
        BazaDanych.getOrderList().get(order.getId()).setDepartment(department);

        for(Record record: order.getRecordList().values())
            for(Contract contract: record.getContractList())
                BazaDanych.getContractList().get(contract.getContractId()).setDeparture(department);
    }

    public int recordChange(String recordId, String recordStatus, String accountOrdered, String accountAccepted) {

        boolean changedStatus = false;
        boolean changedOrdered = false;
        boolean changedAccepted = false;

        RecordTable recordTable = BazaDanych.getRecordList().get(recordId);
        if(recordTable != null) {

            recordTable.setAlerted(false);
            recordTable.setUpdateDate(LocalDate.now());

            if (recordStatus != null) {
                changedStatus = !recordTable.getStatus().equals(recordStatus);
                recordTable.setStatus(recordStatus);
            }

            if (accountOrdered != null) {
                changedOrdered  = recordTable.getAccountOrdered() != Integer.parseInt(accountOrdered);
                recordTable.setAccountOrdered(Integer.parseInt(accountOrdered));
            }

            if (accountAccepted != null) {
                changedAccepted = recordTable.getAccountAccepted() != Integer.parseInt(accountAccepted);
                recordTable.setAccountAccepted(Integer.parseInt(accountAccepted));
            }
        }
        return changedStatus? 1 : (changedAccepted? 2 : (changedOrdered? 3 : 0));
    }


    public void addContractData(Contract contractForm, Order order, User user) {

        CandidateTable candidateTable;

        if(contractForm.getCandidateId()==null || contractForm.getCandidateId().trim().isEmpty())
            contractForm.setCandidateId(contractForm.getContractId());

        if(!BazaDanych.getCandidateList().containsKey(contractForm.getCandidateId().trim())) {
            candidateTable = new CandidateTable(
                    contractForm.getCandidateId().trim(),
                    contractForm.getCandidateName(),
                    contractForm.getCandidateSurname(),
                    contractForm.getCandidatePhone(),
                    contractForm.getCandidateEmail(),
                    contractForm.getCandidateApproval()!=null,

                    contractForm.getCandidateShoeSize(),
                    contractForm.getCandidateWaistSize(),
                    contractForm.getCandidateSize(),

                    contractForm.getCandidateForkliftLicense(),
                    contractForm.getCandidateUDT(),
                    contractForm.getCandidate4B(),
                    contractForm.getCandidateSEP(),
                    contractForm.getCandidateLaw(),

                    contractForm.getCandidateVisaTerm()!=null && contractForm.getCandidateVisaTerm().length()>7 ? LocalDate.parse(contractForm.getCandidateVisaTerm()):null,
                    contractForm.getCandidateSanitaryBookTerm()!=null && contractForm.getCandidateSanitaryBookTerm().length()>7 ? LocalDate.parse(contractForm.getCandidateSanitaryBookTerm()):null,

                    LocalDate.now(),
                    user.getMail());
        } else {
            candidateTable = BazaDanych.getCandidateList().get(contractForm.getCandidateId().trim());
            BazaDanych.getCandidateList().remove(contractForm.getCandidateId().trim());

            if(contractForm.getCandidateId()!=null && !contractForm.getCandidateId().trim().isEmpty())
                candidateTable.setCandidateId(contractForm.getCandidateId().trim());

            if(contractForm.getCandidateName()!=null && !contractForm.getCandidateName().isEmpty())
                candidateTable.setCandidateName(contractForm.getCandidateName());

            if(contractForm.getCandidateSurname()!=null && !contractForm.getCandidateSurname().isEmpty())
                candidateTable.setCandidateSurname(contractForm.getCandidateSurname());

            if(contractForm.getCandidatePhone()!=null && !contractForm.getCandidatePhone().isEmpty())
                candidateTable.setCandidatePhone(contractForm.getCandidatePhone());

            if(contractForm.getCandidateEmail()!=null && !contractForm.getCandidateEmail().isEmpty())
                candidateTable.setCandidateEmail(contractForm.getCandidateEmail());

            candidateTable.setCandidateApproval(contractForm.getCandidateApproval()!=null);

            if(contractForm.getCandidateShoeSize()!=null && !contractForm.getCandidateShoeSize().isEmpty())
                candidateTable.setCandidateShoeSize(contractForm.getCandidateShoeSize());

            if(contractForm.getCandidateWaistSize()!=null && !contractForm.getCandidateWaistSize().isEmpty())
                candidateTable.setCandidateWaistSize(contractForm.getCandidateWaistSize());

            if(contractForm.getCandidateSize()!=null && !contractForm.getCandidateSize().isEmpty())
                candidateTable.setCandidateSize(contractForm.getCandidateSize());

            if(contractForm.getCandidateForkliftLicense()!=null && !contractForm.getCandidateForkliftLicense().isEmpty())
                candidateTable.setCandidateForkliftLicense(contractForm.getCandidateForkliftLicense());

            if(contractForm.getCandidateUDT()!=null && !contractForm.getCandidateUDT().isEmpty())
                candidateTable.setCandidateUDT(contractForm.getCandidateUDT());

            if(contractForm.getCandidate4B()!=null && !contractForm.getCandidate4B().isEmpty())
                candidateTable.setCandidate4B(contractForm.getCandidate4B());

            if(contractForm.getCandidateSEP()!=null && !contractForm.getCandidateSEP().isEmpty())
                candidateTable.setCandidateSEP(contractForm.getCandidateSEP());

            if(contractForm.getCandidateLaw()!=null && !contractForm.getCandidateLaw().isEmpty())
                candidateTable.setCandidateLaw(contractForm.getCandidateLaw());

            if(contractForm.getCandidateVisaTerm()!=null && !contractForm.getCandidateVisaTerm().isEmpty())
                candidateTable.setCandidateVisaTerm(LocalDate.parse(contractForm.getCandidateVisaTerm()));

            if(contractForm.getCandidateSanitaryBookTerm()!=null && !contractForm.getCandidateSanitaryBookTerm().isEmpty())
                candidateTable.setCandidateSanitaryBookTerm(LocalDate.parse(contractForm.getCandidateSanitaryBookTerm()));

            candidateTable.setCandidateLastUpdateDate(LocalDate.now());
        }
        BazaDanych.getCandidateList().put(candidateTable.getCandidateId(), candidateTable);

        ContractTable contractTable;
        if(!BazaDanych.getContractList().containsKey(contractForm.getContractId())){
            contractTable = new ContractTable(
                    contractForm.getContractId(),
                    candidateTable.getCandidateId(),

                    contractForm.getRecordId(),
                    order.getLocation(),
                    order.getDepartment(),

                    contractForm.getCandidateWorkFrom()!=null && contractForm.getCandidateWorkFrom().length()>7 ? LocalDate.parse(contractForm.getCandidateWorkFrom()) : null,
                    contractForm.getCandidateWorkTo()!=null && contractForm.getCandidateWorkTo().length()>7 ? LocalDate.parse(contractForm.getCandidateWorkTo()) : null,

                    contractForm.getCandidateSalary(),
                    contractForm.getCandidateSalaryType(),
                    contractForm.getCandidateCostAccount(),
                    contractForm.getCandidateManager(),
                    contractForm.getCandidateWorkTimeType(),

                    LocalDate.now(),
                    LocalDate.now(),
                    user.getMail()
            );

            int deliveredCandidate = BazaDanych.getRecordList().get(contractForm.getRecordId()).getAccountDelivered();
            BazaDanych.getRecordList().get(contractForm.getRecordId()).setAccountDelivered(deliveredCandidate + 1);
            int lastCandidateNo = BazaDanych.getRecordList().get(contractForm.getRecordId()).getLastCandidateNo();
            BazaDanych.getRecordList().get(contractForm.getRecordId()).setLastCandidateNo(lastCandidateNo + 1);

        } else {
            contractTable = BazaDanych.getContractList().get(contractForm.getContractId());
            BazaDanych.getContractList().remove(contractForm.getContractId());

            if(contractForm.getCandidateId()!=null && !contractForm.getCandidateId().trim().isEmpty())
                contractTable.setCandidateId(contractForm.getCandidateId().trim());

            if(contractForm.getCandidateWorkFrom()!=null && !contractForm.getCandidateWorkFrom().isEmpty())
                contractTable.setCandidateWorkFrom(LocalDate.parse(contractForm.getCandidateWorkFrom()));

            if(contractForm.getCandidateWorkTo()!=null && !contractForm.getCandidateWorkTo().isEmpty())
                contractTable.setCandidateWorkTo(LocalDate.parse(contractForm.getCandidateWorkTo()));

            if(contractForm.getCandidateSalary()!=null && !contractForm.getCandidateSalary().isEmpty())
                contractTable.setCandidateSalary(contractForm.getCandidateSalary());

            if(contractForm.getCandidateSalaryType()!=null && !contractForm.getCandidateSalaryType().isEmpty())
                contractTable.setCandidateSalaryType(contractForm.getCandidateSalaryType());

            if(contractForm.getCandidateCostAccount()!=null && !contractForm.getCandidateCostAccount().isEmpty())
                contractTable.setCandidateCostAccount(contractForm.getCandidateCostAccount());

            if(contractForm.getCandidateManager()!=null && !contractForm.getCandidateManager().isEmpty())
                contractTable.setCandidateManager(contractForm.getCandidateManager());

            if(contractForm.getCandidateWorkTimeType()!=null && !contractForm.getCandidateWorkTimeType().isEmpty())
                contractTable.setCandidateWorkTimeType(contractForm.getCandidateWorkTimeType());

            contractTable.setContractLastUpdateDate(LocalDate.now());
        }
        BazaDanych.getContractList().put(contractTable.getContractId(), contractTable);
    }

    public void removeContract(String contractId) {
        String recordId = BazaDanych.getContractList().remove(contractId).getRecordId();
        BazaDanych.getRecordList().get(recordId).setAccountDelivered(BazaDanych.getRecordList().get(recordId).getAccountDelivered()-1);
    }
}
