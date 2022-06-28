package com.justinwilmot.capstone.app.entity;

//AppointmentSlot entity

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appointment_slot")
public class AppointmentSlot {

	@Id //id is primary key 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne//many to one mapping with location table id
	@JoinColumn(name = "location_id")
	private Location location;
	
	//appointment date
	@Column(name = "appointment_date")
	private String appointmentDate;
	
	//appointment time
	@Column(name = "appointment_time")
	private String appointmentTime;
	
	@ManyToOne//many to one mapping with user table id
	@JoinColumn(name = "patient_id")
	private User patient;
	
	//blood sample id
	@Column(name = "sample_id")
	private Long sampleId;
	
	//empty constructor
	public AppointmentSlot() {}

	//constructor
	public AppointmentSlot(Location location, String appointmentDate, String appointmentTime) {
		this.location = location;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
	
	}
	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public User getPatient() {
		return patient;
	}

	public void setPatient(User patient) {
		this.patient = patient;
	}
	
	public Long getSampleId() {
		return sampleId;
	}

	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
	}

	//toString
	@Override
	public String toString() {
		return "AppointmentSlot [id=" + id + ", location=" + location + ", appointmentDate=" + appointmentDate
				+ ", appointmentTime=" + appointmentTime + ", patient=" + patient + ", sampleId=" + sampleId + "]";
	}

	
	
	
	
}
