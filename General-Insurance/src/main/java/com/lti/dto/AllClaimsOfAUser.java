package com.lti.dto;

import java.time.LocalDate;

public class AllClaimsOfAUser {
	long claimNo;
	long policyNo;
	long vehicleId;
	String policyType;
	double amount;
	String reason;
	String status;
	public long getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(long claimNo) {
		this.claimNo = claimNo;
	}
	public long getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(long policyNo) {
		this.policyNo = policyNo;
	}
	public long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
