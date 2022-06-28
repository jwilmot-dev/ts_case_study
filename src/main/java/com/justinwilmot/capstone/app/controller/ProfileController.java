package com.justinwilmot.capstone.app.controller;

//Profile controller class

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.justinwilmot.capstone.app.entity.AppointmentSlot;
import com.justinwilmot.capstone.app.entity.User;
import com.justinwilmot.capstone.app.service.AppointmentService;
import com.justinwilmot.capstone.app.service.UserService;
import com.justinwilmot.capstone.app.user.CrmUser;


@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	private AppointmentSlot upcomingAppointment;
	private List<AppointmentSlot> pastAppointments;
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private AppointmentService appointmentService;
	
    private Logger logger = Logger.getLogger(getClass().getName());
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	//Profile home page
	@GetMapping("/status")
	public String showMyProfilePage(Principal principal, Model theModel) {
		
		User theUser = userService.findByUserName(principal.getName());
		
		LocalDate bdate = LocalDate.parse(theUser.getDateOfBirth());
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String bDate = bdate.format(dateFormat);
		
		//find a upcoming scheduled appointment of the user
		upcomingAppointment = appointmentService.findScheduledAppointmentByUser(theUser);
		
		String appDate = null;
		String appTime = null;
		
		if(upcomingAppointment != null) {
			LocalDate adate = LocalDate.parse(upcomingAppointment.getAppointmentDate());
			appDate = adate.format(dateFormat);
		
			LocalTime atime = LocalTime.parse(upcomingAppointment.getAppointmentTime());
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
			appTime = atime.format(timeFormat);
		}
		
		//find list of past appointments of the user
		pastAppointments = appointmentService.findPreviousAppointmentsByUser(theUser);
		
		//add to Model
		theModel.addAttribute("upcoming", upcomingAppointment);
		theModel.addAttribute("previous", pastAppointments);
		theModel.addAttribute("theUser", theUser);
		theModel.addAttribute("birthday", bDate);
		theModel.addAttribute("appDate", appDate);
		theModel.addAttribute("appTime", appTime);
		
		return "c-profile";
	}
	
	//Go to update profile page
	@GetMapping("/updateProfile")
	public String updateProfile(Principal principal, Model theModel) {
		
		User theUser = userService.findByUserName(principal.getName());
		theModel.addAttribute("theUser", theUser);
		
		return "c-updateProfile";
	}
	
	//process and changes to user profile
	@PostMapping("/processProfileUpdate")
	public String processProfileUpdate(@RequestParam("firstName") String firstName,
									   @RequestParam("lastName") String lastName,
									   @RequestParam("dateOfBirth") String dateOfBirth,
									   @RequestParam("email") String email,
									   @RequestParam("phone") String phone,
									   Principal principal, Model theModel) {
		
		User theUser = userService.findByUserName(principal.getName());
		
		theUser.setFirstName(firstName);
		theUser.setLastName(lastName);
		theUser.setDateOfBirth(dateOfBirth);
		theUser.setEmail(email);
		theUser.setPhone(phone);
		
		userService.saveUser(theUser);
		
		return "redirect:/profile/status";
	}
	

}
