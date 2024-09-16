package com.insurancemanagment.InsuranceManagmentSystem.dto;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class PolicyBooking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String customer_name;
	private long customer_phone;
	private String customer_email;
	private double policy_amount;
	private String time_of_booking;
	private String status;
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private User user;
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Policies policies;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Policies getPolicies() {
		return policies;
	}

	public void setPolicies(Policies policies) {
		this.policies = policies;
	}

	public PolicyBooking(int id, String customer_name, long customer_phone, String customer_email, double policy_amount,
			String time_of_booking, String status) {
		super();
		this.id = id;
		this.customer_name = customer_name;
		this.customer_phone = customer_phone;
		this.customer_email = customer_email;
		this.policy_amount = policy_amount;
		this.time_of_booking = time_of_booking;
		this.status = status;
	}
	
	public PolicyBooking() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public long getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(long customer_phone) {
		this.customer_phone = customer_phone;
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	public double getPolicy_amount() {
		return policy_amount;
	}
	public void setPolicy_amount(double policy_amount) {
		this.policy_amount = policy_amount;
	}
	public String getTime_of_booking() {
		return time_of_booking;
	}
	public void setTime_of_booking(String time_of_booking) {
		this.time_of_booking = time_of_booking;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
