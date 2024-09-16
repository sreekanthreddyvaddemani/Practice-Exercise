package com.insurancemanagment.InsuranceManagmentSystem.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Questions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int questionId;
	private String customerName;
	private String question;
	private String  answer;
    //private String time;
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private User user;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Questions() {
		
	}
	
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	

	public Questions(int questionId, String customerName, String question, String answer) {
		super();
		this.questionId = questionId;
		this.customerName = customerName;
		this.question = question;
		this.answer = answer;
	}	
	
}
