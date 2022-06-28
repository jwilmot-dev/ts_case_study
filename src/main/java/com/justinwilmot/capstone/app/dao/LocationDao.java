package com.justinwilmot.capstone.app.dao;

//Location DAO

import java.util.List;

import com.justinwilmot.capstone.app.entity.Location;

public interface LocationDao {

	public List<Location> listAllLocations();
	
	public Location findLocationById(Long theId);
	
	public void save(Location location);
	
}
