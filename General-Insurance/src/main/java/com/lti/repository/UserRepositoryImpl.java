package com.lti.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Policy;
import com.lti.entity.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public long save(User user) {
		// TODO Auto-generated method stub
		User u=em.merge(user);
		return u.getUserId();
}

	@Override
	public long findByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		return (long) em
                .createQuery("select u.id from User u where u.email = :em and u.password = :pw")
                .setParameter("em", email)
                .setParameter("pw", password)
                .getSingleResult();	
		}

	@Override
	public User findUserById(long userId) {
		// TODO Auto-generated method stub
		return em.find(User.class,userId);
	}

	@Override
	public List<User> viewAllUser() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("fetch-all-user").getResultList();
	}

	@Override
	public boolean isUserPresent(String email) {
		// TODO Auto-generated method stub
		return (long)
                em
                .createQuery("select count(u.id) from User u where u.email = :em")
                .setParameter("em", email)
                .getSingleResult() == 1 ? true : false;	
	}

	@Override
	public User getpassWord(String email, String mobileNo) {
		// TODO Auto-generated method stub
		return (User)em.createQuery("select u from User u where u.email=:em and u.mobileNo=:mn")
				.setParameter("em", email)
				.setParameter("mn", mobileNo)
				.getSingleResult();
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

}