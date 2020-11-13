package com.lti.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import com.lti.entity.Claim;
import com.lti.entity.Policy;

@Repository
public class ClaimRepositoryImpl implements ClaimRepository {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public long saveClaim(Claim claim) {
		// TODO Auto-generated method stub
		Claim c=em.merge(claim);
		return c.getClaimNo();

	}

	@Override
	public Claim findClaimByNo(long claimId) {
		// TODO Auto-generated method stub
		return em.find(Claim.class, claimId);
	}

	@Override
	public boolean isClaimPresent(long policyNo) {
		return(long)em.createQuery("select count(c.claimNo) from Claim c where c.policy.policyNo = :pn")
			    .setParameter("pn", policyNo)
			    .getSingleResult() == 1 ? true : false;
		
	}
	@Override
	public Claim findClaimUsingPolicy(long policyNo) {
		System.out.println("inside repo...policy no"+policyNo);
		long claimNo=(long) em.createQuery("select c.claimNo from Claim c where c.policy.policyNo=:pn")
				.setParameter("pn", policyNo)
				.getSingleResult();
		
		System.out.println("the claim no is:"+claimNo);
		Claim claim= findClaimByNo(claimNo);
		return claim;
	}

	@Override
	public List<Claim> viewAllpendingclaims() {
		// TODO Auto-generated method stub
		List<Claim> pendingClaims=em.createQuery("select c from Claim c where c.status=:st")
				.setParameter("st","pending")
				.getResultList();
		return pendingClaims;
	}

	@Override
	@Transactional
	public void approveClaim(long claimNo) {
		// TODO Auto-generated method stub
		long claim= em.createQuery("update Claim c set c.status=:st where c.claimNo=:cn")
				.setParameter("st", "Approved")
				.setParameter("cn", claimNo)
				.executeUpdate();
		System.out.println(claim);
	}

	@Override
	@Transactional
	public void rejectClaim(long claimNo) {
		// TODO Auto-generated method stub
		long claim= em.createQuery("update Claim c set c.status=:st where c.claimNo=:cn")
				.setParameter("st", "Rejected")
				.setParameter("cn", claimNo)
				.executeUpdate();
		System.out.println(claim);
	}
	}


