package com.lti.repository;

import java.util.List;

import com.lti.entity.Policy;
import com.lti.entity.User;

public interface UserRepository {
	
	long save(User user);
	long findByEmailAndPassword(String email, String password);
	User findUserById(long userId);
	List<User> viewAllUser();
	boolean isUserPresent(String email);
	User getpassWord(String email,String mobileNo);
	List<Policy> viewAllUserpolicy(long userId);
}
