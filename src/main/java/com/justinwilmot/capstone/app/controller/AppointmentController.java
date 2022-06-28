package com.justinwilmot.capstone.app.controller;

//Controller for all things heavily related to appointment slots
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.justinwilmot.capstone.app.entity.AppointmentSlot;
import com.justinwilmot.capstone.app.entity.Location;
import com.justinwilmot.capstone.app.entity.User;
import com.justinwilmot.capstone.app.service.AppointmentService;
import com.justinwilmot.capstone.app.service.LocationService;
import com.justinwilmot.capstone.app.service.UserService;



@Controller
@RequestMapping("/schedule")
public class AppointmentController {
	
	private List<AppointmentSlot> theAppointments;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private UserService userService;
	

	
	//Get a list of all locations - w/o id
	@GetMapping("/getLocations")
	public String listLocations(Model theModel) {
		
		List<Location> locationList = locationService.listAllLocations();
		System.out.println(locationList);
	
		theModel.addAttribute("locations", locationList);
		return "c-selectLocation";
	}
	
	//get future appointment dates for location
	@GetMapping("/getLocationDates")
	public String getDatesByLocation(@RequestParam("locationId") Long theId, Model theModel) {
		
		Location tempLoc = locationService.findLocationById(theId);
		
		List<AppointmentSlot> locationDates = appointmentService.showDatesByLocation(tempLoc);
		
		ArrayList<String> dateArr = new ArrayList<>();
		
		for(AppointmentSlot locDate : locationDates) {
			LocalDate date = LocalDate.parse(locDate.getAppointmentDate());
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String formattedDate = date.format(dateFormat);
			
			if(!dateArr.contains(formattedDate)) {
				dateArr.add(formattedDate);
			}
		}
		
		theModel.addAttribute("location", tempLoc);
		theModel.addAttribute("dates", dateArr);
		
		return "c-selectDate";
	}
	
