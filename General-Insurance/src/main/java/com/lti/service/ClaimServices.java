package com.lti.service;

import java.util.List;

import com.lti.entity.Claim;
import com.lti.entity.Policy;
import com.lti.entity.User;

public interface ClaimServices {
	long claimInsurance(Claim claim);
	void mail(long claimId,Policy policy);
	List<Claim> allPendingClaims();
	void approveClaimPolicy(long claimNo);
	void rejectClaimPolicy(long claimNo);
	Claim getAClaimByPolicyNo(long policyNo);
	boolean isclaimPresent(long policyNo);
	Claim findClaimByClaimId(long claimId);
	void mail(long claimNo, long policyNo, User user);
	void mailForReject(long claimNo, long policyNo, User user);
}
