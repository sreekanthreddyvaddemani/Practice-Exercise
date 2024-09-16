package com.insurancemanagment.InsuranceManagmentSystem.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.insurancemanagment.InsuranceManagmentSystem.Repository.AdminRepository;
import com.insurancemanagment.InsuranceManagmentSystem.Repository.UserRepository;
import com.insurancemanagment.InsuranceManagmentSystem.dto.Admin;

@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<Admin> saveAdmin(Admin admin) {
		adminRepository.save(admin);
		return new ResponseEntity<Admin>(HttpStatus.CREATED);
	}

	public ResponseEntity<Admin> updateAdmin(Admin admin) {
		adminRepository.save(admin);
		return new ResponseEntity<Admin>(HttpStatus.OK);
	}

	public ResponseEntity<Admin> findbyid(int id) {

		return null;
	}

	public ResponseEntity<String> deleteByUSerId(Integer id) {

		userRepository.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<Admin> verifyAdmin(String email, String password) {
		Optional<Admin> a = adminRepository.findByEmailAndPassword(email, password);
		if (a.isPresent()) {
			Admin admin = a.get();
			return new ResponseEntity<Admin>(admin, HttpStatus.OK);
		}
		return new ResponseEntity<Admin>(HttpStatus.BAD_REQUEST);
	}
}
