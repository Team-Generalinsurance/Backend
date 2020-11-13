package com.lti.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lti.entity.Policy;
import com.lti.entity.User;
import com.lti.entity.Vehicle;
import com.lti.repository.PolicyRepository;
import com.lti.repository.UserRepository;
import com.lti.repository.VehicleRepository;

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	PolicyRepository policyRepository;

	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@Override
	public long buyInsurance(Vehicle vehicle, long userId) {
		User user = userRepository.findUserById(userId);
		System.out.println("In service");
		long vehicleId = vehicleRepository.save(vehicle);
		return vehicleId;
	}

	@Override
	public void mail(long vehicleId, User user) {
		// TODO Auto-generated method stub
		Policy policy = policyRepository.findVehiclePolicy(vehicleId);
		long policyNo = policy.getPolicyNo();
		String text = "Hello " + user.getFname() + "  Successfully Applied for Insurance, Your Policy No is " + policyNo
				+ " It will valid till " + policy.getExpiryDate() + " Your status is " + policy.getStatus();
		String subject = "Insurance Conformation";
		System.out.println(text + " " + subject);
		emailService.sendEmailForNewRegistration(user.getEmail(), text, subject);
	}

	@Override
	public Policy findpolicyByPolicyNo(long policyNo) {
		// TODO Auto-generated method stub
		return policyRepository.findPolicyByPolicyNo(policyNo);
	}

	@Override
	public void renewPolicy(long policyNo, String expiryDate) {
		policyRepository.renewPolicy(policyNo, expiryDate);
	}

	@Override
	public List<Policy> pendingPolicies() {
		// TODO Auto-generated method stub
		return policyRepository.viewAllPendingPolicies();
	}

	@Override
	public void saveRenewPolicy(Policy policy) {
		policyRepository.savePolicy(policy);
	}

	@Override
	public List<Policy> getAllThePoliciesOfAUser(long userId) {
		// TODO Auto-generated method stub
		return policyRepository.viewAllUserpolicy(userId);
	}
	
	@Override
	public void rejectBuyPolicy(long policyNo) {
		// TODO Auto-generated method stub
		policyRepository.rejectPolicy(policyNo);
	}

	@Override
	public void approveBuyPolicy(long policyNo, int term) {
		policyRepository.approvePolicy(policyNo, term);
	}

	@Override
	public Vehicle findVehicleById(long vehicleId) {
		return vehicleRepository.findVehicleById(vehicleId);
	}

	@Override
	public void saveVehicle(Vehicle vehicle) {
		long vehicleId = vehicleRepository.save(vehicle);
	}

	@Override
	public List<Policy> getAllTheApprovedPoliciesOfAUser(long userId) {
		// TODO Auto-generated method stub
		return policyRepository.viewAllUsersApprovedpolicy(userId);
	}

	@Override
	public List<Policy> pendingDocumentPolicies(long userId) {
		// TODO Auto-generated method stub
		return policyRepository.viewAllPendingDocumentPolicies(userId);
	}
	
	@Override
	public List<Policy> getUnapprovedClaimsPolicies(long userId) {
		// TODO Auto-generated method stub
		return policyRepository.viewAllUnapprovedClaimsPolicy(userId);
	}

	@Override
	public void approveBuyInsuranceMail(long policyNo,User user) {

		
		String text = "Hello " + user.getFname() + "  We have successfully approved the Insurance you applied for Policy No " + policyNo;
		String subject = "Insurance Confirmation";
		System.out.println(text + " " + subject);
		emailService.sendEmailForNewRegistration(user.getEmail(), text, subject);
		
	}

	@Override
	public void rejectBuyInsuranceMail(long policyNo,User user) {

		
		String text = "Hello " + user.getFname() + " We regret to inform the cancellation of the Insurance you applied for Policy No: " + policyNo+
				"you can apply for the same policy again with valid credentials.";
		String subject = "Insurance Rejection";
		System.out.println(text + " " + subject);
		emailService.sendEmailForNewRegistration(user.getEmail(), text, subject);
	}

	@Override
	public void mailForRenew(long policyNo, User user) {
		// TODO Auto-generated method stub		
		String text = "Hello " + user.getFname() + "  We have successfully renewed the Insurance you applied for Policy No " + policyNo;
		String subject = "Insurance Renew Confirmation";
		System.out.println(text + " " + subject);
		emailService.sendEmailForNewRegistration(user.getEmail(), text, subject);
	}

	@Override
	public void mailForQuote(String emailId) {
		// TODO Auto-generated method stub
		String text = "Hello customer! Getting your motor vehicle insured, especially against third party liability, is legally mandatory and it also ensures that your finances are cushioned in case of unforeseen circumstances. ICICI Lombard offers motor insurance for the mandatory aspects as well as additional useful covers.\r\n"
				+ "\r\n"
				+ "Our policies provide comprehensive coverage and utmost protection for your vehicle while the prompt and efficient service lessens your worries during trying times. With the add-on covers, your policy can be tailored to suit your future needs and requirements.";
		String subject = "Insurance Renew Confirmation";
		System.out.println(text + " " + subject);
		emailService.sendEmailForNewRegistration(emailId, text, subject);
	}
}