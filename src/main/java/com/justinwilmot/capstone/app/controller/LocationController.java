package com.justinwilmot.capstone.app.controller;

//Location controller class

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
import com.justinwilmot.capstone.app.service.LocationService;

@Controller
@RequestMapping("/locations")
public class LocationController {
	
	private List<Location> theLocations;
	
	@Autowired
	private LocationService locationService;
	
	@GetMapping("/chooseCreateOption")
	public String chooseCreateOption() {
		return "m-locationChoice";
	}
	
	@GetMapping("/getExistingLocations")
	public String getExistingLocations(Model theModel) {
		
		theLocations = locationService.listAllLocations();
		
		//add to the spring model
		theModel.addAttribute("locations", theLocations);
		
		return "m-existingLocations";
	}
	
	@GetMapping("/setLocationDate")
	public String getDatesByLocation(@RequestParam("locationId") Long theId, Model theModel) {
		
		Location tempLoc = locationService.findLocationById(theId);
		theModel.addAttribute("location", tempLoc);
		
		return"m-newDate";
	}
	
	@GetMapping("/createNewLocation")
	public String createNewLocation() {
		
		return "m-newLocation";
	}
	
	@PostMapping("/addNewLocation")
	public String processNewLocation(@RequestParam("locDesc") String locationDescription,
									 @RequestParam("streetAddress") String streetAddress,
									 @RequestParam("city") String city,
									 @RequestParam("state") String state,
									 @RequestParam("zipCode") String zipCode,
									 Model theModel) {
		
		Location newLoc = new Location(locationDescription, 
									   streetAddress, 
									   city, 
									   state, 
									   zipCode);
		
		String theMessage = "Success";
		
		locationService.save(newLoc);
		
		theModel.addAttribute("message", theMessage);
		
		return "m-locationChoice";
	}

}
