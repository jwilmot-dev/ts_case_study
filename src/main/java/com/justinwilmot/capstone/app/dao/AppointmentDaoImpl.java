package com.justinwilmot.capstone.app.dao;

//Appointment slot DAO

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.justinwilmot.capstone.app.entity.AppointmentSlot;
import com.justinwilmot.capstone.app.entity.Location;
import com.justinwilmot.capstone.app.entity.User;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {
	
	@Autowired
	private EntityManager entityManager;

	//Return all slots by a day and location
	@Override
	public List<AppointmentSlot> showAllSlotsByDateAndLocation(String date, Location location) {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AppointmentSlot> theQuery = currentSession.createQuery("from AppointmentSlot where location=:aLocation and appointmentDate=:aDate", AppointmentSlot.class);
		theQuery.setParameter("aLocation", location);
		theQuery.setParameter("aDate", date);
		List<AppointmentSlot> slotList= null;
		try {
			slotList = theQuery.getResultList();
		} catch (Exception e) {
			slotList = null;
		}

		return slotList;
	}

	//get all open slots by day and location
	@Override
	public List<AppointmentSlot> showOpenSlotsByDateAndLocation(String date, Location location) {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AppointmentSlot> theQuery = currentSession.createQuery("from AppointmentSlot a "
                													+ "where a.location=:qLocation and "
                													+ "a.appointmentDate=:qDate and "
                													+ "a.patient is null and "
                													+ "DATE(a.appointmentDate)>CURDATE()", AppointmentSlot.class);
		theQuery.setParameter("qLocation", location);
		theQuery.setParameter("qDate", date);
		
		List<AppointmentSlot> openTimeSlots= null;
		try {
			openTimeSlots = theQuery.getResultList();
		} catch (Exception e) {
			openTimeSlots = null;
		}
		
		return openTimeSlots;
	}

	//find an upcoming appointment for the user
	@Override
	public AppointmentSlot findScheduledAppointmentByUser(User theUser) {
		
		// hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AppointmentSlot> theQuery = currentSession.createQuery("from AppointmentSlot "
																	+ "where patient = :qUser and "														+ "DATE(appointmentDate) > CURDATE()", AppointmentSlot.class);
		theQuery.setParameter("qUser", theUser);
		
		AppointmentSlot scheduledSlot= null;
		try {
			scheduledSlot = theQuery.getSingleResult();
		} catch (Exception e) {
			scheduledSlot = null;
		}

		return scheduledSlot;
	}

	//find previous appointments for the user
	@Override
	public List<AppointmentSlot> findPreviousAppointmentsByUser(User theUser) {
		
		//ibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AppointmentSlot> theQuery = currentSession.createQuery("from AppointmentSlot "
																   + "where patient=:qUser and "
																   + "DATE(appointmentDate) < CURDATE()"
																   + "order by appointmentDate desc", AppointmentSlot.class);
		theQuery.setParameter("qUser", theUser);
		
		List<AppointmentSlot> previousAppointments= null;
		try {
			previousAppointments = theQuery.getResultList();
		} catch (Exception e) {
			previousAppointments = null;
		}

		return previousAppointments;
	}

	//save or update the appointment slot
	@Override
	public void save(AppointmentSlot theAppointmentSlot) {

		Session currentSession = entityManager.unwrap(Session.class);	
		currentSession.saveOrUpdate(theAppointmentSlot);

	}

	//Get a list of upcoming appointment dates for a location
	@Override
	public List<AppointmentSlot> showDatesByLocation(Location location) {

		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AppointmentSlot> theQuery = currentSession.createQuery(""
				                                                   + "from AppointmentSlot a "
				                                                   + "where a.location=:qLocation and a.patient is null and DATE(a.appointmentDate)>CURDATE()", AppointmentSlot.class);
		theQuery.setParameter("qLocation", location);
		
		List<AppointmentSlot> locationDates= null;
		try {
			locationDates = theQuery.getResultList();
		} catch (Exception e) {
			locationDates = null;
		}

		return locationDates;
	}

	//find an appointment slot by a combination of Location, date, and time
	@Override
	public AppointmentSlot findSlotByLocationDateAndTime(Location location, String date, String time) {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AppointmentSlot> theQuery = currentSession.createQuery("from AppointmentSlot "
																	+ "where location = :qLocation and "
																	+ "appointmentDate = :qDate and "
																	+ "appointmentTime = :qTime", AppointmentSlot.class);
		theQuery.setParameter("qLocation", location);
		theQuery.setParameter("qDate", date);
		theQuery.setParameter("qTime", time);
		
		AppointmentSlot theSlot= null;
		try {
			theSlot = theQuery.getSingleResult();
		} catch (Exception e) {
			theSlot = null;
		}

		return theSlot;
	}

	//find an appointment slot by its id
	@Override
	public AppointmentSlot findSlotById(Long theId) {

		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AppointmentSlot> theQuery = currentSession.createQuery("from AppointmentSlot "
																	+ "where id = :qId", AppointmentSlot.class);
		theQuery.setParameter("qId", theId);
		
		AppointmentSlot theSlot= null;
		try {
			theSlot = theQuery.getSingleResult();
		} catch (Exception e) {
			theSlot = null;
		}

		return theSlot;
	}

	//delete an appointment slot by its id
	@Override
	public void deleteSlotById(Long slotId) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AppointmentSlot> theQuery = currentSession.createQuery("delete AppointmentSlot "
				+ "where id = :qSlotId");
		theQuery.setParameter("qSlotId", slotId);
		theQuery.executeUpdate();
	}

}
