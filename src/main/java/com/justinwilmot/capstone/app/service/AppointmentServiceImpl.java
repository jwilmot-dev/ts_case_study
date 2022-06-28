package com.justinwilmot.capstone.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justinwilmot.capstone.app.dao.AppointmentDao;
import com.justinwilmot.capstone.app.entity.AppointmentSlot;
import com.justinwilmot.capstone.app.entity.Location;
import com.justinwilmot.capstone.app.entity.User;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired
	private AppointmentDao appointmentDao;

	
	@Override
	@Transactional
	public List<AppointmentSlot> showAllSlotsByDateAndLocation(String date, Location location) {
		
		return appointmentDao.showAllSlotsByDateAndLocation(date,location);
	}
	
	
	
	@Override
	@Transactional
	public List<AppointmentSlot> showOpenSlotsByDateAndLocation(String date, Location location) {
		
		return appointmentDao.showOpenSlotsByDateAndLocation(date,location);
	}

	@Override
	@Transactional
	public AppointmentSlot findScheduledAppointmentByUser(User theUser) {
		
		return appointmentDao.findScheduledAppointmentByUser(theUser);
	}
	
	@Override
	@Transactional
	public List<AppointmentSlot> findPreviousAppointmentsByUser(User theUser) {
		
		return appointmentDao.findPreviousAppointmentsByUser(theUser);
	}

	@Override
	@Transactional
	public void save(AppointmentSlot theAppointmentSlot) {
		
		appointmentDao.save(theAppointmentSlot);
		

	}



	@Override
	@Transactional
	public List<AppointmentSlot> showDatesByLocation(Location location) {
		
		return appointmentDao.showDatesByLocation(location);
	}



	@Override
	@Transactional
	public AppointmentSlot findSlotByLocationDateAndTime(Location location, String date, String time) {
		
		return appointmentDao.findSlotByLocationDateAndTime(location, date, time);
	}



	@Override
	@Transactional
	public AppointmentSlot findSlotById(Long theId) {
		
		return appointmentDao.findSlotById(theId);
	}



	@Override
	@Transactional
	public void deleteSlotById(Long slotId) {
		appointmentDao.deleteSlotById(slotId);
		
	}

	

	


}
