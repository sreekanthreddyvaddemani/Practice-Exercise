
package com.insurancemanagment.InsuranceManagmentSystem.controller;

import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insurancemanagment.InsuranceManagmentSystem.Repository.UserRepository;
import com.insurancemanagment.InsuranceManagmentSystem.Service.UserService;
import com.insurancemanagment.InsuranceManagmentSystem.dto.Admin;
import com.insurancemanagment.InsuranceManagmentSystem.dto.PolicyBooking;
import com.insurancemanagment.InsuranceManagmentSystem.dto.User;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	private UserService userService;
    
//	1
	@PostMapping(value = "/user/register")
	public ResponseEntity<User> saveAdmin(@RequestBody User user) {
		return userService.saveUser(user);
	}
	

	@PostMapping(value = "/user/login")
	public ResponseEntity<User> verifyUser(@RequestParam String email,
			@RequestParam String password) {
		System.out.println("exicuted");
		return userService.verifyUser(email, password);
	}
	
	
//	2.
	@PutMapping("/changepassword/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable int userId, @RequestParam String password) {
		userService.changePassword(userId, password);
        return ResponseEntity.ok("Password updated successfully!");
    }
	
//   3. Change address, email, and mobile
    @PutMapping("/updateprofile/{customerId}")
    public ResponseEntity<String> updateProfile(@PathVariable int customerId, @RequestBody User updatedDetails) {
    	userService.updateProfile(customerId, updatedDetails);
        return ResponseEntity.ok("Profile updated successfully!");
    }
    
	
 //7.Customer can check status of his policy under history section.
	@GetMapping(value = "/policybookings/{userid}")
	public ResponseEntity<List<PolicyBooking>> getAllPolices(@PathVariable int userid) {
		return userService.getAllPolicyBookings(userid);
	}
	
	@GetMapping(value = "/userquestions/{userid}")
	public ResponseEntity<List<Question>> getQuestions(@PathVariable int userid) {
		return userService.getQuestions(userid);
	}
	
	
	@DeleteMapping("/deletecustomer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {
        return userService.deleteCustomer(customerId);
    }
	
	
	@GetMapping(value = "/user/allusers")
	public ResponseEntity<List<User>> getUsers() {
		return userService.getAllUsers();
	}

	
	
}
