package com.bz.hos.services;

import com.bz.hos.db.*;

import com.bz.hos.model.SettingForm.SettingForm;
import com.bz.hos.model.UserAndLocationEntity.Location;
import com.bz.hos.model.UserAndLocationEntity.User;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServices {

    public boolean changePassword(SettingForm settingForm, User user){

        boolean success = false;

        if(settingForm.getNewPass().equals(settingForm.getNewPassConfirmation()) && user.getPassword().equals(settingForm.getOldPass())){
            BazaDanych.getUserList().get(user.getMail()).setPassword(settingForm.getNewPass());
            settingForm.setOldPass(null);
            settingForm.setNewPass(null);
            settingForm.setNewPassConfirmation(null);
            success = true;
        }

        return success;
    }

    public SettingForm supplyingUserFormList(SettingForm settingForm, User user){

        if (user == null || settingForm == null)
            return null;

        if(user.isAdmin()) {

            List<SettingForm.UserAndRole> allClientList = new ArrayList<>();
            List<SettingForm.UserAndRole> allRecruiterList = new ArrayList<>();
            List<SettingForm.UserAndRole> allManagerList = new ArrayList<>();

            for (UserTable userTable : BazaDanych.getUserList().values()) {

                if(!userTable.isModerator()) {
                    switch (userTable.getRole()) {
                        case "client":
                            allClientList.add(new SettingForm.UserAndRole(userTable.getMail(), userTable.getManager(), userTable.getRole(), userTable.getOrganization()));
                            break;
                        case "recruiter":
                            allRecruiterList.add(new SettingForm.UserAndRole(userTable.getMail(), userTable.getManager(), userTable.getRole(), userTable.getOrganization()));
                            break;
                        case "manager":
                            allManagerList.add(new SettingForm.UserAndRole(userTable.getMail(), userTable.getManager(), userTable.getRole(), userTable.getOrganization()));
                            break;
                    }
                }
                else if(userTable.isModerator() && user.isModerator()){
                    switch (userTable.getRole()) {
                        case "client":
                            allClientList.add(new SettingForm.UserAndRole(userTable.getMail(), userTable.getManager(), userTable.getRole(), userTable.getOrganization()));
                            break;
                        case "manager":
                            allManagerList.add(new SettingForm.UserAndRole(userTable.getMail(), userTable.getManager(), userTable.getRole(), userTable.getOrganization()));
                            break;
                        case "recruiter":
                            allRecruiterList.add(new SettingForm.UserAndRole(userTable.getMail(), userTable.getManager(), userTable.getRole(), userTable.getOrganization()));
                            break;
                    }
                }

            }

            List<SettingForm.LocationAndOrganization> allLocationList = new ArrayList<>();

            for (LocationTable locTable : BazaDanych.getLocationList().values()) {
                allLocationList.add(new SettingForm.LocationAndOrganization(locTable.getId(), locTable.getName(), locTable.getArea() ,locTable.getOrganization()));

                if(!settingForm.getOrganisationMap().containsKey(locTable.getOrganization()))
                    settingForm.getOrganisationMap().put(locTable.getOrganization(), locTable.getOrganization());
            }

            settingForm.setAllClientList(allClientList);
            settingForm.setAllRecruiterList(allRecruiterList);
            settingForm.setAllManagerList(allManagerList);

            settingForm.setAllLocationList(allLocationList);

        }

        settingForm.setPasswordChangeSucces(false);
        return settingForm;
    }

    public SettingForm selectUserFromStaff(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.getSelectedUser().setMail(settingForm.getSelectedUser().getMail());
        settingForm = savedSettingForm;

        if(settingForm.getSelectedUser().getMail() == null)
            return settingForm;

        UserTable tableUser;
        LocationTable tableLocation;

        tableUser = BazaDanych.getUserList().get(settingForm.getSelectedUser().getMail());
        settingForm.getSelectedUser().setName(tableUser.getName());
        settingForm.getSelectedUser().setSurname(tableUser.getSurname());
        settingForm.getSelectedUser().setStatus(tableUser.isStatus());
        settingForm.getSelectedUser().setRole(tableUser.getRole());
        settingForm.getSelectedUser().setAdmin(tableUser.isAdmin());
        settingForm.getSelectedUser().setAssignWorker(tableUser.getAssignWorker());
        settingForm.getSelectedUser().setManager(tableUser.getManager());

        settingForm.setName(settingForm.getSelectedUser().getName());
        settingForm.setSurname(settingForm.getSelectedUser().getSurname());
        settingForm.setOrganization(tableUser.getOrganization());

        settingForm.setSelectedUserStaff(new ArrayList<>());
        settingForm.getSelectedUser().setLocationList(new HashMap<>());

        if(settingForm.getSelectedUser().getRole().equals("manager"))
            for(StaffTable staffTable: BazaDanych.getStaffList().values())
                if( staffTable.isStaffWorkerStatus() && staffTable.getManager().equals(settingForm.getSelectedUser().getMail()) ){
                    tableUser = BazaDanych.getUserList().get(staffTable.getStaffWorker());
                    SettingForm.UserAndRole userAndRole = new SettingForm.UserAndRole(tableUser.getMail(), tableUser.getManager(), tableUser.getRole(), tableUser.getOrganization());
                    settingForm.getSelectedUserStaff().add(userAndRole);
                }

        for(UserLocationTable userLocationTable: BazaDanych.getUserLocationList().values())
            if( userLocationTable.isUserLocationStatus() && userLocationTable.getUser().equals(settingForm.getSelectedUser().getMail()) ){
                tableLocation = BazaDanych.getLocationList().get(userLocationTable.getLocation());
                Location location = new Location(tableLocation.getId(), tableLocation.getName(), tableLocation.getArea(), tableLocation.getOrganization());
                settingForm.getSelectedUser().getLocationList().put(location.getId(), location);
            }

        return settingForm;
    }

    public SettingForm changeSelectedUserData(SettingForm settingForm, SettingForm savedSettingForm){

        savedSettingForm.setName(settingForm.getName());
        savedSettingForm.setSurname(settingForm.getSurname());
        settingForm = savedSettingForm;

        if(settingForm.getSelectedUser() == null)
            return settingForm;

        if(settingForm.getName().isEmpty()) {
            settingForm.setName("Podaj imie");
            return settingForm;
        }
        if(settingForm.getSurname().isEmpty()) {
            settingForm.setSurname("Podaj nazwisko");
            return settingForm;
        }

        BazaDanych.getUserList().get(settingForm.getSelectedUser().getMail()).setName(settingForm.getName());
        BazaDanych.getUserList().get(settingForm.getSelectedUser().getMail()).setSurname(settingForm.getSurname());

        return settingForm;
    }


    public SettingForm changeOrganization(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setOrganization(settingForm.getOrganization());
        settingForm = savedSettingForm;

        if(settingForm.getSelectedUser() == null)
            return settingForm;

        if(settingForm.getOrganization().isEmpty()){
            settingForm.setOrganization("Podaj organizacje");
            return settingForm;
        }

        BazaDanych.getUserList().get(settingForm.getSelectedUser().getMail()).setOrganization(settingForm.getOrganization());
        return settingForm;
    }

    public SettingForm resetSelectedUserPassword(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setTempPass(settingForm.getTempPass());
        savedSettingForm.setTempPassConfirmation(settingForm.getTempPassConfirmation());
        settingForm = savedSettingForm;

        if(settingForm.getSelectedUser() == null)
            return settingForm;

        if(settingForm.getTempPass().equals(settingForm.getTempPassConfirmation())) {
            BazaDanych.getUserList().get(settingForm.getSelectedUser().getMail()).setPassword(settingForm.getTempPass());
            settingForm.setTempPass("");
            settingForm.setTempPassConfirmation("");
        }

        return settingForm;
    }

    public SettingForm addUsersLocation(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setSelectedLocationId(settingForm.getSelectedLocationId());
        settingForm = savedSettingForm;

        if(settingForm.getSelectedUser().getLocationList().containsKey(settingForm.getSelectedLocationId()))
            return settingForm;

        LocationTable locationTable = BazaDanych.getLocationList().get(settingForm.getSelectedLocationId());

        UserLocationTable userLocationTable = new UserLocationTable(BazaDanych.getUserLocationList().size()+1, settingForm.getSelectedUser().getMail(), settingForm.getSelectedLocationId());
        BazaDanych.getUserLocationList().put( BazaDanych.getUserLocationList().size()+1 , userLocationTable);

        Location location = new Location( locationTable.getId(), locationTable.getName(), locationTable.getArea(), locationTable.getOrganization());
        settingForm.getSelectedUser().getLocationList().put(location.getId(), location);

        return settingForm;
    }


    public SettingForm removeUsersLocation(SettingForm settingForm, SettingForm savedSettingForm, String selectedUserLocationId) {

        settingForm = savedSettingForm;
        settingForm.setSelectedLocationId(selectedUserLocationId);

        Location location = settingForm.getSelectedUser().getLocationList().get(selectedUserLocationId);
        if(location!=null){
            settingForm.getSelectedUser().getLocationList().remove(location.getId());

            for(UserLocationTable userLocationTable: BazaDanych.getUserLocationList().values())
                if( userLocationTable.getUser().equals(settingForm.getSelectedUser().getMail()) && userLocationTable.getLocation().equals(settingForm.getSelectedLocationId()) )
                    userLocationTable.setUserLocationStatus(false);
        }

        return settingForm;
    }

    public SettingForm addManagerStaff(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setSelectedAssignWorker(settingForm.getSelectedAssignWorker());
        settingForm = savedSettingForm;

        for(SettingForm.UserAndRole userAndRole: settingForm.getSelectedUserStaff())
            if(userAndRole.getMail().equals(settingForm.getSelectedAssignWorker()))
                return settingForm;

        UserTable userTable = BazaDanych.getUserList().get(settingForm.getSelectedAssignWorker());
        SettingForm.UserAndRole userAndRole = new SettingForm.UserAndRole( userTable.getMail(), userTable.getManager(), userTable.getRole(), userTable.getOrganization());

        settingForm.getSelectedUserStaff().add(userAndRole);
        BazaDanych.getStaffList().put( BazaDanych.getStaffList().size() + 1, new StaffTable(BazaDanych.getStaffList().size() + 1, settingForm.getSelectedUser().getMail(),settingForm.getSelectedAssignWorker()));

        return settingForm;
    }

    public SettingForm removeManagerStaff(SettingForm settingForm, SettingForm savedSettingForm, String selectedAssignWorker) {

        settingForm = savedSettingForm;
        settingForm.setSelectedAssignWorker(selectedAssignWorker);

        List <SettingForm.UserAndRole> userList = new ArrayList<>();

        for(SettingForm.UserAndRole userAndRole: settingForm.getSelectedUserStaff())
            if(userAndRole.getMail().equals(settingForm.getSelectedAssignWorker())){

                userList.add(userAndRole);

                for(StaffTable staffTable: BazaDanych.getStaffList().values())
                    if(staffTable.getManager().equals(settingForm.getSelectedUser().getMail()) && staffTable.getStaffWorker().equals(settingForm.getSelectedAssignWorker()))
                        staffTable.setStaffWorkerStatus(false);
            }

        settingForm.getSelectedUserStaff().removeAll(userList);

        return settingForm;
    }

    public SettingForm changeUserAssignWorkerAndManager(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.getSelectedUser().setAssignWorker(settingForm.getSelectedUser().getAssignWorker());
        savedSettingForm.getSelectedUser().setManager(settingForm.getSelectedUser().getManager());
        settingForm = savedSettingForm;

        BazaDanych.getUserList().get(settingForm.getSelectedUser().getMail()).setAssignWorker(settingForm.getSelectedUser().getAssignWorker());
        BazaDanych.getUserList().get(settingForm.getSelectedUser().getMail()).setManager(settingForm.getSelectedUser().getManager());

        return settingForm;
    }


    public SettingForm changeUserRoleAndStatus(SettingForm settingForm, SettingForm savedSettingForm) {

        switch (savedSettingForm.getSelectedUser().getRole()){
            case "client": {
                for(SettingForm.UserAndRole user: savedSettingForm.getAllClientList())
                    if(user.getMail().equals(savedSettingForm.getSelectedUser().getMail())) {
                        savedSettingForm.getAllClientList().remove(user);
                        break;
                    }
                break;
            }
            case "recruiter": {
                for(SettingForm.UserAndRole user: savedSettingForm.getAllRecruiterList())
                    if(user.getMail().equals(savedSettingForm.getSelectedUser().getMail())) {
                        savedSettingForm.getAllRecruiterList().remove(user);
                        break;
                    }
                break;
            }
            case "manager": {
                for(SettingForm.UserAndRole user: savedSettingForm.getAllManagerList())
                    if(user.getMail().equals(savedSettingForm.getSelectedUser().getMail())) {
                        savedSettingForm.getAllManagerList().remove(user);
                        break;
                    }
                break;
            }
        }

        savedSettingForm.getSelectedUser().setRole(settingForm.getSelectedUser().getRole());
        savedSettingForm.getSelectedUser().setAdmin(settingForm.getSelectedUser().isAdmin());
        settingForm = savedSettingForm;

        switch (settingForm.getSelectedUser().getRole()){
            case "client": {
                settingForm.getAllClientList().add(new SettingForm.UserAndRole(settingForm.getSelectedUser().getMail(), settingForm.getSelectedUser().getManager(), settingForm.getSelectedUser().getRole(), settingForm.getSelectedUser().getOrganization()));
                break;
            }
            case "recruiter": {
                settingForm.getAllRecruiterList().add(new SettingForm.UserAndRole(settingForm.getSelectedUser().getMail(), settingForm.getSelectedUser().getManager(), settingForm.getSelectedUser().getRole(), settingForm.getSelectedUser().getOrganization()));
                break;
            }
            case "manager": {
                settingForm.getAllManagerList().add(new SettingForm.UserAndRole(settingForm.getSelectedUser().getMail(), settingForm.getSelectedUser().getManager(), settingForm.getSelectedUser().getRole(), settingForm.getSelectedUser().getOrganization()));
                break;
            }
        }

        BazaDanych.getUserList().get(settingForm.getSelectedUser().getMail()).setRole(settingForm.getSelectedUser().getRole());
        BazaDanych.getUserList().get(settingForm.getSelectedUser().getMail()).setAdmin(settingForm.getSelectedUser().isAdmin());

        return settingForm;
    }

    public SettingForm changeUserStatus(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.getSelectedUser().setStatus(settingForm.getSelectedUser().isStatus());
        settingForm = savedSettingForm;

        BazaDanych.getUserList().get(settingForm.getSelectedUser().getMail()).setStatus(settingForm.getSelectedUser().isStatus());

        return settingForm;
    }

    public SettingForm closeUserEditionCart(SettingForm savedSettingForm) {

        savedSettingForm.setSelectedUser( null );

        return savedSettingForm;
    }

    public SettingForm visibleNewUserCart(SettingForm settingForm){

        if(settingForm.isShowNewUserCart())
            settingForm.setShowNewUserCart(false);
        else
            settingForm.setShowNewUserCart(true);

        return settingForm;
    }

    public SettingForm addNewUser(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setUserMail(settingForm.getUserMail());
        savedSettingForm.setUserName(settingForm.getUserName());
        savedSettingForm.setUserSurname(settingForm.getUserSurname());

        savedSettingForm.setPassword(settingForm.getPassword());
        savedSettingForm.setConfirmPassword(settingForm.getConfirmPassword());

        savedSettingForm.setUserRole(settingForm.getUserRole());
        savedSettingForm.setUserOrganization(settingForm.getUserOrganization());
        savedSettingForm.setUserAdmin(settingForm.isUserAdmin());

        savedSettingForm.setUserAssignWorker(settingForm.getUserAssignWorker());
        savedSettingForm.setUserManager(settingForm.getUserManager());

        savedSettingForm.setUserStatus(settingForm.isUserStatus());

        settingForm = savedSettingForm;

        if(BazaDanych.getUserList().containsKey(settingForm.getUserMail())){
            settingForm.setUserMailInfo("Podany mail, jest przypisany do isniejącego użytkownika");
            return settingForm;
        } else
            settingForm.setPassInfo(null);


        if(!settingForm.getPassword().equals(settingForm.getConfirmPassword())){
            settingForm.setPassInfo("Hasła się nie zgadzają, proszę podać inne");
            return settingForm;
        } else
            settingForm.setPassInfo(null);

        UserTable userTable = new UserTable(
                settingForm.getUserMail(),
                settingForm.getUserName(),
                settingForm.getUserSurname(),
                settingForm.getPassword(),
                settingForm.getUserRole(),
                settingForm.isUserAdmin(),
                settingForm.getUserOrganization(),
                settingForm.isUserStatus(),
                settingForm.getUserAssignWorker(),
                settingForm.getUserManager()
        );
        BazaDanych.getUserList().put(userTable.getMail(), userTable);

        SettingForm.UserAndRole userAndRole = new SettingForm.UserAndRole(
                settingForm.getUserMail(),
                settingForm.getUserManager(),
                settingForm.getUserRole(),
                settingForm.getUserOrganization()
        );
        switch (userAndRole.getRole()){
            case "recruiter": {
                settingForm.getAllRecruiterList().add(userAndRole);
                break;
            }
            case "client": {
                settingForm.getAllClientList().add(userAndRole);
                break;
            }
            case "manager": {
                settingForm.getAllManagerList().add(userAndRole);
                break;
            }
        }

        settingForm.setUserMail(null);
        settingForm.setUserName(null);
        settingForm.setUserSurname(null);

        settingForm.setPassword(null);
        settingForm.setConfirmPassword(null);

        settingForm.setUserRole(null);
        settingForm.setUserOrganization(null);
        settingForm.setUserAdmin(false);

        settingForm.setUserAssignWorker(null);
        settingForm.setUserManager(null);

        settingForm.setUserStatus(true);

        return settingForm;
    }

    public SettingForm visibleNewLocationCart(SettingForm settingForm) {

        if(settingForm.isShowNewLocationCart())
            settingForm.setShowNewLocationCart(false);
        else
            settingForm.setShowNewLocationCart(true);

        return settingForm;
    }

    public SettingForm addNewLocation(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setLocationId(settingForm.getLocationId());
        savedSettingForm.setLocationName(settingForm.getLocationName());
        savedSettingForm.setLocationArea(settingForm.getLocationArea());
        savedSettingForm.setLocationOrganization(settingForm.getLocationOrganization());
        settingForm = savedSettingForm;

        if(settingForm.getLocationId()==null ||  settingForm.getLocationName()==null || settingForm.getLocationArea()==null || settingForm.getLocationOrganization()==null)
            return settingForm;

        int idNo = 1;
        String locationId = settingForm.getLocationId().toUpperCase();
        while(BazaDanych.getLocationList().containsKey(locationId)){
            if(idNo!=1)
                locationId = locationId.substring(0, locationId.length()-String.valueOf(idNo).length());
            locationId = locationId.concat(String.valueOf(idNo++));
        }
        settingForm.setLocationId(locationId);

        LocationTable locationTable = new LocationTable(settingForm.getLocationId() ,settingForm.getLocationName(),  settingForm.getLocationArea(), settingForm.getLocationOrganization(),0);
        BazaDanych.getLocationList().put(locationTable.getId(), locationTable);

        SettingForm.LocationAndOrganization locationAndOrganization = new SettingForm.LocationAndOrganization(settingForm.getLocationId(), settingForm.getLocationName(), settingForm.getLocationArea(), settingForm.getLocationOrganization());
        settingForm.getAllLocationList().add(locationAndOrganization);

        if(!settingForm.getOrganisationMap().containsKey(settingForm.getLocationOrganization()))
            settingForm.getOrganisationMap().put(settingForm.getLocationOrganization(), settingForm.getLocationOrganization());

        settingForm.setLocationId(null);
        settingForm.setLocationName(null);
        settingForm.setLocationArea(null);
        settingForm.setLocationOrganization(null);

        return settingForm;
    }

    public SettingForm selectLocation(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setEditingLocationId(settingForm.getEditingLocationId());
        settingForm = savedSettingForm;

        LocationTable locationTable = BazaDanych.getLocationList().get(settingForm.getEditingLocationId());
        Location location = new Location(locationTable.getId(), locationTable.getName(), locationTable.getArea(), locationTable.getOrganization());

        for(DepartmentTable departmentTable: BazaDanych.getDepartmentList().values())
            if(departmentTable.getLocation().equals(location.getId()) && departmentTable.isDepartmentStatus())
                location.getDepartment().put(location.getDepartment().size()+1, departmentTable.getDepartment());

        for(LocationEmailTable locationEmailTable: BazaDanych.getLocationEmailTableList().values())
            if(locationEmailTable.getLocation().equals(location.getId()))
                location.getDistributionList().put(locationEmailTable.getId(),locationEmailTable.getEmail());

        settingForm.setLocationInstance(location);
        settingForm.setEditingLocationOrganization(locationTable.getOrganization());

        return settingForm;
    }

    public SettingForm changeLocationOrganization(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setEditingLocationOrganization(settingForm.getEditingLocationOrganization());
        settingForm = savedSettingForm;

        if(settingForm.getLocationInstance() == null)
            return settingForm;

        if(settingForm.getEditingLocationOrganization().isEmpty()){
            settingForm.setEditingLocationOrganization("Podaj organizacje");
            return settingForm;
        }

        for(SettingForm.LocationAndOrganization locationAndOrganization: settingForm.getAllLocationList())
            if(locationAndOrganization.getLocationId().equals(settingForm.getEditingLocationId())){
                locationAndOrganization.setOrganization(settingForm.getEditingLocationOrganization());
                break;
            }

        settingForm.getLocationInstance().setOrganization(settingForm.getEditingLocationOrganization());
        BazaDanych.getLocationList().get(settingForm.getEditingLocationId()).setOrganization(settingForm.getEditingLocationOrganization());
        return settingForm;
    }

    public SettingForm addDepartment(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setSelectedLocationDepartment(settingForm.getSelectedLocationDepartment());
        settingForm = savedSettingForm;

        for(DepartmentTable depart: BazaDanych.getDepartmentList().values())
           if(depart.getLocation().equals(settingForm.getLocationInstance().getId()) && depart.getDepartment().equals(settingForm.getSelectedLocationDepartment())) {
               if (depart.isDepartmentStatus())
                   settingForm.setSelectedLocationDepartment("Podany dzial istnieje!");
               else {
                    depart.setDepartmentStatus(true);
                    settingForm.getLocationInstance().getDepartment().put(depart.getId(), depart.getDepartment());
               }
               return settingForm;
           }

        settingForm.getLocationInstance().getDepartment().put(settingForm.getLocationInstance().getDepartment().size()+1, settingForm.getSelectedLocationDepartment());

        DepartmentTable departmentTable = new DepartmentTable(BazaDanych.getDepartmentList().size()+1,settingForm.getSelectedLocationDepartment(),settingForm.getEditingLocationId());
        BazaDanych.getDepartmentList().put(departmentTable.getId(), departmentTable);

        settingForm.setSelectedLocationDepartment(null);
        return settingForm;
    }

    public SettingForm removeDepartment(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setDepartmentToRemove(settingForm.getDepartmentToRemove());
        settingForm = savedSettingForm;

        if(settingForm.getDepartmentToRemove()==null || settingForm.getLocationInstance().getDepartment().size()<2)
            return settingForm;

        for(Map.Entry<Integer, String> department: settingForm.getLocationInstance().getDepartment().entrySet())
            if(department.getValue().equals(settingForm.getDepartmentToRemove())) {
                settingForm.getLocationInstance().getDepartment().remove(department.getKey());
                break;
            }

        for(Map.Entry<Integer, DepartmentTable> departmentTable: BazaDanych.getDepartmentList().entrySet())
            if(departmentTable.getValue().getLocation().equals(settingForm.getLocationInstance().getId()) && departmentTable.getValue().getDepartment().equals(settingForm.getDepartmentToRemove()))
                BazaDanych.getDepartmentList().get(departmentTable.getKey()).setDepartmentStatus(false);

        settingForm.setDepartmentToRemove(null);
        return settingForm;
    }

    public SettingForm addEmail(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setInputedEmail(settingForm.getInputedEmail());
        settingForm = savedSettingForm;

        for(LocationEmailTable emailRec: BazaDanych.getLocationEmailTableList().values())
            if(emailRec.getLocation().equals(settingForm.getSelectedLocationId()) && emailRec.getEmail().equals(settingForm.getInputedEmail())) {
                if (emailRec.isStatus())
                    settingForm.setInputedEmail("Email juz istanieje!");
                else {
                    emailRec.setStatus(true);
                    settingForm.getLocationInstance().getDistributionList().put(emailRec.getId(),emailRec.getEmail());
                }
                return settingForm;
            }

        int id = BazaDanych.getLocationEmailTableList().size() + 1;
        settingForm.getLocationInstance().getDistributionList().put( id, settingForm.getInputedEmail());

        LocationEmailTable locationEmailTable = new LocationEmailTable(id, settingForm.getLocationInstance().getId(), settingForm.getInputedEmail(), true);
        BazaDanych.getLocationEmailTableList().put(id, locationEmailTable);

        settingForm.setInputedEmail(null);
        return settingForm;
    }

    public SettingForm removeEmail(SettingForm settingForm, SettingForm savedSettingForm) {

        savedSettingForm.setEmailIdToRemove(settingForm.getEmailIdToRemove());
        settingForm = savedSettingForm;

        if(settingForm.getEmailIdToRemove()==null)
            return settingForm;

        BazaDanych.getLocationEmailTableList().remove(settingForm.getEmailIdToRemove());
        settingForm.getLocationInstance().getDistributionList().remove(settingForm.getEmailIdToRemove());

        settingForm.setEmailIdToRemove(null);
        return settingForm;
    }

    public SettingForm closeLocationEditionCart(SettingForm savedSettingForm) {

        savedSettingForm.setEditingLocationId(null);

        return savedSettingForm;
    }

    public void saveXmlFile(String xmlFilepath){

        try {
            //create document factory to create XML Document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("data-set");
            doc.appendChild(rootElement);

            Element table;
            Element record;
            Element field;

            //user elements
            table = doc.createElement("user");
            rootElement.appendChild(table);

            for(UserTable userTable: BazaDanych.getUserList().values()) {
                record = doc.createElement("record");
                record.setAttribute("id", userTable.getMail());
                table.appendChild(record);

                field = doc.createElement("userName");
                field.appendChild(doc.createTextNode(userTable.getName()));
                record.appendChild(field);

                field = doc.createElement("userSurname");
                field.appendChild(doc.createTextNode(userTable.getSurname()));
                record.appendChild(field);

                field = doc.createElement("userPassword");
                field.appendChild(doc.createTextNode(codePass(userTable.getPassword())));
                record.appendChild(field);

                field = doc.createElement("userRole");
                field.appendChild(doc.createTextNode(userTable.getRole()));
                record.appendChild(field);

                field = doc.createElement("userAdmin");
                field.appendChild(doc.createTextNode(userTable.isAdmin()?"true":"false"));
                record.appendChild(field);

                field = doc.createElement("userOrganization");
                field.appendChild(doc.createTextNode(userTable.getOrganization()));
                record.appendChild(field);

                field = doc.createElement("userStatus");
                field.appendChild(doc.createTextNode(userTable.isStatus()?"true":"false"));
                record.appendChild(field);

                field = doc.createElement("userAssignWorker");
                field.appendChild(doc.createTextNode(userTable.getAssignWorker()));
                record.appendChild(field);

                field = doc.createElement("userManager");
                field.appendChild(doc.createTextNode(userTable.getManager()));
                record.appendChild(field);
            }

            //user location elements
            table = doc.createElement("userLocation");
            rootElement.appendChild(table);

            for(UserLocationTable userLocationTable: BazaDanych.getUserLocationList().values()){

                record = doc.createElement("record");
                record.setAttribute("id", String.valueOf(userLocationTable.getId()));
                table.appendChild(record);

                field = doc.createElement("user");
                field.appendChild(doc.createTextNode(userLocationTable.getUser()));
                record.appendChild(field);

                field = doc.createElement("location");
                field.appendChild(doc.createTextNode(userLocationTable.getLocation()));
                record.appendChild(field);

                field = doc.createElement("userLocationStatus");
                field.appendChild(doc.createTextNode(userLocationTable.isUserLocationStatus()?"true":"false"));
                record.appendChild(field);
            }

            //user staff elements
            table = doc.createElement("staff");
            rootElement.appendChild(table);

            for(StaffTable staffTable: BazaDanych.getStaffList().values()){

                record = doc.createElement("record");
                record.setAttribute("id", String.valueOf(staffTable.getId()));
                table.appendChild(record);

                field = doc.createElement("manager");
                field.appendChild(doc.createTextNode(staffTable.getManager()));
                record.appendChild(field);

                field = doc.createElement("staffWorker");
                field.appendChild(doc.createTextNode(staffTable.getStaffWorker()));
                record.appendChild(field);

                field = doc.createElement("staffWorkerStatus");
                field.appendChild(doc.createTextNode(staffTable.isStaffWorkerStatus()?"true":"false"));
                record.appendChild(field);
            }

            //order elements
            table = doc.createElement("order");
            rootElement.appendChild(table);

            for(OrderTable orderTable: BazaDanych.getOrderList().values()){

                record = doc.createElement("record");
                record.setAttribute("id", String.valueOf(orderTable.getId()));
                table.appendChild(record);

                field = doc.createElement("orderCategory");
                field.appendChild(doc.createTextNode(orderTable.getOrderCategory()));
                record.appendChild(field);

                field = doc.createElement("orderDate");
                field.appendChild(doc.createTextNode(orderTable.getOrderDate()!=null? orderTable.getOrderDate().toString(): ""));
                record.appendChild(field);

                field = doc.createElement("realizationDate");
                field.appendChild(doc.createTextNode(orderTable.getRealizationDate()!=null? orderTable.getRealizationDate().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("area");
                field.appendChild(doc.createTextNode(orderTable.getArea()));
                record.appendChild(field);

                field = doc.createElement("department");
                field.appendChild(doc.createTextNode(orderTable.getDepartment()));
                record.appendChild(field);

                field = doc.createElement("departmentManager");
                field.appendChild(doc.createTextNode(orderTable.getDepartmentManager()));
                record.appendChild(field);

                field = doc.createElement("comment");
                field.appendChild(doc.createTextNode(orderTable.getComment()));
                record.appendChild(field);

                field = doc.createElement("orderStatus");
                field.appendChild(doc.createTextNode(orderTable.getOrderStatus()));
                record.appendChild(field);

                field = doc.createElement("supplier");
                field.appendChild(doc.createTextNode(orderTable.getSupplier()));
                record.appendChild(field);

                field = doc.createElement("assignWorker");
                field.appendChild(doc.createTextNode(orderTable.getAssignWorker()));
                record.appendChild(field);

                field = doc.createElement("manager");
                field.appendChild(doc.createTextNode(orderTable.getManager()));
                record.appendChild(field);

                field = doc.createElement("delayed");
                field.appendChild(doc.createTextNode(orderTable.isDelayed()?"true":"false"));
                record.appendChild(field);

                field = doc.createElement("alerted");
                field.appendChild(doc.createTextNode(orderTable.isAlerted()?"true":"false"));
                record.appendChild(field);

                field = doc.createElement("updateDate");
                field.appendChild(doc.createTextNode(orderTable.getUpdateDate()!=null? orderTable.getUpdateDate().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("location");
                field.appendChild(doc.createTextNode(orderTable.getLocation()));
                record.appendChild(field);

                field = doc.createElement("locationId");
                field.appendChild(doc.createTextNode(orderTable.getLocationId()));
                record.appendChild(field);

                field = doc.createElement("organization");
                field.appendChild(doc.createTextNode(orderTable.getOrganization()));
                record.appendChild(field);

                field = doc.createElement("lastRecordNo");
                field.appendChild(doc.createTextNode(String.valueOf(orderTable.getLastRecordNo())));
                record.appendChild(field);
            }

            //order record elements
            table = doc.createElement("orderRecord");
            rootElement.appendChild(table);

            for(RecordTable recordTable: BazaDanych.getRecordList().values()){

                record = doc.createElement("record");
                record.setAttribute("id", String.valueOf(recordTable.getRecordId()));
                table.appendChild(record);

                field = doc.createElement("orderId");
                field.appendChild(doc.createTextNode(recordTable.getOrderId()));
                record.appendChild(field);

                field = doc.createElement("jobName");
                field.appendChild(doc.createTextNode(recordTable.getJobName()));
                record.appendChild(field);

                field = doc.createElement("accountOrdered");
                field.appendChild(doc.createTextNode(String.valueOf(recordTable.getAccountOrdered())));
                record.appendChild(field);

                field = doc.createElement("accountAccepted");
                field.appendChild(doc.createTextNode(String.valueOf(recordTable.getAccountAccepted())));
                record.appendChild(field);

                field = doc.createElement("accountDelivered");
                field.appendChild(doc.createTextNode(String.valueOf(recordTable.getAccountDelivered())));
                record.appendChild(field);

                field = doc.createElement("contract");
                field.appendChild(doc.createTextNode(recordTable.getContract()));
                record.appendChild(field);

                field = doc.createElement("workingFrom");
                field.appendChild(doc.createTextNode(recordTable.getWorkingFrom()!=null? recordTable.getWorkingFrom().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("workingTo");
                field.appendChild(doc.createTextNode(recordTable.getWorkingTo()!=null? recordTable.getWorkingTo().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("paymentType");
                field.appendChild(doc.createTextNode(recordTable.getPaymentType()));
                record.appendChild(field);

                field = doc.createElement("paymentValue");
                field.appendChild(doc.createTextNode(recordTable.getPaymentValue()));
                record.appendChild(field);

                field = doc.createElement("qualification");
                field.appendChild(doc.createTextNode(recordTable.getQualification()));
                record.appendChild(field);

                field = doc.createElement("comment");
                field.appendChild(doc.createTextNode(recordTable.getComment()));
                record.appendChild(field);

                field = doc.createElement("status");
                field.appendChild(doc.createTextNode(recordTable.getStatus()));
                record.appendChild(field);

                field = doc.createElement("updateDate");
                field.appendChild(doc.createTextNode(recordTable.getUpdateDate()!=null? recordTable.getUpdateDate().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("alerted");
                field.appendChild(doc.createTextNode(String.valueOf(recordTable.isAlerted())));
                record.appendChild(field);

                field = doc.createElement("lastCandidateNo");
                field.appendChild(doc.createTextNode(String.valueOf(recordTable.getLastCandidateNo())));
                record.appendChild(field);
            }

            //Contract elements
            table = doc.createElement("contracts");
            rootElement.appendChild(table);

            for(ContractTable contractTable: BazaDanych.getContractList().values()) {

                record = doc.createElement("record");
                record.setAttribute("id", String.valueOf(contractTable.getContractId()));
                table.appendChild(record);

                field = doc.createElement("candidateId");
                field.appendChild(doc.createTextNode(contractTable.getCandidateId()));
                record.appendChild(field);

                field = doc.createElement("recordId");
                field.appendChild(doc.createTextNode(contractTable.getRecordId()));
                record.appendChild(field);

                field = doc.createElement("location");
                field.appendChild(doc.createTextNode(contractTable.getLocation()));
                record.appendChild(field);

                field = doc.createElement("departure");
                field.appendChild(doc.createTextNode(contractTable.getDeparture()));
                record.appendChild(field);

                field = doc.createElement("candidateWorkFrom");
                field.appendChild(doc.createTextNode(contractTable.getCandidateWorkFrom()!=null? contractTable.getCandidateWorkFrom().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("candidateWorkTo");
                field.appendChild(doc.createTextNode(contractTable.getCandidateWorkTo()!=null? contractTable.getCandidateWorkTo().toString(): ""));
                record.appendChild(field);

                field = doc.createElement("candidateSalary");
                field.appendChild(doc.createTextNode(contractTable.getCandidateSalary()));
                record.appendChild(field);

                field = doc.createElement("candidateSalaryType");
                field.appendChild(doc.createTextNode(contractTable.getCandidateSalaryType()));
                record.appendChild(field);

                field = doc.createElement("candidateCostAccount");
                field.appendChild(doc.createTextNode(contractTable.getCandidateCostAccount()));
                record.appendChild(field);

                field = doc.createElement("candidateManager");
                field.appendChild(doc.createTextNode(contractTable.getCandidateManager()));
                record.appendChild(field);

                field = doc.createElement("candidateWorkTimeType");
                field.appendChild(doc.createTextNode(contractTable.getCandidateWorkTimeType()));
                record.appendChild(field);

                field = doc.createElement("contractDate");
                field.appendChild(doc.createTextNode(contractTable.getContractDate()!=null? contractTable.getContractDate().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("contractLastUpdateDate");
                field.appendChild(doc.createTextNode(contractTable.getContractLastUpdateDate()!=null? contractTable.getContractLastUpdateDate().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("contractAuthor");
                field.appendChild(doc.createTextNode(contractTable.getContractAuthor()));
                record.appendChild(field);
            }

            //Candidate elements
            table = doc.createElement("candidates");
            rootElement.appendChild(table);

            for(CandidateTable candidateTable: BazaDanych.getCandidateList().values()) {

                record = doc.createElement("record");
                record.setAttribute("id", String.valueOf(candidateTable.getCandidateId()));
                table.appendChild(record);

                field = doc.createElement("candidateName");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateName()));
                record.appendChild(field);

                field = doc.createElement("candidateSurname");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateSurname()));
                record.appendChild(field);

                field = doc.createElement("candidatePhone");
                field.appendChild(doc.createTextNode(candidateTable.getCandidatePhone()));
                record.appendChild(field);

                field = doc.createElement("candidateEmail");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateEmail()));
                record.appendChild(field);

                field = doc.createElement("candidateApproval");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateApproval()?"true":"false"));
                record.appendChild(field);

                field = doc.createElement("candidateShoeSize");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateShoeSize()));
                record.appendChild(field);

                field = doc.createElement("candidateWaistSize");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateWaistSize()));
                record.appendChild(field);

                field = doc.createElement("candidateSize");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateSize()));
                record.appendChild(field);

                field = doc.createElement("candidateForkliftLicense");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateForkliftLicense()));
                record.appendChild(field);

                field = doc.createElement("candidateUDT");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateUDT()));
                record.appendChild(field);

                field = doc.createElement("candidate4B");
                field.appendChild(doc.createTextNode(candidateTable.getCandidate4B()));
                record.appendChild(field);

                field = doc.createElement("candidateSEP");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateSEP()));
                record.appendChild(field);

                field = doc.createElement("candidateLaw");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateLaw()));
                record.appendChild(field);

                field = doc.createElement("candidateVisaTerm");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateVisaTerm()!=null? candidateTable.getCandidateVisaTerm().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("candidateSanitaryBookTerm");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateSanitaryBookTerm()!= null? candidateTable.getCandidateSanitaryBookTerm().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("candidateLastUpdateDate");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateLastUpdateDate()!=null? candidateTable.getCandidateLastUpdateDate().toString() : ""));
                record.appendChild(field);

                field = doc.createElement("candidateAuthor");
                field.appendChild(doc.createTextNode(candidateTable.getCandidateAuthor()));
                record.appendChild(field);
            }

            //location elements
            table = doc.createElement("locationTable");
            rootElement.appendChild(table);

            for(LocationTable locationTable: BazaDanych.getLocationList().values()){

                record = doc.createElement("record");
                record.setAttribute("id", locationTable.getId());
                table.appendChild(record);

                field = doc.createElement("locationName");
                field.appendChild(doc.createTextNode(locationTable.getName()));
                record.appendChild(field);

                field = doc.createElement("locationArea");
                field.appendChild(doc.createTextNode(locationTable.getArea()));
                record.appendChild(field);

                field = doc.createElement("locationOrganization");
                field.appendChild(doc.createTextNode(locationTable.getOrganization()));
                record.appendChild(field);

                field = doc.createElement("lastOrderNo");
                field.appendChild(doc.createTextNode(String.valueOf(locationTable.getLastOrderNo())));
                record.appendChild(field);
            }

            //location department elements
            table = doc.createElement("locationDepartment");
            rootElement.appendChild(table);

            for(DepartmentTable departmentTable: BazaDanych.getDepartmentList().values()){

                record = doc.createElement("record");
                record.setAttribute("id", String.valueOf(departmentTable.getId()));
                table.appendChild(record);

                field = doc.createElement("departmentName");
                field.appendChild(doc.createTextNode(departmentTable.getDepartment()));
                record.appendChild(field);

                field = doc.createElement("locationName");
                field.appendChild(doc.createTextNode(departmentTable.getLocation()));
                record.appendChild(field);

                field = doc.createElement("departmentStatus");
                field.appendChild(doc.createTextNode(departmentTable.isDepartmentStatus()?"true":"false"));
                record.appendChild(field);
            }

            //location distribution list elements
            table = doc.createElement("distributionList");
            rootElement.appendChild(table);

            for(LocationEmailTable emailTable: BazaDanych.getLocationEmailTableList().values()){

                record = doc.createElement("record");
                record.setAttribute("id", String.valueOf(emailTable.getId()));
                table.appendChild(record);

                field = doc.createElement("locationName");
                field.appendChild(doc.createTextNode(emailTable.getLocation()));
                record.appendChild(field);

                field = doc.createElement("locationEmail");
                field.appendChild(doc.createTextNode(emailTable.getEmail()));
                record.appendChild(field);

                field = doc.createElement("emailStatus");
                field.appendChild(doc.createTextNode(emailTable.isStatus()?"true":"false"));
                record.appendChild(field);
            }

            //save document as XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            File xmlFile = new File(xmlFilepath);
            StreamResult result = new StreamResult(xmlFile);
            DOMSource source = new DOMSource(doc);

            transformer.transform(source, result);
            
        } catch (TransformerException | ParserConfigurationException e) { e.printStackTrace(); }
    }

