package com.insurancemanagment.InsuranceManagmentSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insurancemanagment.InsuranceManagmentSystem.Service.QuestionService;
import com.insurancemanagment.InsuranceManagmentSystem.dto.Questions;

@RestController
public class QuestionController {

	
	@Autowired
	private QuestionService questionService;
	
//	8. Customer can ask question from admin
	@PostMapping(value = "/question/{userId}")
	public ResponseEntity<Questions>  askQuestion(@PathVariable int userId,@RequestBody Questions question){
		return questionService.addQuestion(userId,question);
	}
	

	@PostMapping("/answerquestion/{questionId}")
    public ResponseEntity<String> answerCustomerQuestion(@PathVariable int questionId, @RequestParam String answer) {
		questionService.answerCustomerQuestion(questionId, answer);
        return ResponseEntity.ok("Answer sent to customer");
    }

}
