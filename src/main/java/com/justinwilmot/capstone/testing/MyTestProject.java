package com.justinwilmot.capstone.testing;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.justinwilmot.capstone.app.entity.AppointmentSlot;
import com.justinwilmot.capstone.app.entity.Location;
import com.justinwilmot.capstone.app.entity.User;
import com.justinwilmot.capstone.app.service.AppointmentService;
import com.justinwilmot.capstone.app.service.LocationService;
import com.justinwilmot.capstone.app.service.UserService;

public class MyTestProject {
	
	@Autowired
	UserService userService;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	AppointmentService appointmentService;
	
	//Test User class - findByUserName method
	@Test
	public void testFindByUserName() {
		
		//given
		String testName = "user2";
		
		assertThrows(NullPointerException.class, ()->{ 
			
		User testUser = userService.findByUserName(testName); 
		
		//expected name to test
		String expectedName ="Jane";
		
		//incorrect name to test
		String incorrectName ="Chuck";
		
		//when
		String actualName = testUser.getFirstName();
		
		//then
		assertEquals(expectedName, actualName);
		
		});
	}
	
	@Test
	public void testFindLocationById() {
		
		//given
		Long testId = (long) 3;
		
		assertThrows(NullPointerException.class, ()->{
		
		Location testLoc = locationService.findLocationById(testId);
		
		//expectedStreetAddress
		String expectedStreetAddress = "4122 Spring Ave";
		
		//incorrect street address to test
		String incorrectStreetAddress = "221 B Baker St";
		
		//when
		String actualStreetAddress = testLoc.getStreetAddress();
		
		//then
		assertEquals(expectedStreetAddress, actualStreetAddress);
		
		});
		
	}
	
	@Test
	public void testFindSlotById () {
		
		//given
		Long btestId = (long) 2;
		
		assertThrows(NullPointerException.class, ()->{
			
			AppointmentSlot testApp = appointmentService.findSlotById(btestId);
			
			//expectedStreetAddress
			String expectedAppDate = "2022-07-01";
			
			//incorrect street address to test
			String incorrectAppDate = "2020-03-04";
			
			//when
			String actualAppDate = testApp.getAppointmentDate();
			
			//then
			assertEquals(expectedAppDate, actualAppDate);
			
			});
		
		
	}

}
