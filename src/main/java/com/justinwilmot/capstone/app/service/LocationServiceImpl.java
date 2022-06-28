package com.justinwilmot.capstone.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justinwilmot.capstone.app.dao.LocationDao;
import com.justinwilmot.capstone.app.entity.Location;

@Service
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	private LocationDao locationDao;

	@Override
	@Transactional
	public List<Location> listAllLocations() {
		
		return locationDao.listAllLocations();
	}

	@Override
	@Transactional
	public Location findLocationById(Long theId) {
		
		return locationDao.findLocationById(theId);
	}

	@Override
	@Transactional
	public void save(Location location) {
		locationDao.save(location);

	}

}
