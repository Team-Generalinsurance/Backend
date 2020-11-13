package com.lti.repository;

import java.util.List;

import com.lti.entity.Policy;

public interface PolicyRepository {
	long savePolicy(Policy policy);
	Policy findPolicyByPolicyNo(long policyNo);
	List<Policy> viewAllUserPolicies(long userId);
	boolean isPolicyPresent(long userId);
	public Policy findVehiclePolicy(long vehicleId);
	void renewPolicy(long policyNo, String expiryDate);
	List<Policy> viewAllPendingPolicies();
	List<Policy> viewAllUserpolicy(long userId);
	void rejectPolicy(long policyNo);
	void approvePolicy(long policyNo, int term);
	List<Policy> viewAllUsersApprovedpolicy(long userId);
	List<Policy> viewAllPendingDocumentPolicies(long userId);
	List<Policy> viewAllUnapprovedClaimsPolicy(long userId);
	
}
