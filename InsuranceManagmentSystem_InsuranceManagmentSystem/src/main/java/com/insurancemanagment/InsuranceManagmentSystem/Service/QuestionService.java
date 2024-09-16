package com.insurancemanagment.InsuranceManagmentSystem.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.insurancemanagment.InsuranceManagmentSystem.Repository.QuestionRepository;
import com.insurancemanagment.InsuranceManagmentSystem.Repository.UserRepository;
import com.insurancemanagment.InsuranceManagmentSystem.dto.Questions;
import com.insurancemanagment.InsuranceManagmentSystem.dto.User;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public ResponseEntity<Questions> addQuestion(int userId,Questions question) {
			Optional<User> user=userRepository.findById(userId);
			if(user.isPresent()) {
				User u=user.get();
				u.getQuestions().add(question);
				question.setUser(u);
				userRepository.save(u);
				questionRepository.save(question);
				return new ResponseEntity<Questions>(HttpStatus.CREATED);
			}
		  return new ResponseEntity<Questions>(HttpStatus.BAD_REQUEST);
		}
	
	
	
	public void answerCustomerQuestion(int questionId, String answer) {
        Questions question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        question.setAnswer(answer);
        questionRepository.save(question);
    }
	
	
	
	
	}