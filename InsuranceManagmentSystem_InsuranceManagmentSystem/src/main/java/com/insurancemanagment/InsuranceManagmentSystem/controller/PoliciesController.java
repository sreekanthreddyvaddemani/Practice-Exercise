package com.insurancemanagment.InsuranceManagmentSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insurancemanagment.InsuranceManagmentSystem.Service.PoliciesService;
import com.insurancemanagment.InsuranceManagmentSystem.dto.Policies;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PoliciesController {

	@Autowired
	 private PoliciesService policiesService;
	
	
		@PostMapping(value = "polices/addpolices/{adminId}")
		public ResponseEntity<Policies > addPolices(@PathVariable int  adminId,@RequestBody Policies policy) {
			return policiesService.addPolices(adminId,policy);
		}
		
//  4   View all policy that are added by admin
	@GetMapping(value = "/polices")
	public ResponseEntity<List<Policies>> getAllPolices() {
		return policiesService.getAllPolices();
	}

	@PutMapping("/update-policy/{policyId}")
    public ResponseEntity<String> updatePolicy(@PathVariable int policyId, @RequestBody Policies updatedPolicy) {
        return policiesService.updatePolicy(policyId, updatedPolicy);
    }
	
	
	@DeleteMapping("/deletepolicy/{policyId}")
    public ResponseEntity<String> deletePolicy(@PathVariable int policyId) {
        return policiesService.deletePolicy(policyId);
    }
}
