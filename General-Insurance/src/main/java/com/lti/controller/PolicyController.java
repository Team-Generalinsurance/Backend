package com.lti.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusType;
import com.lti.entity.Policy;
import com.lti.entity.User;
import com.lti.entity.Vehicle;
import com.lti.exception.UserServiceException;
import com.lti.dto.AdminPendingInsurance;
import com.lti.dto.BuyInsuranceDto;
import com.lti.dto.DocumentDto;
import com.lti.dto.PendingAllBuyInsuranceDto;
import com.lti.dto.RenewPolicyDto;
import com.lti.service.PolicyService;
import com.lti.service.UserService;

@RestController
@CrossOrigin
public class PolicyController {
	
	@Autowired
	PolicyService policyService;
	
	@Autowired
	UserService userService;
	
	@PostMapping(path="/buyInsurance",consumes="application/json")
	public Status BuyInsurance(@RequestBody BuyInsuranceDto buyInsuranceDto)
	{
		
	/*	String imageUploadLocation = "d:/uploads/";
		String aadharFile = buyInsuranceDto.getAadhar().getOriginalFilename();
		String drivingLicense= buyInsuranceDto.getDrivingLicenceDocument().getOriginalFilename();
		String vehicleDocument= buyInsuranceDto.getVehicleDocument().getOriginalFilename();
		

		String[] targetFile= new String[3];
		targetFile[0]= imageUploadLocation +aadharFile;
		targetFile[1]=imageUploadLocation +drivingLicense;
		targetFile[2]=imageUploadLocation + vehicleDocument;
		
			try {
				FileCopyUtils.copy(buyInsuranceDto.getAadhar().getInputStream(), new FileOutputStream(targetFile[0]));
				FileCopyUtils.copy(buyInsuranceDto.getDrivingLicenceDocument().getInputStream(), new FileOutputStream(targetFile[1]));
				FileCopyUtils.copy(buyInsuranceDto.getVehicleDocument().getInputStream(), new FileOutputStream(targetFile[2]));
			
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
*/
	
		
		Vehicle vehicle= new Vehicle();
		vehicle.setChassisNo(buyInsuranceDto.getChassisNo());
		vehicle.setRegistrationNo(buyInsuranceDto.getRegistrationNo());
		System.out.println(buyInsuranceDto.getRegistrationNo());
		vehicle.setDrivingLicence(buyInsuranceDto.getDrivingLicence());
		vehicle.setEngineNo(buyInsuranceDto.getEngineNo());
		vehicle.setManufacturer(buyInsuranceDto.getManufacturer());
		vehicle.setModel(buyInsuranceDto.getModel());
		vehicle.setPrice(buyInsuranceDto.getPrice());
		vehicle.setPurchasedDate(buyInsuranceDto.getPurchasedDate());
		vehicle.setVehicleType(buyInsuranceDto.getVehicleType());
		//vehicle.setAadharUrl(aadharFile);
		//vehicle.setDrivingLicenceUrl(drivingLicense);
		//vehicle.setVehicledocumentUrl(vehicleDocument);
		
		User user= userService.findUserById(buyInsuranceDto.getUserId());
		
		Policy policy= new Policy();
		policy.setPolicyType(buyInsuranceDto.getPolicyType());
		policy.setStatus("Pending");
		policy.setTerm(buyInsuranceDto.getTerm());
		//policy.setExpiryDate("2030-09-09");
		
		vehicle.setPolicy(policy);

		policy.setVehicle(vehicle);

//		System.out.println(vehicle);
		policy.setUser(user);


//		System.out.println(policy);
		try {
			long vehicleId=policyService.buyInsurance(vehicle,user.getUserId());

			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Buy Policy successful!");
			policyService.mail(vehicleId,user);
			
			/*Vehicle updatedVehicle= policyService.findVehicleById(vehicleId);
			
			updatedVehicle.setAadharUrl(aadharFile);
			updatedVehicle.setDrivingLicenceUrl(drivingLicense);
			updatedVehicle.setVehicledocumentUrl(vehicleDocument);
			
			policyService.saveVehicle(updatedVehicle);*/

			return status;
		} catch (UserServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}

	}
	