	//get a list of open appointment slots by selected location and date
	@GetMapping("/getOpenSlots")
	public String listOpenSlotsByLocationAndDate(@RequestParam("locationId") Long theId,
												 @RequestParam("date") String theDate,
												 Model theModel) {
		
		LocalDate date = LocalDate.parse(theDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String newDate = date.format(dateFormat);
		
		Location tempLoc = locationService.findLocationById(theId);
		System.out.println(tempLoc);
		
		List<AppointmentSlot> timeSlots = appointmentService.showOpenSlotsByDateAndLocation(newDate, tempLoc);	
		
		ArrayList<String> timeArr = new ArrayList<>();
		
		for(AppointmentSlot slot : timeSlots) {
			
			LocalTime time = LocalTime.parse(slot.getAppointmentTime());
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
			String formattedTime = time.format(timeFormat);
			
			if(!timeArr.contains(formattedTime)) {
				timeArr.add(formattedTime);
			}
		}
		
		theModel.addAttribute("timeslots", timeArr);
		theModel.addAttribute("location", tempLoc);
		theModel.addAttribute("date", theDate);
		return "c-selectTime";
	}
	
	
	//reserve an appointment time slot
	@GetMapping("/reserveSlot")
	public String reserveAppointment(@RequestParam("locationId") Long theId,
			 						 @RequestParam("date") String theDate,
			 						 @RequestParam("time") String theTime,
			 						 Model theModel,
			 						 Principal principal) {
		
		
		Location tempLoc = locationService.findLocationById(theId);
		
		LocalDate date = LocalDate.parse(theDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String newDate = date.format(dateFormat);
		
		LocalTime time = LocalTime.parse(theTime, DateTimeFormatter.ofPattern("hh:mm a"));
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		String newTime = time.format(timeFormat);
		
		AppointmentSlot aSlot = appointmentService.findSlotByLocationDateAndTime(tempLoc, newDate, newTime);
		
		User thisUser = userService.findByUserName(principal.getName());
		
		aSlot.setPatient(thisUser);
		
		appointmentService.save(aSlot);
		
		return "redirect:/profile/status";
	}
	
	//cancel an appointment
	@GetMapping("/cancelAppointment")
	public String cancelAppointment(@RequestParam("appointmentId") Long theId,
			 						 Model theModel) {
		
		AppointmentSlot aSlot = appointmentService.findSlotById(theId);
		aSlot.setPatient(null);
		aSlot.setSampleId(null);
		appointmentService.save(aSlot);
		
		return "redirect:/profile/status";
	}
	
	//manager function to get all locations (all info - includes id)
	@GetMapping("/managerGetLocations")
	public String listManagerLocations(Model theModel) {
		
		List<Location> locationList = locationService.listAllLocations();
		theModel.addAttribute("locations", locationList);
		
		return "m-selectLocation";
	}
	
	//manager function to get the available dates of a location
	@GetMapping("/managerGetDates")
	public String getDatesByLocationManager(@RequestParam("locationId") Long theId, Model theModel) {
		
		Location tempLoc = locationService.findLocationById(theId);
		
		List<AppointmentSlot> locationDates = appointmentService.showDatesByLocation(tempLoc);

		ArrayList<String> dateArr = new ArrayList<>();
		
		for(AppointmentSlot locDate : locationDates) {
			LocalDate date = LocalDate.parse(locDate.getAppointmentDate());
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String formattedDate = date.format(dateFormat);
			
			if(!dateArr.contains(formattedDate)) {
				dateArr.add(formattedDate);
			}
		}

		theModel.addAttribute("location", tempLoc);
		theModel.addAttribute("dates", dateArr);
		
		
		return "m-selectDate";
	}
	

	//based on a location and date bring up an appointment schedule that can be edited
	@GetMapping("/manageAppointments")
	public String manageAppointments(@RequestParam("locationId") Long theId,
			 						 @RequestParam("date") String theDate,
			 						 Model theModel) {
	
		Location tempLoc = locationService.findLocationById(theId);
		
		LocalDate date = LocalDate.parse(theDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String newDate = date.format(dateFormat);
		
		List<AppointmentSlot> theAppointments = appointmentService.showAllSlotsByDateAndLocation(newDate, tempLoc);	

		theModel.addAttribute("appointments", theAppointments);
		theModel.addAttribute("location", tempLoc);
		theModel.addAttribute("date", theDate);
		
		return "m-manageAppointments";
	}
	
	//manager update an appointment slot with a blood sample id number
	@GetMapping("/updateSample")
	public String updateSample(@RequestParam("slotId") Long slotId,
							   @RequestParam("locationId") Long theId,
			                   @RequestParam("date") String theDate,
			 				    Model theModel) {
	
		AppointmentSlot theSlot = appointmentService.findSlotById(slotId);
		Location tempLoc = locationService.findLocationById(theId);
		
		LocalDate dob = LocalDate.parse(theSlot.getPatient().getDateOfBirth());
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String formattedDate = dob.format(dateFormat);
		
		LocalTime time = LocalTime.parse(theSlot.getAppointmentTime());
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
		String formattedTime = time.format(timeFormat);
		
		theModel.addAttribute("slot", theSlot);
		theModel.addAttribute("location", tempLoc);
		theModel.addAttribute("date", theDate);
		theModel.addAttribute("birthday", formattedDate);
		theModel.addAttribute("appTime", formattedTime);
		
		return "m-updateSample";
	}
	
	//process update of change to blood sample id number
	@PostMapping("/processSampleUpdate")
	public String processSampleUpdate(@RequestParam("sample") Long sampleId,
									  @RequestParam("appId") Long slotId,
									  @RequestParam("locId") Long theId,
									  @RequestParam("date") String theDate,
			 						  Model theModel) {
	
		
		AppointmentSlot theSlot = appointmentService.findSlotById(slotId);
		theSlot.setSampleId(sampleId);
		appointmentService.save(theSlot);
		
		Location tempLoc = locationService.findLocationById(theId);
		
		LocalDate date = LocalDate.parse(theDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String newDate = date.format(dateFormat);
		
		List<AppointmentSlot> theAppointments = appointmentService.showAllSlotsByDateAndLocation(newDate, tempLoc);	
		
		theModel.addAttribute("appointments", theAppointments);
		theModel.addAttribute("location", tempLoc);
		theModel.addAttribute("date", theDate);
		
		return "m-manageAppointments";
		
	}
	
	//manager delete appointment time slot
	@GetMapping("/deleteSlot")
	public String deleteSlot(@RequestParam("slotId") Long slotId,
							 @RequestParam("locationId") Long theId,
			                 @RequestParam("date") String theDate,
			 				 Model theModel) {
	
		AppointmentSlot theSlot = appointmentService.findSlotById(slotId);
		appointmentService.deleteSlotById(slotId);
		
		Location tempLoc = locationService.findLocationById(theId);
		
		LocalDate date = LocalDate.parse(theDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String newDate = date.format(dateFormat);
		
		//get all appointment slots for a given location and date from db
		List<AppointmentSlot> theAppointments = appointmentService.showAllSlotsByDateAndLocation(newDate, tempLoc);	

		theModel.addAttribute("appointments", theAppointments);
		theModel.addAttribute("location", tempLoc);
		theModel.addAttribute("date", theDate);
		
		return "m-manageAppointments";
	}
	
	//manager select a daate to create a appointment time slot
	@PostMapping("/customDate")
	public String addDates(@RequestParam("locationId") Long theId,
            			   @RequestParam("date") String theDate,
            			   Model theModel) {
		
		
		Location tempLoc = locationService.findLocationById(theId);
		
		LocalDate dob = LocalDate.parse(theDate);
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String formattedDate = dob.format(dateFormat);
		
		theModel.addAttribute("location", tempLoc);
		theModel.addAttribute("date", formattedDate);
		
		return "m-customTime";
	}
	
	//manager select a date to create a appointment time slot
	@PostMapping("/addNewAppointment")
	public String addNewAppointment(@RequestParam("locationId") Long theId,
			   			   			@RequestParam("date") String theDate,
			   			   		    @RequestParam("time") String theTime,
			   			   			Model theModel) { 
		
		Location tempLoc = locationService.findLocationById(theId);
		
		LocalDate date = LocalDate.parse(theDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String newDate = date.format(dateFormat);
		
		AppointmentSlot theSlot = new AppointmentSlot(tempLoc, newDate, theTime);
		
		String theMessage = "Success";
		appointmentService.save(theSlot);
		theModel.addAttribute("message", theMessage);
		
		return "m-locationChoice";
	}

}
