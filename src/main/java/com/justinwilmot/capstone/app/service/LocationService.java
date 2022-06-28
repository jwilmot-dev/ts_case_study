package com.justinwilmot.capstone.app.service;

import java.util.List;

import com.justinwilmot.capstone.app.entity.Location;

public interface LocationService {
	
	public List<Location> listAllLocations();
	
	public Location findLocationById(Long theId);
	
	public void save(Location location);

}
