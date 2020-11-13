package com.lti.dto;

import org.springframework.web.multipart.MultipartFile;

public class DocumentDto {
	long policyNo;
    private MultipartFile aadhar;
    private MultipartFile drivingLicence;
    private MultipartFile vehicleDocument;
	
    
    
    
    public long getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(long policyNo) {
		this.policyNo = policyNo;
	}
	public MultipartFile getAadhar() {
		return aadhar;
	}
	public void setAadhar(MultipartFile aadhar) {
		this.aadhar = aadhar;
	}
	public MultipartFile getDrivingLicence() {
		return drivingLicence;
	}
	public void setDrivingLicence(MultipartFile drivingLicence) {
		this.drivingLicence = drivingLicence;
	}
	public MultipartFile getVehicleDocument() {
		return vehicleDocument;
	}
	public void setVehicleDocument(MultipartFile vehicleDocument) {
		this.vehicleDocument = vehicleDocument;
	}
	
    
    

}
