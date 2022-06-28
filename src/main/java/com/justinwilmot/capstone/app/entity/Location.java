package com.justinwilmot.capstone.app.entity;

//Location entity

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class Location {
	
	@Id//id is primary key 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	//location description
	@Column(name = "location_desc")
	private String locationDesc;
	
	//location street address
	@Column(name = "street_address")
	private String streetAddress;
	
	//location city
	@Column(name = "city")
	private String city;
	
	//location state
	@Column(name = "state")
	private String state;
	
	//location zip
	@Column(name = "zip")
	private String zipCode;
	
	//empty constructor
	public Location() {}

	//constructor
	public Location(String locationDesc, String streetAddress, String city, String state, String zipCode) {
		this.locationDesc = locationDesc;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	//toString
	@Override
	public String toString() {
		return "Location [id=" + id + ", locationDesc=" + locationDesc + ", streetAddress=" + streetAddress + ", city="
				+ city + ", state=" + state + ", zipCode=" + zipCode + "]";
	}
	
	

}
