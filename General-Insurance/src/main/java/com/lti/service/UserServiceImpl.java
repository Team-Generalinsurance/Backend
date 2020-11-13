package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lti.entity.Policy;
import com.lti.entity.User;
import com.lti.exception.UserServiceException;
import com.lti.repository.PolicyRepository;
import com.lti.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PolicyRepository policyRepository;
	
	
	@Autowired
	private EmailService emailService;
	
	
	@Override
	public void register(User user) {
		if(!userRepository.isUserPresent(user.getEmail())) {
		long id=userRepository.save(user);
		String text="Successfully Registered. Your id is "+id;
		String subject = "Registration Conformation";
		emailService.sendEmailForNewRegistration(user.getEmail(), text, subject);
	}//else
	else {
		throw new UserServiceException("User already registered");
	}
}

	@Override
	public User login(String email, String password) {
		try {
            if(!userRepository.isUserPresent(email))
                throw new UserServiceException("User not registered!");
            long id = userRepository.findByEmailAndPassword(email, password);
            User user = userRepository.findUserById(id);
            return user;
        }
        catch(EmptyResultDataAccessException e) {
            throw new UserServiceException("Incorrect email/password");
        }	}

	@Override
	public User findUserById(long userId) {
		// TODO Auto-generated method stub
		return userRepository.findUserById(userId);
	}

	@Override
	public User getPassWord(String email, String mobileNo) {
		// TODO Auto-generated method stub
		return userRepository.getpassWord(email, mobileNo);
	}
	
	@Override
	public void forgotPasswordMail(String password,String mail) {
		// TODO Auto-generated method stub
		String text="Successfully Forgot Password. Your Password is: "+password;
		String subject = "Forgot Password Confirmation";
		emailService.sendEmailForNewRegistration(mail, text, subject);
		
	}
	@Override
	public List<Policy> getAllThePoliciesOfAUser(long userId) {
		// TODO Auto-generated method stub
		return policyRepository.viewAllUserpolicy(userId);
	}




}