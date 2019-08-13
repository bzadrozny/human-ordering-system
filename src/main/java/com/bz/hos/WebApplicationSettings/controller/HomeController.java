package com.bz.hos.WebApplicationSettings.controller;

import com.bz.hos.db.OrderTable;
import com.bz.hos.model.CandidateEntity.Contract;
import com.bz.hos.model.CandidateEntity.ContractManagementForm;
import com.bz.hos.model.CommentEntity.Comment;
import com.bz.hos.model.FileBucketModel.FileBucket;
import com.bz.hos.model.FilterForm.FilterForm;
import com.bz.hos.model.LoginForm.LoginForm;
import com.bz.hos.model.NewOrderForm.NewOrder;
import com.bz.hos.model.OrdersEntity.Order;
import com.bz.hos.model.ReportModels.RangesForReport;
import com.bz.hos.model.SettingForm.SettingForm;
import com.bz.hos.model.UserAndLocationEntity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bz.hos.services.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

	@Autowired
	LoginService loginService;
	@Autowired
	NewOrderService newOrderService;
	@Autowired
	ListService listService;
	@Autowired
	OrderService orderService;
	@Autowired
	UserServices userServices;
	@Autowired
	SupportServices supportServices;
	@Autowired
	ReportServices reportServices;
	@Autowired
	EmailService emailService;
	@Autowired
	ContractManagementService contractService;

	private static Long logOutTime = (1000L * 60L * 15L);

	public static String xmlFilepath = "";
	static {
		try { xmlFilepath = new ClassPathResource("/").getURL().toString().concat("database.xml").substring(6); }
		catch (IOException e) { e.printStackTrace(); }
	}

	@RequestMapping(value="/", method = {RequestMethod.GET})
	public ModelAndView home(ModelAndView model){

		loginService.initializeUser(xmlFilepath);

		LoginForm loginForm = new LoginForm();
		model.addObject("loginForm", loginForm);

		model.setViewName("login");
		return model;
	}

	@RequestMapping(value="/", method = {RequestMethod.POST})
	public ModelAndView homePost( HttpServletRequest request, ModelAndView model, @ModelAttribute("loginForm") LoginForm loginForm)
	{
		User user = loginService.getUser(loginForm);

		if(user == null) {
			model.addObject("login",loginForm);
			model.setViewName("login");
			return model;
		}

		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		model.setViewName("/menu");
		return model;
	}

	@RequestMapping(value={"/logout", "/menu", "/newOrder", "/newOrder/Confirmation", "/list", "/showOrder", "/account-management", "/support", "/report", "/contractManagement"})
	public ModelAndView logOut (final HttpServletRequest request, ModelAndView model)
	{
		HttpSession session = request.getSession();
		if(session != null)
			session.invalidate();

		model.setViewName("redirect:/");
		return model;
	}

	@RequestMapping(value="/menu", method = RequestMethod.POST)
	public ModelAndView menuPost (final HttpServletRequest request, ModelAndView model)
	{
		HttpSession session = request.getSession();
		if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) || session.isNew()) {
			model.setViewName("redirect:/");
			return model;
		}

		model.setViewName("/menu");
		return model;
	}

	@RequestMapping(value ="/newOrder", method = RequestMethod.POST)
	public ModelAndView newOrder(final HttpServletRequest request, ModelAndView model, @ModelAttribute("order")NewOrder newOrder)
	{
		HttpSession session = request.getSession();
		if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) || session.isNew()) {
			model.setViewName("redirect:/");
			return model;
		}

		NewOrder savedOrder = (NewOrder) session.getAttribute("savedOrder");
		User user = (User) session.getAttribute("user");

		if (savedOrder == null || request.getParameter("cancelOrder") != null)
		{
			newOrder = newOrderService.newOrderInitialization(null, user);
			session.removeAttribute("savedOrder");
		}

		else if (request.getParameter("addSpecification") != null)
			newOrder = newOrderService.updateSpecification(newOrder, savedOrder);

		else if (request.getParameter("addRealizationSpecification") != null)
			newOrder = newOrderService.updateRealization(newOrder, savedOrder);

		else if (request.getParameter("addNewRecord") != null)
			newOrder = newOrderService.validateNewRecord(newOrder, savedOrder, request.getParameter("workingFrom"), request.getParameter("workingTo"));

		else if (request.getParameter("editRecord") != null)
			newOrder = newOrderService.editRecord(request.getParameter("recordNo"), savedOrder);

		else if (request.getParameter("deleteRecord") != null)
			newOrder = newOrderService.deleteRecord(request.getParameter("recordNo"), savedOrder);

		else if(request.getParameter("saveOrder") != null)
		{
			newOrderService.addNewOrderToDataBase(savedOrder);
			emailService.sendOrderConfirmation(savedOrder);

			session.removeAttribute("savedOrder");
			model.setViewName("/menu");
			return model;
		}

		else
			newOrder = savedOrder;


		model.addObject("order", newOrder);
		session.setAttribute("savedOrder", newOrder);

		model.setViewName("/newOrder");
		return model;
	}

    @RequestMapping(value = "/neworder-confirmation", method = RequestMethod.POST)
	public ModelAndView newOrderConfirmation(final HttpServletRequest request, ModelAndView model)
	{
		HttpSession session = request.getSession();
		if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) || session.isNew()) {
			model.setViewName("redirect:/");
			return model;
		}

        NewOrder savedOrder = (NewOrder) session.getAttribute("savedOrder");
		User user = (User) session.getAttribute("user");

		if(newOrderService.validateOrderConfirmation(savedOrder, user)) {
			session.setAttribute("savedOrder", savedOrder);
			model.setViewName("newOrderConfirmation");
		} else {
			model.addObject("order", savedOrder);
			model.setViewName("newOrder");
		}

		return model;
	}

	@RequestMapping(value ="/list", method = RequestMethod.POST)
    public ModelAndView list(final HttpServletRequest request, ModelAndView model, @ModelAttribute("filterForm")FilterForm filterForm)
	{
		HttpSession session = request.getSession();
		if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) || session.isNew()) {
			model.setViewName("redirect:/");
			return model;
		}

		User user = (User) session.getAttribute("user");
		Map<String, OrderTable> orderList = new HashMap<>();
		Map<String, OrderTable> waitingList = new HashMap<>();
		Map<String, OrderTable> openList = new HashMap<>();

		if( request.getParameter("launchFilter") != null ) {
			orderList = listService.getOrderListFromFilter(filterForm, user, waitingList, openList);
		}
		else if( request.getParameter("findId") != null ){
			String orderId = request.getParameter("inputedId").trim();
			OrderTable order = listService.getOrderFromId(orderId, filterForm);
			if(order != null)
				orderList.put(orderId, order);
			else
				filterForm.setSearchedId(orderId);
		}
		else
			orderList = listService.getOrderList(filterForm, user, waitingList, openList);

		if(user.isAdmin())
			listService.getManagerList(filterForm);

		model.addObject("filterForm", filterForm);
		model.addObject("orderList", orderList);
		model.addObject("waitingList", waitingList);
		model.addObject("openList", openList);
		model.setViewName("/orderList");
	    return model;
    }

	@RequestMapping(value ="/showOrder", method = RequestMethod.POST)
	public ModelAndView showOrder(final HttpServletRequest request, ModelAndView model, @ModelAttribute("contractForm") Contract contractForm)
	{
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) || session.isNew()) {
			model.setViewName("redirect:/");
			return model;
		}

        //Anyway that provide access to /showOrder set a parameter "orderId" (from method "addNewCandidate" and JSP "orderList")
        Order order = orderService.findOrder(request.getParameter("orderId"));
        boolean isOrderStatusChanged = false;
        boolean isRecordStatusChanged = false;
        boolean isRecordOrderedAmountChanged = false;
        boolean isRecordAcceptedAmountChanged = false;

        if(order == null)
            return list(request, model, null);

		//Order com.bz.hos.services methods
		if (request.getParameter("setSpecificationChange")!=null)
			isOrderStatusChanged = orderService.specificationChange(order, request.getParameter("realizationDate"), request.getParameter("orderStatus"));

		else if(request.getParameter("setAccreditationChange")!=null)
			orderService.accreditationChange(order, request.getParameter("assignWorker"), request.getParameter("department"));

		else if(request.getParameter("setNewRecordChange")!=null){
			int value =  orderService.recordChange(
					request.getParameter("recordId"),
					request.getParameter("recordStatus"),
					request.getParameter("accountOrdered"),
					request.getParameter("accountAccepted"));
			if(value==1)
				isRecordStatusChanged=true;
			else if(value==2)
				isRecordAcceptedAmountChanged=true;
			else if(value==3)
				isRecordOrderedAmountChanged=true;
		}

        else if(request.getParameter("setContract")!=null || request.getParameter("editContract")!=null)
            orderService.addContractData(contractForm, order, user);

        else if(request.getParameter("deleteContract")!=null)
        	orderService.removeContract(request.getParameter("contractId"));

        orderService.getRecordsMapFromOrderId(order);

        if(isOrderStatusChanged)
			emailService.sendStatusChangeInformation(order, false, false, false,null, user.getMail());
		else if(isRecordStatusChanged)
			emailService.sendStatusChangeInformation(order,true, false,false, request.getParameter("recordId"), user.getMail());
		else if(isRecordAcceptedAmountChanged)
			emailService.sendStatusChangeInformation(order,false, true, false, request.getParameter("recordId"), user.getMail());
		else if(isRecordOrderedAmountChanged)
			emailService.sendStatusChangeInformation(order,false, false, true, request.getParameter("recordId"), user.getMail());

		model.addObject("order", order);
		model.addObject("contractForm", new Contract());

		model.setViewName("/showOrder");
		return model;
	}

	@RequestMapping(value ="/account-management", method = RequestMethod.POST)
	public ModelAndView accountManagement(final HttpServletRequest request, ModelAndView model,
										  @ModelAttribute("settingForm")SettingForm settingForm)
	{
		HttpSession session = request.getSession();
		if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) || session.isNew()) {
			model.setViewName("redirect:/");
			return model;
		}

		model.setViewName("settings");
		User user = (User) session.getAttribute("user");
		SettingForm savedSettingForm = (SettingForm) session.getAttribute("savedSettingForm");

		//Edit LoggedUser Data
		if (request.getParameter("changeUserPassword") != null)
			settingForm.setPasswordChangeSucces(userServices.changePassword(settingForm, user));


		//Display/Hide New User Cart
        else if (request.getParameter("displayNewUserCart") != null || request.getParameter("hideNewUserCart") != null)
            settingForm = userServices.visibleNewUserCart(savedSettingForm);

        //New User
        else if (request.getParameter("addNewUser") != null)
            settingForm = userServices.addNewUser(settingForm, savedSettingForm);

		//Selecting User
		else if (request.getParameter("selectUserFromStaff") != null)
			settingForm = userServices.selectUserFromStaff(settingForm, savedSettingForm);

		//User Account Management
		else if(request.getParameter("changeSelectedUserData") != null)
			settingForm = userServices.changeSelectedUserData(settingForm, savedSettingForm);

		else if(request.getParameter("changeOrganization") != null)
			settingForm = userServices.changeOrganization(settingForm, savedSettingForm);

		else if(request.getParameter("resetUserPassword") != null)
			settingForm = userServices.resetSelectedUserPassword(settingForm, savedSettingForm);

		else if (request.getParameter("addUsersLocation") != null)
			settingForm = userServices.addUsersLocation(settingForm, savedSettingForm);

		else if (request.getParameter("removeUsersLocation") != null)
            settingForm = userServices.removeUsersLocation(settingForm, savedSettingForm, request.getParameter("selectedUserLocationId"));

		else if (request.getParameter("addMangersStaff") != null)
            settingForm = userServices.addManagerStaff(settingForm, savedSettingForm);

		else if (request.getParameter("removeMangersStaff") != null)
            settingForm = userServices.removeManagerStaff(settingForm, savedSettingForm, request.getParameter("selectedAssignWorker"));

        else if (request.getParameter("changeUserAssignWorkerAndManager") != null)
            settingForm = userServices.changeUserAssignWorkerAndManager(settingForm, savedSettingForm);

        else if (request.getParameter("changeUserRoleAndStatus") != null)
            settingForm = userServices.changeUserRoleAndStatus(settingForm, savedSettingForm);

        else if (request.getParameter("changeUserStatus") != null)
			settingForm = userServices.changeUserStatus(settingForm, savedSettingForm);

        else if (request.getParameter("closeUserEditionCart") != null)
			settingForm = userServices.closeUserEditionCart(savedSettingForm);


        //Display/Hide New Location Cart
        else if (request.getParameter("displayNewLocationCart") != null || request.getParameter("hideNewLocationCart") != null)
            settingForm = userServices.visibleNewLocationCart(savedSettingForm);

        //New Location
		else if (request.getParameter("addNewLocation") != null)
		    settingForm = userServices.addNewLocation(settingForm, savedSettingForm);

        //Selecting Location
        else if (request.getParameter("selectLocation") != null)
            settingForm = userServices.selectLocation(settingForm, savedSettingForm);

        //Location Management
		else if (request.getParameter("changeLocationOrganization") != null)
			settingForm = userServices.changeLocationOrganization(settingForm, savedSettingForm);

        else if (request.getParameter("addDepartment") != null)
            settingForm = userServices.addDepartment(settingForm, savedSettingForm);

		else if (request.getParameter("removeDepartment") != null)
            settingForm = userServices.removeDepartment(settingForm, savedSettingForm);

		else if (request.getParameter("addEmail") != null)
			settingForm = userServices.addEmail(settingForm, savedSettingForm);

		else if (request.getParameter("removeEmail") != null)
			settingForm = userServices.removeEmail(settingForm, savedSettingForm);


		else if (request.getParameter("closeLocationEditionCart") != null)
            settingForm = userServices.closeLocationEditionCart(savedSettingForm);

		else if(request.getParameter("analyticsReset")!=null) {
			loginService.runOrderDataAnalyticsAgain();
			model.setViewName("redirect:/");
			return model;
		}

		//Initialization of beginning data if source isn't like new Form.
		else {
			session.removeAttribute("savedSelectedUser");
			userServices.supplyingUserFormList(settingForm, user);
		}

		model.addObject("settingForm", settingForm);
		model.addObject("fileModel", new FileBucket());
		return model;
	}

	@RequestMapping(value ="/getDataBase", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE,
			headers = "Accept=application/xml")
	@ResponseBody
	public FileSystemResource getXML(final HttpServletRequest request, final HttpServletResponse response){

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) ||
				session.isNew() ||
				session.getAttribute("user")==null ||
				!user.isModerator()
				) {
			return null;
		}

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename=" + "database.xml" );
		userServices.saveXmlFile(xmlFilepath);

		return new FileSystemResource(xmlFilepath);
	}

	@RequestMapping(value ="/setDataBase", method = RequestMethod.POST)
	public ModelAndView setXML(final HttpServletRequest request, @ModelAttribute("fileModel") FileBucket fileModel){

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) ||
				session.isNew() ||
				user == null ||
				!user.isModerator())
		{
			return null;
		}

		userServices.setNewDataBase(fileModel.getFile(), xmlFilepath);

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value ="/support", method = RequestMethod.POST)
	public ModelAndView clientSupport(final HttpServletRequest request, ModelAndView model){

		HttpSession session = request.getSession();
		if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) || session.isNew()) {
			model.setViewName("redirect:/");
			return model;
		}

        if( request.getParameter("addNewComment") != null){
			Comment comment = new Comment();
			comment.setTitle(request.getParameter("subject"));
			comment.setDescription(request.getParameter("description"));

			User user = (User) session.getAttribute("user");

			supportServices.addNewComment(comment, user);
		}

		model.addObject("commentsList", supportServices.getAllComment());
		model.setViewName("/support");
		return model;
	}

    @RequestMapping(value ="/report", method = RequestMethod.POST)
    public ModelAndView report(final HttpServletRequest request, ModelAndView model,
							   @ModelAttribute("rangesForReport")RangesForReport rangesForReport){

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) || session.isNew() || user == null) {
			model.setViewName("redirect:/");
			return model;
		}

		//Fill ranges if empty and update From new Data
		rangesForReport = reportServices.getUpdateRangesForReport(user, rangesForReport, (RangesForReport)session.getAttribute("savedRangesForReport"));

		//Get user report from selected data range
		if(request.getParameter("reportRange")!=null)
            session.setAttribute("userReport", reportServices.getUserReportWithinRange(rangesForReport, rangesForReport.getUserMail()));

		//Get location report from selected data range
		else if(request.getParameter("submitLocationReportRange")!=null)
            session.setAttribute("locationReport", reportServices.getLocationReport(rangesForReport));

		//Get report for staff of selected User
		else if(request.getParameter("submitStaffReportRange")!=null)
            session.setAttribute("staffUserReport",reportServices.getStaffReportList(rangesForReport));

		//If there wasn't any submit button service gives standard data about user (if manager) and (locations - directly first)
		else{
            session.setAttribute("userReport", reportServices.getUserReportWithinRange(rangesForReport, rangesForReport.getUserMail()));
            session.setAttribute("locationReport", reportServices.getLocationReport(rangesForReport));
            session.setAttribute("staffUserReport", reportServices.getStaffReportList(rangesForReport));
		}

        session.setAttribute("savedRangesForReport", rangesForReport);
        model.addObject("rangesForReport", rangesForReport);
		model.setViewName("/report");
        return model;
    }

    @RequestMapping(value="/contractManagement", method = RequestMethod.POST)
    public ModelAndView contractManagement(HttpServletRequest request, ModelAndView model,
										   @ModelAttribute("contractForm")ContractManagementForm contractForm){

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if((System.currentTimeMillis() - session.getLastAccessedTime())>(logOutTime) || session.isNew() || user == null) {
			model.setViewName("redirect:/");
			return model;
		}

		if(request.getParameter("updateContracts")!=null)
			contractService.saveContractsChanges(contractForm.getTempJson(), user.getMail(), contractForm.getLocationId());

		if(contractForm!=null && contractForm.getLocationId()!=null && !contractForm.getLocationId().isEmpty())
			contractForm = contractService.loadLocationContracts(contractForm.getLocationId());
		else if(request.getParameter("selectedLocationId")!=null)
			contractForm = contractService.loadLocationContracts(request.getParameter("selectedLocationId"));

		model.addObject("selectedLocationId", new String());
	    model.addObject("contractForm", contractForm);
		model.setViewName("/contractManagement");
	    return model;
    }

}