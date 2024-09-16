package com.insurancemanagment.InsuranceManagmentSystem.Service;

import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.insurancemanagment.InsuranceManagmentSystem.Repository.UserRepository;
import com.insurancemanagment.InsuranceManagmentSystem.dto.Admin;
import com.insurancemanagment.InsuranceManagmentSystem.dto.Policies;
import com.insurancemanagment.InsuranceManagmentSystem.dto.PolicyBooking;
import com.insurancemanagment.InsuranceManagmentSystem.dto.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<User> saveUser(User user) {
		userRepository.save(user);
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}
	
	public ResponseEntity<User> verifyUser(String email, String password) {
		Optional<User> a = userRepository.findByEmailAndPassword(email, password);
		if (a.isPresent()) {
			User user = a.get();
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<PolicyBooking>> getAllPolicyBookings(int id) {
		List<PolicyBooking> listPolicyBooking = userRepository.findPolicyBookings(id);
		return new ResponseEntity<List<PolicyBooking>>(listPolicyBooking, HttpStatus.OK);
	}
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> listUser = userRepository.findAll();
		return new ResponseEntity<List<User>>(listUser, HttpStatus.OK);
	}

	public ResponseEntity<List<Question>> getQuestions(int userid) {
		List<Question> listQuestion = userRepository.findQuestionsByUserId(userid);
		return new ResponseEntity<List<Question>>(listQuestion, HttpStatus.OK);	}

	
	
	public ResponseEntity<User> changePassword(int customerId, String newPassword) {
		User user = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        user.setPassword(newPassword); 
        userRepository.save(user);
		return new ResponseEntity<User>( HttpStatus.OK);	
		}

	public ResponseEntity<User> updateProfile(int userId, User updatedDetails) {
			User user = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User  not found"));
			user.setAddress(updatedDetails.getAddress());
			user.setEmail(updatedDetails.getEmail());
			user.setPhone(updatedDetails.getPhone());
	        userRepository.save(user);
	        return new ResponseEntity<User>(HttpStatus.OK);
	}
	
	
//	public ResponseEntity<User> updateUser(int userId, User updatedDetails) {
//		Optional<User> user = userRepository.findById(userId);
//		if(user.isPresent()) {
//			User u=user.get();
//			List<PolicyBooking> 
//			return new ResponseEntity<String>(HttpStatus.OK);
//	    }else {
//			return new ResponseEntity<String>("User  Not Found",HttpStatus.BAD_REQUEST);
//	    }
//		
//		user.setAddress(updatedDetails.getAddress());
//		user.setEmail(updatedDetails.getEmail());
//		user.setPhone(updatedDetails.getPhone());
//        userRepository.save(user);
//        return new ResponseEntity<User>(HttpStatus.OK);
//}

	public  ResponseEntity<String> deleteCustomer(int userId) {
		Optional<User> u=userRepository.findById(userId);
		if(u.isPresent()) {
			userRepository.delete(u.get());
			return new ResponseEntity<String>(HttpStatus.OK);
	    }else {
			return new ResponseEntity<String>("User  Not Found",HttpStatus.BAD_REQUEST);
	    }
	}
}
