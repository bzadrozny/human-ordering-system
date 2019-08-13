package com.bz.hos.model.SettingForm;

import com.bz.hos.model.UserAndLocationEntity.Location;
import com.bz.hos.model.UserAndLocationEntity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingForm {

    //change name
    private String name;
    private String surname;
    private String mail;

    //change organization
    private String organization;

    //change pass
    private String oldPass;
    private String newPass;
    private String newPassConfirmation;
    private boolean passwordChangeSucces;

    //new User
    private boolean showNewUserCart = false;

    private String userMail;
    private String userMailInfo;
    private String userName;
    private String userSurname;
    private String password;
    private String confirmPassword;
    private String passInfo;

    private String userRole;
    private boolean userAdmin;
    private String userOrganization;

    private boolean userStatus;

    private String userAssignWorker;
    private String userManager;

    //new Location
    private boolean showNewLocationCart = false;

    private String locationId;
    private String locationName;
    private String locationArea;
    private String locationOrganization;

    //add or remove Location from selectedUser
    //add or remove AssignWorker form selectedUser
    //change password from selectedUser
    //change assignWorker or Manager form selectedUser
    private User selectedUser;
    private List<UserAndRole> selectedUserStaff = new ArrayList<>();

    private String tempPass;
    private String tempPassConfirmation;

    private String selectedLocationId;
    private String selectedAssignWorker;
    private String selectedLocationDepartment;

    //add or remove Department from selectedLocation
    private String editingLocationId;
    private String editingLocationOrganization;
    private Location locationInstance;
    private String departmentToRemove;
    private String inputedEmail;
    private Integer emailIdToRemove;

    //list for admin
    private List<LocationAndOrganization> allLocationList = new ArrayList<>();
    private List<UserAndRole> allClientList = new ArrayList<>();
    private List<UserAndRole> allRecruiterList = new ArrayList<>();
    private List<UserAndRole> allManagerList = new ArrayList<>();
    private Map<String, String> organisationMap = new HashMap<>();

    public static class UserAndRole {

        String mail;
        String manager;
        String role;
        String organization;
        List<String> locList = new ArrayList<>();
        List<String> staffList = new ArrayList<>();

        public UserAndRole(String mail, String manager, String role, String organization) {
            this.mail = mail;
            this.manager = manager;
            this.role = role;
            this.organization = organization;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getManager() {
            return manager;
        }

        public void setManager(String manager) {
            this.manager = manager;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public List<String> getLocList() {
            return locList;
        }

        public void setLocList(List<String> locList) {
            this.locList = locList;
        }

        public List<String> getStaffList() {
            return staffList;
        }

        public void setStaffList(List<String> staffList) {
            this.staffList = staffList;
        }

        @Override
        public String toString() {
            return mail + " | stanowisko: " + role ;
        }
    }

    public static class LocationAndOrganization {

        String locationId;
        String locationName;
        String area;
        String organization;

        public LocationAndOrganization(String locationId, String locationName, String area, String organization) {
            this.locationId = locationId;
            this.locationName = locationName;
            this.area = area;
            this.organization = organization;
        }

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

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        @Override
        public String toString(){

            String areaWord = area.equals("N") ? "Północ" : area.equals("S") ? "Południe" : area.equals("W") ? "Zachód" : area.equals("E") ? "Wschód" : null;

            return locationId + " | " +  locationName + " | " + areaWord + " | " + organization;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }


    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewPassConfirmation() {
        return newPassConfirmation;
    }

    public void setNewPassConfirmation(String newPassConfirmation) {
        this.newPassConfirmation = newPassConfirmation;
    }

    public boolean isPasswordChangeSucces() {
        return passwordChangeSucces;
    }

    public void setPasswordChangeSucces(boolean passwordChangeSucces) {
        this.passwordChangeSucces = passwordChangeSucces;
    }


    public boolean isShowNewUserCart() {
        return showNewUserCart;
    }

    public void setShowNewUserCart(boolean showNewUserCart) {
        this.showNewUserCart = showNewUserCart;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserMailInfo() {
        return userMailInfo;
    }

    public void setUserMailInfo(String userMailInfo) {
        this.userMailInfo = userMailInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassInfo() {
        return passInfo;
    }

    public void setPassInfo(String passInfo) {
        this.passInfo = passInfo;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public boolean isUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(boolean userAdmin) {
        this.userAdmin = userAdmin;
    }

    public String getUserOrganization() {
        return userOrganization;
    }

    public void setUserOrganization(String userOrganization) {
        this.userOrganization = userOrganization;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserAssignWorker() {
        return userAssignWorker;
    }

    public void setUserAssignWorker(String userAssignWorker) {
        this.userAssignWorker = userAssignWorker;
    }

    public String getUserManager() {
        return userManager;
    }

    public void setUserManager(String userManager) {
        this.userManager = userManager;
    }


    public boolean isShowNewLocationCart() {
        return showNewLocationCart;
    }

    public void setShowNewLocationCart(boolean showNewLocationCart) {
        this.showNewLocationCart = showNewLocationCart;
    }

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

    public String getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea;
    }

    public String getLocationOrganization() {
        return locationOrganization;
    }

    public void setLocationOrganization(String locationOrganization) {
        this.locationOrganization = locationOrganization;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<UserAndRole> getSelectedUserStaff() {
        return selectedUserStaff;
    }

    public void setSelectedUserStaff(List<UserAndRole> selectedUserStaff) {
        this.selectedUserStaff = selectedUserStaff;
    }

    public String getTempPass() {
        return tempPass;
    }

    public void setTempPass(String tempPass) {
        this.tempPass = tempPass;
    }

    public String getTempPassConfirmation() {
        return tempPassConfirmation;
    }

    public void setTempPassConfirmation(String tempPassConfirmation) {
        this.tempPassConfirmation = tempPassConfirmation;
    }

    public String getSelectedLocationId() {
        return selectedLocationId;
    }

    public void setSelectedLocationId(String selectedLocationId) {
        this.selectedLocationId = selectedLocationId;
    }

    public String getSelectedLocationDepartment() {
        return selectedLocationDepartment;
    }

    public void setSelectedLocationDepartment(String selectedLocationDepartment) {
        this.selectedLocationDepartment = selectedLocationDepartment;
    }

    public String getSelectedAssignWorker() {
        return selectedAssignWorker;
    }

    public void setSelectedAssignWorker(String selectedAssignWorker) {
        this.selectedAssignWorker = selectedAssignWorker;
    }



    public String getEditingLocationId() {
        return editingLocationId;
    }

    public void setEditingLocationId(String editingLocationId) {
        this.editingLocationId = editingLocationId;
    }

    public String getEditingLocationOrganization() {
        return editingLocationOrganization;
    }

    public void setEditingLocationOrganization(String editingLocationOrganization) {
        this.editingLocationOrganization = editingLocationOrganization;
    }

    public Location getLocationInstance() {
        return locationInstance;
    }

    public void setLocationInstance(Location locationInstance) {
        this.locationInstance = locationInstance;
    }

    public String getDepartmentToRemove() {
        return departmentToRemove;
    }

    public void setDepartmentToRemove(String departmentToRemove) {
        this.departmentToRemove = departmentToRemove;
    }

    public String getInputedEmail() {
        return inputedEmail;
    }

    public void setInputedEmail(String inputedEmail) {
        this.inputedEmail = inputedEmail;
    }

    public Integer getEmailIdToRemove() {
        return emailIdToRemove;
    }

    public void setEmailIdToRemove(Integer emailIdToRemove) {
        this.emailIdToRemove = emailIdToRemove;
    }


    public List<LocationAndOrganization> getAllLocationList() {
        return allLocationList;
    }

    public void setAllLocationList(List<LocationAndOrganization> allLocationList) {
        this.allLocationList = allLocationList;
    }

    public List<UserAndRole> getAllClientList() {
        return allClientList;
    }

    public void setAllClientList(List<UserAndRole> allClientList) {
        this.allClientList = allClientList;
    }

    public List<UserAndRole> getAllRecruiterList() {
        return allRecruiterList;
    }

    public void setAllRecruiterList(List<UserAndRole> allRecruiterList) {
        this.allRecruiterList = allRecruiterList;
    }

    public List<UserAndRole> getAllManagerList() {
        return allManagerList;
    }

    public void setAllManagerList(List<UserAndRole> allManagerList) {
        this.allManagerList = allManagerList;
    }

    public Map<String, String> getOrganisationMap() {
        return organisationMap;
    }

    public void setOrganisationMap(Map<String, String> organisationMap) {
        this.organisationMap = organisationMap;
    }
}
