package com.insurancemanagment.InsuranceManagmentSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insurancemanagment.InsuranceManagmentSystem.Service.AdminService;
import com.insurancemanagment.InsuranceManagmentSystem.dto.Admin;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/admin")
public class AdminController {
	@Autowired
 private AdminService adminService;
	
	@PostMapping(value = "/admin/add")
	public ResponseEntity<Admin> saveAdmin(@RequestBody Admin admin) {
		return adminService.saveAdmin(admin);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) {
		return adminService.updateAdmin(admin);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Admin> findById(@PathVariable int id) {
		return adminService.findbyid(id);
	}

	@PostMapping(value = "/admin/login")
	public ResponseEntity<Admin> verifyAdmin(@RequestParam String email,
			@RequestParam String password) {
		System.out.println("exicuted");
		return adminService.verifyAdmin(email, password);
	}
	

	@DeleteMapping(value = "/admin/deletebyuserid/{id}")
	public ResponseEntity<String> deleteByUSerId(@PathVariable Integer id) {
		adminService.deleteByUSerId(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
