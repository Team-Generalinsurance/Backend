package com.lti.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Policy;
import com.lti.entity.User;

@Repository
public class PolicyRepositoryImpl implements PolicyRepository {
	
	@PersistenceContext
	EntityManager em;


	@Override
	@Transactional
	public long savePolicy(Policy policy) {
		// TODO Auto-generated method stub
		Policy p=em.merge(policy);
		return p.getPolicyNo();
	}

	@Override
	public Policy findPolicyByPolicyNo(long policyNo) {
		// TODO Auto-generated method stub
		return em.find(Policy.class, policyNo);
	}
	@Override
	public List<Policy> viewAllPendingPolicies() {
		// TODO Auto-generated method stub
		List<Policy> pendingPolicies= em.createQuery("select p from Policy p where p.status=:st and p.vehicle.aadharUrl!=null")
				.setParameter("st", "Pending")
				.getResultList();
		return pendingPolicies;
	}
	@Override
	public List<Policy> viewAllUserPolicies(long userId) {
		// TODO Auto-generated method stub
		List<Policy> policies=em.createQuery("select p from Policy p where p.user.userId=:em")
				.setParameter("em",userId)
				.getResultList();
		return policies;
	}

	@Override
	public boolean isPolicyPresent(long userId) {
		// TODO Auto-generated method stub
		return(long)
    em
    .createQuery("select count(p.policyNo) from Policy p where p.user.userId = :em")
    .setParameter("em", userId)
    .getSingleResult() == 1 ? true : false;
	}

	@Override
	public Policy findVehiclePolicy(long vehicleId) {
		// TODO Auto-generated method stub
		Policy policy=(Policy) em.createQuery("select p from Policy p where p.vehicle.vehicleId=:vi")
				.setParameter("vi", vehicleId)
				.getSingleResult();
		return policy;
	}


	@Override
	@Transactional
	public void approvePolicy(long policyNo,int term) {
		
		// TODO Auto-generated method stub
		LocalDate currdate=LocalDate.now();
		LocalDate expDate=currdate.plusYears(term);
		long policy= em.createQuery("update Policy p set p.status=:st,p.expiryDate=:ed where p.policyNo=:pn")
				.setParameter("st", "Approved")
				.setParameter("ed", expDate)
				.setParameter("pn", policyNo)
				.executeUpdate();
		System.out.println(policy);
	}
	@Override
	@Transactional
	public void renewPolicy(long policyNo,String ExpiryDate)
	{
		long policy = em.createQuery("update Policy p set p.expiryDate=:ed where p.policyNo=:pn")
				.setParameter("ed", ExpiryDate)
				.setParameter("pn", policyNo)
				.executeUpdate();
		
			System.out.println(policy);	
	}

	@Override
	public List<Policy> viewAllUserpolicy(long userId) {
		
	List<Policy> policies=em.createQuery("select p from Policy p where p.user.userId=:em")
				.setParameter("em",userId)
				.getResultList();
				for(Policy p:policies) {
		//	System.out.println(p.getPolicyNo());
		}
		return policies;
	}
	@Override
	@Transactional
	public void rejectPolicy(long policyNo) {
		// TODO Auto-generated method stub
		long policy= em.createQuery("update Policy p set p.status=:st where p.policyNo=:pn")
				.setParameter("st", "Rejected")
				.setParameter("pn", policyNo)
				.executeUpdate();
		System.out.println(policy);
	}
	
	@Override
	public List<Policy> viewAllUsersApprovedpolicy(long userId) {
		// TODO Auto-generated method stub
		List<Policy> policies=em.createQuery("select p from Policy p where p.user.userId=:em and p.status=:st")
				.setParameter("em",userId)
				.setParameter("st", "Approved")
				.getResultList();
		return policies;
	}
	
	@Override
	public List<Policy> viewAllPendingDocumentPolicies(long userId) {
		// TODO Auto-generated method stub
		List<Policy> pendingPolicies= em.createQuery("select p from Policy p where p.status=:st and p.user.userId=:ui and p.vehicle.aadharUrl=null")
				.setParameter("st", "Pending")
				.setParameter("ui", userId)
				.getResultList();
		return pendingPolicies;
	}
	
	@Override
	public List<Policy> viewAllUnapprovedClaimsPolicy(long userId) {
		List<Policy> unapprovedClaimsPolicies= em.createQuery("select p from Policy p where  p.claim.status=:sts and p.user.userId=:ui" )
				.setParameter("sts", "Rejected")
				.setParameter("ui", userId)
				.getResultList();
		return unapprovedClaimsPolicies;
	}

	}

