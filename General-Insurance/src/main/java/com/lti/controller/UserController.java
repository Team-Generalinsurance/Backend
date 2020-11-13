package com.lti.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.AllPoliciesofAUser;
import com.lti.dto.ForgotPasswordDto;
import com.lti.dto.LoginDto;
import com.lti.dto.LoginStatus;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusType;
import com.lti.dto.UnapprovedClaimsPolicies;
import com.lti.entity.Policy;
import com.lti.entity.User;
import com.lti.exception.UserServiceException;
import com.lti.service.PolicyService;
import com.lti.service.UserService;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	PolicyService policyService;

	// @RequestMapping(path = .., method = POST, ...)
	@PostMapping(path = "/register")
	public Status register(@RequestBody User user) {
		try {
			userService.register(user);

			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Registration successful!");
			return status;
		} catch (UserServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}

	}
	
	@PostMapping("/login")
	public LoginStatus login(@RequestBody LoginDto loginDto) {
		try {
			System.out.println(loginDto.getPassword());
			User user = userService.login(loginDto.getEmail(), loginDto.getPassword());
//			long userId=user.getUserId();
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setStatus(StatusType.SUCCESS);
			loginStatus.setMessage("Login Successful!");
			loginStatus.setUserId(user.getUserId());
			loginStatus.setAddress(user.getAddress());
			loginStatus.setCity(user.getCity());
			loginStatus.setDateOfBirth(user.getDateOfBirth());
			loginStatus.setEmail(user.getEmail());
			loginStatus.setFname(user.getFname());
			loginStatus.setLname(user.getLname());
			loginStatus.setMobileNo(user.getMobileNo());
			loginStatus.setPincode(user.getPincode());
			loginStatus.setState(user.getState());
//			loginStatus.setMobileNo(user.getPassword());
			
			return loginStatus;
		}
		catch(UserServiceException e) {
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setStatus(StatusType.FAILURE);
			loginStatus.setMessage(e.getMessage());
			return loginStatus;
		}
	}
	@PostMapping("/forgotPassword")
	public Status forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto)
	{
		String email=forgotPasswordDto.getEmail();
		String mobileNo= forgotPasswordDto.getMobileNo();
		Status status=new Status();

		
		try {
			User user= userService.getPassWord(email, mobileNo);
			if(user!=null)
			{
				status.setStatus(StatusType.SUCCESS);
				status.setMessage("Forgot Password Success!");
				userService.forgotPasswordMail(user.getPassword(), user.getEmail());
				return status;
			}else {
				status.setStatus(StatusType.FAILURE);
				status.setMessage("User Not Found");
				return status;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}
	}
	
	@GetMapping("/viewUsersApprovedPolicyforRenew")
	public List<AllPoliciesofAUser> getAllUserApprovedPolicies(long userId){
	 List<AllPoliciesofAUser> allPolicies=new ArrayList<AllPoliciesofAUser>(); 
	 AllPoliciesofAUser allThePolicies;	
	 List<Policy> policies=policyService.getAllTheApprovedPoliciesOfAUser(userId);
	 for(Policy p:policies) {
		 allThePolicies=new AllPoliciesofAUser();
		 allThePolicies.setPolicyNo(p.getPolicyNo());
		 System.out.println(allThePolicies.getPolicyNo());
		 allThePolicies.setVehicleId(p.getVehicle().getVehicleId());
		 allThePolicies.setPolicyType(p.getPolicyType());
		 allThePolicies.setExpiryDate(p.getExpiryDate());
		 allThePolicies.setStatus(p.getStatus());
		 allPolicies.add(allThePolicies);
	 }
	 for(AllPoliciesofAUser a:allPolicies) {
		 System.out.println(a.getPolicyNo());
	 }
	 return allPolicies; 
	}
	
	
	@GetMapping("/pendingDocumentPolicies")
	public List<AllPoliciesofAUser> viewPendingDocumentPolicies(long userId){
		List<AllPoliciesofAUser> pendingPolicies= new ArrayList<AllPoliciesofAUser>();
		 AllPoliciesofAUser a;	
		 List<Policy> policies=policyService.pendingDocumentPolicies(userId);
		 for(Policy p:policies) {
			 a=new AllPoliciesofAUser();
			 a.setPolicyNo(p.getPolicyNo());
			 //System.out.println(allThePolicies.getPolicyNo());
			 a.setVehicleId(p.getVehicle().getVehicleId());
			 a.setPolicyType(p.getPolicyType());
			 a.setExpiryDate(p.getExpiryDate());
			 a.setStatus(p.getStatus());
			 pendingPolicies.add(a);
		 }
		return pendingPolicies;


	}
	
	@GetMapping("/getPolicyNoforUnapprovedClaim")
	public List<UnapprovedClaimsPolicies> GetUnapprovedClaimsPolicy(long userId){
		List<UnapprovedClaimsPolicies> unapprovedclaimspolicies=new ArrayList<UnapprovedClaimsPolicies>();
		UnapprovedClaimsPolicies uclaimspolicy;
		List<Policy> policies=policyService.getUnapprovedClaimsPolicies(userId);
		for(Policy p:policies) {
			uclaimspolicy=new UnapprovedClaimsPolicies();
			uclaimspolicy.setPolicyNo(p.getPolicyNo());
			uclaimspolicy.setVehicleId(p.getVehicle().getVehicleId());
			uclaimspolicy.setPolicyType(p.getPolicyType());
			uclaimspolicy.setExpiryDate(p.getExpiryDate());
			uclaimspolicy.setStatus(p.getStatus());
			unapprovedclaimspolicies.add(uclaimspolicy);
		}
		return unapprovedclaimspolicies;
	}
	@GetMapping("/getQuotation")
	public Status getInformation(@RequestParam("email") String email, HttpServletRequest request) {
		Status status = new Status();
		try {
		policyService.mailForQuote(email);
		
		status.setStatus(StatusType.SUCCESS);
		status.setMessage("Quotation Sent Successfully");
		return status;
		}
		 catch (Exception e) {
				// TODO: handle exception
				status.setStatus(StatusType.FAILURE);
				status.setMessage(e.getMessage());
				return status;
			}	
	}
	

}