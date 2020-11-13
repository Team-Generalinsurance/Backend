package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.entity.Claim;
import com.lti.entity.Policy;
import com.lti.entity.User;
import com.lti.repository.ClaimRepository;
import com.lti.repository.PolicyRepository;

@Service
public class ClaimServicesImpl implements ClaimServices {
	@Autowired
	PolicyRepository policyRepository;
	
	@Autowired
	EmailService emailService;
	@Autowired
	ClaimRepository claimRepository;
	@Override
	public long claimInsurance(Claim claim) {
		long claimId= claimRepository.saveClaim(claim);
		return claimId;
	}
	
	@Override
	public void mail(long claimId,Policy policy) {
		String text="Successfully Applied for claim Insurance for the policy "+ policy.getPolicyNo()+"and your claim id is: "+claimId;
		String subject = "Insurance Conformation";
		
		emailService.sendEmailForNewRegistration(policy.getUser().getEmail(), text, subject);
	}

	@Override
	public List<Claim> allPendingClaims() {
		// TODO Auto-generated method stub
		return claimRepository.viewAllpendingclaims();
	}

	@Override
	public void approveClaimPolicy(long claimNo) {
		// TODO Auto-generated method stub
		claimRepository.approveClaim(claimNo);
	}

	@Override
	public void rejectClaimPolicy(long claimNo) {
		// TODO Auto-generated method stub
		claimRepository.rejectClaim(claimNo);
	}

	@Override
	public Claim getAClaimByPolicyNo(long policyNo) {
	
		return claimRepository.findClaimUsingPolicy(policyNo);
	}

	@Override
	public boolean isclaimPresent(long policyNo) {
		return claimRepository.isClaimPresent(policyNo);
	}

	@Override
	public Claim findClaimByClaimId(long claimId) {
		// TODO Auto-generated method stub
		return claimRepository.findClaimByNo(claimId);
	}

	@Override
	public void mail(long claimNo, long policyNo, User user) {
		// TODO Auto-generated method stub
		String text="Hello!"+user.getFname()+"Congratulations! Your claim is approved for the claimNo: "+claimNo;
		String subject = "Claim Insurance Confirmation";
		
		emailService.sendEmailForNewRegistration(user.getEmail(), text, subject);
	}
	@Override
	public void mailForReject(long claimNo, long policyNo, User user) {
		// TODO Auto-generated method stub
		String text="Hello!"+user.getFname()+"We regret to inform you that your claim is rejected for the claimNo: "+claimNo;
		String subject = "Claim Insurance Rejection";
		
		emailService.sendEmailForNewRegistration(user.getEmail(), text, subject);
	}
	
	
	
	

}
