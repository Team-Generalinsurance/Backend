package com.lti.service;

import java.util.List;
import com.lti.entity.Policy;
import com.lti.entity.User;
import com.lti.entity.Vehicle;

public interface PolicyService {
	Policy findpolicyByPolicyNo(long policyNo);
	long buyInsurance(Vehicle vehicle,long userId);
	void mail(long vehicleId, User user);
	void renewPolicy(long policyNo, String expiryDate);
	List<Policy> pendingPolicies();
	void saveRenewPolicy(Policy policy);
	List<Policy> getAllThePoliciesOfAUser(long userId);
	void rejectBuyPolicy(long policyNo);
	void approveBuyPolicy(long policyNo, int term);
	 Vehicle findVehicleById(long vehicleId);
	void saveVehicle(Vehicle updatedVehicle);
	List<Policy> getAllTheApprovedPoliciesOfAUser(long userId);
	List<Policy> pendingDocumentPolicies(long userId);
	List<Policy> getUnapprovedClaimsPolicies(long userId);
	void approveBuyInsuranceMail(long policyNo,User user);
	void rejectBuyInsuranceMail(long policyNo,User user);
	void mailForRenew(long policyNo, User user);
	void mailForQuote(String emailId);
}