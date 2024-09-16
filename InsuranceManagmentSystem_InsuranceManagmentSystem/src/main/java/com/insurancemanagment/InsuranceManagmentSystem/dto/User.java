package com.insurancemanagment.InsuranceManagmentSystem.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

//@Data
//@Getter
//@Setter
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private long phone;
	private LocalDate dob;
	private String email;
	private String address;

	private long adhaar;
	@Column(nullable = false)
	private String password;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PolicyBooking> policyBookings;
	@OneToMany(mappedBy = "user")
    private List<Questions> questions;

	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}

	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getAdhaar() {
		return adhaar;
	}

	public void setAdhaar(long adhaar) {
		this.adhaar = adhaar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<PolicyBooking> getPolicyBookings() {
		return policyBookings;
	}

	public void setPolicyBookings(List<PolicyBooking> policyBookings) {
		this.policyBookings = policyBookings;
	}

	public User(int id, String name, long phone, LocalDate dob, String email, long adhaar, String password) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.dob = dob;
		this.email = email;
		this.adhaar = adhaar;
		this.password = password;
	}

}