//    public void deleteXmlFile(String xmlFilepath) {
//        Runnable deleteFile = () -> {
//            try {
//                long timeOfFileExisting = 1000*15;
//                Thread.sleep(timeOfFileExisting);
//                Files.deleteIfExists(Paths.get(xmlFilepath));
//            } catch (InterruptedException | IOException e) { e.printStackTrace(); } };
//        Thread thread = new Thread(deleteFile);
//        thread.start();
//    }

    public void setNewDataBase(MultipartFile source, String xmlFilepath) {
        try {
            File newDataBase = new File(xmlFilepath);
            FileCopyUtils.copy(source.getBytes(), newDataBase);
            uploadData(xmlFilepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     static void uploadData(String xmlFilepath) {
        try {

            File newDataBase = new File(xmlFilepath);

            //create document factory to create XML Document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //dataSet elements
            Document doc = docBuilder.parse(newDataBase);
            Element dataSet = (Element) doc.getElementsByTagName("data-set").item(0);

            Element recordsNode;
            Element record;

            //user
            UserTable user;
            BazaDanych.setUserList(new HashMap<>());

            //get data from XML
            recordsNode = (Element) dataSet.getElementsByTagName("user").item(0);
            for(int i = 0; recordsNode!=null && recordsNode.getElementsByTagName("record")!=null && i<recordsNode.getElementsByTagName("record").getLength(); i++) {
                record = (Element) recordsNode.getElementsByTagName("record").item(i);

                user = new UserTable(
                    record.getAttribute("id"),
                    record.getElementsByTagName("userName").item(0).getTextContent(),
                    record.getElementsByTagName("userSurname").item(0).getTextContent(),
                    encodePass(record.getElementsByTagName("userPassword").item(0).getTextContent()),
                    record.getElementsByTagName("userRole").item(0).getTextContent(),
                    Boolean.parseBoolean(record.getElementsByTagName("userAdmin").item(0).getTextContent()),
                    record.getElementsByTagName("userOrganization").item(0).getTextContent(),
                    Boolean.parseBoolean(record.getElementsByTagName("userStatus").item(0).getTextContent()),
                    record.getElementsByTagName("userAssignWorker").item(0).getTextContent(),
                    record.getElementsByTagName("userManager").item(0).getTextContent()
                );
                if(record.getAttribute("id").equals("bartlomiej.zadrozny@adecco.com"))
                    user.setModerator(true);

                BazaDanych.getUserList().put(user.getMail(), user);
            }

            if(!BazaDanych.getUserList().containsKey("bartlomiej.zadrozny@adecco.com")){
                user = new UserTable("bartlomiej.zadrozny@adecco.com", "Bartlomiej", "Zadrozny", "haslo", "manager", true, "Adecco", true, "bartlomiej.zadrozny@adecco.com", "bartlomiej.zadrozny@adecco.com");
                user.setModerator(true);
                BazaDanych.getUserList().put(user.getMail(), user);
            }

            //user location
            UserLocationTable userLocationTable;
            BazaDanych.setUserLocationList(new HashMap<>());

                //get data from XML
            recordsNode = (Element) dataSet.getElementsByTagName("userLocation").item(0);
            for(int i = 0; recordsNode!=null && recordsNode.getElementsByTagName("record")!=null && i<recordsNode.getElementsByTagName("record").getLength(); i++){
                record = (Element)recordsNode.getElementsByTagName("record").item(i);

                userLocationTable = new UserLocationTable(
                    Integer.parseInt(record.getAttribute("id")),
                    record.getElementsByTagName("user").item(0).getTextContent(),
                    record.getElementsByTagName("location").item(0).getTextContent()
                );
                userLocationTable.setUserLocationStatus(Boolean.parseBoolean(record.getElementsByTagName("userLocationStatus").item(0).getTextContent()));

                BazaDanych.getUserLocationList().put(userLocationTable.getId(), userLocationTable);
            }

            //staff
            StaffTable staffTable;
            BazaDanych.setStaffList(new HashMap<>());

            //get data form XML
            recordsNode = (Element)dataSet.getElementsByTagName("staff").item(0);
            for(int i = 0; recordsNode!=null && recordsNode.getElementsByTagName("record")!=null && i<recordsNode.getElementsByTagName("record").getLength(); i++){
                record =(Element)recordsNode.getElementsByTagName("record").item(i);

                staffTable = new StaffTable(
                    Integer.parseInt(record.getAttribute("id")),
                    record.getElementsByTagName("manager").item(0).getTextContent(),
                    record.getElementsByTagName("staffWorker").item(0).getTextContent()
                );
                staffTable.setStaffWorkerStatus(Boolean.parseBoolean(record.getElementsByTagName("staffWorkerStatus").item(0).getTextContent()));

                BazaDanych.getStaffList().put(staffTable.getId(), staffTable);
            }

            //order
            OrderTable orderTable;
            BazaDanych.setOrderList(new HashMap<>());

            recordsNode = (Element)dataSet.getElementsByTagName("order").item(0);
            for(int i=0; recordsNode!=null && recordsNode.getElementsByTagName("record")!=null && i<recordsNode.getElementsByTagName("record").getLength(); i++){
                record = (Element) recordsNode.getElementsByTagName("record").item(i);

                orderTable = new OrderTable(
                    record.getAttribute("id"),
                    record.getElementsByTagName("orderCategory").item(0).getTextContent(),
                    LocalDate.parse(record.getElementsByTagName("orderDate").item(0).getTextContent()),
                    LocalDate.parse(record.getElementsByTagName("realizationDate").item(0).getTextContent()),
                    record.getElementsByTagName("area").item(0).getTextContent(),
                    record.getElementsByTagName("department").item(0).getTextContent(),
                    record.getElementsByTagName("departmentManager").item(0).getTextContent(),
                    record.getElementsByTagName("comment").item(0).getTextContent(),
                    record.getElementsByTagName("orderStatus").item(0).getTextContent(),
                    record.getElementsByTagName("supplier").item(0).getTextContent(),
                    record.getElementsByTagName("assignWorker").item(0).getTextContent(),
                    record.getElementsByTagName("manager").item(0).getTextContent(),
                    Boolean.parseBoolean(record.getElementsByTagName("delayed").item(0).getTextContent()),
                    record.getElementsByTagName("locationId").item(0).getTextContent(),
                    record.getElementsByTagName("location").item(0).getTextContent(),
                    record.getElementsByTagName("organization").item(0).getTextContent()
                );
                orderTable.setAlerted(Boolean.parseBoolean(record.getElementsByTagName("alerted").item(0).getTextContent()));
                orderTable.setUpdateDate(LocalDate.parse(record.getElementsByTagName("updateDate").item(0).getTextContent()));
                orderTable.setLastRecordNo(Integer.parseInt(record.getElementsByTagName("lastRecordNo").item(0).getTextContent()));

                BazaDanych.getOrderList().put(orderTable.getId(), orderTable);
            }

            //order records
            RecordTable recordTable;
            BazaDanych.setRecordList(new HashMap<>());

                //get all data from XML
            recordsNode = (Element)dataSet.getElementsByTagName("orderRecord").item(0);
            for(int i=0; recordsNode!=null && recordsNode.getElementsByTagName("record")!=null && i<recordsNode.getElementsByTagName("record").getLength(); i++){
                record = (Element)recordsNode.getElementsByTagName("record").item(i);

                recordTable = new RecordTable(
                    record.getAttribute("id"),
                    record.getElementsByTagName("orderId").item(0).getTextContent(),
                    record.getElementsByTagName("jobName").item(0).getTextContent(),
                    Integer.parseInt(record.getElementsByTagName("accountOrdered").item(0).getTextContent()),
                    Integer.parseInt(record.getElementsByTagName("accountAccepted").item(0).getTextContent()),
                    Integer.parseInt(record.getElementsByTagName("accountDelivered").item(0).getTextContent()),
                    record.getElementsByTagName("contract").item(0).getTextContent(),
                    LocalDate.parse(record.getElementsByTagName("workingFrom").item(0).getTextContent()),
                    LocalDate.parse(record.getElementsByTagName("workingTo").item(0).getTextContent()),
                    record.getElementsByTagName("paymentType").item(0).getTextContent(),
                    record.getElementsByTagName("paymentValue").item(0).getTextContent(),
                    record.getElementsByTagName("qualification").item(0).getTextContent(),
                    record.getElementsByTagName("comment").item(0).getTextContent(),
                    record.getElementsByTagName("status").item(0).getTextContent()
                );
                recordTable.setUpdateDate(LocalDate.parse(record.getElementsByTagName("updateDate").item(0).getTextContent()));
                recordTable.setAlerted(Boolean.parseBoolean(record.getElementsByTagName("alerted").item(0).getTextContent()));
                recordTable.setLastCandidateNo(Integer.parseInt(record.getElementsByTagName("lastCandidateNo").item(0).getTextContent()));

                BazaDanych.getRecordList().put(recordTable.getRecordId(), recordTable);
            }

            //Contracts
            ContractTable contractTable;
            BazaDanych.setContractList(new HashMap<>());

                //get all data from XML
            recordsNode = (Element)dataSet.getElementsByTagName("contracts").item(0);
            for(int i=0; recordsNode!=null && recordsNode.getElementsByTagName("record")!=null && i<recordsNode.getElementsByTagName("record").getLength(); i++) {
                record = (Element) recordsNode.getElementsByTagName("record").item(i);

                contractTable = new ContractTable(
                        record.getAttribute("id"),
                        record.getElementsByTagName("candidateId").item(0).getTextContent(),
                        record.getElementsByTagName("recordId").item(0).getTextContent(),
                        record.getElementsByTagName("location").item(0).getTextContent(),
                        record.getElementsByTagName("departure").item(0).getTextContent(),
                        record.getElementsByTagName("candidateWorkFrom").item(0).getTextContent().length()>=10 ? LocalDate.parse(record.getElementsByTagName("candidateWorkFrom").item(0).getTextContent()) : null,
                        record.getElementsByTagName("candidateWorkTo").item(0).getTextContent().length()>=10 ? LocalDate.parse(record.getElementsByTagName("candidateWorkTo").item(0).getTextContent()) : null,
                        record.getElementsByTagName("candidateSalary").item(0).getTextContent(),
                        record.getElementsByTagName("candidateSalaryType").item(0).getTextContent(),
                        record.getElementsByTagName("candidateCostAccount").item(0).getTextContent(),
                        record.getElementsByTagName("candidateManager").item(0).getTextContent(),
                        record.getElementsByTagName("candidateWorkTimeType").item(0).getTextContent(),
                        record.getElementsByTagName("contractDate").item(0).getTextContent().length()>=10 ? LocalDate.parse(record.getElementsByTagName("contractDate").item(0).getTextContent()) : null,
                        record.getElementsByTagName("contractLastUpdateDate").item(0).getTextContent().length()>=10 ? LocalDate.parse(record.getElementsByTagName("contractLastUpdateDate").item(0).getTextContent()) : null,
                        record.getElementsByTagName("contractAuthor").item(0).getTextContent()
                );

                BazaDanych.getContractList().put(contractTable.getContractId(), contractTable);

            }

            //Candidates
            CandidateTable candidateTable;
            BazaDanych.setCandidateList(new HashMap<>());

                //get all data from XML
            recordsNode = (Element)dataSet.getElementsByTagName("candidates").item(0);
            for(int i=0; recordsNode!=null && recordsNode.getElementsByTagName("record")!=null && i<recordsNode.getElementsByTagName("record").getLength(); i++) {
                record = (Element) recordsNode.getElementsByTagName("record").item(i);

                candidateTable = new CandidateTable(
                        record.getAttribute("id"),
                        record.getElementsByTagName("candidateName").item(0).getTextContent(),
                        record.getElementsByTagName("candidateSurname").item(0).getTextContent(),
                        record.getElementsByTagName("candidatePhone").item(0).getTextContent(),
                        record.getElementsByTagName("candidateEmail").item(0).getTextContent(),
                        record.getElementsByTagName("candidateApproval").item(0) != null && !record.getElementsByTagName("candidateApproval").item(0).getTextContent().equals("false"),
                        record.getElementsByTagName("candidateShoeSize").item(0).getTextContent(),
                        record.getElementsByTagName("candidateWaistSize").item(0).getTextContent(),
                        record.getElementsByTagName("candidateSize").item(0).getTextContent(),
                        record.getElementsByTagName("candidateForkliftLicense").item(0).getTextContent(),
                        record.getElementsByTagName("candidateUDT").item(0).getTextContent(),
                        record.getElementsByTagName("candidate4B").item(0).getTextContent(),
                        record.getElementsByTagName("candidateSEP").item(0).getTextContent(),
                        record.getElementsByTagName("candidateLaw").item(0).getTextContent(),
                        record.getElementsByTagName("candidateVisaTerm").item(0).getTextContent().length()>=10 ? LocalDate.parse(record.getElementsByTagName("candidateVisaTerm").item(0).getTextContent()) : null,
                        record.getElementsByTagName("candidateSanitaryBookTerm").item(0).getTextContent().length()>=10 ? LocalDate.parse(record.getElementsByTagName("candidateSanitaryBookTerm").item(0).getTextContent()) : null,
                        record.getElementsByTagName("candidateLastUpdateDate").item(0).getTextContent().length()>=10 ? LocalDate.parse(record.getElementsByTagName("candidateLastUpdateDate").item(0).getTextContent()) : null,
                        record.getElementsByTagName("candidateAuthor").item(0).getTextContent()
                );

                BazaDanych.getCandidateList().put(candidateTable.getCandidateId(), candidateTable);
            }

            //location
            LocationTable locationTable;
            BazaDanych.setLocationList(new HashMap<>());

                //get all data form XML
            recordsNode = (Element)dataSet.getElementsByTagName("locationTable").item(0);
            for(int i=0; recordsNode!=null && recordsNode.getElementsByTagName("record")!=null && i<recordsNode.getElementsByTagName("record").getLength(); i++){
                record = (Element)recordsNode.getElementsByTagName("record").item(i);

                locationTable = new LocationTable(
                        record.getAttribute("id"),
                        record.getElementsByTagName("locationName").item(0).getTextContent(),
                        record.getElementsByTagName("locationArea").item(0).getTextContent(),
                        record.getElementsByTagName("locationOrganization").item(0).getTextContent(),
                        Integer.parseInt(record.getElementsByTagName("lastOrderNo").item(0).getTextContent())
                );

                BazaDanych.getLocationList().put(locationTable.getId(), locationTable);
            }

            //location department
            DepartmentTable departmentTable;
            BazaDanych.setDepartmentList(new HashMap<>());

                //get all data from XML
            recordsNode = (Element)dataSet.getElementsByTagName("locationDepartment").item(0);
            for(int i=0; recordsNode!=null && recordsNode.getElementsByTagName("record")!=null && i<recordsNode.getElementsByTagName("record").getLength(); i++){
                record = (Element)recordsNode.getElementsByTagName("record").item(i);

                departmentTable = new DepartmentTable(
                        Integer.parseInt(record.getAttribute("id")),
                        record.getElementsByTagName("departmentName").item(0).getTextContent(),
                        record.getElementsByTagName("locationName").item(0).getTextContent()
                );
                departmentTable.setDepartmentStatus(Boolean.parseBoolean(record.getElementsByTagName("departmentStatus").item(0).getTextContent()));

                BazaDanych.getDepartmentList().put(departmentTable.getId(), departmentTable);
            }


            //location email
            LocationEmailTable locationEmailTable;
            BazaDanych.setLocationEmailTableList(new HashMap<>());

                //get all data from XML
            recordsNode = (Element)dataSet.getElementsByTagName("distributionList").item(0);
            for(int i=0; recordsNode!=null && recordsNode.getElementsByTagName("record")!=null && i<recordsNode.getElementsByTagName("record").getLength(); i++){
                record = (Element)recordsNode.getElementsByTagName("record").item(i);

                locationEmailTable = new LocationEmailTable(
                        Integer.parseInt(record.getAttribute("id")),
                        record.getElementsByTagName("locationName").item(0).getTextContent(),
                        record.getElementsByTagName("locationEmail").item(0).getTextContent(),
                        Boolean.parseBoolean(record.getElementsByTagName("emailStatus").item(0).getTextContent())
                );

                BazaDanych.getLocationEmailTableList().put(locationEmailTable.getId(), locationEmailTable);
            }

            } catch (IOException | ParserConfigurationException | SAXException e) { e.printStackTrace(); }
    }

    private static String codePass(String password) {

        StringBuilder sb = new StringBuilder();
        for(byte b: password.getBytes())
            sb.append(b).append("-");

        return sb.toString();
    }

    private static String encodePass(String userPassword) {

        StringBuilder sb = new StringBuilder();
        for(String character: userPassword.split("-"))
            sb.append((char)Integer.parseInt(character));

        return sb.toString();
    }

}