	@GetMapping(path="/pendingBuyInsurance")
	public List<AdminPendingInsurance> viewPendingPolicies()
	{
		List<AdminPendingInsurance> pendingInsurance=new ArrayList<AdminPendingInsurance>();
		AdminPendingInsurance d;
		List<Policy> pendingPolicies= policyService.pendingPolicies();
		for(Policy p:pendingPolicies)
		{
			d=new AdminPendingInsurance();
			d.setPolicyNo(p.getPolicyNo());
			d.setVehicleId(p.getVehicle().getVehicleId());
			d.setPolicyType(p.getPolicyType());
			d.setChassisNo(p.getVehicle().getChassisNo());
			d.setEngineNo(p.getVehicle().getEngineNo());
			d.setRegistrationNo(p.getVehicle().getRegistrationNo());
			d.setModel(p.getVehicle().getModel());
			d.setManufacturer(p.getVehicle().getManufacturer());
			d.setVehicleType(p.getVehicle().getVehicleType());
			pendingInsurance.add(d);
		}
		for(AdminPendingInsurance p:pendingInsurance)
		{
			System.out.println(p.getPolicyNo());
		}
		return pendingInsurance;
	}
	@PostMapping(path="/approveBuyInsurance")
	public Status approveBuyInsurance(long policyNo)
	{
		Policy policy=policyService.findpolicyByPolicyNo(policyNo);
		User user=policy.getUser();
		int term=policy.getTerm();
		try {
			policyService.approveBuyPolicy(policyNo,term);

			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Approval successful!");
			policyService.approveBuyInsuranceMail(policyNo,user);
			return status;
		} catch (UserServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}
	}
	@PostMapping(path="/rejectBuyInsurance")
	public Status rejectBuyInsurance(long policyNo)
	{
		Policy policy=policyService.findpolicyByPolicyNo(policyNo);
		User user=policy.getUser();
		System.out.println(policyNo);
		try {
			policyService.rejectBuyPolicy(policyNo);

			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("you request for buying an insurance has been rejected!");
			policyService.rejectBuyInsuranceMail(policyNo,user);
			return status;
		} catch (UserServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}
	}
	@PostMapping(path="/renewInsurance")
	public Status renewPolicy( @RequestBody RenewPolicyDto renewPolicyDto)
	{
		
		try {
			Policy policy= policyService.findpolicyByPolicyNo(renewPolicyDto.getPolicyNo());
			LocalDate expdate=policy.getExpiryDate();
			int term=renewPolicyDto.getTerm();
			LocalDate renewalDate=expdate.plusYears(term);
			policy.setExpiryDate(renewalDate);
			policyService.saveRenewPolicy(policy);
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Renew successful!");
			policyService.mailForRenew(policy.getPolicyNo(),policy.getUser());
			System.out.println("Term "+renewPolicyDto.getTerm());
			return status;
		} 
		catch (UserServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}	
	}
	
	@PostMapping("/documentUpload")
	public Status documentUpload( DocumentDto documentDto)
	{
		//User user= userService.findUserById(documentDto.getUserId());
		long policyNo=documentDto.getPolicyNo();
		Policy policy= policyService.findpolicyByPolicyNo(policyNo);
		
		
		
		Vehicle vehicle= policy.getVehicle();
		
		String imageUploadLocation = "d:/uploads/";
		String aadharFile = documentDto.getAadhar().getOriginalFilename();
		String drivingLicense= documentDto.getDrivingLicence().getOriginalFilename();
		String vehicleDocument= documentDto.getVehicleDocument().getOriginalFilename();
	
//		List<DocumentDto> targetFile= new ArrayList<String>();
		//long userId= user.getUserId();
		
		
		String[] targetFile= new String[3];
		targetFile[0]= imageUploadLocation +aadharFile;
		targetFile[1]=imageUploadLocation +drivingLicense;
		targetFile[2]=imageUploadLocation + vehicleDocument;
	
//		String targetFile = imageUploadLocation + fileName;
		for(String s:targetFile)
		{
			System.out.println("Target File");
			System.out.println(s);
		}
		
		try {
			FileCopyUtils.copy(documentDto.getAadhar().getInputStream(), new FileOutputStream(targetFile[0]));
			FileCopyUtils.copy(documentDto.getDrivingLicence().getInputStream(), new FileOutputStream(targetFile[1]));
			FileCopyUtils.copy(documentDto.getVehicleDocument().getInputStream(), new FileOutputStream(targetFile[2]));

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
		vehicle.setAadharUrl(aadharFile);
		vehicle.setDrivingLicenceUrl(drivingLicense);
		vehicle.setVehicledocumentUrl(vehicleDocument);
		
		policyService.saveVehicle(vehicle);
		
		Status status = new Status();
		status.setStatus(StatusType.SUCCESS);
		status.setMessage("Uploaded!");
		return status;
	}
	
	@GetMapping("/viewAllDocuments")
	public Vehicle document(@RequestParam("vehicleId") int vehicleId, HttpServletRequest request) {
		//User user = userService.get(userId);
		System.out.println("vehicleId-"+vehicleId);
		Vehicle vehicle= policyService.findVehicleById(vehicleId);
		System.out.println("AadharUrl-"+vehicle.getAadharUrl());
		System.out.println("vehicleId- "+vehicle.getVehicleId());
	//	List<Policy> policies=policyService.getAllThePoliciesOfAUser(userId);
	//	for(Policy singlepolicy :policies) 
	//	{
			//long policyNo=p.getPolicyNo();
			//Policy singlepolicy=policyService.findpolicyByPolicyNo(policyNo);
			String projectPath = request.getServletContext().getRealPath("/");
			String tempDownloadPath = projectPath + "/downloads/";
			System.out.println(tempDownloadPath);
			File f = new File(tempDownloadPath);
			
			if(!f.exists())
			f.mkdir();
			String targetFile[]=new String[3];
			targetFile[0] = tempDownloadPath + vehicle.getAadharUrl();
			targetFile[1] = tempDownloadPath + vehicle.getDrivingLicenceUrl();
			targetFile[2] = tempDownloadPath + vehicle.getVehicledocumentUrl();
			
			String uploadedImagesPath = "d:/uploads/";				//-------same as uploadimage path in doc upload--------------????????
			String sourceFile[]=new String[3];
			sourceFile[0] = uploadedImagesPath + vehicle.getAadharUrl();
			sourceFile[1] = uploadedImagesPath + vehicle.getDrivingLicenceUrl();
			sourceFile[2] = uploadedImagesPath + vehicle.getVehicledocumentUrl();
			try {
				FileCopyUtils.copy(new File(sourceFile[0]), new File(targetFile[0]));
				FileCopyUtils.copy(new File(sourceFile[1]), new File(targetFile[1]));
				FileCopyUtils.copy(new File(sourceFile[2]), new File(targetFile[2]));
			} catch (IOException e) {
			e.printStackTrace();
			}
//		}
		return vehicle;
	}

}