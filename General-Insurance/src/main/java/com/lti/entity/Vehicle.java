package com.lti.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "vehicle_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","policy"})
public class Vehicle {

	@Id
	@GeneratedValue
	long vehicleId;

	@NaturalId
	String RegistrationNo;

	String model;
	String manufacturer;

	String vehicleType;
	double price;
	
	
	String drivingLicence;
	LocalDate purchasedDate;

	@NaturalId
	String engineNo;

	@NaturalId
	String chassisNo;

	String vehicledocumentUrl;
	
	String aadharUrl;
	
	String drivingLicenceUrl;


	@OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
	Policy policy;

	public long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getRegistrationNo() {
		return RegistrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		RegistrationNo = registrationNo;
	}

	public String getModel() {
		return model;
	}

		public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDrivingLicence() {
		return drivingLicence;
	}

	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	public LocalDate getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(LocalDate purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getChassisNo() {
		return chassisNo;
	}

	public void setChassisNo(String chassisNo) {
		this.chassisNo = chassisNo;
	}


	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public String getVehicledocumentUrl() {
		return vehicledocumentUrl;
	}

	public void setVehicledocumentUrl(String vehicledocumentUrl) {
		this.vehicledocumentUrl = vehicledocumentUrl;
	}

	public String getAadharUrl() {
		return aadharUrl;
	}

	public void setAadharUrl(String aadharUrl) {
		this.aadharUrl = aadharUrl;
	}

	public String getDrivingLicenceUrl() {
		return drivingLicenceUrl;
	}

	public void setDrivingLicenceUrl(String drivingLicenceUrl) {
		this.drivingLicenceUrl = drivingLicenceUrl;
	}
	
	

}
