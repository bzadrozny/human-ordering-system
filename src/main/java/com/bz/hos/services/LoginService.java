package com.bz.hos.services;

import com.bz.hos.WebApplicationSettings.config.RootConfiguration;
import com.bz.hos.db.*;
import com.bz.hos.model.LoginForm.LoginForm;
import com.bz.hos.model.UserAndLocationEntity.Location;
import com.bz.hos.model.UserAndLocationEntity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class LoginService {

    private boolean initialized = false;
    private OrderDataAnalyticsService orderDataAnalyticsService;

    public void initializeUser(String xmlFilepath){

        if(initialized) return;
        else {
            BazaDanych.getUserList().put("bartlomiej.zadrozny@adecco.com", new UserTable("bartlomiej.zadrozny@adecco.com", "Bartlomiej", "Zadrozny", "haslo", "manager", true, "Adecco", true, "bartlomiej.zadrozny@adecco.com", "bartlomiej.zadrozny@adecco.com"));
            BazaDanych.getUserList().get("bartlomiej.zadrozny@adecco.com").setModerator(true);
            BazaDanych.getStaffList().put(0, new StaffTable(0, "bartlomiej.zadrozny@adecco.com", "bartlomiej.zadrozny@adecco.com"));
            initialized = true;
            if(Files.exists(Paths.get(xmlFilepath)))
                UserServices.uploadData(xmlFilepath);
        }

        runOrderDataAnalyticsAgain();
    }

    public User getUser(LoginForm loginForm){

        UserTable user = BazaDanych.getUserList().get(loginForm.getLogin());

        if(user==null || !user.isStatus())
            return null;

        if(!user.getPassword().equals(loginForm.getPassword()))
            return null;

        User loggedUser = new User(
                user.getMail(),
                user.getName(),
                user.getSurname(),
                user.getPassword(),

                user.getRole(),
                user.isAdmin(),
                user.isModerator(),

                user.getOrganization(),

                user.isStatus(),

                user.getAssignWorker(),
                user.getManager()
        );

        for(UserLocationTable userLocations: BazaDanych.getUserLocationList().values())
            if( userLocations.isUserLocationStatus() && loggedUser.getMail().equals(userLocations.getUser())) {

                LocationTable locationTable = BazaDanych.getLocationList().get(userLocations.getLocation());
                Location location = new Location(locationTable.getId() ,locationTable.getName(), locationTable.getArea(), locationTable.getOrganization());

                for(DepartmentTable department: BazaDanych.getDepartmentList().values())
                    if( department.isDepartmentStatus() && department.getLocation().equals(location.getId()))
                        location.getDepartment().put(department.getId(), department.getDepartment());

                loggedUser.getLocationList().put(location.getId(), location);

                if(!loggedUser.getOrganizationList().containsKey(location.getOrganization()))
                    loggedUser.getOrganizationList().put(location.getOrganization(), location.getOrganization());
            }

        if(loggedUser.getRole().equals("manager"))
            for(StaffTable staff: BazaDanych.getStaffList().values())
                if( staff.isStaffWorkerStatus() && loggedUser.getMail().equals(staff.getManager()))
                    loggedUser.getStaffList().put(staff.getStaffWorker(), staff.getStaffWorker());

        return loggedUser;
    }

    public void runOrderDataAnalyticsAgain() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(RootConfiguration.class);

        if(orderDataAnalyticsService!=null)
            orderDataAnalyticsService.stopAnalytics();

        orderDataAnalyticsService = (OrderDataAnalyticsService)ctx.getBean("orderDataAnalyticsService");
        orderDataAnalyticsService.start();
    }
}
