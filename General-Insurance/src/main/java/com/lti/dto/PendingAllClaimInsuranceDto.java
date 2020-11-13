package com.lti.dto;

public class PendingAllClaimInsuranceDto {
long claimNo;
long policyNo;
String reason;
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
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}

}
