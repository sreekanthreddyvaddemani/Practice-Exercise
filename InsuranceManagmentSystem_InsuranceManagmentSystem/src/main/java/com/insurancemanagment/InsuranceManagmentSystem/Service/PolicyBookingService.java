package com.insurancemanagment.InsuranceManagmentSystem.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.insurancemanagment.InsuranceManagmentSystem.Repository.PoliciesRepository;
import com.insurancemanagment.InsuranceManagmentSystem.Repository.PolicyBookingRepository;
import com.insurancemanagment.InsuranceManagmentSystem.Repository.UserRepository;
import com.insurancemanagment.InsuranceManagmentSystem.dto.Policies;
import com.insurancemanagment.InsuranceManagmentSystem.dto.PolicyBooking;
import com.insurancemanagment.InsuranceManagmentSystem.dto.User;
@Service
public class PolicyBookingService  {
	
	@Autowired
	private PolicyBookingRepository policyBookingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PoliciesRepository policiesRepository;

	public ResponseEntity<PolicyBooking> policyRegister(int userId, int policyId) {
		Optional<User> user=userRepository.findById(userId);
		Optional<Policies> policyDetails=policiesRepository.findById(policyId);
		if(user.isPresent() && policyDetails.isPresent()) {
			PolicyBooking pb=new PolicyBooking();
			User u=user.get();
			Policies p=policyDetails.get();
			pb.setCustomer_name(u.getName());
			pb.setCustomer_email(u.getEmail());
			pb.setPolicy_amount(p.getPremiumAmount());
			pb.setTime_of_booking("12-12-1234");
			pb.setStatus("PENDING");
			u.getPolicyBookings().add(pb);
			p.getPolicybooking().add(pb);
			pb.setUser(u);
			pb.setPolicies(p);
			userRepository.save(u);
			policiesRepository.save(p);
			return new ResponseEntity<PolicyBooking>(HttpStatus.CREATED);
		}
	  return new ResponseEntity<PolicyBooking>(HttpStatus.BAD_REQUEST);
	}
	

	public ResponseEntity<List<PolicyBooking>> getAllPolicyBookings() {
		List<PolicyBooking> listPolicyBooking = policyBookingRepository.findAll();
		System.out.println(listPolicyBooking);
		return new ResponseEntity<List<PolicyBooking>>(listPolicyBooking, HttpStatus.OK);
	}


	public ResponseEntity<Map<String, Integer>> getPolicyStatistics() {
		Map<String, Integer> policiesData=new HashMap();
		policiesData.put("totalHolders", policyBookingRepository.countByStatus("APPROVED"));
		policiesData.put("disapprovedHolders", policyBookingRepository.countByStatus("DISAPPROVED"));
		policiesData.put("totalApplications", (int) policyBookingRepository.count());
        return new ResponseEntity<Map<String,Integer>>(policiesData,HttpStatus.OK);
    }
	
	
}
