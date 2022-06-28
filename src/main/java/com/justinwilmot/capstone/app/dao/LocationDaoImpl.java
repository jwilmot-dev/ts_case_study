package com.justinwilmot.capstone.app.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.justinwilmot.capstone.app.entity.Location;

@Repository
public class LocationDaoImpl implements LocationDao {
	
	@Autowired
	private EntityManager entityManager;

	//get a list of all locations
	@Override
	public List<Location> listAllLocations() {

		Session currentSession = entityManager.unwrap(Session.class);
		
		// now retrieve/read all locations from database 
		Query<Location> theQuery = currentSession.createQuery("from Location", Location.class);
		List<Location> locationList= null;
		try {
			locationList = theQuery.getResultList();
		} catch (Exception e) {
			locationList = null;
		}

		return locationList;
	}

	//find a location by its id
	@Override
	public Location findLocationById(Long theId) {

		Session currentSession = entityManager.unwrap(Session.class);
				
				// now retrieve/read all locations from database 
				Query<Location> theQuery = currentSession.createQuery("from Location where id=:qId", Location.class);
				theQuery.setParameter("qId", theId);
				Location loc = null;
				try {
					loc = theQuery.getSingleResult();
				} catch (Exception e) {
					loc = null;
				}

				return loc;
	}

	//save a location
	@Override
	public void save(Location location) {

		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(location);
	}

}
