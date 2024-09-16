package com.insurancemanagment.InsuranceManagmentSystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insurancemanagment.InsuranceManagmentSystem.Service.PolicyBookingService;
import com.insurancemanagment.InsuranceManagmentSystem.dto.PolicyBooking;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PolicyBookingController {

	
	@Autowired
	private PolicyBookingService policyBookingService;
	
	
    // 5. Apply for a policy
	@PostMapping(value = "/policybook/register/{userId}/{policyId}")
	public ResponseEntity<PolicyBooking>  policyRegister(@PathVariable int userId,@PathVariable int policyId){
		return policyBookingService.policyRegister(userId,policyId);
	}
	
	@GetMapping(value = "/getpolicystatistics")
	public ResponseEntity<Map<String, Integer>> getPolicyStatistics() {
        return policyBookingService.getPolicyStatistics();
}
	
	@GetMapping(value = "/policybook/getpolicybookings")
	public ResponseEntity<List<PolicyBooking>> getAllPolices() {
		return policyBookingService.getAllPolicyBookings();
	}
	
	
}
