package com.bz.hos.services;

import com.bz.hos.db.*;
import com.bz.hos.model.FilterForm.FilterForm;
import com.bz.hos.model.UserAndLocationEntity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ListService {

    public Map<String, OrderTable> getOrderListFromFilter(FilterForm filter, User user, Map<String, OrderTable> waitingList, Map<String, OrderTable> openList){

        Map<String, OrderTable> orderList = new HashMap<>();
        Boolean filterValidation;

        for(OrderTable order: BazaDanych.getOrderList().values()) {
            if ((user.getLocationList().containsKey(order.getLocationId()) ||
                    order.getAssignWorker().equals(user.getMail()) || order.getManager().equals(user.getMail()) ||
                    user.getStaffList().containsKey(order.getAssignWorker()) || user.getStaffList().containsKey(order.getManager()) ||
                    user.isAdmin())) {

                if (order.getOrderStatus().equals("Oczekuje"))
                    waitingList.put(order.getId(), order);

                else if ((order.getOrderStatus().equals("Zatwierdzone") || order.getOrderStatus().equals("Realizacja")) && order.isAlerted())
                    openList.put(order.getId(), order);


                filterValidation = true;

                if (!filter.getArea().equals("any") && !filter.getArea().equals(order.getArea()))
                    filterValidation = false;

                if (!filter.getLocalization().equals("any") && !filter.getLocalization().equals(order.getLocationId()))
                    filterValidation = false;

                if (!filter.getDepartment().equals("any") && !filter.getDepartment().equals(order.getDepartment()))
                    filterValidation = false;

                if (!filter.getOrderStatus().equals("any") && !filter.getOrderStatus().equals(order.getOrderStatus()))
                    filterValidation = false;

                if (!filter.getSupplier().equals("any") && !filter.getSupplier().equals(order.getSupplier()))
                    filterValidation = false;

                if (!filter.getAssignWorker().equals("any") && !filter.getAssignWorker().equals(order.getAssignWorker()))
                    filterValidation = false;

                if (filter.getOrderDateFrom() != null && filter.getOrderDateFrom().length() != 0)
                    if (!LocalDate.parse(filter.getOrderDateFrom()).isBefore(order.getOrderDate()))
                        filterValidation = false;

                if (filter.getOrderDateTo() != null && filter.getOrderDateTo().length() != 0)
                    if (!LocalDate.parse(filter.getOrderDateTo()).isAfter(order.getOrderDate()))
                        filterValidation = false;

                if (filter.getRealizationDateFrom() != null && filter.getRealizationDateFrom().length() != 0)
                    if (!LocalDate.parse(filter.getRealizationDateFrom()).isBefore(order.getOrderDate()))
                        filterValidation = false;

                if (filter.getRealizationDateTo() != null && filter.getRealizationDateTo().length() != 0)
                    if (!LocalDate.parse(filter.getRealizationDateTo()).isAfter(order.getOrderDate()))
                        filterValidation = false;

                if (filter.getOrganization() != null)
                    if (!filter.getOrganization().equals("any") && !filter.getOrganization().equals(order.getOrganization()))
                        filterValidation = false;

                if (filter.getManager() != null)
                    if (!filter.getManager().equals("any") && !filter.getManager().equals(order.getManager()))
                        filterValidation = false;

                if (filterValidation)
                    orderList.put(order.getId(), order);

            }
        }

        return orderList;
    }

    public OrderTable getOrderFromId(String orderId, FilterForm filter){

        OrderTable order = null;

        for(OrderTable orderRec: BazaDanych.getOrderList().values()) {
            if (!filter.getAssignWorkerList().contains(orderRec.getAssignWorker()))
                filter.getAssignWorkerList().add(orderRec.getAssignWorker());

            if(orderRec.getId().equals(orderId))
                order = orderRec;
        }
        return order;
    }

    public Map<String, OrderTable> getOrderList(FilterForm filter, User user, Map<String, OrderTable> waitingList, Map<String, OrderTable> openList){

        Map<String, OrderTable> orderList = new HashMap<>();

        for(String staff: user.getStaffList().values()) {
            filter.getAssignWorkerList().add(staff);
            filter.getSupplierList().add(staff);
        }

        for(OrderTable order: BazaDanych.getOrderList().values()){

            if(user.getLocationList().containsKey(order.getLocationId()) || order.getManager().equals(user.getMail()) || order.getAssignWorker().equals(user.getMail()) || user.isAdmin()) {
                orderList.put(order.getId(), order);

                if (order.getOrderStatus().equals("Oczekuje"))
                    waitingList.put(order.getId(), order);

                else if ((order.getOrderStatus().equals("Zatwierdzone") || order.getOrderStatus().equals("Realizacja")) && order.isAlerted())
                    openList.put(order.getId(), order);

                if(!filter.getAssignWorkerList().contains(order.getAssignWorker()))
                    filter.getAssignWorkerList().add(order.getAssignWorker());

                if(!filter.getSupplierList().contains(order.getSupplier()))
                    filter.getSupplierList().add(order.getSupplier());
            }
        }
        return orderList;
    }

    public void getManagerList(FilterForm filter){
        for(UserTable userTable: BazaDanych.getUserList().values())
            if(userTable.getRole().equals("manager"))
                filter.getManagerList().add(userTable.getMail());
    }

}
