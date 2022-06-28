package com.justinwilmot.capstone.app.dao;

//Appointment DAO interface

import java.util.List;

import com.justinwilmot.capstone.app.entity.AppointmentSlot;
import com.justinwilmot.capstone.app.entity.Location;
import com.justinwilmot.capstone.app.entity.User;

public interface AppointmentDao {
	
	public List<AppointmentSlot> showAllSlotsByDateAndLocation(String date, Location location);
	
	public List<AppointmentSlot> showOpenSlotsByDateAndLocation(String date, Location location);
	
	public List<AppointmentSlot> showDatesByLocation(Location location);
	
	public AppointmentSlot findScheduledAppointmentByUser(User theUser);
	
	public AppointmentSlot findSlotByLocationDateAndTime(Location location, String date, String time);
	
	public AppointmentSlot findSlotById(Long theId);
	
	public List<AppointmentSlot> findPreviousAppointmentsByUser(User theUser);
	
	public void save(AppointmentSlot theAppointmentSlot);
	
	public void deleteSlotById(Long slotId);

}
