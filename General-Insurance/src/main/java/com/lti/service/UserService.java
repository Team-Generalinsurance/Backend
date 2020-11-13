package com.lti.service;

import java.util.List;

import com.lti.entity.Policy;
import com.lti.entity.User;

public interface UserService {

	void register(User user);
	
	User login(String email, String password);
	
	User findUserById(long userId);

	User getPassWord(String email,String mobileNo);

	void forgotPasswordMail(String password, String mail);

	List<Policy> getAllThePoliciesOfAUser(long userId);

}
