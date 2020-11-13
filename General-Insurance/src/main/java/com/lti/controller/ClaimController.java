package com.lti.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.ClaimInsuranceDto;
import com.lti.dto.DocumentDto;
import com.lti.dto.PendingAllBuyInsuranceDto;
import com.lti.dto.PendingAllClaimInsuranceDto;
import com.lti.dto.AllClaimsOfAUser;
import com.lti.dto.AllPoliciesofAUser;
import com.lti.dto.ClaimDocumentDto;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusType;
import com.lti.entity.Claim;
import com.lti.entity.Policy;
import com.lti.entity.User;
import com.lti.entity.Vehicle;
import com.lti.exception.UserServiceException;
import com.lti.repository.ClaimRepository;
import com.lti.service.ClaimServices;
import com.lti.service.PolicyService;
import com.lti.service.UserService;


@RestController
@CrossOrigin
public class ClaimController {
	@Autowired
	UserService userServices;
	@Autowired
	ClaimServices claimServices;
	@Autowired
	PolicyService policyService;
	
	@PostMapping(path="/claimInsurance")
	public Status ClaimInsurance(@RequestBody ClaimInsuranceDto claimInsurancedto)
	{
		Claim claim= new Claim();
		long policyNo=claimInsurancedto.getPolicyNo();
		Policy policy=policyService.findpolicyByPolicyNo(policyNo);
		claim.setPolicy(policy);
		claim.setAmount(claimInsurancedto.getAmount());
		claim.setReason(claimInsurancedto.getReason());
		claim.setStatus("pending");
		try {
			long claimId=claimServices.claimInsurance(claim);
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("you have successfully claimed for the insurance!");
			claimServices.mail(claimId, policy);
			return status;
		} catch (UserServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}
	}
	@GetMapping(path="/pendingClaimInsurance")
	public List<PendingAllClaimInsuranceDto> viewPendingClaims()
	{
		List<PendingAllClaimInsuranceDto> pendingClaims= new ArrayList<PendingAllClaimInsuranceDto>();
		List<Claim> pendingClaimFull= claimServices.allPendingClaims();
		PendingAllClaimInsuranceDto c;
		for(Claim p:pendingClaimFull)
		{
			 c=new PendingAllClaimInsuranceDto();
			
			c.setClaimNo(p.getClaimNo());
			c.setPolicyNo(p.getPolicy().getPolicyNo());
			c.setReason(p.getReason());
			pendingClaims.add(c);
		}
		for(PendingAllClaimInsuranceDto p:pendingClaims) {
			System.out.println(p.getClaimNo());
		}
		
		return pendingClaims;
	}
	@GetMapping(path="/pendingBuyInsurancewithLessData")
	public List<PendingAllBuyInsuranceDto> viewPendingBuyInsurances()
	{
		List<PendingAllBuyInsuranceDto> pendingInsurances=new ArrayList<PendingAllBuyInsuranceDto>();
		PendingAllBuyInsuranceDto d=new PendingAllBuyInsuranceDto();
		List<Policy> pendingPolicies= policyService.pendingPolicies();
		for(Policy p:pendingPolicies)
		{
			d.setPolicyNo(p.getPolicyNo());
			d.setVehicleId(p.getVehicle().getVehicleId());
			d.setPolicyType(p.getPolicyType());
			pendingInsurances.add(d);
		}
		return pendingInsurances;
	}
	@GetMapping(path="/viewUserspolicies")
	public List<AllPoliciesofAUser> getAllUserAppliedPolicies(long userId){
	 List<AllPoliciesofAUser> allPolicies=new ArrayList<AllPoliciesofAUser>(); 
	 AllPoliciesofAUser allThePolicies;	
	 List<Policy> policies=policyService.getAllThePoliciesOfAUser(userId);
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
		 
	
	
	@PostMapping(path="/approveClaimInsurance")
	public Status approveClaimInsurance( long claimNo)
	{
		Claim claim=claimServices.findClaimByClaimId(claimNo);
		Policy policy=claim.getPolicy();
		User user=policy.getUser();
		System.out.println("Claim No-"+claimNo);
		try {
			claimServices.approveClaimPolicy(claimNo);
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Approval successful!");
		claimServices.mail(claimNo, policy.getPolicyNo(),user);
			return status;
		} catch (UserServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}
	}
	@PostMapping(path="/rejectClaimInsurance")
	public Status rejectClaimInsurance(long claimNo)
	{
		Claim claim=claimServices.findClaimByClaimId(claimNo);
		Policy policy=claim.getPolicy();
		User user=policy.getUser();
		System.out.println(claimNo);
		try {
			claimServices.rejectClaimPolicy(claimNo);
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("your claim has been rejected!");
			claimServices.mailForReject(claimNo, policy.getPolicyNo(),user);
			return status;
		} catch (UserServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}
	}
/*	@GetMapping(path="/viewAllPreviousClaims")
	public List<AllClaimsOfAUser> getAllUserClaims(long userId){
		 List<AllClaimsOfAUser> allclaims=new ArrayList<AllClaimsOfAUser>(); 
		 AllClaimsOfAUser allTheClaims;	
		 User user=userServices.findUserById(userId);
		 List<Policy> policy=user.getPoilicies();
		 for(Policy p:policy) {
			 long policyNo=p.getPolicyNo();
		 List<Claim> claims=claimServices.getAllTheClaimsOfAUser(userId,policyNo);
		 for(Claim c:claims) {
			 allTheClaims=new AllClaimsOfAUser();
			 allTheClaims.setClaimNo(c.getClaimNo());
			 allTheClaims.setPolicyNo(c.getClaimNo());
			 allTheClaims.setVehicleId(c.getPolicy().getPolicyNo());
			 allTheClaims.setPolicyType(c.getPolicy().getPolicyType());
			 allTheClaims.setAmount(c.getAmount());
			 allTheClaims.setReason(c.getReason());
			 allTheClaims.setStatus(c.getStatus());
			 allclaims.add(allTheClaims);
		 }
		 }
		 for(AllClaimsOfAUser a:allclaims) {
			 System.out.println(a.getPolicyNo());
		 }
		 return allclaims; 
		
	}	*/
	@GetMapping(path="/viewAllPreviousClaims")
	public List<AllClaimsOfAUser> getAllUserClaims(long userId){
		 List<AllClaimsOfAUser> allclaims=new ArrayList<AllClaimsOfAUser>(); 
		 AllClaimsOfAUser allTheClaims;
		 User user=userServices.findUserById(userId);
		 System.out.println("users detail"+user.getFname());
		 List<Policy> policy=policyService.getAllThePoliciesOfAUser(userId);	 
		 for(Policy p:policy) {
			 allTheClaims=new AllClaimsOfAUser();
			 long policyNo=p.getPolicyNo();
			 System.out.println(policyNo);
			Claim claim;
			boolean val=claimServices.isclaimPresent(policyNo);
		if(val) {
		 claim=claimServices.getAClaimByPolicyNo(policyNo);
		 allTheClaims.setClaimNo(claim.getClaimNo());
		 allTheClaims.setPolicyNo(claim.getPolicy().getPolicyNo());
		 allTheClaims.setVehicleId(claim.getPolicy().getVehicle().getVehicleId());
		 allTheClaims.setPolicyType(claim.getPolicy().getPolicyType());
		 allTheClaims.setAmount(claim.getAmount());
		 allTheClaims.setStatus(claim.getStatus());
		 allTheClaims.setReason(claim.getReason());
		allclaims.add(allTheClaims);
		}
		 }
		 return allclaims;
		
	}
	
	@PostMapping("/claimDocumentUpload")
	public Status documentUpload( ClaimDocumentDto claimDocumentDto)
	{
		long policyNo=claimDocumentDto.getPolicyNo();

		//User user= userService.findUserById(documentDto.getUserId());
		Claim claim=claimServices.getAClaimByPolicyNo(policyNo);
		
		
		
		//Vehicle vehicle= policy.getVehicle();
		
		String imageUploadLocation = "d:/uploads/";
		String claimDocument= claimDocumentDto.getDocumentImage().getOriginalFilename();
	
//		List<DocumentDto> targetFile= new ArrayList<String>();
		//long userId= user.getUserId();
		
		
		/*String[] targetFile= new String[3];
		targetFile[0]= imageUploadLocation +aadharFile;
		targetFile[1]=imageUploadLocation +drivingLicense;
		targetFile[2]=imageUploadLocation + vehicleDocument;*/
	
		String targetFile = imageUploadLocation + claimDocument;
		
		try {
			FileCopyUtils.copy(claimDocumentDto.getDocumentImage().getInputStream(), new FileOutputStream(targetFile));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}
		
		//System.out.println(aadharFile);
		//System.out.println(drivingLicense);
		//System.out.println(vehicleDocument);
		/*vehicle.setAadharUrl(aadharFile);
		vehicle.setDrivingLicenceUrl(drivingLicense);
		vehicle.setVehicledocumentUrl(vehicleDocument);*/
		claim.setDocumentImageUrl(claimDocument);
		
		claimServices.claimInsurance(claim);
		
		Status status = new Status();
		status.setStatus(StatusType.SUCCESS);
		status.setMessage("Uploaded!");
		return status;
	}
	
	@GetMapping("/claimDocuments")
	public Claim profile(@RequestParam("claimNo") int claimNo, HttpServletRequest request) {
		//fetching customer data from the database
		Claim claim = claimServices.findClaimByClaimId(claimNo);

		//reading the project's deployed folder location
		String projPath = request.getServletContext().getRealPath("/");
		String tempDownloadPath = projPath + "/downloads/";
		//creating a folder within the project where we will place the profile pic of the customer getting fetched
		File f = new File(tempDownloadPath);
		if(!f.exists())
			f.mkdir();
		String targetFile = tempDownloadPath + claim.getDocumentImageUrl();
		
		//the original location where the uploaded images are present
		String uploadedImagesPath = "d:/uploads/";
		String sourceFile = uploadedImagesPath + claim.getDocumentImageUrl();
		
		try {
			FileCopyUtils.copy(new File(sourceFile), new File(targetFile));
		} catch (IOException e) {
			e.printStackTrace();
			//maybe for this customer there is no profile pic
		}
		
		return claim;
	}

	
	
}

