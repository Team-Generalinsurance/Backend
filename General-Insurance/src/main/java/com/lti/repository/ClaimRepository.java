package com.lti.repository;

import java.util.List;

import com.lti.entity.Claim;
import com.lti.entity.Policy;

public interface ClaimRepository {
	long saveClaim(Claim claim);
	Claim findClaimByNo(long claimNo);
	boolean isClaimPresent(long policyNo);
	Claim findClaimUsingPolicy(long policyNo);
	List<Claim> viewAllpendingclaims();
	void approveClaim(long claimNo);
	void rejectClaim(long claimNo);
}
